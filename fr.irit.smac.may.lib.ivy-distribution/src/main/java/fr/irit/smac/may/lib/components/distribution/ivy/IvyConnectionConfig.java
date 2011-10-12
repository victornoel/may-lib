package fr.irit.smac.may.lib.components.distribution.ivy;

public class IvyConnectionConfig {

	protected String broadCastAdress;
	protected String port;
	
	protected String actorName;
	protected String actorName2;
	
	public IvyConnectionConfig(String broadCastAdress, String port,
			String actorName, String actorName2) {
		super();
		this.broadCastAdress = broadCastAdress;
		this.port = port;
		this.actorName = actorName;
		this.actorName2 = actorName2;
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
