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
