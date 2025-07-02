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
