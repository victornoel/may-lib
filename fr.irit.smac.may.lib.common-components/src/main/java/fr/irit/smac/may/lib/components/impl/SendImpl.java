package fr.irit.smac.may.lib.components.impl;

import fr.irit.smac.may.lib.components.ReferenceSender;
import fr.irit.smac.may.lib.interfaces.Send;

public class SendImpl<Msg, Ref> extends ReferenceSender<Msg, Ref> {

	public class AgentSide extends ReferenceSender.Agent<Msg, Ref> {
		@Override
		public Send<Msg, Ref> send() {
			return new Send<Msg, Ref>() {
				public void send(Msg message, Ref receiver) {
					SendImpl.this.deposit().send(message, receiver);
				};
			};
		}
	}
}
