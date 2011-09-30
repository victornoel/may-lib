package fr.irit.smac.may.lib.classic.impl;

import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcherImpl;
import fr.irit.smac.may.lib.components.messaging.receiver.AgentRef;

public class ClassicAgentComponentImpl<Msg> extends ClassicAgentComponent<Msg, AgentRef> {
	
	private final ClassicBehaviour<Msg, AgentRef> beh;

	public ClassicAgentComponentImpl(
			ClassicBehaviour<Msg, AgentRef> beh) {
		this.beh = beh;
	}

	@Override
	protected SequentialDispatcher<Msg> make_dispatcher() {
		return new SequentialDispatcherImpl<Msg>();
	}

	@Override
	public ClassicBehaviour<Msg, AgentRef> make_beh() {
		return beh;
	}
}
