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
