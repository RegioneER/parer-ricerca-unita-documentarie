/**
 *
 */
package it.eng.parer.ricerca.ud.beans.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import it.eng.parer.ricerca.ud.beans.utils.Costants.OrderType;
import it.eng.parer.ricerca.ud.runner.rest.input.UdQuery;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

public class UdQueryOrdinamentoValidator implements
	ConstraintValidator<it.eng.parer.ricerca.ud.beans.validator.UdQueryOrdinamentoValidator.ValidUdQueryOrdinamento, UdQuery> {

    @Override
    public boolean isValid(UdQuery value, ConstraintValidatorContext context) {
	// check mandatories (no paging)
	return value.dataversamento.isEmpty()
		|| value.dataversamento.isPresent() && Arrays.stream(OrderType.values()).anyMatch(
			order -> order.name().equalsIgnoreCase(value.dataversamento.get()));

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {
	    UdQueryOrdinamentoValidator.class })
    public @interface ValidUdQueryOrdinamento {
	String message() default "Filtro impostato non corretto";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
    }
}
