package fr.irit.smac.may.lib.classic.remote.impl;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.components.messaging.receiver.AgentRef;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.messaging.receiver.ReceiverImpl;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.meta.ForwardImpl;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiverImpl;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.components.remote.place.Placed;
import fr.irit.smac.may.lib.components.remote.place.PlacedImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.SchedulerImpl;
import fr.irit.smac.may.lib.interfaces.Send;

public class RemoteClassicImpl<Msg> extends RemoteClassic<Msg> {

	private final int port;

	private volatile int i = 0;
	
	public RemoteClassicImpl(final int port) {
		this.port = port;
	}

	@Override
	protected Scheduler make_scheduler() {
		return new SchedulerImpl();
	}

	@Override
	protected Forward<Send<Msg, RemoteAgentRef>> make_sender() {
		return new ForwardImpl<Send<Msg,RemoteAgentRef>>();
	}

	@Override
	public Receiver<Msg> make_receive() {
		return new ReceiverImpl<Msg>();
	}

	@Override
	public RemoteReceiver<Msg, AgentRef> make_remReceive() {
		return new RemoteReceiverImpl<Msg, AgentRef>();
	}

	@Override
	public ExecutorService make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}

	@Override
	protected RemoteFactory<Msg, RemoteAgentRef> make_fact() {
		return new RemoteFactoryImpl<Msg, RemoteAgentRef>();
	}

	@Override
	protected CreateRemoteClassic<Msg, RemoteAgentRef> make_create() {
		return new CreateRemoteClassic<Msg, RemoteAgentRef>() {
			public RemoteAgentRef create(
					final AbstractRemoteClassicBehaviour<Msg, RemoteAgentRef> beh) {
				
				ClassicAgent.Component<Msg> agent = newClassicAgent(beh, "agent"+(i++));
				agent.start();
				return agent.ref().pull();
			}

			public RemoteAgentRef create(
					AbstractRemoteClassicBehaviour<Msg, RemoteAgentRef> beh,
					Place place) {
				return parts().fact().factCreate().create(beh, place);
			}
		};
	}

	@Override
	protected Placed make_placed() {
		return new PlacedImpl(this.port);
	}

	@Override
	protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> make_ClassicAgent(
			final AbstractRemoteClassicBehaviour<Msg, RemoteAgentRef> beh, String name) {
		return new ClassicAgent<Msg>() {
			@Override
			protected RemoteClassicAgentComponent<Msg, RemoteAgentRef> make_arch() {
				return new RemoteClassicAgentComponentImpl<Msg>(beh);
			}
		};
	}
}
