package fr.irit.smac.may.lib.components.distribution.ivy;

public class IvyConnectionStatus {
	protected String broadCastAdress;
	protected String port;
	
	protected String actorName;
	protected String actorName2;
	
	protected boolean connected;
	
	
	
	public IvyConnectionStatus(String broadCastAdress, String port,
			String actorName, String actorName2, boolean connected) {
		super();
		this.broadCastAdress = broadCastAdress;
		this.port = port;
		this.actorName = actorName;
		this.actorName2 = actorName2;
		this.connected = connected;
	}
	
	public boolean isconnected(){
		return connected;
	}
	public String getBroadCastAdress() {
		return broadCastAdress;
	}
	public String getPort() {
		return port;
	}

	public String getActorName() {
		return actorName;
	}

	public String getActorName2() {
		return actorName2;
	}
}
