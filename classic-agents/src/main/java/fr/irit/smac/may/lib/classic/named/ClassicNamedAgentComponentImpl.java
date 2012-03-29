package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcherImpl;

public class ClassicNamedAgentComponentImpl<Msg, Ref> extends ClassicNamedAgentComponent<Msg, Ref> {

	private final ClassicNamedBehaviour<Msg, Ref> beh;
	
	public ClassicNamedAgentComponentImpl(ClassicNamedBehaviour<Msg, Ref> beh) {
		this.beh = beh;
	}

	@Override
	protected SequentialDispatcher<Msg> make_dispatcher() {
		return new SequentialDispatcherImpl<Msg>();
	}

	@Override
	protected ClassicNamedBehaviour<Msg, Ref> make_beh() {
		return beh;
	}



}
