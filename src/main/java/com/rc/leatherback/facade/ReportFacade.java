package com.rc.leatherback.facade;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.sf.jasperreports.engine.JRException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rc.leatherback.facade.dto.PageableDto;
import com.rc.leatherback.facade.dto.ReportQuery;
import com.rc.leatherback.model.Prescription;
import com.rc.leatherback.service.ReportService;

@Path("/report")
public class ReportFacade {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportFacade.class);
	private static final int PAGE_SIZE = 10;

	@Context
	private ServletContext context;

	private ReportService service;

	public ReportFacade() {
		this.service = new ReportService();
	}

	@POST
	@Path("/query/{index}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response query(@PathParam("index") int pageIndex, ReportQuery query) {
		List<Prescription> prescriptions = service.query(query.getStartDate(), query.getEndDate(), query.getLotNumber(),
				query.getPartNumberHead(), query.getPartNumberBody(), pageIndex, PAGE_SIZE);

		int totalNumberOfPrescriptions = service.getTotalNumberOfQueryResult(query.getStartDate(), query.getEndDate(),
				query.getLotNumber(), query.getPartNumberHead(), query.getPartNumberBody(), pageIndex, PAGE_SIZE);

		PageableDto<Prescription> responseData = new PageableDto<Prescription>();
		responseData.setData(prescriptions);
		responseData.setCurrentPage(pageIndex);
		responseData.setTotalItems(totalNumberOfPrescriptions);

		return Response.status(200).entity(responseData).build();
	}

	@POST
	@Path("/pdf")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ "application/pdf" })
	public Response generateReport(ReportQuery query) throws JRException, IOException {
		String reportLocation = context.getRealPath("/WEB-INF");

		File file = service.generateReport(reportLocation);
		ResponseBuilder response = Response.ok(file);
		response.header("Content-Disposition", "attachment; filename=report.pdf");
		return response.build();
	}
}
