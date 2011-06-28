package fr.irit.smac.may.lib.components.refreceive.impl;

import java.io.Serializable;

public class AgentRef<Msg> implements Serializable {

	private static final long serialVersionUID = 683175997362391141L;
	
	private ReceiveImpl<Msg>.AgentSide ref;
	private final String name;
	
	AgentRef(ReceiveImpl<Msg>.AgentSide ref, String name) {
		this.ref = ref;
		this.name = name;
	}

	void receive(Msg m) {
		this.ref.receive(m);
	}
	
	void stop() {
		// allow for garbage collection
		this.ref = null;
	}
	
	@Override
	public String toString() {
		return name + (ref == null ? "(dead agent)" : "");
	}

}
