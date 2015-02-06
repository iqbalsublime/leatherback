package com.rc.leatherback.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.rc.leatherback.DatabaseContext;
import com.rc.leatherback.data.UserDao;
import com.rc.leatherback.exception.AuthenticatedFailedException;
import com.rc.leatherback.model.User;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService() {
        this.userDao = new UserDao();
    }

    public User login(String username, String password) throws SQLException, ClassNotFoundException {
        try (Connection connection = DatabaseContext.getConnection()) {
            User user = userDao.getByUsername(connection, username);
            if (user == null) {
                throw new AuthenticatedFailedException(String.format("User is not found by username: %s", username));
            }

            if (user.getPassword() != password) {
                throw new AuthenticatedFailedException(String.format("Invalid password: %s, username: %s", password, username));
            }

            return user;
        }
    }
}
