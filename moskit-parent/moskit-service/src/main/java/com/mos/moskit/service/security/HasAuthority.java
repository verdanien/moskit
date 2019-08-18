package com.mos.moskit.service.security;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Repeatable(HasAuthority.HasAuthorities.class)
public @interface HasAuthority {
	String value();

	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RUNTIME)
	@Inherited
	@Documented
	public static @interface HasAuthorities {

		HasAuthority[] value();
	}
}
