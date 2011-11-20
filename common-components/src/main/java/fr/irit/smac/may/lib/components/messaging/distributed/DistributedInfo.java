package fr.irit.smac.may.lib.components.messaging.distributed;

public class DistributedInfo<T> {

	private final T info;
	private final String dest;

	// dummy constructor for jackson! this is bad
	@SuppressWarnings("unused")
	private DistributedInfo() {
		this(null,null);
	}
	
	public DistributedInfo(T info, String dest) {
		this.info = info;
		this.dest = dest;
	}

	public T getInfo() {
		return info;
	}

	public String getDest() {
		return dest;
	}
	
}
