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
