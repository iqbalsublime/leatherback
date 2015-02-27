package com.rc.leatherback.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

//@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		// TODO Auto-generated method stub
		return null;
	}
	//
	// @Override
	// public Response toResponse(Throwable exception) {
	//
	// ErrorMessageDto errorMessage = new ErrorMessageDto();
	// setHttpStatus(exception, errorMessage);
	// errorMessage.setCode(RandomStringUtils.randomAlphanumeric(8));
	// errorMessage.setMessage(exception.getMessage());
	// StringWriter errorStackTrace = new StringWriter();
	// exception.printStackTrace(new PrintWriter(errorStackTrace));
	// errorMessage.setDeveloperMessage(errorStackTrace.toString());
	// errorMessage.setLink("http://localhost");
	//
	// return Response.status(errorMessage.getStatus()).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
	// }
	//
	// private void setHttpStatus(Throwable ex, ErrorMessageDto errorMessage) {
	// if (ex instanceof WebApplicationException) {
	// errorMessage.setStatus(((WebApplicationException) ex).getResponse().getStatus());
	// } else {
	// errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); // defaults to internal server error 500
	// }
	// }
}