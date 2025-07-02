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

import static it.eng.parer.ricerca.ud.beans.utils.Costants.COD_ERR_INTERNAL;

import java.text.MessageFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.ricerca.ud.beans.exceptions.AppGenericRuntimeException;
import it.eng.parer.ricerca.ud.beans.utils.UUIDMdcLogUtil;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/*
 * ExceptionMapper che gestisce gli errori di tipoo WebApplicationException, nel caso specifico si
 * deve verificare la logica del provider CustomJaxbMessageBodyReader il quale, come da default,
 * utilizza questo tipo di eccezioni.
 */
@Provider
public class AppGenericRuntimeExceptionMapperProvider
	implements ExceptionMapper<AppGenericRuntimeException> {

    private static final Logger log = LoggerFactory
	    .getLogger(AppGenericRuntimeExceptionMapperProvider.class);

    @Context
    SecurityContext securityCtx;

    @Override
    public Response toResponse(AppGenericRuntimeException exception) {
	log.atError().log("Eccezione generica", exception);
	return Response.status(500).entity(Map.of(COD_ERR_INTERNAL, MessageFormat.format(
		"Errore in fase di elaborazione unità documentarie per utente ''{0}'', si prega di contattare l''assistenza tecnica fornendo il codice {1}",
		securityCtx.getUserPrincipal().getName(), UUIDMdcLogUtil.getUuid()))).build();

    }

}
