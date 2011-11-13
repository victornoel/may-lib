package fr.irit.smac.may.lib.pmbehaviour;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PatternMatchingBehavior implements Serializable {

	private static final long serialVersionUID = 7135919339938664075L;

	private final Object target;

	public PatternMatchingBehavior(Object target) {
		this.target = target;
	}

	/**
	 * Call to pattern matches a message extending the
	 * {@link PatternMatchingMessage} class.
	 * 
	 * Methods in target must have the name of classes extending
	 * {@link PatternMatchingMessage} preceded by case and parameters the types
	 * of the arguments of their constructor.
	 * 
	 * defaultCase({@link Object} m) is called if no message matches.
	 * 
	 */
	public final void match(PatternMatchingMessage msg) {
		try {
			invoke(target, msg.content, "case" + msg.getClass().getSimpleName());
		} catch (InvocationTargetException e) {
			throw new RuntimeException("exception in method", e);
		} catch (Exception e) {
			try {
				Method m = target.getClass().getMethod("defaultCase",
						Object.class);
				m.invoke(target, new Object[] { msg });
			} catch (Exception e1) {
				throw new RuntimeException("No fallback method for "
						+ msg.toString() + " in "
						+ target.getClass().getSimpleName(), e);
			}
		}
	}

	private void invoke(Object targetObject, Object[] parameters,
			String methodName) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		for (Method method : targetObject.getClass().getMethods()) {
			if (!method.getName().equals(methodName)) {
				continue;
			}
			Class<?>[] parameterTypes = method.getParameterTypes();
			boolean matches = true;
			for (int i = 0; i < parameterTypes.length; i++) {
				if (!parameterTypes[i].isAssignableFrom(parameters[i]
						.getClass())) {
					matches = false;
					break;
				}
			}
			if (matches) {
				// obtain a Class[] based on the passed arguments as Object[]
				method.invoke(targetObject, parameters);
				return;
			}
		}
		throw new NoSuchMethodException(methodName);
	}

}