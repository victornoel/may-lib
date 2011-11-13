package fr.irit.smac.may.lib.classic.interfaces;

import fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour;


public interface CreateNamed<Msg,Ref> {

	public Ref create(ClassicNamedBehaviour<Msg, Ref> beh, Ref r);
}
