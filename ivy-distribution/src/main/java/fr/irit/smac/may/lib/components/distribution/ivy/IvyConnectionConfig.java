package fr.irit.smac.may.lib.components.distribution.ivy;

public class IvyConnectionConfig {

	public final String broadCastAdress;
	public final String port;
	
	public final String actorName;
	public final String helloWorldMessage;
	
	public IvyConnectionConfig(String broadCastAdress, String port,
			String actorName, String helloWorldMessage) {
		super();
		this.broadCastAdress = broadCastAdress;
		this.port = port;
		this.actorName = actorName;
		this.helloWorldMessage = helloWorldMessage;
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

	public String getHelloWorldMessage() {
		return helloWorldMessage;
	}
}
