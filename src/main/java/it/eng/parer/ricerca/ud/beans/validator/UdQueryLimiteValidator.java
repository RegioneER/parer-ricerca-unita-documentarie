/**
 *
 */
package it.eng.parer.ricerca.ud.beans.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import it.eng.parer.ricerca.ud.runner.rest.input.UdQuery;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

public class UdQueryLimiteValidator implements
	ConstraintValidator<it.eng.parer.ricerca.ud.beans.validator.UdQueryLimiteValidator.ValidUdQueryLimite, UdQuery> {

    @ConfigProperty(name = "parer.ricerca.ud.limite")
    Integer limite;

    @Override
    public boolean isValid(UdQuery value, ConstraintValidatorContext context) {
	// disattivazione del constraint standard
	context.disableDefaultConstraintViolation();
	// injection del valore dalla configproperty
	context.buildConstraintViolationWithTemplate(
		MessageFormat.format(context.getDefaultConstraintMessageTemplate(), limite))
		.addConstraintViolation();
	// check limite (positivo e non superiore al limite massimo rappresentato dall'Integer ossia
	// : 2,147,483,647)
	return Objects.isNull(value.limite)
		|| Objects.nonNull(value.limite) && (value.limite.compareTo(limite) <= 0
			&& value.limite.compareTo(BigInteger.ZERO.intValue()) > 0);

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {
	    UdQueryLimiteValidator.class })
    public @interface ValidUdQueryLimite {
	String message() default "Filtro impostato non corretto";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
    }

}
