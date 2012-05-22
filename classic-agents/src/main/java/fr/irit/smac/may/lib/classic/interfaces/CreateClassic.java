package fr.irit.smac.may.lib.classic.interfaces;

import fr.irit.smac.may.lib.classic.impl.AbstractClassicBehaviour;


public interface CreateClassic<Msg, Ref> {

	public Ref create(AbstractClassicBehaviour<Msg, Ref> beh);
	
}
