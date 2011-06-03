package fr.irit.smac.may.lib.components.remrefreceive.impl;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteAgent<Msg> extends Remote {
	public void receive(Msg msg) throws RemoteException;
}
