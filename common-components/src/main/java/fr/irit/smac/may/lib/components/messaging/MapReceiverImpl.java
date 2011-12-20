package fr.irit.smac.may.lib.components.messaging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Send;

public class MapReceiverImpl<Msg, RealRef, Ref> extends MapReceiver<Msg, RealRef, Ref> {

	private final Map<Ref,RealRef> map = new ConcurrentHashMap<Ref, RealRef>();
	
	@Override
	protected Send<Msg, Ref> depositKey() {
		return new Send<Msg, Ref>() {
			public void send(Msg message, Ref receiver) {
				if (MapReceiverImpl.this.map.containsKey(receiver)) {
					depositValue().send(message, MapReceiverImpl.this.map.get(receiver));
				} else {
					// TODO not normal…
				}
			};
		};
	}

	public class AgentSide extends Agent<Msg, RealRef, Ref> {

		@Override
		protected void start() {
			super.start();
			MapReceiverImpl.this.map.put(key().pull(), value().pull());
		}

		@Override
		protected Do disconnect() {
			return new Do() {
				public void doIt() {
					MapReceiverImpl.this.map.remove(key().pull());
				}
			};
		}
	}

}
