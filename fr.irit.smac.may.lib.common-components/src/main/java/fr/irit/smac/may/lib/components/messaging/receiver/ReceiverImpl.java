package fr.irit.smac.may.lib.components.messaging.receiver;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

public class ReceiverImpl<Msg> extends Receiver<Msg> {
	
	// TODO
	/**
	 * Use directly the inner class, since it is only created by ReceiverImpl
	 * we can avoid to publish the Msg abstract type!
	 */
	public class AgentRefImpl implements AgentRef {

		private static final long serialVersionUID = 683175997362391141L;
		
		private ReceiverImpl<Msg>.AgentSide ref;
		private final String name;
		
		AgentRefImpl(ReceiverImpl<Msg>.AgentSide ref, String name) {
			this.ref = ref;
			this.name = name;
		}

		void receive(Msg m) {
			if (this.ref != null) this.ref.receive(m);
		}
		
		void stop() {
			// allow for garbage collection
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
	public Send<Msg, AgentRef> deposit() {
		return new Send<Msg, AgentRef>() {
			public void send(Msg msg, AgentRef receiver) {
				// TODO
				((AgentRefImpl)receiver).receive(msg);
			};
		};
	}

}
