package com.rc.leatherback.facade;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rc.leatherback.exception.AuthenticatedFailedException;
import com.rc.leatherback.exception.FailedToUpdatePasswordException;
import com.rc.leatherback.facade.dto.PasswordDto;
import com.rc.leatherback.model.User;
import com.rc.leatherback.service.UserService;

@Path("/user")
public class UserFacade {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);

	private UserService service;

	public UserFacade() {
		this.service = new UserService();
	}

	@POST
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response query(@Context HttpServletRequest req, PasswordDto passwordDto) {
		try {
			HttpSession session = req.getSession(true);
			Object userObject = session.getAttribute("user");
			if (userObject != null) {
				User loggedInUser = (User) userObject;
				service.changePassword(loggedInUser, passwordDto.getNewPassword());
			} else {
				throw new AuthenticatedFailedException("User is not found in session");
			}

			return Response.status(200).build();
		} catch (AuthenticatedFailedException exception) {
			LOGGER.error("Failed to authenticate", exception);
			return Response.status(403).build();
		} catch (ClassNotFoundException | SQLException | FailedToUpdatePasswordException exception) {
			LOGGER.error("Failed to change user password", exception);
			return Response.status(590).build();
		}
	}
}
