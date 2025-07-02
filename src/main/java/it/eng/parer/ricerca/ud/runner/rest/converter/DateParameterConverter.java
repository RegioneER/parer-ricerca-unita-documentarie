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

import static it.eng.parer.ricerca.ud.beans.utils.DateUtilsConverter.convert;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import it.eng.parer.ricerca.ud.beans.exceptions.AppBadRequestException;
import it.eng.parer.ricerca.ud.runner.rest.annotation.DateFormat;
import it.eng.parer.ricerca.ud.runner.rest.annotation.DateTimeFormat;
import jakarta.ws.rs.ext.ParamConverter;

public class DateParameterConverter implements ParamConverter<Date> {

    public static final String DEFAULT_FORMAT = DateTimeFormat.DEFAULT_DATE_TIME;

    private DateTimeFormat customDateTimeFormat;
    private DateFormat customDateFormat;

    public void setCustomDateFormat(DateFormat customDateFormat) {
	this.customDateFormat = customDateFormat;
    }

    public void setCustomDateTimeFormat(DateTimeFormat customDateTimeFormat) {
	this.customDateTimeFormat = customDateTimeFormat;
    }

    @Override
    public Date fromString(String value) {
	String format = DEFAULT_FORMAT;
	if (customDateFormat != null) {
	    format = customDateFormat.value();
	} else if (customDateTimeFormat != null) {
	    format = customDateTimeFormat.value();
	}

	// validation strictly of value passed !
	try {
	    TemporalAccessor parsed = DateTimeFormatter.ofPattern(format).parseBest(value,
		    LocalDateTime::from, LocalDate::from);
	    LocalDateTime dt = null;
	    if (parsed instanceof LocalDateTime localdatetime) {
		dt = localdatetime;
	    } else if (parsed instanceof LocalDate localdate) {
		dt = localdate.atTime(LocalTime.MIDNIGHT);
	    }
	    return convert(dt);
	} catch (DateTimeParseException ex) {
	    throw AppBadRequestException.builder().cause(ex)
		    .message("La data {0} non rispetta il formato {1} previsto", value, format)
		    .build();
	}
    }

    @Override
    public String toString(Date date) {
	return new SimpleDateFormat(DEFAULT_FORMAT).format(date);
    }

}
