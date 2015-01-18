package com.rc.leatherback.facade;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rc.leatherback.exception.PrescriptionNotFoundException;
import com.rc.leatherback.model.Prescription;
import com.rc.leatherback.model.User;
import com.rc.leatherback.service.PrescriptionService;

@Path("/prescription")
public class PrescriptionFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionFacade.class);

    private PrescriptionService service;

    public PrescriptionFacade() {
        this.service = new PrescriptionService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        try {
            List<Prescription> prescriptions = service.findPrescriptionsByPage(1, 1);

            return Response.status(200).entity(prescriptions).build();
        } catch (ClassNotFoundException | SQLException exception) {
            LOGGER.error("Failed to find all prescriptions", exception);
            return Response.status(590).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id") long id) {
        try {
            Prescription prescription = service.getPrescriptionById(id);

            return Response.status(200).entity(prescription).build();
        } catch (ClassNotFoundException | SQLException exception) {
            LOGGER.error("Failed to find all prescriptions", exception);
            return Response.status(590).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Prescription prescription) {
        try {
            User loggedInUser = new User();
            loggedInUser.setName("rocky");

            service.addPrescription(loggedInUser, prescription);

            return Response.status(200).build();
        } catch (ClassNotFoundException | SQLException exception) {
            LOGGER.error("Failed to create prescription", exception);
            return Response.status(590).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Prescription prescription) {
        try {
            User loggedInUser = new User();
            loggedInUser.setName("rocky");

            service.updatePrescription(loggedInUser, id, prescription);

            return Response.status(200).build();
        } catch (PrescriptionNotFoundException | ClassNotFoundException | SQLException exception) {
            LOGGER.error("Failed to update prescription", exception);
            return Response.status(590).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        try {
            service.deletePrescription(id);

            return Response.status(200).build();
        } catch (PrescriptionNotFoundException | ClassNotFoundException | SQLException exception) {
            LOGGER.error("Failed to delete prescription", exception);
            return Response.status(590).build();
        }
    }
}
