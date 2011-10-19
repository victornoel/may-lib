package fr.irit.smac.may.lib.components.messaging.distributed;

public class DistributedMessage<Msg> {

	public final Msg msg;
	public final DistRef ref;
	
	// dummy constructor for jackson! this is bad
	private DistributedMessage() {
		this(null,null);
	}
	
	public DistributedMessage(DistRef ref, Msg msg) {
		this.ref = ref;
		this.msg = msg;
	}
}
