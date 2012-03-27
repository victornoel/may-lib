package fr.irit.smac.may.lib.components.meta;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class VoidImpl<I> extends Void<I> {

	private final Class<I> clazz;

	public VoidImpl(Class<I> clazz) {
		this.clazz = clazz;
	}
	
	public abstract Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
	
	@SuppressWarnings("unchecked")
	@Override
	protected I make_port() {
		InvocationHandler handler = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return VoidImpl.this.invoke(proxy, method, args);
			}
		};
		return (I) Proxy.newProxyInstance(
									clazz.getClassLoader(),
		                            new Class[] { clazz },
		                            handler);
	}
}
