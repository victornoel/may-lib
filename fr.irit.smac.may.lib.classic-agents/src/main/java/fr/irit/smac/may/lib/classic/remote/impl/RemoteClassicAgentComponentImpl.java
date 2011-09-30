package fr.irit.smac.may.lib.classic.remote.impl;

import fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcherImpl;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.interfaces.Do;

public class RemoteClassicAgentComponentImpl<Msg> extends RemoteClassicAgentComponent<Msg, RemoteAgentRef> {
	private final RemoteClassicBehaviour<Msg, RemoteAgentRef> beh;
	public RemoteClassicAgentComponentImpl(RemoteClassicBehaviour<Msg, RemoteAgentRef> beh) {
		this.beh = beh;
	}
	@Override
	protected SequentialDispatcher<Msg> make_dispatcher() {
		return new SequentialDispatcherImpl<Msg>();
	}
	@Override
	public RemoteClassicBehaviour<Msg, RemoteAgentRef> make_beh() {
		return beh;
	}
	
	@Override
	protected Do die() {
		return new Do() {
			public void doIt() {
				stopExec().doIt();
				stopReceive().doIt();
			}
		};
	}
}