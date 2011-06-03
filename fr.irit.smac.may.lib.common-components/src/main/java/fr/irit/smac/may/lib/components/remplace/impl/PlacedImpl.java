package fr.irit.smac.may.lib.components.remplace.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import fr.irit.smac.may.lib.components.Placed;
import fr.irit.smac.may.lib.interfaces.Pull;

public class PlacedImpl extends Placed {

	private final Place place;
	
	public PlacedImpl(int port) {
		try {
			place = new Place(InetAddress.getLocalHost().getHostName(),
					port);
		} catch (UnknownHostException e) {
			throw new RuntimeException("getHostname failed", e);
		}
	}
	
	@Override
	protected Pull<Place> thisPlace() {
		return new Pull<Place>() {
			public Place pull() {
				return place;
			}
		};
	}
	
	public class AgentSide extends Placed.Agent {
		@Override
		protected Pull<Place> myPlace() {
			return new Pull<Place>() {
				public Place pull() {
					return place;
				}
			};
		}
	}

}
