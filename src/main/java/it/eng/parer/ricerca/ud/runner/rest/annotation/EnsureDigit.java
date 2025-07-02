/**
 *
 */
package it.eng.parer.ricerca.ud.runner.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation custom per verificare che il valore passato sia un Integer valido
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.FIELD, ElementType.PARAMETER })
public @interface EnsureDigit {

    boolean integer() default true;

    boolean decimal() default false;

}