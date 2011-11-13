package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcherImpl;
import fr.irit.smac.may.lib.components.meta.Data;
import fr.irit.smac.may.lib.components.meta.DataImpl;

public class ClassicNamedAgentComponentImpl<Msg, Ref> extends ClassicNamedAgentComponent<Msg, Ref> {

	private final Ref name;
	private final ClassicNamedBehaviour<Msg, Ref> beh;
	
	public ClassicNamedAgentComponentImpl(Ref name, ClassicNamedBehaviour<Msg, Ref> beh) {
		this.name = name;
		this.beh = beh;
	}

	@Override
	protected Data<Ref> make_name() {
		return new DataImpl<Ref>(name);
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
