package com.mos.moskit.common.proxy;

public class ModifiableProperty<T> {
	private T value;

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}

	public boolean isPresent() {
		return value != null;
	}
}
