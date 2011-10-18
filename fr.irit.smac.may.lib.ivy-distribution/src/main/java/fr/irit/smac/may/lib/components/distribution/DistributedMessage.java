package fr.irit.smac.may.lib.components.distribution;

public class DistributedMessage<Msg> {

	final Msg msg;
	final DistRef ref;

	public DistributedMessage(DistRef ref, Msg msg) {
		this.ref = ref;
		this.msg = msg;
	}
}
