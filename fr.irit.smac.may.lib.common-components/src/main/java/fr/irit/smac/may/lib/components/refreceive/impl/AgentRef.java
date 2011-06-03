package fr.irit.smac.may.lib.components.refreceive.impl;

import java.io.Serializable;

public class AgentRef<Msg> implements Serializable {

	private static final long serialVersionUID = 683175997362391141L;
	
	private ReceiveImpl<Msg>.AgentSide ref;
	
	AgentRef(ReceiveImpl<Msg>.AgentSide ref, String name) {
		this.ref = ref;
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
		return ref.getName();
	}

}
