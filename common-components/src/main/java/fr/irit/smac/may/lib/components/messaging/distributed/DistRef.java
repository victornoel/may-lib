package fr.irit.smac.may.lib.components.messaging.distributed;

public class DistRef<NodeRef> {

	public final String name;
	public final NodeRef platform;

	// dummy constructor for jackson! this is bad
	@SuppressWarnings("unused")
	private DistRef() {
		this(null,null);
	}
	
	public DistRef(String name, NodeRef platform) {
		this.name = name;
		this.platform = platform;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((platform == null) ? 0 : platform.hashCode());
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
		@SuppressWarnings("rawtypes")
		DistRef other = (DistRef) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		return true;
	}
	
}
