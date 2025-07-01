/**
 *
 */
package it.eng.parer.ricerca.ud.runner.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation custom per la gestione del formato datatime (non presente annotation su Quarkus per
 * field utilizzando @BeanParam)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
	ElementType.FIELD, ElementType.PARAMETER })
public @interface DateTimeFormat {

    public static final String DEFAULT_DATE_TIME = "dd-MM-yyyy'T'HH:mm:ss";

    String value() default DEFAULT_DATE_TIME;
}
