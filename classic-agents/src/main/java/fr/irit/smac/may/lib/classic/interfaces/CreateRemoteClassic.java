package fr.irit.smac.may.lib.classic.interfaces;

import fr.irit.smac.may.lib.classic.remote.impl.AbstractRemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.remote.place.Place;

public interface CreateRemoteClassic<Msg, Ref> {

	public Ref create(AbstractRemoteClassicBehaviour<Msg, Ref> beh);
	
	public Ref create(AbstractRemoteClassicBehaviour<Msg, Ref> beh, Place place);
}
