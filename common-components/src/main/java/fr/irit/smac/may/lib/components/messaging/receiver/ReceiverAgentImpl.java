package fr.irit.smac.may.lib.components.messaging.receiver;

import fr.irit.smac.may.lib.components.messaging.exceptions.AgentDoesNotExistException;
import fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class ReceiverAgentImpl<Msg> extends Receiver.Agent<Msg> {

	// TODO
	/**
	 * Use directly the inner class, since it is only created by ReceiverImpl
	 * we can avoid to publish the Msg abstract type!
	 */
	public class AgentRefImpl implements AgentRef {

		private static final long serialVersionUID = 683175997362391141L;
		
		private ReceiverAgentImpl<Msg> ref;
		private final String name;
		
		private AgentRefImpl(ReceiverAgentImpl<Msg> ref, String name) {
			this.ref = ref;
			this.name = name;
		}

		protected void receive(Msg m) throws AgentDoesNotExistException {
			if (this.ref != null) this.ref.receive(m);
			else throw new AgentDoesNotExistException();
		}
		
		private void stop() {
			// allow for garbage collection and reliability checks
			this.ref = null;
		}
		
		public String getName() {
			return this.name;
		}
		
		@Override
		public String toString() {
			return this.name + (this.ref == null ? "(dead agent)" : "");
		}

	}
	
	private final AgentRefImpl agentRef;
	
	/**
	 * Name is not used as a reference
	 */
	public ReceiverAgentImpl(String name) {
		this.agentRef = new AgentRefImpl(this, name);
	}
	
	void receive(Msg m) {
		put().push(m);
	}
	
	@Override
	protected Pull<AgentRef> me() {
		return new Pull<AgentRef>() {
			public AgentRef pull() {
				return ReceiverAgentImpl.this.agentRef;
			}
		};
	}

	@Override
	protected Do stop() {
		return new Do() {
			public void doIt() {
				ReceiverAgentImpl.this.agentRef.stop();
			}
		};
	}

	@Override
	protected ReliableSend<Msg, AgentRef> send() {
		return new ReliableSend<Msg, AgentRef>() {
			public void reliableSend(Msg message, AgentRef receiver) throws AgentDoesNotExistException {
				infraSelf().deposit().reliableSend(message, receiver);
			};
			public void send(Msg message, AgentRef receiver) {
				infraSelf().deposit().send(message, receiver);
			};
		};
	}
}

