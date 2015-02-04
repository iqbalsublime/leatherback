package com.rc.leatherback.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.rc.leatherback.data.PrescriptionDao;
import com.rc.leatherback.data.PrescriptionDetailDao;
import com.rc.leatherback.facade.dto.ReportQuery;
import com.rc.leatherback.model.Prescription;
import com.rc.leatherback.model.PrescriptionDetail;
import com.rc.leatherback.model.report.PrescriptionDetailReport;
import com.rc.leatherback.model.report.PrescriptionReport;

public class ReportService {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/leatherback_dev";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "rockey.chen";

	private PrescriptionDao prescriptionDao;
	private PrescriptionDetailDao detailDao;

	public ReportService() {
		this.prescriptionDao = new PrescriptionDao();
		this.detailDao = new PrescriptionDetailDao();
	}

	public List<Prescription> query(ReportQuery reportQuery, int pageIndex, int pageSize) throws ClassNotFoundException,
			SQLException {

		int searchBy = determinateQueryCondition(reportQuery);

		Class.forName(DB_DRIVER);
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			int limit = pageSize;
			int offset = pageSize * (pageIndex - 1);
			List<Prescription> prescriptions = new ArrayList<Prescription>();

			switch (searchBy) {
			case 1:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumber(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumber(), limit, offset);
				break;
			case 2:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumberHead(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumberHead(), limit, offset);
				break;
			case 3:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumberBody(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumberBody(), limit, offset);
				break;

			}

			for (Prescription prescription : prescriptions) {
				List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection, prescription.getId());
				prescription.setDetails(details);
			}

			return prescriptions;
		}
	}

	public int getTotalNumberOfQueryResult(ReportQuery reportQuery, int pageIndex, int pageSize) throws ClassNotFoundException,
			SQLException {

		int countResult = 0;
		int searchBy = determinateQueryCondition(reportQuery);

		Class.forName(DB_DRIVER);
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			switch (searchBy) {
			case 1:
				countResult = prescriptionDao.countByDateAndLotNumberAndPartNumber(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumber());
			case 2:
				countResult = prescriptionDao.countByDateAndLotNumberAndPartNumberHead(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumberHead());
			case 3:
				countResult = prescriptionDao.countByDateAndLotNumberAndPartNumberBody(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumberBody());
			}

			return countResult;
		}
	}

	public File generateReport(ReportQuery reportQuery, String reportLocation) throws JRException, IOException,
			ClassNotFoundException, SQLException {
		// load JasperDesign from XML and compile it into JasperReport
		JasperDesign jasperDesign = JRXmlLoader.load(reportLocation + "/report.jrxml");
		JasperDesign jasperSubDesign = JRXmlLoader.load(reportLocation + "/sub_report.jrxml");

		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperReport jasperSubReport = JasperCompileManager.compileReport(jasperSubDesign);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SubReportParam", jasperSubReport);

		List<Prescription> prescriptions = getPrescriptions(reportQuery);
		List<PrescriptionReport> reports = transferToReport(prescriptions);
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(reports);
		// fill JasperPrint using fillReport() method
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

		File report = File.createTempFile("report-", ".pdf");
		JasperExportManager.exportReportToPdfFile(jasperPrint, report.getPath());

		return report;
	}

	private int determinateQueryCondition(ReportQuery reportQuery) {
		if (reportQuery.getStartDate() == null) {
			Calendar startDateCalendar = Calendar.getInstance();
			startDateCalendar.set(1920, 1, 1);
			reportQuery.setStartDate(startDateCalendar.getTime());
		}

		if (reportQuery.getEndDate() == null) {
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.set(2030, 12, 31);
			reportQuery.setEndDate(endDateCalendar.getTime());
		}

		if (reportQuery.getLotNumber() == null) {
			reportQuery.setLotNumber("%");
		} else {
			reportQuery.setLotNumber("%" + reportQuery.getLotNumber() + "%");
		}

		// 1: by partNumber, 2: by partNumberHead, 3: by partNumberBody
		int searchBy = 1;
		if (reportQuery.getPartNumberHead() == null && reportQuery.getPartNumberBody() == null) {
			searchBy = 1;
			reportQuery.setPartNumber("%");
		} else if (reportQuery.getPartNumberHead() != null && reportQuery.getPartNumberBody() == null) {
			searchBy = 2;
			reportQuery.setPartNumberHead("%" + reportQuery.getPartNumberHead() + "%");
		} else if (reportQuery.getPartNumberHead() == null && reportQuery.getPartNumberBody() != null) {
			searchBy = 3;
			reportQuery.setPartNumberBody("%" + reportQuery.getPartNumberBody() + "%");
		}

		return searchBy;
	}

	private List<Prescription> getPrescriptions(ReportQuery reportQuery) throws ClassNotFoundException, SQLException {
		int searchBy = determinateQueryCondition(reportQuery);

		Class.forName(DB_DRIVER);
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			List<Prescription> prescriptions = new ArrayList<Prescription>();

			switch (searchBy) {
			case 1:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumber(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumber());
				break;
			case 2:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumberHead(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumberHead());
				break;
			case 3:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumberBody(connection, reportQuery.getStartDate(),
						reportQuery.getEndDate(), reportQuery.getLotNumber(), reportQuery.getPartNumberBody());
				break;

			}

			for (Prescription prescription : prescriptions) {
				List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection, prescription.getId());
				prescription.setDetails(details);
			}

			return prescriptions;
		}
	}

	private List<PrescriptionReport> transferToReport(List<Prescription> prescriptions) {
		List<PrescriptionReport> reports = new ArrayList<PrescriptionReport>();

		for (Prescription prescription : prescriptions) {
			PrescriptionReport report = new PrescriptionReport(prescription.getLotNumber(), prescription.getDate(),
					prescription.getPartNumber(), prescription.getTotalAmount(), prescription.getTotalPrice(),
					prescription.getAverageCost());

			int itemIndex = 1;
			for (PrescriptionDetail detail : prescription.getDetails()) {
				PrescriptionDetailReport reportDetail = new PrescriptionDetailReport(itemIndex, detail.getName(),
						detail.getAmount(), detail.getPrice(), detail.getNote());
				report.addDetail(reportDetail);
			}

			reports.add(report);
		}

		return reports;
	}
}
