package com.rc.leatherback.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.rc.leatherback.data.DatabaseContext;
import com.rc.leatherback.data.PrescriptionDao;
import com.rc.leatherback.data.PrescriptionDetailDao;
import com.rc.leatherback.exception.PrescriptionNotFoundException;
import com.rc.leatherback.model.Prescription;
import com.rc.leatherback.model.PrescriptionDetail;
import com.rc.leatherback.model.User;

public class PrescriptionService {

    private PrescriptionDao dao;
    private PrescriptionDetailDao detailDao;

    public PrescriptionService() {
        this.dao = new PrescriptionDao();
        this.detailDao = new PrescriptionDetailDao();
    }

    public Prescription getPrescriptionById(long id) throws ClassNotFoundException, SQLException {
        try (Connection connection = DatabaseContext.getConnection()) {
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
        try (Connection connection = DatabaseContext.getConnection()) {
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

    // public List<Prescription> findPrescriptionsByLotNumber(String lotNumber, int pageIndex, int
    // pageSize)
    // throws ClassNotFoundException, SQLException {
    //
    // try (Connection connection = DatabaseContext.getConnection()) {
    // int limit = pageSize;
    // int offset = pageSize * (pageIndex - 1);
    // List<Prescription> prescriptions = dao.findByLotNumberPagination(connection, lotNumber,
    // limit, offset);
    // for (Prescription prescription : prescriptions) {
    // List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection,
    // prescription.getId());
    // prescription.setDetails(details);
    // }
    //
    // return prescriptions;
    // }
    // }
    //
    // public List<Prescription> findPrescriptionsByPartNumber(String partNumber, int pageIndex, int
    // pageSize)
    // throws ClassNotFoundException, SQLException {
    //
    // try (Connection connection = DatabaseContext.getConnection()) {
    // int limit = pageSize;
    // int offset = pageSize * (pageIndex - 1);
    // List<Prescription> prescriptions = dao.findByPartNumberPagination(connection, partNumber,
    // limit, offset);
    // for (Prescription prescription : prescriptions) {
    // List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection,
    // prescription.getId());
    // prescription.setDetails(details);
    // }
    //
    // return prescriptions;
    // }
    // }

    public void addPrescription(User loggedInUser, Prescription prescription) throws SQLException, ClassNotFoundException {
        Date createDate = new Date();

        prescription.setPartNumber(String.format("%s-%s", prescription.getPartNumberHead(), prescription.getPartNumberBody()));
        prescription.setCreatedBy(loggedInUser.getName());
        prescription.setCreatedDate(createDate);
        prescription.setModifiedBy(loggedInUser.getName());
        prescription.setModifiedDate(createDate);

        Connection connection = DatabaseContext.getConnection();
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

        Connection connection = DatabaseContext.getConnection();
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
        Connection connection = DatabaseContext.getConnection();
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
        try (Connection connection = DatabaseContext.getConnection()) {
            return dao.countAll(connection);
        }
    }
}
