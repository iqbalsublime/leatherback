package com.rc.leatherback.service;

import java.sql.SQLException;

import com.rc.leatherback.exception.AuthenticatedFailedException;
import com.rc.leatherback.exception.AuthorisationNotFoundException;
import com.rc.leatherback.exception.UserNotFoundException;
import com.rc.leatherback.model.User;

public class AuthenticationService {

	private UserService userService;

	public AuthenticationService() {
		this.userService = new UserService();
	}

	public User login(String username, String password) throws ClassNotFoundException, SQLException {
		try {
			User user = userService.getUserByUsername(username);

			if (!user.getPassword().equals(password)) {
				throw new AuthenticatedFailedException(String.format("Invalid password: %s, username: %s", password, username));
			}

			return user;
		} catch (UserNotFoundException | AuthorisationNotFoundException exception) {
			throw new AuthenticatedFailedException(String.format("User is not found by username: %s", username));
		}
	}
}
