/**
 *
 */
package it.eng.parer.ricerca.ud.runner.providers;

import static it.eng.parer.ricerca.ud.beans.utils.Costants.COD_ERR_BADREQ;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/*
 * ExceptionMapper che gestisce l'http 404 qualora venga richiamata una risorsa non esistente
 * (risposta vuota con il relativo http error code)
 */
@Provider
public class ConstraintViolationExceptionMapperProvider
	implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger log = LoggerFactory
	    .getLogger(ConstraintViolationExceptionMapperProvider.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
	log.atError().log("Richiesta errata", exception);

	Map<String, String> errors = new TreeMap<>();
	AtomicInteger count = new AtomicInteger(1);

	// check violation errors
	exception.getConstraintViolations().forEach(c -> {
	    StringBuilder message = new StringBuilder();

	    // generic
	    message.append(c.getMessage());
	    errors.put(COD_ERR_BADREQ.concat("-").concat(String.valueOf(count.getAndAdd(1))),
		    message.toString());

	});

	return Response.status(400).entity(errors).build();
    }

}
