package fr.irit.smac.may.lib.interfaces;

public interface RemoteCall<I,To> {

	/**
	 * enable to get access to I on a remote place to
	 * 
	 * @param to the remote place
	 * @return an interface to call
	 */
	I call(To to);
}
