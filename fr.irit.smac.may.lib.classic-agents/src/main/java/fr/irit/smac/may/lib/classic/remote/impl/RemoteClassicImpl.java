package fr.irit.smac.may.lib.classic.remote.impl;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.components.ExecutorService;
import fr.irit.smac.may.lib.components.Placed;
import fr.irit.smac.may.lib.components.ReferenceReceiver;
import fr.irit.smac.may.lib.components.ReferenceSender;
import fr.irit.smac.may.lib.components.RemoteReferenceReceiver;
import fr.irit.smac.may.lib.components.Scheduler;
import fr.irit.smac.may.lib.components.impl.FixedThreadPoolExecutorServiceImpl;
import fr.irit.smac.may.lib.components.impl.SchedulerImpl;
import fr.irit.smac.may.lib.components.impl.SendImpl;
import fr.irit.smac.may.lib.components.refreceive.impl.AgentRef;
import fr.irit.smac.may.lib.components.refreceive.impl.ReceiveImpl;
import fr.irit.smac.may.lib.components.remplace.impl.Place;
import fr.irit.smac.may.lib.components.remplace.impl.PlacedImpl;
import fr.irit.smac.may.lib.components.remrefreceive.impl.RemoteAgentRef;
import fr.irit.smac.may.lib.components.remrefreceive.impl.RemoteRefReceiveImpl;

public class RemoteClassicImpl<Msg> extends RemoteClassic<Msg> {

	private final int port;
	private SchedulerImpl scheduler;
	private SendImpl<Msg, RemoteAgentRef<Msg>> send;
	private ReceiveImpl<Msg> receive;
	private RemoteRefReceiveImpl<Msg, AgentRef<Msg>> remoteRefReceive;
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
	public ReferenceSender<Msg, RemoteAgentRef<Msg>> make_sender() {
		send = new SendImpl<Msg, RemoteAgentRef<Msg>>();
		return send;
	}

	@Override
	public ReferenceReceiver<Msg> make_receive() {
		receive = new ReceiveImpl<Msg>();
		return receive;
	}

	@Override
	public RemoteReferenceReceiver<Msg, AgentRef<Msg>> make_remReceive() {
		remoteRefReceive = new RemoteRefReceiveImpl<Msg, AgentRef<Msg>>();
		return remoteRefReceive;
	}

	@Override
	public ExecutorService make_executor() {
		return new FixedThreadPoolExecutorServiceImpl(5);
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
