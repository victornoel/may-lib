package fr.irit.smac.may.lib.interfaces;

public interface Send<T,R> {
	
	/**
	 * Send message to receiver
	 * 
	 * Should not block
	 * 
	 * @param message
	 * @param receiver
	 */
	public void send(T message, R receiver);

}
