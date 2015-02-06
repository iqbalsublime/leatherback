package com.rc.leatherback.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rc.leatherback.model.User;

public class UserDao {

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
