package fr.irit.smac.may.lib.classic.impl;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.interactions.AsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.DirectReferences;
import fr.irit.smac.may.lib.components.interactions.asyncreceiver.AsyncReceiverImpl;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirectReferencesImpl;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.meta.ForwardImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.SchedulerImpl;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

public class ClassicImpl<Msg> extends Classic<Msg> {

	private volatile int i = 0;
	
	@Override
	protected Scheduler make_scheduler() {
		return new SchedulerImpl();
	}

	@Override
	protected ExecutorService make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}

	@Override
	protected Forward<Send<Msg, DirRef>> make_sender() {
		return new ForwardImpl<Send<Msg, DirRef>>();
	}

	@Override
	protected AsyncReceiver<Msg, DirRef> make_receive() {
		return new AsyncReceiverImpl<Msg, DirRef>();
	}
	
	@Override
	protected DirectReferences<Push<Msg>> make_refs() {
		return new DirectReferencesImpl<Push<Msg>>();
	}

	@Override
	protected Forward<CreateClassic<Msg, DirRef>> make_fact() {
		return new ForwardImpl<CreateClassic<Msg,DirRef>>();
	}
	
	@Override
	protected CreateClassic<Msg, DirRef> make_create() {
		return new CreateClassic<Msg, DirRef>() {
			public DirRef create(
					final ClassicBehaviour<Msg, DirRef> beh) {
				ClassicAgent.Component<Msg> agent = newClassicAgent(beh, "agent"+(i++));
				agent.start();
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
