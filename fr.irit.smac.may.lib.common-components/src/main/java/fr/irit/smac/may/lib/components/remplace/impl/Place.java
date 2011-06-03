package fr.irit.smac.may.lib.components.remplace.impl;

import java.io.Serializable;

public class Place implements Serializable {

	private static final long serialVersionUID = -9061810180222870790L;

	private final String host;
	private final int port;

	public Place(String h, int p) {
		this.host = h;
		this.port = p;
	}

	@Override
	public String toString() {
		return host+":"+port;
	}

	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
}
