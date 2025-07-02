/**
 *
 */
package it.eng.parer.ricerca.ud.runner.rest.converter;

import it.eng.parer.ricerca.ud.beans.exceptions.AppBadRequestException;
import jakarta.ws.rs.ext.ParamConverter;

public class IntegerParameterConverter implements ParamConverter<Integer> {

    @Override
    public Integer fromString(String value) {
	try {
	    return Integer.parseInt(value);
	} catch (NumberFormatException ex) {
	    throw AppBadRequestException.builder().cause(ex)
		    .message("Il valore ''{0}'' fornito non è un intero valido", value).build();
	}
    }

    @Override
    public String toString(Integer number) {
	return number.toString();
    }

}
