/**
 *
 */
package it.eng.parer.ricerca.ud.beans.utils;

import java.net.URLDecoder;
import java.nio.charset.Charset;

import io.vertx.core.http.HttpServerRequest;

public class UriUtil {

    private UriUtil() {
	throw new IllegalStateException("Utility class");
    }

    public static final String decodeUriQuietly(HttpServerRequest request) {
	try {
	    return URLDecoder.decode(request.uri(), Charset.defaultCharset());
	} catch (Exception e) {
	    // quiet no exception (original uri / or null)
	    return request.uri();
	}
    }
}
