package fr.irit.smac.may.lib.classic.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.Classic;
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

	private SchedulerImpl scheduler;
	private ForwardImpl<Send<Msg, AgentRef>> send;
	private ReceiverImpl<Msg> receive;
	private ForwardImpl<CreateClassic<Msg,AgentRef>> forward;

	private AtomicInteger i = new AtomicInteger(0);
	
	@Override
	protected Scheduler make_scheduler() {
		scheduler = new SchedulerImpl();
		return scheduler;
	}

	@Override
	protected ExecutorService make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}

	@Override
	protected Forward<Send<Msg, AgentRef>> make_sender() {
		send = new ForwardImpl<Send<Msg, AgentRef>>();
		return send;
	}

	@Override
	protected Receiver<Msg> make_receive() {
		receive = new ReceiverImpl<Msg>();
		return receive;
	}

	@Override
	protected Forward<CreateClassic<Msg, AgentRef>> make_fact() {
		forward = new ForwardImpl<CreateClassic<Msg,AgentRef>>();
		return forward;
	}
	@Override
	protected CreateClassic<Msg, AgentRef> create() {
		return new CreateClassic<Msg, AgentRef>() {
			public AgentRef create(
					final ClassicBehaviour<Msg, AgentRef> beh) {
				ClassicAgent<Msg> agent = new ClassicAgent<Msg>(
						new ClassicAgentComponentImpl<Msg>(beh),
						scheduler.new AgentSide(), forward.new AgentSide(),
						receive.new AgentSide("agent"+i.getAndIncrement()), send.new AgentSide());
				agent.start();
				return agent.receive().me().pull();
			}
		};
	}
}
