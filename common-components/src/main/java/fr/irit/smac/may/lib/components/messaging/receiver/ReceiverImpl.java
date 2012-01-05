package fr.irit.smac.may.lib.components.messaging.receiver;

import fr.irit.smac.may.lib.components.messaging.exceptions.AgentDoesNotExistException;
import fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend;

public class ReceiverImpl<Msg> extends Receiver<Msg> {
	
	@Override
	public ReliableSend<Msg, AgentRef> deposit() {
		return new ReliableSend<Msg, AgentRef>() {
			public void send(Msg msg, AgentRef receiver) {
				try {
					self().deposit().reliableSend(msg, receiver);
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
	protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent<Msg> make_Agent(
			String name) {
		return new ReceiverAgentImpl<Msg>(name);
	}

}
