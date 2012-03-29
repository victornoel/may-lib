package fr.irit.smac.may.lib.components.interactions.interfaces;

import fr.irit.smac.may.lib.components.interactions.exceptions.RefDoesNotExistsException;

public interface Call<I,R> {
	
	public I call(R ref) throws RefDoesNotExistsException;

}
