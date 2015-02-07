package com.rc.leatherback.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rc.leatherback.model.Authorisation;

public class AuthorisationDao {

	private static final String GET_BY_USER_ID = "select * from table_authroisation where user_id = ?;";

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

	private Authorisation bindData(ResultSet resultSet) throws SQLException {
		Authorisation authorisation = new Authorisation();

		authorisation.setId(resultSet.getLong("id"));
		authorisation.setCreatedBy(resultSet.getString("created_by"));
		authorisation.setCreatedDate(resultSet.getTimestamp("created_date"));
		authorisation.setModifiedBy(resultSet.getString("modified_by"));
		authorisation.setModifiedDate(resultSet.getTimestamp("modified_date"));

		return authorisation;
	}
}
