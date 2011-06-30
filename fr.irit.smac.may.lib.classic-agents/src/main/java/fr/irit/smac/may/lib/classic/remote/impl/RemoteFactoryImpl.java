package fr.irit.smac.may.lib.classic.remote.impl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.components.remote.place.Place;

public class RemoteFactoryImpl<Msg, Ref> extends RemoteFactory<Msg, Ref> {

	public RemoteFactoryImpl() {
		
		RemoteMedium<Msg,Ref> rmedium = new RemoteMedium<Msg,Ref>() {
			public Ref create(RemoteClassicBehaviour<Msg, Ref> beh)
					throws RemoteException {
				return RemoteFactoryImpl.this.infraCreate().create(beh);
			}
		};
		
		try {
			//System.setProperty("java.security.policy", "java.policy");
			//System.setSecurityManager(new RMISecurityManager());

			// reuse port instead of opening another one!
			int port = this.thisPlace().pull().getPort();
			UnicastRemoteObject.exportObject(rmedium, port);
			(LocateRegistry.createRegistry(port)).bind("ActorMedium", rmedium);
		} catch (RemoteException e) {
			throw new RuntimeException("createRegistry failed", e);
		} catch (AlreadyBoundException e) {
			throw new RuntimeException("createRegistry failed", e);
		}
	}
	
	public class AgentSide extends RemoteFactory.Agent<Msg, Ref> {
		@Override
		protected CreateRemoteClassic<Msg, Ref> create() {
			return new CreateRemoteClassic<Msg, Ref>() {
				public Ref create(RemoteClassicBehaviour<Msg, Ref> beh, Place place) {
					return RemoteFactoryImpl.this.factCreate().create(beh,place);
				}
				public Ref create(RemoteClassicBehaviour<Msg, Ref> beh) {
					return RemoteFactoryImpl.this.factCreate().create(beh);
				}
			};
		}
	}

	@Override
	protected CreateRemoteClassic<Msg, Ref> factCreate() {
		return new CreateRemoteClassic<Msg, Ref>() {
			public Ref create(RemoteClassicBehaviour<Msg, Ref> beh) {
				return infraCreate().create(beh);
			}
			public Ref create(RemoteClassicBehaviour<Msg, Ref> beh, Place place) {
				if (place.equals(RemoteFactoryImpl.this.thisPlace().pull()))
					return RemoteFactoryImpl.this.infraCreate().create(beh);

				RemoteMedium<Msg, Ref> m;

				try {
					m = (RemoteMedium<Msg, Ref>) Naming.lookup("rmi://" + place + "/ActorMedium");
				} catch (MalformedURLException e) {
					throw new RuntimeException("lookup failed", e);
				} catch (RemoteException e) {
					throw new RuntimeException("lookup failed", e);
				} catch (NotBoundException e) {
					throw new RuntimeException("lookup failed", e);
				}

				try {
					return m.create(beh);
				} catch (RemoteException e) {
					throw new RuntimeException("create failed", e);
				}
			}
		};
	}
	
	public interface RemoteMedium<Msg,Ref> extends Remote {
		  public Ref create(RemoteClassicBehaviour<Msg, Ref> beh) throws RemoteException;
	}

}
