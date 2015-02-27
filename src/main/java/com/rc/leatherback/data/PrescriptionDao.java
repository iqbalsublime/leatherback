package com.rc.leatherback.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rc.core.util.DateTimeUtil;
import com.rc.leatherback.model.Prescription;

public class PrescriptionDao {

	private static final String GET_BY_ID = "select * from table_prescription where id = ?;";

	public Prescription getById(Connection connection, long id) throws SQLException {
		Prescription prescription = null;

		PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescription = bindData(resultSet);
		}

		resultSet.close();
		statement.close();

		return prescription;
	}

	private static final String FIND_ALL = "select * from table_prescription;";

	public List<Prescription> findAll(Connection connection) throws SQLException {
		List<Prescription> prescriptions = new ArrayList<Prescription>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(FIND_ALL);
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	// private static final String FIND_ALL_PAGINATION = "select * from table_prescription limit ? offset ?;";
	private static final String FIND_ALL_PAGINATION = ";with result_cte as ( select *, ROW_NUMBER() over (order by id) as row_num from table_prescription ) select * from result_cte where row_num >= ? and row_num < ?;";

	public List<Prescription> findAllPagination(Connection connection, int limit, int offset) throws SQLException {
		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_ALL_PAGINATION);
		// statement.setInt(1, limit);
		// statement.setInt(2, offset);
		statement.setInt(1, offset);
		statement.setInt(2, offset + limit);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	// private static final String FIND_BY_LOT_NUMBER_PAGINATION =
	// "select * from table_prescription where lot_number = ? limit ? offset ?;";
	private static final String FIND_BY_LOT_NUMBER_PAGINATION = ";with result_cte as ( select *, ROW_NUMBER() over (order by id) as row_num from table_prescription where lot_number = ?) select * from result_cte where row_num >= ? and row_num < ?;";

	public List<Prescription> findByLotNumberPagination(Connection connection, String lotNumber, int limit, int offset) throws SQLException {
		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_BY_LOT_NUMBER_PAGINATION);
		statement.setString(1, lotNumber);
		// statement.setInt(2, limit);
		// statement.setInt(3, offset);
		statement.setInt(2, offset);
		statement.setInt(3, offset + limit);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	// private static final String FIND_BY_PART_NUMBER_PAGINATION =
	// "select * from table_prescription where part_number = ? limit ? offset ?;";
	private static final String FIND_BY_PART_NUMBER_PAGINATION = ";with result_cte as ( select *, ROW_NUMBER() over (order by id) as row_num from table_prescription where part_number = ?) select * from result_cte where row_num >= ? and row_num < ?;";

	public List<Prescription> findByPartNumberPagination(Connection connection, String partNumber, int limit, int offset)
			throws SQLException {
		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_BY_PART_NUMBER_PAGINATION);
		statement.setString(1, partNumber);
		// statement.setInt(2, limit);
		// statement.setInt(3, offset);
		statement.setInt(2, offset);
		statement.setInt(3, offset + limit);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	private static final String INSERT = "insert into table_prescription"
			+ "(lot_number, date, part_number, part_number_head, part_number_body, total_amount, total_price, average_cost, hand, total_amount_after_handed, created_by, created_date, modified_by, modified_date) values"
			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

	public void add(Connection connection, Prescription prescription) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, prescription.getLotNumber());
			statement.setDate(2, new java.sql.Date(prescription.getDate().getTime()));
			statement.setString(3, prescription.getPartNumber());
			statement.setString(4, prescription.getPartNumberHead());
			statement.setString(5, prescription.getPartNumberBody());
			statement.setDouble(6, prescription.getTotalAmount());
			statement.setDouble(7, prescription.getTotalPrice());
			statement.setDouble(8, prescription.getAverageCost());
			statement.setDouble(9, prescription.getHand());
			statement.setDouble(10, prescription.getTotalAmountAfterHanded());
			statement.setString(11, prescription.getCreatedBy());
			if (prescription.getCreatedDate() != null) {
				statement.setTimestamp(12, DateTimeUtil.convertToSqlTimestamp(prescription.getCreatedDate()));
			} else {
				statement.setTimestamp(12, null);
			}
			statement.setString(13, prescription.getModifiedBy());
			if (prescription.getModifiedDate() != null) {
				statement.setTimestamp(14, DateTimeUtil.convertToSqlTimestamp(prescription.getModifiedDate()));
			} else {
				statement.setTimestamp(14, null);
			}

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					prescription.setId(generatedKeys.getLong(1));
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		}
	}

	private static final String UPDATE = "update table_prescription "
			+ "set lot_number = ?, date = ?, part_number = ?, part_number_head = ?, part_number_body = ?, total_amount = ?, total_price = ?, average_cost = ?, hand = ?, total_amount_after_handed = ?, modified_by = ?, modified_date = ? "
			+ "where id = ?;";

	public void update(Connection connection, Prescription prescription) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
			statement.setString(1, prescription.getLotNumber());
			statement.setDate(2, new java.sql.Date(prescription.getDate().getTime()));
			statement.setString(3, prescription.getPartNumber());
			statement.setString(4, prescription.getPartNumberHead());
			statement.setString(5, prescription.getPartNumberBody());
			statement.setDouble(6, prescription.getTotalAmount());
			statement.setDouble(7, prescription.getTotalPrice());
			statement.setDouble(8, prescription.getAverageCost());
			statement.setDouble(9, prescription.getHand());
			statement.setDouble(10, prescription.getTotalAmountAfterHanded());
			statement.setString(11, prescription.getModifiedBy());
			statement.setTimestamp(12, DateTimeUtil.convertToSqlTimestamp(prescription.getModifiedDate()));
			statement.setLong(13, prescription.getId());
			statement.executeUpdate();
		}
	}

	private static final String DELETE = "delete from table_prescription where id = ?;";

	public void remove(Connection connection, long id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
			statement.setLong(1, id);
			statement.executeUpdate();
		}
	}

	private static final String COUNT_ALL = "select count(*) from table_prescription;";

	public int countAll(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery(COUNT_ALL)) {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				} else {
					return 0;
				}
			}
		}
	}

	private static final String COUNT_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER = "select count(id) from table_prescription where date between ? and ? and lot_number like ? and part_number like ?;";

	public int countByDateAndLotNumberAndPartNumber(Connection connection, Date startDate, Date endDate, String lotNumber, String partNumber)
			throws SQLException {

		int countResult = 0;

		PreparedStatement statement = connection.prepareStatement(COUNT_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumber);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			countResult = resultSet.getInt(1);
		}

		resultSet.close();
		statement.close();

		return countResult;
	}

	private static final String COUNT_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_HEAD = "select count(id) from table_prescription where date between ? and ? and lot_number like ? and part_number_head like ?;";

	public int countByDateAndLotNumberAndPartNumberHead(Connection connection, Date startDate, Date endDate, String lotNumber,
			String partNumberHead) throws SQLException {

		int countResult = 0;

		PreparedStatement statement = connection.prepareStatement(COUNT_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_HEAD);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumberHead);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			countResult = resultSet.getInt(1);
		}

		resultSet.close();
		statement.close();

		return countResult;
	}

	private static final String COUNT_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_BODY = "select count(id) from table_prescription where date between ? and ? and lot_number like ? and part_number_body like ?;";

	public int countByDateAndLotNumberAndPartNumberBody(Connection connection, Date startDate, Date endDate, String lotNumber,
			String partNumberBody) throws SQLException {

		int countResult = 0;

		PreparedStatement statement = connection.prepareStatement(COUNT_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_BODY);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumberBody);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			countResult = resultSet.getInt(1);
		}

		resultSet.close();
		statement.close();

		return countResult;
	}

	private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER = "select * from table_prescription where date between ? and ? and lot_number like ? and part_number like ?;";

	public List<Prescription> findByDateAndLotNumberAndPartNumber(Connection connection, Date startDate, Date endDate, String lotNumber,
			String partNumber) throws SQLException {

		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumber);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_HEAD = "select * from table_prescription where date between ? and ? and lot_number like ? and part_number_head like ?;";

	public List<Prescription> findByDateAndLotNumberAndPartNumberHead(Connection connection, Date startDate, Date endDate,
			String lotNumber, String partNumberHead) throws SQLException {

		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_HEAD);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumberHead);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_BODY = "select * from table_prescription where date between ? and ? and lot_number like ? and part_number_body like ?;";

	public List<Prescription> findByDateAndLotNumberAndPartNumberBody(Connection connection, Date startDate, Date endDate,
			String lotNumber, String partNumberBody) throws SQLException {

		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_BODY);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumberBody);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	// private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_PAGINATION =
	// "select * from table_prescription where date between ? and ? and lot_number like ? and part_number like ? limit ? offset ?;";
	private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_PAGINATION = ";with result_cte as ( select *, ROW_NUMBER() over (order by id) as row_num from table_prescription where date between ? and ? and lot_number like ? and part_number like ?) select * from result_cte where row_num >= ? and row_num < ?;";

	public List<Prescription> findByDateAndLotNumberAndPartNumber(Connection connection, Date startDate, Date endDate, String lotNumber,
			String partNumber, int limit, int offset) throws SQLException {
		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_PAGINATION);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumber);
		// statement.setInt(5, limit);
		// statement.setInt(6, offset);
		statement.setInt(5, offset);
		statement.setInt(6, offset + limit);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	// private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_HEAD_PAGINATION =
	// "select * from table_prescription where date between ? and ? and lot_number like ? and part_number_head like ? limit ? offset ?;";
	private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_HEAD_PAGINATION = ";with result_cte as ( select *, ROW_NUMBER() over (order by id) as row_num from table_prescription where date between ? and ? and lot_number like ? and part_number_head like ?) select * from result_cte where row_num >= ? and row_num < ?;";

	public List<Prescription> findByDateAndLotNumberAndPartNumberHead(Connection connection, Date startDate, Date endDate,
			String lotNumber, String partNumberHead, int limit, int offset) throws SQLException {
		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_HEAD_PAGINATION);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumberHead);
		// statement.setInt(5, limit);
		// statement.setInt(6, offset);
		statement.setInt(5, offset);
		statement.setInt(6, offset + limit);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	// private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_BODY_PAGINATION =
	// "select * from table_prescription where date between ? and ? and lot_number like ? and part_number_body like ? limit ? offset ?;";
	private static final String FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_BODY_PAGINATION = ";with result_cte as ( select *, ROW_NUMBER() over (order by id) as row_num from table_prescription where date between ? and ? and lot_number like ? and part_number_body like ?) select * from result_cte where row_num >= ? and row_num < ?;";

	public List<Prescription> findByDateAndLotNumberAndPartNumberBody(Connection connection, Date startDate, Date endDate,
			String lotNumber, String partNumberBody, int limit, int offset) throws SQLException {
		List<Prescription> prescriptions = new ArrayList<Prescription>();

		PreparedStatement statement = connection.prepareStatement(FIND_BY_DATE_AND_LOT_NUMBER_AND_PART_NUMBER_BODY_PAGINATION);
		statement.setDate(1, DateTimeUtil.convertToSqlDate(startDate));
		statement.setDate(2, DateTimeUtil.convertToSqlDate(endDate));
		statement.setString(3, lotNumber);
		statement.setString(4, partNumberBody);
		// statement.setInt(5, limit);
		// statement.setInt(6, offset);
		statement.setInt(5, offset);
		statement.setInt(6, offset + limit);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			prescriptions.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return prescriptions;
	}

	private Prescription bindData(ResultSet resultSet) throws SQLException {
		Prescription prescription = new Prescription();

		prescription.setId(resultSet.getLong("id"));
		prescription.setLotNumber(resultSet.getString("lot_number"));
		prescription.setDate(resultSet.getDate("date"));
		prescription.setPartNumber(resultSet.getString("part_number"));
		prescription.setPartNumberHead(resultSet.getString("part_number_head"));
		prescription.setPartNumberBody(resultSet.getString("part_number_body"));
		prescription.setTotalAmount(resultSet.getDouble("total_amount"));
		prescription.setTotalPrice(resultSet.getDouble("total_price"));
		prescription.setAverageCost(resultSet.getDouble("average_cost"));
		prescription.setHand(resultSet.getDouble("hand"));
		prescription.setTotalAmountAfterHanded(resultSet.getDouble("total_amount_after_handed"));
		prescription.setCreatedBy(resultSet.getString("created_by"));
		prescription.setCreatedDate(resultSet.getTimestamp("created_date"));
		prescription.setModifiedBy(resultSet.getString("modified_by"));
		prescription.setModifiedDate(resultSet.getTimestamp("modified_date"));

		return prescription;
	}
}
