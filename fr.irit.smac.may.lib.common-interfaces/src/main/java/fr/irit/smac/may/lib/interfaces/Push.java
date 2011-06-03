package fr.irit.smac.may.lib.interfaces;

public interface Push<T> {

	/**
	 * Push a thing to the port provider
	 * 
	 * Should not block
	 * 
	 * @param t
	 */
	public void push(T thing);
}
