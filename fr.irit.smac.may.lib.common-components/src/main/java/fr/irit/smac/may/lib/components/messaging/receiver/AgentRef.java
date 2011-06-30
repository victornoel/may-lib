package fr.irit.smac.may.lib.components.messaging.receiver;

import java.io.Serializable;

public class AgentRef<Msg> implements Serializable {

	private static final long serialVersionUID = 683175997362391141L;
	
	private ReceiverImpl<Msg>.AgentSide ref;
	private final String name;
	
	AgentRef(ReceiverImpl<Msg>.AgentSide ref, String name) {
		this.ref = ref;
		this.name = name;
	}

	void receive(Msg m) {
		if (this.ref != null) this.ref.receive(m);
	}
	
	void stop() {
		// allow for garbage collection
		this.ref = null;
	}
	
	@Override
	public String toString() {
		return this.name + (this.ref == null ? "(dead agent)" : "");
	}

}
