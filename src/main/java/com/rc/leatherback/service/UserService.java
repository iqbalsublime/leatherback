package com.rc.leatherback.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.rc.leatherback.data.AuthorisationDao;
import com.rc.leatherback.data.DatabaseContext;
import com.rc.leatherback.data.UserDao;
import com.rc.leatherback.exception.AuthorisationNotFoundException;
import com.rc.leatherback.exception.FailedToUpdatePasswordException;
import com.rc.leatherback.exception.UserNotFoundException;
import com.rc.leatherback.model.Authorisation;
import com.rc.leatherback.model.User;

public class UserService {

    private UserDao userDao;
    private AuthorisationDao authorisationDao;

    public UserService() {
        this.userDao = new UserDao();
        this.authorisationDao = new AuthorisationDao();
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

    public List<User> findAllUsers() throws ClassNotFoundException, SQLException {
        try (Connection connection = DatabaseContext.getConnection()) {
            return userDao.findAllUsers(connection);
        }
    }

    public User getUserByUserId(long userId) throws ClassNotFoundException, SQLException {
        try (Connection connection = DatabaseContext.getConnection()) {
            User user = userDao.getById(connection, userId);
            if (user == null) {
                throw new UserNotFoundException(String.format("User is not found by user id: %s", userId));
            }

            Authorisation authorisation = authorisationDao.getByUserId(connection, user.getId());
            if (authorisation == null) {
                throw new AuthorisationNotFoundException(String.format("Authorisation is not found by user id: %s", userId));
            }
            user.setAuthorisation(authorisation);

            return user;
        }
    }

    public void updateUser(User loggedInUser, long userId, User user) throws ClassNotFoundException, SQLException {
        Date modifyDate = new Date();

        user.setModifiedBy(loggedInUser.getName());
        user.setModifiedDate(modifyDate);
        user.getAuthorisation().setModifiedBy(loggedInUser.getName());
        user.getAuthorisation().setModifiedDate(modifyDate);

        Connection connection = DatabaseContext.getConnection();
        try {
            connection.setAutoCommit(false);

            // Check whether parameter id is valid
            User originalUser = userDao.getById(connection, userId);
            if (originalUser == null) {
                throw new UserNotFoundException(String.format("User is not found by user id: %s", userId));
            }

            Authorisation originalAuthorisation = authorisationDao.getByUserId(connection, user.getId());
            if (originalAuthorisation == null) {
                throw new AuthorisationNotFoundException(String.format("Authorisation is not found by user id: %s", userId));
            }

            // Update prescription
            user.setId(originalUser.getId());
            userDao.update(connection, user);

            user.getAuthorisation().setId(originalAuthorisation.getId());
            authorisationDao.update(connection, user.getAuthorisation());

            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();

            throw exception;
        } finally {
            connection.close();
        }
    }
}
