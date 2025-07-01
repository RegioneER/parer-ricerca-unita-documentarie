/**
 *
 */
package it.eng.parer.ricerca.ud.runner.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation custom per la gestione del formato data (non presente annotation su Quarkus per field
 * utilizzando @BeanParam)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.FIELD, ElementType.PARAMETER })
public @interface DateFormat {

    public static final String DEFAULT_DATE = "dd-MM-yyyy";

    String value() default DEFAULT_DATE;
}