package com.mos.moskit.common.proxy;

public interface MethodAction<T> {

	Object invoke(InvokedMethod<T> invoked) throws Exception;

}
