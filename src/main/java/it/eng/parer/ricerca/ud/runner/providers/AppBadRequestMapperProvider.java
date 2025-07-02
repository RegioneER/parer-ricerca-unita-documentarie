/**
 *
 */
package it.eng.parer.ricerca.ud.runner.providers;

import static it.eng.parer.ricerca.ud.beans.utils.Costants.COD_ERR_BADREQ;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.ricerca.ud.beans.exceptions.AppBadRequestException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AppBadRequestMapperProvider implements ExceptionMapper<AppBadRequestException> {

    private static final Logger log = LoggerFactory.getLogger(AppBadRequestMapperProvider.class);

    @Context
    SecurityContext securityCtx;

    @Override
    public Response toResponse(AppBadRequestException exception) {
	log.atError().log("Eccezione generica", exception);
	return Response.status(400).entity(Map.of(COD_ERR_BADREQ, exception.getMessage())).build();

    }

}
