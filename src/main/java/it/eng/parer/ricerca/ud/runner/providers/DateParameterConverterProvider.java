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
