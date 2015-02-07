package com.rc.leatherback.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rc.leatherback.model.User;

public class UserDao {

	private static final String FIND_ALL_USERS = "select * from table_user;";

	public List<User> findAllUsers(Connection connection) throws SQLException {
		List<User> users = new ArrayList<User>();

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
		while (resultSet.next()) {
			users.add(bindData(resultSet));
		}

		resultSet.close();
		statement.close();

		return users;
	}

	private static final String GET_BY_ID = "select * from table_user where id = ?;";

	public User getById(Connection connection, long id) throws SQLException {
		User user = null;

		PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			user = bindData(resultSet);
		}

		resultSet.close();
		statement.close();

		return user;
	}

	private static final String GET_BY_USERNAME = "select * from table_user where username = ?;";

	public User getByUsername(Connection connection, String username) throws SQLException {
		User user = null;

		PreparedStatement statement = connection.prepareStatement(GET_BY_USERNAME);
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			user = bindData(resultSet);
		}

		resultSet.close();
		statement.close();

		return user;
	}

	private static final String UPDATE_PASSWORD = "update table_user set password = ? where id = ?;";

	public void updatePassword(Connection connection, long id, String newPassword) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD);
		statement.setString(1, newPassword);
		statement.setLong(2, id);
		statement.execute();

		statement.close();
	}

	private User bindData(ResultSet resultSet) throws SQLException {
		User user = new User();

		user.setId(resultSet.getLong("id"));
		user.setName(resultSet.getString("name"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setCreatedBy(resultSet.getString("created_by"));
		user.setCreatedDate(resultSet.getTimestamp("created_date"));
		user.setModifiedBy(resultSet.getString("modified_by"));
		user.setModifiedDate(resultSet.getTimestamp("modified_date"));

		return user;
	}
}
