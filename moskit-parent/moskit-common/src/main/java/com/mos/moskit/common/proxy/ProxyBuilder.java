package com.mos.moskit.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class ProxyBuilder<T> {
	private final Class<T> clazz;
	private final Map<MethodPattern, MethodAction<T>> customActions;
	private Optional<MethodExceptionHandler<T>> exceptionHandler;
	private Optional<MethodAction<T>> defaultAction;
	private final ModifiableProperty<T> real;

	private ProxyBuilder(Class<T> clazz) {
		this.clazz = clazz;
		this.customActions = new HashMap<>();
		this.exceptionHandler = Optional.empty();
		this.defaultAction = Optional.empty();
		this.real = new ModifiableProperty<T>();
	}

	public static <T> ProxyBuilder<T> creatProxyBuilder(Class<T> clazz) {
		if (!clazz.isInterface()) {
			throw new IllegalArgumentException(String.format("type: %s is not an interface.", String.valueOf(clazz)));
		}
		return new ProxyBuilder<T>(clazz);
	}

	public ProxyBuilder<T> withReal(T real) {
		this.real.set(real);
		return this;
	}

	public ProxyBuilder<T> withOverridden(MethodPattern pattern, MethodAction<T> action) {
		customActions.put(pattern, action);
		return this;
	}

	public ProxyBuilder<T> withDefaultAction(MethodAction<T> action) {
		this.defaultAction = Optional.ofNullable(action);
		return this;
	}

	public ProxyBuilder<T> withExceptionHandler(MethodExceptionHandler<T> exceptionHandler) {
		this.exceptionHandler = Optional.ofNullable(exceptionHandler);
		return this;
	}

	@SuppressWarnings("unchecked")
	public T build() {
		InvocationHandler invocationHandler = createInvocationHandler();
		return (T) Proxy.newProxyInstance(ProxyBuilder.class.getClassLoader(), new Class<?>[] { clazz }, invocationHandler);
	}

	private InvocationHandler createInvocationHandler() {
		return new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				@SuppressWarnings("unchecked")
				InvokedMethod<T> invoked = new InvokedMethod<>(real, (T) proxy, method, args);

				Object result;
				try {
					return result = invokeIntern(invoked);
				} catch (Exception exception) {
					if (exceptionHandler.isPresent()) {
						result = exceptionHandler.get().handle(exception, invoked);
					} else {
						throw exception;
					}
				}
				return result;
			}

		};
	}

	private Object invokeIntern(InvokedMethod<T> invoked) throws Exception {
		MethodAction<T> overridden = getOverriden(invoked.getMethod());
		return overridden == null ? invokeReal(invoked) : overridden.invoke(invoked);
	}

	private Object invokeReal(InvokedMethod<T> invoked) throws Exception {
		return real.isPresent() ? invoked.invoke(real.get()) : null;
	}

	private MethodAction<T> getOverriden(Method method) {
		MethodAction<T> overidded = defaultAction.orElse(null);
		for (Entry<MethodPattern, MethodAction<T>> entry : customActions.entrySet()) {
			if (entry.getKey().matches(method)) {
				overidded = entry.getValue();
				break;
			}
		}
		return overidded;
	}

}
