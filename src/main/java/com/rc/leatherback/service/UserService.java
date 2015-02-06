package com.rc.leatherback.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.rc.leatherback.DatabaseContext;
import com.rc.leatherback.data.UserDao;
import com.rc.leatherback.exception.FailedToUpdatePasswordException;
import com.rc.leatherback.model.User;

public class UserService {

	private UserDao userDao;

	public UserService() {
		this.userDao = new UserDao();
	}

	public void changePassword(User user, String newPassword) throws ClassNotFoundException, SQLException {
		try (Connection connection = DatabaseContext.getConnection()) {
			try {
				connection.setAutoCommit(false);
				userDao.updatePassword(connection, user.getId(), newPassword);
				connection.commit();
			} catch (SQLException exception) {
				connection.rollback();

				throw new FailedToUpdatePasswordException(String.format("User %s 's password updated failed.", user.getId()),
						exception);
			}
		}
	}
}
