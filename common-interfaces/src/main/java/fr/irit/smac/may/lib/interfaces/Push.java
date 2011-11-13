package fr.irit.smac.may.lib.interfaces;

public interface Push<T> {

	/**
	 * Push a thing to the port provider
	 * 
	 * @param thing
	 */
	public void push(T thing);
}
