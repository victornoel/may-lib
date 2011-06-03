package fr.irit.smac.may.lib.components.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.irit.smac.may.lib.components.IdentifierReceiver;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

public class IdentifierReceiverImpl<M,R> extends IdentifierReceiver<M,R> {

	private final Map<String, R> map = new ConcurrentHashMap<String, R>();
	
	@Override
	protected Send<M,String> deposit() {
		return new Send<M,String>() {
			public void send(M message, String receiver) {
				realDeposit().send(message, map.get(receiver));
			}
		};
	}
	
	public class AgentSide extends IdentifierReceiver.Agent<M,R> {

		private final String me;
		
		public AgentSide(String myName) {
			this.me = myName;
		}
		
		@Override
		protected void start() {
			super.start();
			// TODO check duplicate
			IdentifierReceiverImpl.this.map.put(me, AgentSide.this.realMe().pull());
		}
		
		@Override
		protected Pull<String> me() {
			return new Pull<String>() {
				public String pull() {
					return AgentSide.this.me;
				}
			};
		}

		@Override
		protected Do disconnect() {
			return new Do() {
				public void doIt() {
					IdentifierReceiverImpl.this.map.remove(AgentSide.this.me);
				}
			};
		}
	}

}
