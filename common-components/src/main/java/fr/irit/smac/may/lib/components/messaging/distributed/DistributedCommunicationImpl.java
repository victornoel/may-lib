package fr.irit.smac.may.lib.components.messaging.distributed;

import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

public class DistributedCommunicationImpl<T> extends DistributedCommunication<T> {

	private final String nodeName;

	public DistributedCommunicationImpl(String nodeName) {
		this.nodeName = nodeName;
	}
	
	@Override
	protected Send<T, String> in() {
		return new Send<T, String>() {
			public void send(T message, String receiver) {
				broadcast().push(new DistributedInfo<T>(message, receiver));
			}
		};
	}

	@Override
	protected Push<DistributedInfo<T>> handle() {
		return new Push<DistributedInfo<T>>() {
			public void push(DistributedInfo<T> thing) {
				if (thing.getDest().equals(nodeName)) {
					out().push(thing.getInfo());
				}
			}
		};
	}

	@Override
	protected Pull<String> nodeName() {
		return new Pull<String>() {
			public String pull() {
				return nodeName;
			}
		};
	}

}
