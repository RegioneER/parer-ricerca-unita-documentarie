/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package it.eng.parer.ricerca.ud.beans.exceptions;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.ws.rs.WebApplicationException;

public class AppGenericRuntimeException extends WebApplicationException {

    private static final long serialVersionUID = 5015771412184277789L;

    private final ErrorCategory category;

    public AppGenericRuntimeException(AppGenericRuntimeExceptionBuilder builder) {
	super(builder.message, builder.cause);
	this.category = builder.category;
    }

    public ErrorCategory getCategory() {
	return category;
    }

    public static AppGenericRuntimeExceptionBuilder builder() {
	return new AppGenericRuntimeExceptionBuilder();
    }

    @Override
    public String getMessage() {
	return "[" + getCategory().toString() + "]" + super.getMessage();
    }

    public static class AppGenericRuntimeExceptionBuilder {

	private ErrorCategory category = ErrorCategory.INTERNAL_ERROR; // default
	private String message;
	private Throwable cause;

	public AppGenericRuntimeException build() {
	    return new AppGenericRuntimeException(this);
	}

	public AppGenericRuntimeExceptionBuilder category(ErrorCategory category) {
	    this.setCategory(category);
	    return this;
	}

	public ErrorCategory getCategory() {
	    return category;
	}

	public void setCategory(ErrorCategory category) {
	    this.category = category;
	}

	public AppGenericRuntimeExceptionBuilder message(String messageToFormat, Object... args) {
	    this.setMessage(MessageFormat.format(messageToFormat, args));
	    return this;
	}

	public String getMessage() {
	    return message;
	}

	public void setMessage(String message) {
	    this.message = message;
	}

	public AppGenericRuntimeExceptionBuilder cause(Throwable cause) {
	    this.setCause(cause);
	    return this;
	}

	@JsonIgnore
	public Throwable getCause() {
	    return cause;
	}

	public void setCause(Throwable cause) {
	    this.cause = cause;
	}
    }
}
