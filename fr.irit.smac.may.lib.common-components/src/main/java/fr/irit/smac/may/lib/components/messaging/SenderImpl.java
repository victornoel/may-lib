package fr.irit.smac.may.lib.components.messaging;

import fr.irit.smac.may.lib.interfaces.Send;

public class SenderImpl<Msg, Ref> extends Sender<Msg, Ref> {

	public class AgentSide extends Agent<Msg, Ref> {
		@Override
		public Send<Msg, Ref> send() {
			return new Send<Msg, Ref>() {
				public void send(Msg message, Ref receiver) {
					SenderImpl.this.deposit().send(message, receiver);
				};
			};
		}
	}
}
