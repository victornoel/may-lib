package fr.irit.smac.may.lib.classic.interfaces;

import fr.irit.smac.may.lib.classic.named.AbstractClassicNamedBehaviour;


public interface CreateNamed<Msg,Ref> {

	public Ref create(AbstractClassicNamedBehaviour<Msg, Ref> beh, Ref r);
}
