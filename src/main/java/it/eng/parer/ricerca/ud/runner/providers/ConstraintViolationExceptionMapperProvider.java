/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

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
