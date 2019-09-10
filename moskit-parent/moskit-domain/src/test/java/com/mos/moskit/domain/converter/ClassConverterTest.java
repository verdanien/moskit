package com.mos.moskit.domain.converter;

import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.converters.Nullable;

@RunWith(JUnitParamsRunner.class)
public class ClassConverterTest {

	private ClassConverter classConverter;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		classConverter = new ClassConverter();
	}

	@Test
	//@formatter:off
	@Parameters({
		"java.lang.String, java.lang.String",
		"java.lang.Object, java.lang.Object",
		"null, null"
		})
	//@formatter:on
	public void testConvertToDatabaseColumn(@Nullable Class<?> className, @Nullable String expectation) throws Exception {
		// when
		String result = classConverter.convertToDatabaseColumn(className);

		// then
		Assert.assertThat(result, equalTo(expectation));
	}

	@Test
	//@formatter:off
	@Parameters({
		"java.lang.String, java.lang.String",
		"java.lang.Object, java.lang.Object",
		"null, null",
		", null"
	})
	//@formatter:on
	public void testConvertToEntityAttribute(@Nullable String given, @Nullable Class<?> expected) {
		// when
		Class<?> result = classConverter.convertToEntityAttribute(given);

		// then
		Assert.assertThat(result, equalTo(expected));
	}

	@Test
	//@formatter:off
	@Parameters({
		"invalid_class_name, invalid_class_name",
	})
	//@formatter:on
	public void testConvertToEntityAttribute_ThrowsException_ifInvalidClassName(@Nullable String given, String expectedErrorMessage) {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(expectedErrorMessage);

		classConverter.convertToEntityAttribute(given);
	}
}