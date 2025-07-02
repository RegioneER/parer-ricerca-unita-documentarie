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
