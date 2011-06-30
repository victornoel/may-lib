package fr.irit.smac.may.lib.classic.remote.impl;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.components.messaging.Sender;
import fr.irit.smac.may.lib.components.messaging.SenderImpl;
import fr.irit.smac.may.lib.components.messaging.receiver.AgentRef;
import fr.irit.smac.may.lib.components.messaging.receiver.ReceiveImpl;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
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

public class RemoteClassicImpl<Msg> extends RemoteClassic<Msg> {

	private final int port;
	private SchedulerImpl scheduler;
	private SenderImpl<Msg, RemoteAgentRef<Msg>> send;
	private ReceiveImpl<Msg> receive;
	private RemoteReceiverImpl<Msg, AgentRef<Msg>> remoteRefReceive;
	private RemoteFactoryImpl<Msg, RemoteAgentRef<Msg>> factory;

	private volatile int i = 0;
	private PlacedImpl placed; 
	
	public RemoteClassicImpl(final int port) {
		this.port = port;
	}

	@Override
	protected Scheduler make_scheduler() {
		scheduler = new SchedulerImpl();
		return scheduler;
	}

	@Override
	public Sender<Msg, RemoteAgentRef<Msg>> make_sender() {
		send = new SenderImpl<Msg, RemoteAgentRef<Msg>>();
		return send;
	}

	@Override
	public Receiver<Msg> make_receive() {
		receive = new ReceiveImpl<Msg>();
		return receive;
	}

	@Override
	public RemoteReceiver<Msg, AgentRef<Msg>> make_remReceive() {
		remoteRefReceive = new RemoteReceiverImpl<Msg, AgentRef<Msg>>();
		return remoteRefReceive;
	}

	@Override
	public ExecutorService make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}

	@Override
	protected RemoteFactory<Msg, RemoteAgentRef<Msg>> make_fact() {
		factory = new RemoteFactoryImpl<Msg, RemoteAgentRef<Msg>>();
		return factory;
	}

	@Override
	protected CreateRemoteClassic<Msg, RemoteAgentRef<Msg>> create() {
		return new CreateRemoteClassic<Msg, RemoteAgentRef<Msg>>() {
			public RemoteAgentRef<Msg> create(
					final RemoteClassicBehaviour<Msg, RemoteAgentRef<Msg>> beh) {

				ClassicAgent<Msg> agent = new ClassicAgent<Msg>(
						new RemoteClassicAgentComponentImpl<Msg>(beh),
						placed.new AgentSide(), factory.new AgentSide(), scheduler.new AgentSide(),
						receive.new AgentSide("agent"+(i++)), send.new AgentSide(),
						remoteRefReceive.new AgentSide());
				agent.start();
				return agent.remReceive().me().pull();
			}

			public RemoteAgentRef<Msg> create(
					RemoteClassicBehaviour<Msg, RemoteAgentRef<Msg>> beh,
					Place place) {
				return fact().factCreate().create(beh, place);
			}
		};
	}

	@Override
	protected Placed make_placed() {
		placed = new PlacedImpl(this.port);
		return placed;
	}
}
