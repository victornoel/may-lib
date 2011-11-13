package fr.irit.smac.may.lib.components.messaging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Send;

public class MapReceiverImpl<Msg, RealRef, Ref> extends MapReceiver<Msg, RealRef, Ref> {

	private final Map<Ref,RealRef> map = new ConcurrentHashMap<Ref, RealRef>();
	
	@Override
	protected Send<Msg, Ref> deposit() {
		return new Send<Msg, Ref>() {
			public void send(Msg message, Ref receiver) {
				if (MapReceiverImpl.this.map.containsKey(receiver)) {
					realDeposit().send(message, MapReceiverImpl.this.map.get(receiver));
				} else {
					// TODO not normal…
				}
			};
		};
	}

	public class AgentSide extends Agent<Msg, RealRef, Ref> {

		private Ref myRef;

		@Override
		protected void start() {
			super.start();
			this.myRef = me().pull();
			MapReceiverImpl.this.map.put(myRef, this.realMe().pull());
		}

		@Override
		protected Do disconnect() {
			return new Do() {
				public void doIt() {
					MapReceiverImpl.this.map.remove(myRef);
				}
			};
		}
	}

}
