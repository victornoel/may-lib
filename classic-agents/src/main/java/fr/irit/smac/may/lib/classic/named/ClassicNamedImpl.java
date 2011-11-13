package fr.irit.smac.may.lib.classic.named;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.interfaces.CreateNamed;
import fr.irit.smac.may.lib.components.messaging.MapReceiver;
import fr.irit.smac.may.lib.components.messaging.MapReceiverImpl;
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

public class ClassicNamedImpl<Msg> extends ClassicNamed<Msg> {

	private SchedulerImpl scheduler;
	private ForwardImpl<Send<Msg, String>> send;
	private ReceiverImpl<Msg> realReceive;
	private ForwardImpl<CreateNamed<Msg,String>> forward;
	private MapReceiverImpl<Msg, AgentRef, String> receive;
	
	private volatile int i = 0;
	
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
	protected Forward<Send<Msg, String>> make_sender() {
		send = new ForwardImpl<Send<Msg, String>>();
		return send;
	}

	@Override
	protected Receiver<Msg> make_realReceive() {
		realReceive = new ReceiverImpl<Msg>();
		return realReceive;
	}
	
	@Override
	protected MapReceiver<Msg,AgentRef,String> make_receive() {
		receive = new MapReceiverImpl<Msg,AgentRef,String>();
		return receive;
	}

	@Override
	protected Forward<CreateNamed<Msg, String>> make_fact() {
		forward = new ForwardImpl<CreateNamed<Msg,String>>();
		return forward;
	}
	@Override
	protected CreateNamed<Msg, String> create() {
		return new CreateNamed<Msg, String>() {
			public String create(
					final ClassicNamedBehaviour<Msg, String> beh, String name) {
				ClassicNamedAgent<Msg> agent = new ClassicNamedAgent<Msg>(
						new ClassicNamedAgentComponentImpl<Msg,String>(name,beh),
						scheduler.new AgentSide(), forward.new AgentSide(),
						receive.new AgentSide(), realReceive.new AgentSide("agent"+(i++)), send.new AgentSide());
				agent.start();
				return agent.component().me().pull();
			}
		};
	}

}
