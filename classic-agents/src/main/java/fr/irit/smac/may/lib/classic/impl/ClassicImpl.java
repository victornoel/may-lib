package fr.irit.smac.may.lib.classic.impl;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.messaging.receiver.AgentRef;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.messaging.receiver.ReceiverImpl;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.meta.ForwardImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.SchedulerImpl;
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
	protected Forward<Send<Msg, AgentRef>> make_sender() {
		return new ForwardImpl<Send<Msg, AgentRef>>();
	}

	@Override
	protected Receiver<Msg> make_receive() {
		return new ReceiverImpl<Msg>();
	}

	@Override
	protected Forward<CreateClassic<Msg, AgentRef>> make_fact() {
		return new ForwardImpl<CreateClassic<Msg,AgentRef>>();
	}
	
	@Override
	protected CreateClassic<Msg, AgentRef> create() {
		return new CreateClassic<Msg, AgentRef>() {
			public AgentRef create(
					final ClassicBehaviour<Msg, AgentRef> beh) {
				ClassicAgent.Component<Msg> agent = createClassicAgent(beh, "agent"+(i++));
				agent.start();
				return agent.ref().pull();
			}
		};
	}

	@Override
	protected fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent<Msg> make_ClassicAgent(
			final ClassicBehaviour<Msg, AgentRef> beh, String name) {
		return new ClassicAgent<Msg>() {
			@Override
			protected ClassicAgentComponent<Msg, AgentRef> make_arch() {
				return new ClassicAgentComponentImpl<Msg>(beh);
			}
		};
	}
}
