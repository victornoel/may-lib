package fr.irit.smac.may.lib.classic.impl;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.classic.local.Factory;
import fr.irit.smac.may.lib.components.ExecutorService;
import fr.irit.smac.may.lib.components.ReferenceReceiver;
import fr.irit.smac.may.lib.components.ReferenceSender;
import fr.irit.smac.may.lib.components.Scheduler;
import fr.irit.smac.may.lib.components.impl.FixedThreadPoolExecutorServiceImpl;
import fr.irit.smac.may.lib.components.impl.SchedulerImpl;
import fr.irit.smac.may.lib.components.impl.SendImpl;
import fr.irit.smac.may.lib.components.refreceive.impl.AgentRef;
import fr.irit.smac.may.lib.components.refreceive.impl.ReceiveImpl;

public class ClassicImpl<Msg> extends Classic<Msg> {

	private SchedulerImpl schedulerImpl;
	private SendImpl<Msg, AgentRef<Msg>> send;
	private ReceiveImpl<Msg> receive;
	private FactoryImpl<Msg, AgentRef<Msg>> factory;

	private volatile int i = 0;
	
	@Override
	protected Scheduler make_scheduler() {
		schedulerImpl = new SchedulerImpl();
		return schedulerImpl;
	}

	@Override
	protected ExecutorService make_executor() {
		return new FixedThreadPoolExecutorServiceImpl(5);
	}

	@Override
	protected ReferenceSender<Msg, AgentRef<Msg>> make_sender() {
		send = new SendImpl<Msg, AgentRef<Msg>>();
		return send;
	}

	@Override
	protected ReferenceReceiver<Msg> make_receive() {
		receive = new ReceiveImpl<Msg>();
		return receive;
	}

	@Override
	protected Factory<Msg, AgentRef<Msg>> make_fact() {
		factory = new FactoryImpl<Msg, AgentRef<Msg>>();
		return factory;
	}

	@Override
	protected CreateClassic<Msg, AgentRef<Msg>> create() {
		return new CreateClassic<Msg, AgentRef<Msg>>() {
			public AgentRef<Msg> create(
					final ClassicBehaviour<Msg, AgentRef<Msg>> beh) {
				ClassicAgent<Msg> agent = new ClassicAgent<Msg>(
						new ClassicAgentComponentImpl<Msg>(beh),
						schedulerImpl.new AgentSide(), factory.new AgentSide(),
						receive.new AgentSide("agent"+(i++)), send.new AgentSide());
				agent.start();
				return agent.receive().me().pull();
			}
		};
	}
}
