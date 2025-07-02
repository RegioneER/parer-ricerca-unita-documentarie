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

public class UdQueryMandatoriesValidator implements
	ConstraintValidator<it.eng.parer.ricerca.ud.beans.validator.UdQueryMandatoriesValidator.ValidUdQueryMandatories, UdQuery> {

    @Override
    public boolean isValid(UdQuery value, ConstraintValidatorContext context) {
	// check mandatories (no paging)
	return value.nextpagetoken.isEmpty() && Objects.nonNull(value.amb)
		&& Objects.nonNull(value.ente) && Objects.nonNull(value.strut)
		&& Objects.nonNull(value.anno)
		|| value.nextpagetoken.isPresent()
			&& (Objects.isNull(value.amb) && Objects.isNull(value.ente)
				&& Objects.isNull(value.strut) && Objects.isNull(value.anno)
				&& Objects.isNull(value.dtuda) && Objects.isNull(value.dtudda)
				&& Objects.isNull(value.dtversda) && Objects.isNull(value.dtversa)
				&& Objects.isNull(value.limite) && value.numero.isEmpty()
				&& value.registro.isEmpty() && value.tipoud.isEmpty()
				&& value.dataversamento.isEmpty() && value.userid.isEmpty());

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {
	    UdQueryMandatoriesValidator.class })
    public @interface ValidUdQueryMandatories {
	String message() default "Filtro impostato non corretto";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
    }
}
