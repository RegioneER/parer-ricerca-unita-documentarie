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

package it.eng.parer.ricerca.ud.runner.rest;

import static it.eng.parer.ricerca.ud.beans.utils.Costants.RESOURCE_UD;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.URL_API_BASE;
import static it.eng.parer.ricerca.ud.beans.utils.UdFilterParser.parseUdQuery;
import static it.eng.parer.ricerca.ud.beans.utils.UriUtil.decodeUriQuietly;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.smallrye.common.annotation.Blocking;
import io.vertx.core.http.HttpServerRequest;
import it.eng.parer.ricerca.ud.beans.IFindUnitadocService;
import it.eng.parer.ricerca.ud.beans.exceptions.AppBadRequestException;
import it.eng.parer.ricerca.ud.beans.exceptions.AppGenericRuntimeException;
import it.eng.parer.ricerca.ud.beans.model.UdFilter;
import it.eng.parer.ricerca.ud.beans.model.UdResponse;
import it.eng.parer.ricerca.ud.runner.rest.input.UdQuery;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.EntityTag;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Tag(name = "Ricerca unità documentarie", description = "Api che restituisce la lista delle unità documentarie")
@SecurityScheme(securitySchemeName = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
@RequestScoped
@Path(URL_API_BASE)
public class UnitadocumentarieEndpoint {

    /* constants */
    private static final String ETAG = "ud-v1.0";

    @ConfigProperty(name = "quarkus.uuid")
    String instanceUUID;

    /* interfaces */
    private final IFindUnitadocService findUdservice;
    private final SecurityContext securityCtx;

    @Inject
    public UnitadocumentarieEndpoint(IFindUnitadocService findUdservice,
	    SecurityContext securityCtx) {
	this.findUdservice = findUdservice;
	this.securityCtx = securityCtx;
    }

    @Operation(summary = "Lista unità documentarie", description = "Lista unità documentarie con applicazione di query string per filtro del risultato ottenuto")
    @SecurityRequirement(name = "bearerAuth")
    @APIResponses(value = {
	    @APIResponse(responseCode = "200", description = "Lista unità documentarie recuperata con successo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UdResponse.class))),
	    @APIResponse(responseCode = "400", description = "Richiesta non valida", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = AppBadRequestException.class))),
	    @APIResponse(responseCode = "401", description = "Autenticazione fallita"),
	    @APIResponse(responseCode = "403", description = "Non autorizzato ad accedere al servizio"),
	    @APIResponse(responseCode = "405", description = "Invocazione non corretta"),
	    @APIResponse(responseCode = "500", description = "Errore generico (richiesta non valida secondo specifiche)", content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = AppGenericRuntimeException.class))) })
    @GET
    @Path(RESOURCE_UD)
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    public Response listud(@BeanParam @Valid UdQuery udQuery, @Context HttpServerRequest request) {
	// do something .....
	UdResponse results = getListaUdResponseFromDto(udQuery, request);
	//
	return Response.ok(results)
		.lastModified(
			Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
		.tag(new EntityTag(ETAG)).build();
    }

    private UdResponse getListaUdResponseFromDto(UdQuery udQuery, HttpServerRequest request) {
	String uri = decodeUriQuietly(request);
	UdFilter filter = parseUdQuery(udQuery);
	return findUdservice.findUdByQuertStr(securityCtx.getUserPrincipal().getName(), filter,
		uri);
    }
}
