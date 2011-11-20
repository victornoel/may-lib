package fr.irit.smac.may.lib.components.messaging.distributed;

public class DistributedMessage<Msg,NodeRef> {

	public final Msg msg;
	public final DistRef<NodeRef> ref;
	
	// dummy constructor for jackson! this is bad
	@SuppressWarnings("unused")
	private DistributedMessage() {
		this(null,null);
	}
	
	public DistributedMessage(DistRef<NodeRef> ref, Msg msg) {
		this.ref = ref;
		this.msg = msg;
	}
}
