package com.mos.moskit.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;

@Converter
public class ClassConverter implements AttributeConverter<Class<?>, String> {

	@Override
	public String convertToDatabaseColumn(Class<?> attribute) {
		return attribute == null ? null : attribute.getName();
	}

	@Override
	public Class<?> convertToEntityAttribute(String databaseValue) {
		try {
			return StringUtils.isEmpty(databaseValue) ? null : Class.forName(databaseValue);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}