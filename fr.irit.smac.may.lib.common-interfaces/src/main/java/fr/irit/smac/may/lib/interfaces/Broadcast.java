package fr.irit.smac.may.lib.interfaces;

public interface Broadcast<T> {

	/**
	 * Send message to a set of agents
	 * 
	 * @param message
	 */
	public void broadcast(T message);
}
