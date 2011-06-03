package fr.irit.smac.may.lib.interfaces;

public interface Pull<T> {

	/**
	 * Pull a T from the provider
	 * 
	 * Should not block
	 * 
	 * @return
	 */
	public T pull();
}
