package com.rc.leatherback.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rc.core.util.DateTimeUtil;
import com.rc.leatherback.model.PrescriptionDetail;

public class PrescriptionDetailDao {
    private static final String FIND_BY_PRESCRIPTION_ID = "select * from table_prescription_detail where prescription_id = ?;";

    public List<PrescriptionDetail> findByPrescriptionId(Connection connection, long prescriptionDetailId) throws SQLException {
        List<PrescriptionDetail> prescriptionDetails = new ArrayList<PrescriptionDetail>();

        PreparedStatement statement = connection.prepareStatement(FIND_BY_PRESCRIPTION_ID);
        statement.setLong(1, prescriptionDetailId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            prescriptionDetails.add(bindData(resultSet));
        }

        resultSet.close();
        statement.close();

        return prescriptionDetails;
    }

    private static final String INSERT =
            "insert into table_prescription_detail"
                    + "(prescription_id, prescription_name, amount, price, note, created_by, created_date, modified_by, modified_date) values"
                    + "(?,?,?,?,?,?,?,?,?);";

    public void add(Connection connection, PrescriptionDetail prescriptionDetail) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, prescriptionDetail.getPrescriptionId());
            statement.setString(2, prescriptionDetail.getPrescriptionName());
            statement.setDouble(3, prescriptionDetail.getAmount());
            statement.setDouble(4, prescriptionDetail.getPrice());
            statement.setString(5, prescriptionDetail.getNote());
            statement.setString(6, prescriptionDetail.getCreatedBy());
            if (prescriptionDetail.getCreatedDate() != null) {
                statement.setTimestamp(7, DateTimeUtil.convertToSqlTimestamp(prescriptionDetail.getCreatedDate()));
            } else {
                statement.setTimestamp(7, null);
            }
            statement.setString(8, prescriptionDetail.getModifiedBy());
            if (prescriptionDetail.getModifiedDate() != null) {
                statement.setTimestamp(9, DateTimeUtil.convertToSqlTimestamp(prescriptionDetail.getModifiedDate()));
            } else {
                statement.setTimestamp(9, null);
            }

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    prescriptionDetail.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    private static final String UPDATE = "update table_prescription_detail "
            + "set prescription_name = ?, amount = ?, price = ?, note = ?, modified_by = ?, modified_date = ? " + "where id = ?;";

    public void update(Connection connection, PrescriptionDetail prescriptionDetail) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, prescriptionDetail.getPrescriptionName());
            statement.setDouble(2, prescriptionDetail.getAmount());
            statement.setDouble(3, prescriptionDetail.getPrice());
            statement.setString(4, prescriptionDetail.getNote());
            statement.setString(5, prescriptionDetail.getModifiedBy());
            statement.setTimestamp(6, DateTimeUtil.convertToSqlTimestamp(prescriptionDetail.getModifiedDate()));
            statement.setLong(7, prescriptionDetail.getId());
            statement.executeUpdate();
        }
    }

    private static final String DELETE = "delete from table_prescription_detail where id = ?;";

    public void remove(Connection connection, long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    private static final String DELETE_BY_PRESCRIPTION_ID = "delete from table_prescription_detail where prescription_id = ?;";

    public void removeByPrescriptionId(Connection connection, long prescriptionId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BY_PRESCRIPTION_ID)) {
            statement.setLong(1, prescriptionId);
            statement.executeUpdate();
        }
    }

    private PrescriptionDetail bindData(ResultSet resultSet) throws SQLException {
        PrescriptionDetail prescriptionDetail = new PrescriptionDetail();

        prescriptionDetail.setId(resultSet.getLong("id"));
        prescriptionDetail.setPrescriptionId(resultSet.getLong("prescription_id"));
        prescriptionDetail.setPrescriptionName(resultSet.getString("prescription_name"));
        prescriptionDetail.setAmount(resultSet.getDouble("amount"));
        prescriptionDetail.setPrice(resultSet.getDouble("price"));
        prescriptionDetail.setNote(resultSet.getString("note"));
        prescriptionDetail.setCreatedBy(resultSet.getString("created_by"));
        prescriptionDetail.setCreatedDate(resultSet.getTimestamp("created_date"));
        prescriptionDetail.setModifiedBy(resultSet.getString("modified_by"));
        prescriptionDetail.setModifiedDate(resultSet.getTimestamp("modified_date"));

        return prescriptionDetail;
    }
}
