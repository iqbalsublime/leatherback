package com.rc.leatherback.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		// Class.forName("com.mysql.jdbc.Driver");
		// try (Connection connection =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/leatherback_dev",
		// "root",
		// "rockey.chen")) {
		//
		// Prescription prescription = dao.getById(connection, id);
		// if (prescription == null) {
		// throw new
		// PrescriptionNotFoundException(String.format("Prescription is not found by id: %s",
		// id));
		// }
		//
		// List<PrescriptionDetail> details =
		// detailDao.findByPrescriptionId(connection, prescription.getId());
		// prescription.setDetails(details);
		//
		// return prescription;
		// }

		Prescription prescription = new Prescription();
		prescription.setId(1);
		prescription.setLotNumber("20150123001");
		prescription.setDate(new Date(2015, 1, 23));
		prescription.setPartNumber("TS-ABC01");
		prescription.setPartNumberHead("TS");
		prescription.setPartNumberHead("ABC01");
		prescription.setTotalAmount(12.5);
		prescription.setTotalPrice(36.23);
		prescription.setTotalAmountAfterHanded(48.281);
		prescription.setHand(1.2);

		List<PrescriptionDetail> details = new ArrayList<PrescriptionDetail>();
		PrescriptionDetail detail = new PrescriptionDetail();
		detail.setId(1);
		detail.setPrescriptionId(1);
		detail.setPrescriptionName("aa");
		detail.setAmount(10);
		detail.setPrice(38);
		detail.setNote("test");
		details.add(detail);

		detail = new PrescriptionDetail();
		detail.setId(2);
		detail.setPrescriptionId(1);
		detail.setPrescriptionName("abb");
		detail.setAmount(11);
		detail.setPrice(381);
		detail.setNote("test 2");
		details.add(detail);

		prescription.setDetails(details);

		return prescription;
	}

	public List<Prescription> findPrescriptionsByPage(int pageIndex, int pageSize) throws ClassNotFoundException, SQLException {
		// Class.forName("com.mysql.jdbc.Driver");
		// try (Connection connection =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/leatherback_dev",
		// "root", "rockey.chen")) {
		//
		// int limit = pageSize;
		// int offset = pageSize * (pageIndex - 1);
		// List<Prescription> prescriptions = dao.findAllPagination(connection,
		// limit, offset);
		// for (Prescription prescription : prescriptions) {
		// List<PrescriptionDetail> details =
		// detailDao.findByPrescriptionId(connection, prescription.getId());
		// prescription.setDetails(details);
		// }
		//
		// return prescriptions;
		// }

		List<Prescription> prescriptions = new ArrayList<Prescription>();
		Prescription prescription = new Prescription();
		prescription.setId(1);
		prescription.setLotNumber("20150123001");
		prescription.setDate(new Date(2015, 1, 23));
		prescription.setPartNumber("TS-ABC01");
		prescription.setPartNumberHead("TS");
		prescription.setPartNumberHead("ABC01");
		prescription.setTotalAmount(12.5);
		prescription.setTotalPrice(36.23);
		prescription.setTotalAmountAfterHanded(48.281);
		prescription.setHand(1.2);

		List<PrescriptionDetail> details = new ArrayList<PrescriptionDetail>();
		PrescriptionDetail detail = new PrescriptionDetail();
		detail.setId(1);
		detail.setPrescriptionId(1);
		detail.setPrescriptionName("aa");
		detail.setAmount(10);
		detail.setPrice(38);
		detail.setNote("test");
		details.add(detail);

		detail = new PrescriptionDetail();
		detail.setId(2);
		detail.setPrescriptionId(1);
		detail.setPrescriptionName("abb");
		detail.setAmount(11);
		detail.setPrice(381);
		detail.setNote("test 2");
		details.add(detail);

		prescription.setDetails(details);
		prescriptions.add(prescription);

		return prescriptions;

	}

	public void addPrescription(User loggedInUser, Prescription prescription) throws SQLException, ClassNotFoundException {
		Date createDate = new Date();

		prescription.setPartNumber(String.format("%s-%s", prescription.getPartNumberHead(), prescription.getPartNumberBody()));
		prescription.setCreatedBy(loggedInUser.getName());
		prescription.setCreatedDate(createDate);
		prescription.setModifiedBy(loggedInUser.getName());
		prescription.setModifiedDate(createDate);

		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/leatherback_dev", "root", "rockey.chen");
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

		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/leatherback_dev", "root", "rockey.chen");
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
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/leatherback_dev", "root", "rockey.chen");
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
		// Class.forName("com.mysql.jdbc.Driver");
		// try (Connection connection =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/leatherback_dev",
		// "root",
		// "rockey.chen")) {
		// return dao.countAll(connection);
		// }

		return 1;
	}
}
