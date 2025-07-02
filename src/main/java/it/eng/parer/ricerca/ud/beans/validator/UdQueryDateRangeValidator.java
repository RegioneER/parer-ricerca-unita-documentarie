/**
 *
 */
package it.eng.parer.ricerca.ud.beans.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

import it.eng.parer.ricerca.ud.runner.rest.input.UdQuery;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

public class UdQueryDateRangeValidator implements
	ConstraintValidator<it.eng.parer.ricerca.ud.beans.validator.UdQueryDateRangeValidator.ValidUdQueryDateRange, UdQuery> {

    @Override
    public boolean isValid(UdQuery value, ConstraintValidatorContext context) {
	// check range date validation
	return (Objects.isNull(value.dtudda) && Objects.isNull(value.dtuda)
		&& Objects.isNull(value.dtversda) && Objects.isNull(value.dtversa))
		|| (Objects.nonNull(value.dtudda) && Objects.nonNull(value.dtuda)
			&& !value.dtuda.before(value.dtudda))
		|| (Objects.nonNull(value.dtversda) && Objects.nonNull(value.dtversa)
			&& !value.dtversa.before(value.dtversda));
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {
	    UdQueryDateRangeValidator.class })
    public @interface ValidUdQueryDateRange {
	String message() default "Filtro impostato non corretto";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
    }
}
