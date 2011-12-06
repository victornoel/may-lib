package fr.irit.smac.may.lib.components.messaging.receiver;

import fr.irit.smac.may.lib.components.messaging.exceptions.AgentDoesNotExistException;
import fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class ReceiverImpl<Msg> extends Receiver<Msg> {
	
	// TODO
	/**
	 * Use directly the inner class, since it is only created by ReceiverImpl
	 * we can avoid to publish the Msg abstract type!
	 */
	private class AgentRefImpl implements AgentRef {

		private static final long serialVersionUID = 683175997362391141L;
		
		private ReceiverImpl<Msg>.AgentSide ref;
		private final String name;
		
		private AgentRefImpl(ReceiverImpl<Msg>.AgentSide ref, String name) {
			this.ref = ref;
			this.name = name;
		}

		private void receive(Msg m) throws AgentDoesNotExistException {
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
	
	public class AgentSide extends Agent<Msg> {
		
		private final AgentRefImpl agentRef;
		
		/**
		 * Name is not used as a reference
		 */
		public AgentSide(String name) {
			this.agentRef = new AgentRefImpl(this, name);
		}
		
		void receive(Msg m) {
			put().push(m);
		}
		
		@Override
		protected Pull<AgentRef> me() {
			return new Pull<AgentRef>() {
				public AgentRef pull() {
					return AgentSide.this.agentRef;
				}
			};
		}

		@Override
		protected Do stop() {
			return new Do() {
				public void doIt() {
					AgentSide.this.agentRef.stop();
				}
			};
		}
	}
	
	@Override
	public ReliableSend<Msg, AgentRef> deposit() {
		return new ReliableSend<Msg, AgentRef>() {
			public void send(Msg msg, AgentRef receiver) {
				try {
					reliableSend(msg, receiver);
				} catch (AgentDoesNotExistException e) {
					// do nothing, on purpose!
				}
			}

			public void reliableSend(Msg message, AgentRef receiver)
					throws AgentDoesNotExistException {
				// TODO using nested classes
				@SuppressWarnings("unchecked")
				AgentRefImpl agentRefImpl = (AgentRefImpl)receiver;
				agentRefImpl.receive(message);
			};
		};
	}

}
