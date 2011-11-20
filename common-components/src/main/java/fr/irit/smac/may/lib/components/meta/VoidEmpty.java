package fr.irit.smac.may.lib.components.meta;

import java.lang.reflect.Method;

public class VoidEmpty<I> extends VoidImpl<I> {

	public VoidEmpty(Class<I> clazz) {
		super(clazz);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// do nothing on purpose
		return null;
	}

}
