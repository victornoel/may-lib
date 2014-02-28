package fr.irit.smac.may.lib.components.remote.messaging.receiver;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

public class RemoteReceiverImpl<Msg, LocalRef> extends
		RemoteReceiver<Msg, LocalRef> {
	
	public class RemoteAgentRefImpl implements RemoteAgentRef, Serializable {

		private static final long serialVersionUID = 3786174379034488447L;
		
		final RemoteAgent agent;

		private final Place place;

		private final String name;

		RemoteAgentRefImpl(RemoteAgent agent, Place place, String name) {
			this.place = place;
			this.name = name;
			this.agent = agent;
		}
		
		@Override
		public String toString() {
			return name+"@"+place;
		};
	}
	
	public class RemoteAgent implements Remote {

		private final LocalRef ref;
		
		public RemoteAgent(LocalRef ref) {
			this.ref = ref;
		}
		
		public void receive(Msg msg) throws RemoteException {
			requires().localSend().send(msg, ref);
		}
	}

	public class AgentSide extends Agent<Msg, LocalRef> {

		private RemoteAgentRefImpl me;
		
		@Override
		protected void start() {
			super.start();
			// beware that anonymous instance of classes are kept linked to
			// "this" and so may not be gc'ed when we would want
			RemoteAgent remoteMe = new RemoteAgent(requires().localMe().pull());

			try {
				// reuse port instead of opening another one.
				UnicastRemoteObject.exportObject(remoteMe, eco_requires().myPlace().pull().getPort());
			} catch (RemoteException e) {
				throw new RuntimeException("export failed", e);
			}

			// we encapsulate it inside this class to hide the remote object
			this.me = new RemoteAgentRefImpl(remoteMe, eco_requires().myPlace().pull(), requires().localMe().pull().toString());
		}

		@Override
		public Pull<RemoteAgentRef> make_me() {
			return new Pull<RemoteAgentRef>() {
				public RemoteAgentRef pull() {
					return AgentSide.this.me;
				}
			};
		}
		
		@Override
		protected Do make_disconnect() {
			return new Do() {
				public void doIt() {
					try {
						UnicastRemoteObject.unexportObject(AgentSide.this.me.agent, true);
					} catch (NoSuchObjectException e) {
						throw new RuntimeException("unexport failed", e);
					}
				}
			};
		}
	}

	@Override
	public Send<Msg, RemoteAgentRef> make_send() {
		return new Send<Msg, RemoteAgentRef>() {
			public void send(Msg msg, RemoteAgentRef receiver) {
				try {
					// TODO using nested classes
					@SuppressWarnings("unchecked")
					RemoteAgentRefImpl remoteAgentRefImpl = (RemoteAgentRefImpl)receiver;
					remoteAgentRefImpl.agent.receive(msg);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
	}
	
	@Override
	protected Agent<Msg, LocalRef> make_Agent() {
		return new AgentSide();
	}
}
