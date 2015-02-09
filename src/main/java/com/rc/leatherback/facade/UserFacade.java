package com.rc.leatherback.facade;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rc.leatherback.exception.AuthenticatedFailedException;
import com.rc.leatherback.exception.FailedToUpdatePasswordException;
import com.rc.leatherback.exception.PrescriptionNotFoundException;
import com.rc.leatherback.facade.dto.PasswordDto;
import com.rc.leatherback.model.User;
import com.rc.leatherback.service.UserService;

// @Path("/user")
public class UserFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

    private UserService service;

    public UserFacade() {
        this.service = new UserService();
    }

    @PUT
    @Path("/user/changePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response query(@Context HttpServletRequest req, PasswordDto passwordDto) {
        try {
            HttpSession session = req.getSession(true);
            Object userObject = session.getAttribute("user");
            if (userObject != null) {
                User loggedInUser = (User) userObject;
                service.changePassword(loggedInUser, passwordDto.getNewPassword());

                return Response.status(200).build();
            } else {
                throw new AuthenticatedFailedException("User is not found in session");
            }
        } catch (AuthenticatedFailedException exception) {
            LOGGER.error("Failed to authenticate", exception);
            return Response.status(403).build();
        } catch (ClassNotFoundException | SQLException | FailedToUpdatePasswordException exception) {
            LOGGER.error("Failed to change user password", exception);
            return Response.status(590).build();
        }
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<User> users = service.findAllUsers();

            return Response.status(200).entity(users).build();
        } catch (ClassNotFoundException | SQLException exception) {
            LOGGER.error("Failed to find all users", exception);
            return Response.status(590).build();
        }
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id") long userId) {
        try {
            User user = service.getUserByUserId(userId);

            return Response.status(200).entity(user).build();
        } catch (ClassNotFoundException | SQLException exception) {
            LOGGER.error("Failed to get user by user id", exception);
            return Response.status(590).build();
        }
    }

    @PUT
    @Path("/user/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@Context HttpServletRequest req, @PathParam("id") long id, User user) {
        try {
            HttpSession session = req.getSession(true);
            Object userObject = session.getAttribute("user");
            if (userObject != null) {
                User loggedInUser = (User) userObject;
                service.updateUser(loggedInUser, id, user);

                return Response.status(200).build();
            } else {
                throw new AuthenticatedFailedException("User is not found in session");
            }
        } catch (PrescriptionNotFoundException | ClassNotFoundException | SQLException exception) {
            LOGGER.error("Failed to update prescription", exception);
            return Response.status(590).build();
        }
    }
}
