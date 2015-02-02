package com.rc.leatherback.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.lang3.time.DateUtils;

import com.rc.leatherback.data.PrescriptionDao;
import com.rc.leatherback.data.PrescriptionDetailDao;
import com.rc.leatherback.exception.PrescriptionNotFoundException;
import com.rc.leatherback.model.Prescription;
import com.rc.leatherback.model.PrescriptionDetail;
import com.rc.leatherback.model.User;
import com.rc.leatherback.model.report.PrescriptionDetailReport;
import com.rc.leatherback.model.report.PrescriptionReport;

public class PrescriptionService {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/leatherback_dev";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rockey.chen";

    private PrescriptionDao dao;
    private PrescriptionDetailDao detailDao;

    public PrescriptionService() {
        this.dao = new PrescriptionDao();
        this.detailDao = new PrescriptionDetailDao();
    }

    public Prescription getPrescriptionById(long id) throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            Prescription prescription = dao.getById(connection, id);
            if (prescription == null) {
                throw new PrescriptionNotFoundException(String.format("Prescription is not found by id: %s", id));
            }

            List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection, prescription.getId());
            prescription.setDetails(details);

            return prescription;
        }
    }

    public List<Prescription> findPrescriptionsByPage(int pageIndex, int pageSize) throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            int limit = pageSize;
            int offset = pageSize * (pageIndex - 1);
            List<Prescription> prescriptions = dao.findAllPagination(connection, limit, offset);
            for (Prescription prescription : prescriptions) {
                List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection, prescription.getId());
                prescription.setDetails(details);
            }

            return prescriptions;
        }
    }

    public List<Prescription> findPrescriptionsByLotNumber(String lotNumber, int pageIndex, int pageSize)
                    throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            int limit = pageSize;
            int offset = pageSize * (pageIndex - 1);
            List<Prescription> prescriptions = dao.findByLotNumberPagination(connection, lotNumber, limit, offset);
            for (Prescription prescription : prescriptions) {
                List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection, prescription.getId());
                prescription.setDetails(details);
            }

            return prescriptions;
        }
    }

    public List<Prescription> findPrescriptionsByPartNumber(String partNumber, int pageIndex, int pageSize)
                    throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            int limit = pageSize;
            int offset = pageSize * (pageIndex - 1);
            List<Prescription> prescriptions = dao.findByPartNumberPagination(connection, partNumber, limit, offset);
            for (Prescription prescription : prescriptions) {
                List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection, prescription.getId());
                prescription.setDetails(details);
            }

            return prescriptions;
        }
    }

    public void addPrescription(User loggedInUser, Prescription prescription) throws SQLException, ClassNotFoundException {
        Date createDate = new Date();

        prescription.setPartNumber(String.format("%s-%s", prescription.getPartNumberHead(), prescription.getPartNumberBody()));
        prescription.setCreatedBy(loggedInUser.getName());
        prescription.setCreatedDate(createDate);
        prescription.setModifiedBy(loggedInUser.getName());
        prescription.setModifiedDate(createDate);

        Class.forName(DB_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        try {
            connection.setAutoCommit(false);
            dao.add(connection, prescription);
            for (PrescriptionDetail detail : prescription.getDetails()) {
                detail.setPrescriptionId(prescription.getId());
                detail.setCreatedBy(loggedInUser.getName());
                detail.setCreatedDate(createDate);
                detail.setModifiedBy(loggedInUser.getName());
                detail.setModifiedDate(createDate);

                detailDao.add(connection, detail);
            }
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();

            throw exception;
        } finally {
            connection.close();
        }
    }

    public void updatePrescription(User loggedInUser, long id, Prescription prescription) throws SQLException,
                    ClassNotFoundException {
        Date modifyDate = new Date();

        prescription.setPartNumber(String.format("%s-%s", prescription.getPartNumberHead(), prescription.getPartNumberBody()));
        prescription.setModifiedBy(loggedInUser.getName());
        prescription.setModifiedDate(modifyDate);

        Class.forName(DB_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        try {
            connection.setAutoCommit(false);

            // Check whether parameter id is valid
            Prescription original = dao.getById(connection, id);
            if (original == null) {
                throw new PrescriptionNotFoundException(String.format("Prescription is not found by id: %s", id));
            }
            // Update prescription
            prescription.setId(original.getId());
            dao.update(connection, prescription);

            // The approach for updating details is to first delete the details
            // of the prescription,
            // and then add the new details of the prescription.
            detailDao.removeByPrescriptionId(connection, original.getId());
            for (PrescriptionDetail detail : prescription.getDetails()) {
                detail.setPrescriptionId(prescription.getId());
                // Use prescription's creation information to act as the
                // original data
                detail.setCreatedBy(original.getCreatedBy());
                detail.setCreatedDate(original.getCreatedDate());
                detail.setModifiedBy(loggedInUser.getName());
                detail.setModifiedDate(modifyDate);

                detailDao.add(connection, detail);
            }

            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();

            throw exception;
        } finally {
            connection.close();
        }
    }

    public void deletePrescription(long id) throws SQLException, ClassNotFoundException {
        Class.forName(DB_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        try {
            connection.setAutoCommit(false);

            // Check whether parameter id is valid
            Prescription original = dao.getById(connection, id);
            if (original == null) {
                throw new PrescriptionNotFoundException(String.format("Prescription is not found by id: %s", id));
            }
            // Delete prescription
            dao.remove(connection, original.getId());
            // Delete details
            List<PrescriptionDetail> originalDetails = detailDao.findByPrescriptionId(connection, original.getId());
            for (PrescriptionDetail originalDetail : originalDetails) {
                detailDao.remove(connection, originalDetail.getId());
            }

            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();

            throw exception;
        } finally {
            connection.close();
        }
    }

    public int getTotalNumberOfPrescriptions() throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            return dao.countAll(connection);
        }
    }

    public File generateReport(String reportLocation) throws JRException, IOException {
        // load JasperDesign from XML and compile it into JasperReport
        JasperDesign jasperDesign = JRXmlLoader.load(reportLocation + "/report.jrxml");
        JasperDesign jasperSubDesign = JRXmlLoader.load(reportLocation + "/sub_report.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperReport jasperSubReport = JasperCompileManager.compileReport(jasperSubDesign);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("SubReportParam", jasperSubReport);


        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(getPrescriptions());
        // fill JasperPrint using fillReport() method
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

        File report = File.createTempFile("report-", ".pdf");
        JasperExportManager.exportReportToPdfFile(jasperPrint, report.getPath());


        return report;
    }

    private List<PrescriptionReport> getPrescriptions() {
        List<PrescriptionReport> prescriptions = new ArrayList<PrescriptionReport>();
        PrescriptionReport prescription = new PrescriptionReport();
        prescription.setLotNumber("20070101001");
        prescription.setDate(new Date());
        prescription.setPartNumber("TS-ABCD001");
        prescription.setTotalAmount(15.8);
        prescription.setTotalPrice(12.321);
        prescription.setAverageCost(283.2822);

        List<PrescriptionDetailReport> details = new ArrayList<PrescriptionDetailReport>();
        PrescriptionDetailReport detail = new PrescriptionDetailReport();
        detail.setItem(1);
        detail.setPrescription("AAA");
        detail.setAmount(3);
        detail.setPrice(4.2);
        detail.setNote("TEST1");
        details.add(detail);

        detail = new PrescriptionDetailReport();
        detail.setItem(2);
        detail.setPrescription("BBB");
        detail.setAmount(4);
        detail.setPrice(5.2);
        detail.setNote("TEST2");
        details.add(detail);

        prescription.setDetails(details);
        prescriptions.add(prescription);

        prescription = new PrescriptionReport();
        prescription.setLotNumber("20070101002");
        prescription.setDate(DateUtils.addDays(new Date(), 10));
        prescription.setPartNumber("TS-ABCD002");
        prescription.setTotalAmount(35.8);
        prescription.setTotalPrice(32.321);
        prescription.setAverageCost(383.2822);
        prescriptions.add(prescription);

        return prescriptions;
    }
}
