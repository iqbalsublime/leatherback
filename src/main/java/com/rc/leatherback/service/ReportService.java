package com.rc.leatherback.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
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

import org.apache.commons.lang3.StringUtils;

import com.rc.core.util.DateTimeUtil;
import com.rc.leatherback.DatabaseContext;
import com.rc.leatherback.data.PrescriptionDao;
import com.rc.leatherback.data.PrescriptionDetailDao;
import com.rc.leatherback.exception.ReportNotFoundException;
import com.rc.leatherback.facade.dto.ReportQuery;
import com.rc.leatherback.model.Prescription;
import com.rc.leatherback.model.PrescriptionDetail;
import com.rc.leatherback.model.report.PrescriptionDetailReport;
import com.rc.leatherback.model.report.PrescriptionReport;

public class ReportService {

	private PrescriptionDao prescriptionDao;
	private PrescriptionDetailDao detailDao;

	public ReportService() {
		this.prescriptionDao = new PrescriptionDao();
		this.detailDao = new PrescriptionDetailDao();
	}

	public List<Prescription> query(ReportQuery reportQuery, int pageIndex, int pageSize) throws ClassNotFoundException,
			SQLException {

		return getPrescriptions(reportQuery, pageIndex, pageSize);
	}

	public int getTotalNumberOfQueryResult(ReportQuery reportQuery) throws ClassNotFoundException, SQLException {
		return countPrescriptions(reportQuery);
	}

	/**
	 * 
	 * @param reportQuery
	 * @param reportLocation
	 * @return A drawer key of the reports cabinet.
	 * @throws JRException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String generateReport(ReportQuery reportQuery, String reportLocation) throws JRException, IOException,
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

		String drawerKey = ReportArchiveService.keepReport(report);

		return drawerKey;
	}

	public File downloadReport(String drawerKey) {
		try {
			File report = ReportArchiveService.getReport(drawerKey);
			ReportArchiveService.destroyReport(drawerKey);

			return report;
		} catch (ReportNotFoundException exception) {
			throw exception;
		}
	}

	private List<Prescription> getPrescriptions(final ReportQuery reportQuery) throws ClassNotFoundException, SQLException {
		ReportQuery determinatedQuery = determinateQueryCondition(reportQuery);
		try (Connection connection = DatabaseContext.getConnection()) {
			List<Prescription> prescriptions = new ArrayList<Prescription>();

			switch (determinatedQuery.getSearchBy()) {
			case 1:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumber(connection, determinatedQuery.getStartDate(),
						determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(), determinatedQuery.getPartNumber());
				break;
			case 2:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumberHead(connection,
						determinatedQuery.getStartDate(), determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(),
						determinatedQuery.getPartNumberHead());
				break;
			case 3:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumberBody(connection,
						determinatedQuery.getStartDate(), determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(),
						determinatedQuery.getPartNumberBody());
				break;

			}

			for (Prescription prescription : prescriptions) {
				List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection, prescription.getId());
				prescription.setDetails(details);
			}

			return prescriptions;
		}
	}

	private List<Prescription> getPrescriptions(final ReportQuery reportQuery, int pageIndex, int pageSize)
			throws ClassNotFoundException, SQLException {

		ReportQuery determinatedQuery = determinateQueryCondition(reportQuery);
		try (Connection connection = DatabaseContext.getConnection()) {
			int limit = pageSize;
			int offset = pageSize * (pageIndex - 1);
			List<Prescription> prescriptions = new ArrayList<Prescription>();

			switch (determinatedQuery.getSearchBy()) {
			case 1:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumber(connection, determinatedQuery.getStartDate(),
						determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(), determinatedQuery.getPartNumber(),
						limit, offset);
				break;
			case 2:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumberHead(connection,
						determinatedQuery.getStartDate(), determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(),
						determinatedQuery.getPartNumberHead(), limit, offset);
				break;
			case 3:
				prescriptions = prescriptionDao.findByDateAndLotNumberAndPartNumberBody(connection,
						determinatedQuery.getStartDate(), determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(),
						determinatedQuery.getPartNumberBody(), limit, offset);
				break;

			}

			for (Prescription prescription : prescriptions) {
				List<PrescriptionDetail> details = detailDao.findByPrescriptionId(connection, prescription.getId());
				prescription.setDetails(details);
			}

			return prescriptions;
		}
	}

	private int countPrescriptions(final ReportQuery reportQuery) throws ClassNotFoundException, SQLException {
		int countResult = 0;
		ReportQuery determinatedQuery = determinateQueryCondition(reportQuery);
		try (Connection connection = DatabaseContext.getConnection()) {
			switch (determinatedQuery.getSearchBy()) {
			case 1:
				countResult = prescriptionDao.countByDateAndLotNumberAndPartNumber(connection, determinatedQuery.getStartDate(),
						determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(), determinatedQuery.getPartNumber());
				break;
			case 2:
				countResult = prescriptionDao.countByDateAndLotNumberAndPartNumberHead(connection,
						determinatedQuery.getStartDate(), determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(),
						determinatedQuery.getPartNumberHead());
				break;
			case 3:
				countResult = prescriptionDao.countByDateAndLotNumberAndPartNumberBody(connection,
						determinatedQuery.getStartDate(), determinatedQuery.getEndDate(), determinatedQuery.getLotNumber(),
						determinatedQuery.getPartNumberBody());
				break;
			}

			return countResult;
		}
	}

	private ReportQuery determinateQueryCondition(final ReportQuery reportQuery) {
		ReportQuery determinatedQuery = new ReportQuery();

		if (reportQuery.getStartDate() == null) {
			Calendar startDateCalendar = Calendar.getInstance();
			startDateCalendar.set(1920, 1, 1);
			determinatedQuery.setStartDate(startDateCalendar.getTime());
		} else {
			determinatedQuery.setStartDate(DateTimeUtil.getTheBeginingOfDate(reportQuery.getStartDate()));
		}

		if (reportQuery.getEndDate() == null) {
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.set(2030, 12, 31);
			determinatedQuery.setEndDate(endDateCalendar.getTime());
		} else {
			determinatedQuery.setEndDate(DateTimeUtil.getTheEndOfDate(reportQuery.getEndDate()));
		}

		if (StringUtils.isEmpty(reportQuery.getLotNumber())) {
			determinatedQuery.setLotNumber("%");
		} else {
			determinatedQuery.setLotNumber("%" + reportQuery.getLotNumber() + "%");
		}

		// 1: by partNumber, 2: by partNumberHead, 3: by partNumberBody
		if (StringUtils.isEmpty(reportQuery.getPartNumberHead()) && StringUtils.isEmpty(reportQuery.getPartNumberBody())) {
			determinatedQuery.setSearchBy(1);
			determinatedQuery.setPartNumber("%");
		} else if (!StringUtils.isEmpty(reportQuery.getPartNumberHead()) && StringUtils.isEmpty(reportQuery.getPartNumberBody())) {
			determinatedQuery.setSearchBy(2);
			determinatedQuery.setPartNumberHead("%" + reportQuery.getPartNumberHead() + "%");
		} else if (StringUtils.isEmpty(reportQuery.getPartNumberHead()) && !StringUtils.isEmpty(reportQuery.getPartNumberBody())) {
			determinatedQuery.setSearchBy(3);
			determinatedQuery.setPartNumberBody("%" + reportQuery.getPartNumberBody() + "%");
		} else {
			determinatedQuery.setSearchBy(1);
			determinatedQuery.setPartNumber(String.format("%s-%s", reportQuery.getPartNumberHead(),
					reportQuery.getPartNumberBody()));
		}

		return determinatedQuery;
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
