package it.eng.parer.ricerca.ud.runner.providers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import it.eng.parer.ricerca.ud.runner.rest.annotation.EnsureDigit;
import it.eng.parer.ricerca.ud.runner.rest.converter.IntegerParameterConverter;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DigitParameterConverterProvider implements ParamConverterProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType,
	    final Annotation[] annotations) {
	if (Number.class.isAssignableFrom(rawType)) {

	    for (Annotation annotation : annotations) {
		if (annotation instanceof EnsureDigit ensureDigit) {
		    if (ensureDigit.decimal()) {
			throw new UnsupportedOperationException("Non supportato");
		    } else if (ensureDigit.integer()) {
			return (ParamConverter<T>) new IntegerParameterConverter();
		    } else {
			// none of them
			return null;
		    }
		}
	    }

	}
	return null;
    }

}
