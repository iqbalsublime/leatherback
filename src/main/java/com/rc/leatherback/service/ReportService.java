package com.rc.leatherback.service;

import java.io.File;
import java.io.IOException;
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

import com.rc.leatherback.model.Prescription;
import com.rc.leatherback.model.report.PrescriptionDetailReport;
import com.rc.leatherback.model.report.PrescriptionReport;

public class ReportService {
	public List<Prescription> query(Date startDate, Date endDate, String lotNumber, String partNumberHead, String partNumberBody,
			int pageIndex, int pageSize) {
		return new ArrayList<Prescription>();
	}

	public int getTotalNumberOfQueryResult(Date startDate, Date endDate, String lotNumber, String partNumberHead,
			String partNumberBody, int pageIndex, int pageSize) {
		return 0;
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
