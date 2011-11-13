package fr.irit.smac.may.lib.components.remote.messaging.receiver;

import java.io.Serializable;


public interface RemoteAgentRef extends Serializable {}

/*
public class RemoteAgentRef<Msg> implements Serializable {

	private static final long serialVersionUID = 3786174379034488447L;
	
	final RemoteAgent<Msg> agent;

	private final Place place;

	private final String name;

	RemoteAgentRef(RemoteAgent<Msg> agent, Place place, String name) {
		this.place = place;
		this.name = name;
		this.agent = agent;
	}
	
	@Override
	public String toString() {
		return name+"@"+place;
	};
}
*/