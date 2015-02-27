package com.rc.leatherback.facade;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.rc.leatherback.facade.dto.ErrorMessageDto;

@Provider
public class JacksonExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonExceptionMapper.class);

	@Override
	public Response toResponse(UnrecognizedPropertyException exception) {
		String traceId = RandomStringUtils.randomAlphanumeric(8);

		ErrorMessageDto errorMessage = new ErrorMessageDto();
		setHttpStatus(exception, errorMessage);
		errorMessage.setStatus(Status.BAD_REQUEST);
		errorMessage.setTraceId(traceId);
		// errorMessage.setCode();

		errorMessage.setMessage(exception.getMessage());
		StringWriter errorStackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(errorStackTrace));
		errorMessage.setDeveloperMessage(errorStackTrace.toString());
		// errorMessage.setLink("http://localhost");

		return Response.status(errorMessage.getStatus()).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
	}

	private void setHttpStatus(Throwable ex, ErrorMessageDto errorMessage) {
		if (ex instanceof WebApplicationException) {
			errorMessage.setStatus(((WebApplicationException) ex).getResponse().getStatus());
		} else {
			errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); // defaults to internal server error 500
		}
	}
}