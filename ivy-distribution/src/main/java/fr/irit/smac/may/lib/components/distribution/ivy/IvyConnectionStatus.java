package fr.irit.smac.may.lib.components.distribution.ivy;

public class IvyConnectionStatus {
	
	private final String broadCastAdress;
	private final String port;
	
	private final String actorName;
	private final String helloWorldMessage;
	
	private final boolean connected;
	
	public IvyConnectionStatus(String broadCastAdress, String port,
			String actorName, String helloWorldMessage, boolean connected) {
		super();
		this.broadCastAdress = broadCastAdress;
		this.port = port;
		this.actorName = actorName;
		this.helloWorldMessage = helloWorldMessage;
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

	public String getHelloWorldMessage() {
		return helloWorldMessage;
	}
}
