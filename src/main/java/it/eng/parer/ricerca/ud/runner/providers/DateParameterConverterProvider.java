package it.eng.parer.ricerca.ud.runner.providers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import it.eng.parer.ricerca.ud.runner.rest.annotation.DateFormat;
import it.eng.parer.ricerca.ud.runner.rest.annotation.DateTimeFormat;
import it.eng.parer.ricerca.ud.runner.rest.converter.DateParameterConverter;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DateParameterConverterProvider implements ParamConverterProvider {

    @SuppressWarnings("unchecked")
    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType,
	    final Annotation[] annotations) {
	if (Date.class.equals(rawType)) {
	    final DateParameterConverter dateParameterConverter = new DateParameterConverter();

	    for (Annotation annotation : annotations) {
		if (DateTimeFormat.class.equals(annotation.annotationType())) {
		    dateParameterConverter.setCustomDateTimeFormat((DateTimeFormat) annotation);
		} else if (DateFormat.class.equals(annotation.annotationType())) {
		    dateParameterConverter.setCustomDateFormat((DateFormat) annotation);
		}
	    }
	    return (ParamConverter<T>) dateParameterConverter;
	}
	return null;
    }

}
