package fr.irit.smac.may.lib.components.distribution.ivy.interfaces;

import java.util.List;

import fr.irit.smac.may.lib.interfaces.Push;

public interface Bind {

	/**
	 * Bind regex so that callback is called when matched on a received message
	 * 
	 * @param regex
	 * @param callback
	 * @return an int representing the binding (-1 if it fails)
	 */
	public int bind(String regex, Push<List<String>> callback);
}
