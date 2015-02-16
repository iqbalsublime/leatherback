package com.rc.leatherback.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rc.core.util.DateTimeUtil;
import com.rc.leatherback.model.Authorisation;

public class AuthorisationDao {

    private static final String GET_BY_USER_ID = "select * from table_authorisation where user_id = ?;";

    public Authorisation getByUserId(Connection connection, long userId) throws SQLException {
        Authorisation authorisation = null;

        PreparedStatement statement = connection.prepareStatement(GET_BY_USER_ID);
        statement.setLong(1, userId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            authorisation = bindData(resultSet);
        }

        resultSet.close();
        statement.close();

        return authorisation;
    }

    private static final String UPDATE_AUTHORISATION =
                    "update table_authorisation set access_user_module = ?, access_report_module = ?, create_prescription = ?, delete_prescription = ?, modify_prescription = ?, list_prescription = ?, modified_by = ?, modified_date = ? where id = ?;";

    public void update(Connection connection, Authorisation authorisation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_AUTHORISATION);
        statement.setBoolean(1, authorisation.isAccessUserModule());
        statement.setBoolean(2, authorisation.isAccessReportModule());
        statement.setBoolean(3, authorisation.isCreatePrescription());
        statement.setBoolean(4, authorisation.isDeletePrescription());
        statement.setBoolean(5, authorisation.isModifyPrescription());
        statement.setBoolean(6, authorisation.isListPrescription());
        statement.setString(7, authorisation.getModifiedBy());
        statement.setTimestamp(8, DateTimeUtil.convertToSqlTimestamp(authorisation.getModifiedDate()));
        statement.setLong(9, authorisation.getId());
        statement.execute();
    }

    private Authorisation bindData(ResultSet resultSet) throws SQLException {
        Authorisation authorisation = new Authorisation();

        authorisation.setId(resultSet.getLong("id"));
        authorisation.setCreatedBy(resultSet.getString("created_by"));
        authorisation.setCreatedDate(resultSet.getTimestamp("created_date"));
        authorisation.setModifiedBy(resultSet.getString("modified_by"));
        authorisation.setModifiedDate(resultSet.getTimestamp("modified_date"));
        authorisation.setAccessUserModule(resultSet.getBoolean("access_user_module"));
        authorisation.setAccessReportModule(resultSet.getBoolean("access_report_module"));
        authorisation.setCreatePrescription(resultSet.getBoolean("create_prescription"));
        authorisation.setDeletePrescription(resultSet.getBoolean("delete_prescription"));
        authorisation.setModifyPrescription(resultSet.getBoolean("modify_prescription"));
        authorisation.setListPrescription(resultSet.getBoolean("list_prescription"));

        return authorisation;
    }
}
