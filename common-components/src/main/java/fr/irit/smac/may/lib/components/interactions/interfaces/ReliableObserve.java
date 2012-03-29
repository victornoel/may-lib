package fr.irit.smac.may.lib.components.interactions.interfaces;

import fr.irit.smac.may.lib.components.interactions.exceptions.RefDoesNotExistsException;

public interface ReliableObserve<V, R> extends Observe<V, R> {
	
	public V reliableObserve(R ref) throws RefDoesNotExistsException;

}
