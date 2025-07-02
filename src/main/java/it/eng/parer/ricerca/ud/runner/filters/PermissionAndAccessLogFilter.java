/**
 *
 */
package it.eng.parer.ricerca.ud.runner.filters;

import static it.eng.parer.ricerca.ud.beans.utils.Costants.COD_PERM_INTERNAL;
import static it.eng.parer.ricerca.ud.beans.utils.Costants.URL_GET_UD;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import io.vertx.core.http.HttpServerRequest;
import it.eng.parer.ricerca.ud.beans.IPermissionAndAccessLogService;
import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response.Status;

/*
 * Filtro che intercetta le chiamate di tipo GET sul servizio /api/organizzazioni e verifica
 * l'abilitazione dell'utente e ne logga l'accesso su sistema centrale
 */
public class PermissionAndAccessLogFilter {

    @Inject
    IPermissionAndAccessLogService service;

    @Context
    HttpServerRequest request;

    @ServerRequestFilter(priority = 2000)
    public Optional<RestResponse<Map<String, String>>> getFilter(ContainerRequestContext ctx) {

	if (ctx.getMethod().equals(HttpMethod.GET)
		&& ctx.getUriInfo().getPath().contains(URL_GET_UD)) {
	    final String nmUserid = ctx.getSecurityContext().getUserPrincipal().getName();
	    // check abilitazione servizio
	    if (!service.isUserEnabledOnService(nmUserid)) {
		//
		return Optional.of(RestResponse.status(Status.UNAUTHORIZED,
			Map.of(COD_PERM_INTERNAL, MessageFormat.format(
				"L''utente ''{0}'' non risulta abilitato al servizio invocato",
				ctx.getSecurityContext().getUserPrincipal().getName()))));
	    } else {
		// creazione access log
		service.createAccessLog(nmUserid, getIpAddressFromRequest());
	    }

	}

	return Optional.empty();
    }

    private String getIpAddressFromRequest() {
	String ipVers = request.getHeader("RERFwFor");
	// cerco l'header custom della RER
	if (ipVers == null || ipVers.isEmpty()) {
	    ipVers = request.getHeader("X-FORWARDED-FOR");
	    // se non c'e`, uso l'header standard
	}
	if (ipVers == null || ipVers.isEmpty()) {
	    ipVers = request.remoteAddress().hostAddress();
	    // se non c'e` perche' la macchina e' esposta direttamente,
	    // leggo l'IP fisico del chiamante
	}
	return ipVers;
    }
}
