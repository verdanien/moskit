package com.mos.moskit.common.proxy;

public interface MethodExceptionHandler<T> {
	Object handle(Exception exception, InvokedMethod<T> method) throws Exception;
}
