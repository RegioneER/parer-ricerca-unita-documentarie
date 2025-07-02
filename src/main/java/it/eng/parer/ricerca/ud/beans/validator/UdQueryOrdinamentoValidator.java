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
