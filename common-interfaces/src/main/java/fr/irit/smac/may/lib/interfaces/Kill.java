package fr.irit.smac.may.lib.interfaces;

public interface Kill<R> {

	/**
	 * Kill an agent referenced by the reference r of type R
	 * 
	 */
	public void kill(R r);
}
