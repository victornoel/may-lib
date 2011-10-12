package fr.irit.smac.may.lib.components.messaging.distributed;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

public class DistributedSenderReceiverImpl<Msg, LocalRef, DistRef> extends DistributedSenderReceiver<Msg, LocalRef, DistRef> {

	// TODO synchronization???
	private final Map<DistRef,LocalRef> map = new HashMap<DistRef, LocalRef>();
	
	@Override
	protected Send<Msg, DistRef> deposit() {
		return new Send<Msg, DistRef>() {
			public void send(Msg message, DistRef receiver) {
				if (DistributedSenderReceiverImpl.this.map.containsKey(receiver)) {
					localDeposit().send(message, DistributedSenderReceiverImpl.this.map.get(receiver));
				} else {
					// TODO not normal…
				}
			};
		};
	}

	public class AgentSide extends Agent<Msg, LocalRef, DistRef> {

		private final DistRef myRef;
		
		public AgentSide() {
			myRef = DistributedSenderReceiverImpl.this.generateRef().pull();
			DistributedSenderReceiverImpl.this.map.put(myRef, this.localMe().pull());
		}
		
		@Override
		protected Pull<DistRef> me() {
			return new Pull<DistRef>() {
				public DistRef pull() {
					return myRef;
				}
			};
		}

		@Override
		protected Do disconnect() {
			return new Do() {
				public void doIt() {
					DistributedSenderReceiverImpl.this.map.remove(myRef);
				}
			};
		}

		@Override
		protected Send<Msg, DistRef> send() {
			return new Send<Msg, DistRef>() {
				public void send(Msg message, DistRef receiver) {
					if (DistributedSenderReceiverImpl.this.map.containsKey(receiver)) {
						localDeposit().send(message, DistributedSenderReceiverImpl.this.map.get(receiver));
					} else {
						distributedDeposit().send(message, receiver);
					}
				};
			};
		}
		
	}
}
