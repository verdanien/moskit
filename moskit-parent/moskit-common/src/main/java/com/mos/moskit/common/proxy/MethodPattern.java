package com.mos.moskit.common.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

import lombok.Getter;

public class MethodPattern {
	private final @Getter String name;
	private final @Getter Class<?>[] args;

	public static MethodPatternBuilder builder() {
		return new MethodPatternBuilder();
	}

	private MethodPattern(MethodPatternBuilder builder) {
		name = builder.getName();
		args = builder.getArgs();
	}

	public boolean matches(Method method) {
		// @formatter:off
		return matchIfPresent(name, name -> method.getName().matches(name))
				&& matchIfPresent(args, args -> Arrays.equals(args, method.getParameterTypes()));
		// @formatter:on
	}

	public <T> boolean matchIfPresent(T value, Function<T, Boolean> matchFunction) {
		Boolean result = value == null ? true : matchFunction.apply(value);
		if (result == null) {
			throw new UnsupportedOperationException("result cannot be null!");
		}
		return result.booleanValue();
	}

	public static class MethodPatternBuilder {
		private @Getter String name;
		private @Getter Class<?>[] args;

		public MethodPattern build() {
			return new MethodPattern(this);
		}

		public MethodPatternBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public MethodPatternBuilder withArgs(Class<?>... args) {
			this.args = args;
			return this;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MethodPattern other = (MethodPattern) obj;
		if (!Arrays.equals(args, other.args)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
