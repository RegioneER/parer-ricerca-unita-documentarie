/**
 *
 */
package it.eng.parer.ricerca.ud.jpa.converter;

import java.util.Objects;

import jakarta.persistence.AttributeConverter;

public class FlBooleanConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
	return Objects.nonNull(attribute) && attribute.booleanValue() ? "1" : "0";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
	return dbData.equalsIgnoreCase("1");
    }
}