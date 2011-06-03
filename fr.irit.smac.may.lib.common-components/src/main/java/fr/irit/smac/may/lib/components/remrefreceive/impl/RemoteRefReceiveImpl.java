package fr.irit.smac.may.lib.components.remrefreceive.impl;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import fr.irit.smac.may.lib.components.RemoteReferenceReceiver;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

public class RemoteRefReceiveImpl<Msg, LocalRef> extends
		RemoteReferenceReceiver<Msg, LocalRef> {
	
	public class RemoteAgentImpl implements RemoteAgent<Msg> {

		private final LocalRef ref;
		
		public RemoteAgentImpl(LocalRef ref) {
			this.ref = ref;
		}
		
		public void receive(Msg msg) throws RemoteException {
			localDeposit().send(msg, ref);
		}
	}

	public class AgentSide extends RemoteReferenceReceiver.Agent<Msg, LocalRef> {

		private RemoteAgentRef<Msg> me;
		
		@Override
		protected void start() {
			super.start();
			// beware that anonymous instance of classes are kept linked to
			// "this" and so may not be gc'ed when we would want
			RemoteAgent<Msg> remoteMe = new RemoteAgentImpl(localMe().pull());

			try {
				// reuse port instead of opening another one.
				UnicastRemoteObject.exportObject(remoteMe, myPlace().pull().getPort());
			} catch (RemoteException e) {
				throw new RuntimeException("export failed", e);
			}

			// we encapsulate it inside this class to hide the remote object
			this.me = new RemoteAgentRef<Msg>(remoteMe, myPlace().pull(), localMe().pull().toString());
		}

		@Override
		public Pull<RemoteAgentRef<Msg>> me() {
			return new Pull<RemoteAgentRef<Msg>>() {
				public RemoteAgentRef<Msg> pull() {
					return AgentSide.this.me;
				}
			};
		}
		
		@Override
		protected Do disconnect() {
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
	public Send<Msg, RemoteAgentRef<Msg>> deposit() {
		return new Send<Msg, RemoteAgentRef<Msg>>() {
			public void send(Msg msg, RemoteAgentRef<Msg> receiver) {
				try {
					receiver.agent.receive(msg);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		};
	}
}
