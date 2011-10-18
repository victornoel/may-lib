package fr.irit.smac.may.lib.components.messaging;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

public class MapSenderReceiverImpl<Msg, LocalRef, Ref> extends MapSenderReceiver<Msg, LocalRef, Ref> {

	// TODO synchronization???
	private final Map<Ref,LocalRef> map = new HashMap<Ref, LocalRef>();
	
	@Override
	protected Send<Msg, Ref> inDeposit() {
		return new Send<Msg, Ref>() {
			public void send(Msg message, Ref receiver) {
				if (MapSenderReceiverImpl.this.map.containsKey(receiver)) {
					localDeposit().send(message, MapSenderReceiverImpl.this.map.get(receiver));
				} else {
					// TODO not normal…
				}
			};
		};
	}

	public class AgentSide extends Agent<Msg, LocalRef, Ref> {

		private Ref myRef;

		@Override
		protected void start() {
			super.start();
			myRef = MapSenderReceiverImpl.this.generateRef().pull();
			MapSenderReceiverImpl.this.map.put(myRef, this.localMe().pull());
		}
		
		@Override
		protected Pull<Ref> me() {
			return new Pull<Ref>() {
				public Ref pull() {
					return myRef;
				}
			};
		}

		@Override
		protected Do disconnect() {
			return new Do() {
				public void doIt() {
					MapSenderReceiverImpl.this.map.remove(myRef);
				}
			};
		}

		@Override
		protected Send<Msg, Ref> send() {
			return new Send<Msg, Ref>() {
				public void send(Msg message, Ref receiver) {
					if (MapSenderReceiverImpl.this.map.containsKey(receiver)) {
						MapSenderReceiverImpl.this.localDeposit().send(message, MapSenderReceiverImpl.this.map.get(receiver));
					} else {
						MapSenderReceiverImpl.this.outDeposit().send(message, receiver);
					}
				};
			};
		}
		
	}

}
