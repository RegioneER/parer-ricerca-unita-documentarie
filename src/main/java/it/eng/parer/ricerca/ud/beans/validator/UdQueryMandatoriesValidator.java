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
