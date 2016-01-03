package org.virutor.rest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.resteasy.spi.ApplicationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

    private static final Logger loggger = LogManager.getLogger(ApplicationExceptionMapper.class);

    @Override
    public Response toResponse(ApplicationException exception) {
        loggger.error("Mapping ApplicationException to INTERNAL_SERVER_ERROR", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
    }

}
