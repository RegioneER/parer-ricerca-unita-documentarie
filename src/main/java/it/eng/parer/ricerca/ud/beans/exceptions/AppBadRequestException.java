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

package it.eng.parer.ricerca.ud.beans.exceptions;

import java.text.MessageFormat;

import jakarta.ws.rs.BadRequestException;

public class AppBadRequestException extends BadRequestException {

    private static final long serialVersionUID = -7917206270508553978L;

    public AppBadRequestException(AppBadRequestExceptionBuilder builder) {
	super(builder.message, builder.cause);
    }

    public static AppBadRequestExceptionBuilder builder() {
	return new AppBadRequestExceptionBuilder();
    }

    public static class AppBadRequestExceptionBuilder {

	private String message;
	private Throwable cause;

	public AppBadRequestException build() {
	    return new AppBadRequestException(this);
	}

	public AppBadRequestExceptionBuilder message(String messageToFormat, Object... args) {
	    this.setMessage(MessageFormat.format(messageToFormat, args));
	    return this;
	}

	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}

	public AppBadRequestExceptionBuilder cause(Throwable cause) {
	    this.setCause(cause);
	    return this;
	}

	public Throwable getCause() {
	    return cause;
	}

	public void setCause(Throwable cause) {
	    this.cause = cause;
	}

    }

}
