package fr.irit.smac.may.lib.classic.interfaces;

import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;


public interface CreateClassic<Msg, Ref> {

	public Ref create(ClassicBehaviour<Msg, Ref> beh);
	
}
