/**
 *
 */
package it.eng.parer.ricerca.ud.runner.filters;

import java.util.Optional;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import it.eng.parer.ricerca.ud.beans.utils.UUIDMdcLogUtil;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.container.ContainerRequestContext;

/*
 * Filtro che intercetta le chiamate di tipo GET e inietta sull'MCD un UUID
 */
public class UUIDFilter {

    @ServerRequestFilter(priority = 1000)
    public Optional<RestResponse<Void>> getFilter(ContainerRequestContext ctx) {

	if (ctx.getMethod().equals(HttpMethod.GET)) {
	    UUIDMdcLogUtil.genUuid();
	}

	return Optional.empty();
    }
}
