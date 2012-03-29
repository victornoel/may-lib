package fr.irit.smac.may.lib.classic.impl;

import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcherImpl;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;

public class ClassicAgentComponentImpl<Msg> extends ClassicAgentComponent<Msg, DirRef> {
	
	private final ClassicBehaviour<Msg, DirRef> beh;

	public ClassicAgentComponentImpl(
			ClassicBehaviour<Msg, DirRef> beh) {
		this.beh = beh;
	}

	@Override
	protected SequentialDispatcher<Msg> make_dispatcher() {
		return new SequentialDispatcherImpl<Msg>();
	}

	@Override
	public ClassicBehaviour<Msg, DirRef> make_beh() {
		return beh;
	}
}
