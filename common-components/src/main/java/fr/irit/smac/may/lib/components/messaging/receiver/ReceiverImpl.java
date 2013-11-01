package fr.irit.smac.may.lib.components.messaging.receiver;

import fr.irit.smac.may.lib.components.messaging.exceptions.AgentDoesNotExistException;
import fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend;

public class ReceiverImpl<Msg> extends Receiver<Msg> {
	
	@Override
	public ReliableSend<Msg, AgentRef> make_deposit() {
		return new ReliableSend<Msg, AgentRef>() {
			public void send(Msg msg, AgentRef receiver) {
				try {
					provides().deposit().reliableSend(msg, receiver);
				} catch (AgentDoesNotExistException e) {
					// do nothing, on purpose!
				}
			}

			public void reliableSend(Msg message, AgentRef receiver)
					throws AgentDoesNotExistException {
				// TODO using nested classes
				@SuppressWarnings("unchecked")
				ReceiverAgentImpl<Msg>.AgentRefImpl agentRefImpl = (ReceiverAgentImpl<Msg>.AgentRefImpl)receiver;
				agentRefImpl.receive(message);
			};
		};
	}

	@Override
	protected Agent<Msg> make_Agent(
			String name) {
		return new ReceiverAgentImpl<Msg>(name);
	}

}
