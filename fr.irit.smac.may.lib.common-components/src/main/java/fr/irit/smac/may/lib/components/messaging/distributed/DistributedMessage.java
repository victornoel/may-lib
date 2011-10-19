package fr.irit.smac.may.lib.components.messaging.distributed;

public class DistributedMessage<Msg> {

	public final Msg msg;
	public final DistRef ref;
	
	public DistributedMessage(DistRef ref, Msg msg) {
		this.ref = ref;
		this.msg = msg;
	}
}
