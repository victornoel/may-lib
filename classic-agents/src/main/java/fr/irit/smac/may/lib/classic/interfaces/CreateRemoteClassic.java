package fr.irit.smac.may.lib.classic.interfaces;

import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.remote.place.Place;

public interface CreateRemoteClassic<Msg, Ref> {

	public Ref create(RemoteClassicBehaviour<Msg, Ref> beh);
	
	public Ref create(RemoteClassicBehaviour<Msg, Ref> beh, Place place);
}
