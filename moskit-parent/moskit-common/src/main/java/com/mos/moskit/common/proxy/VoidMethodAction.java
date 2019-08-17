package com.mos.moskit.common.proxy;

public abstract class VoidMethodAction implements MethodAction {

	@Override
	public final Object invoke(InvokedMethod invoked) throws Exception {
		invokeIntern(invoked);
		return null;
	}

	protected abstract void invokeIntern(InvokedMethod invoked) throws Exception;

}
