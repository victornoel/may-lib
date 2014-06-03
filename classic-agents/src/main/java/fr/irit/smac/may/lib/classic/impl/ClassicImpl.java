package fr.irit.smac.may.lib.classic.impl;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiverImpl;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;

public class ClassicImpl<Msg> extends Classic<Msg> {

	private volatile int i = 0;
	
	@Override
	protected ExecutorServiceWrapper make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}

	@Override
	protected DirRefAsyncReceiver<Msg> make_receive() {
		return new DirRefAsyncReceiverImpl<Msg>();
	}
	
//	@Override
//	protected Forward<CreateClassic<Msg, DirRef>> make_fact() {
//		return new ForwardImpl<CreateClassic<Msg,DirRef>>();
//	}
	
	@Override
	protected CreateClassic<Msg, DirRef> make_create() {
		return new CreateClassic<Msg, DirRef>() {
			public DirRef create(
					final AbstractClassicBehaviour<Msg, DirRef> beh) {
				ClassicAgent.Component<Msg> agent = newClassicAgent(beh, "agent"+(i++));
				return agent.me().pull();
			}
		};
	}

	@Override
	protected ClassicAgent<Msg> make_ClassicAgent(final ClassicBehaviour<Msg, DirRef> beh, String name) {
		return new ClassicAgent<Msg>() {
			@Override
			protected ClassicAgentComponent<Msg, DirRef> make_arch() {
				return new ClassicAgentComponentImpl<Msg>(beh);
			}
		};
	}
}
