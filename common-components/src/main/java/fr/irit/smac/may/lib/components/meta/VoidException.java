package fr.irit.smac.may.lib.components.meta;

import java.lang.reflect.Method;

public class VoidException<I> extends VoidImpl<I> {

	public VoidException(@SuppressWarnings("rawtypes") Class clazz) {
		super(clazz);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		throw new RuntimeException("method "+method+ " should not be called.");
	}
}
