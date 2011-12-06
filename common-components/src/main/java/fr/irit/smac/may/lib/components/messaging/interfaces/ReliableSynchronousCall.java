package fr.irit.smac.may.lib.components.messaging.interfaces;

import fr.irit.smac.may.lib.components.messaging.exceptions.CallRefDoesNotExistException;

public interface ReliableSynchronousCall<I,R> {

	/**
	 * Gives access to interface I on entity of reference ref
	 * 
	 * @param ref ref to call
	 * @return the interface to be called
	 */
	public I call(R ref) throws CallRefDoesNotExistException;
}
