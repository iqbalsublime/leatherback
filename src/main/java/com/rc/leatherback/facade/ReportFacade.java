package com.rc.leatherback.facade;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import com.rc.leatherback.exception.ReportNotFoundException;
import com.rc.leatherback.facade.dto.PageableDto;
import com.rc.leatherback.facade.dto.ReportQuery;
import com.rc.leatherback.facade.dto.ReportResultDto;
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
	public Response query(ReportQuery query, @PathParam("index") int pageIndex) {
		try {
			List<Prescription> prescriptions = service.query(query, pageIndex, PAGE_SIZE);
			int totalNumberOfPrescriptions = service.getTotalNumberOfQueryResult(query);

			PageableDto<Prescription> responseData = new PageableDto<Prescription>();
			responseData.setData(prescriptions);
			responseData.setCurrentPage(pageIndex);
			responseData.setTotalItems(totalNumberOfPrescriptions);

			return Response.status(200).entity(responseData).build();
		} catch (ClassNotFoundException | SQLException exception) {
			LOGGER.error("Failed to create prescription", exception);
			return Response.status(590).build();
		}
	}

	@POST
	@Path("/pdf")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateReport(ReportQuery query) {
		try {
			String reportLocation = context.getRealPath("/WEB-INF");
			String drawerKey = service.generateReport(query, reportLocation);
			ReportResultDto responseData = new ReportResultDto();
			responseData.setDrawerKey(drawerKey);

			return Response.status(200).entity(responseData).build();
		} catch (JRException | IOException | ClassNotFoundException | SQLException exception) {
			LOGGER.error("Failed to create prescription", exception);
			return Response.status(590).build();
		}
	}

	@GET
	@Path("/pdf/download/{key}")
	@Produces({ "application/pdf" })
	public Response downloadReport(@PathParam("key") String drawerKey) {
		try {
			File file = service.downloadReport(drawerKey);
			ResponseBuilder response = Response.ok(file);
			response.header("Content-Disposition", "attachment; filename=report.pdf");

			return response.build();
		} catch (ReportNotFoundException exception) {
			LOGGER.error("Failed to create prescription", exception);
			return Response.status(590).build();
		}
	}
}
