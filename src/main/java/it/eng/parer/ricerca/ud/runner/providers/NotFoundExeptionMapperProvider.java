/**
 *
 */
package it.eng.parer.ricerca.ud.runner.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/*
 * ExceptionMapper che gestisce l'http 404 qualora venga richiamata una risorsa non esistente
 * (risposta vuota con il relativo http error code)
 */
@Provider
public class NotFoundExeptionMapperProvider implements ExceptionMapper<NotFoundException> {

    private static final Logger log = LoggerFactory.getLogger(NotFoundExeptionMapperProvider.class);

    @Override
    public Response toResponse(NotFoundException exception) {
	log.atError().log("Eccezione generica", exception);
	return Response.status(404).build();
    }

}
