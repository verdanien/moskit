package com.mos.moskit.common.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import lombok.Getter;

public class InvokedMethod<T> {
	private final ModifiableProperty<T> real;
	private final @Getter T proxy;
	private final @Getter Method method;
	private final Object[] args;

	public InvokedMethod(ModifiableProperty<T> real, T proxy, Method method, Object[] args) {
		super();
		this.real = real;
		this.proxy = proxy;
		this.method = method;
		this.args = args;
	}

	/**
	 * @return array copy of given arguments
	 */
	public Object[] getArgs() {
		return args.clone();
	}

	public T getReal() {
		return real.get();
	}

	public void setReal(T real) {
		this.real.set(real);
	}

	public Object invokeReal() throws Exception {
		return invoke(real.get());
	}

	public Object invokeReal(Object[] args) throws Exception {
		return invoke(real.get(), args);
	}

	public Object invokeProxy() throws Exception {
		return invoke(proxy);
	}

	public Object invokeProxy(Object[] args) throws Exception {
		return invoke(proxy, args);
	}

	public Object invoke(T instance) throws Exception {
		if (instance == null) {
			throw new NullPointerException("instance cannot be null");
		}
		return method.invoke(instance, args);
	}

	public Object invoke(T instance, Object[] args) throws Exception {
		if (instance == null) {
			throw new NullPointerException("instance cannot be null");
		}
		validateParams(args);
		return method.invoke(instance, args);
	}

	private void validateParams(Object[] args) {
		Parameter[] parameters = method.getParameters();

		if (parameters.length != args.length) {
			throw new IllegalArgumentException(String.format("given arguments not matches to method %s definition: %s, but given %s", method.getName(),
					Arrays.toString(method.getParameterTypes()), Arrays.toString(args)));
		}

		for (int i = 0; i < args.length; i++) {
			validate(parameters[i], args[i]);
		}
	}

	private void validate(Parameter parameter, Object object) {
		Class<?> type = parameter.getType();
		if (object == null) {
			if (type.isPrimitive()) {
				throw new NullPointerException(String.format("null is not allowed for parameter %s withg primitive type %s", parameter.getName(),
						type.getName()));
			}
		} else if (!type.isAssignableFrom(object.getClass())) {
			throw new ClassCastException(String.format("given value %s cannot been cast to parameter %s with type %s", String.valueOf(object),
					parameter.getName(), type.getName()));
		}
	}
}
