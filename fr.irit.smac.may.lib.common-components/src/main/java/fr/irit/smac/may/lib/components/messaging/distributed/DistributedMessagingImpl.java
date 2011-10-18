package fr.irit.smac.may.lib.components.messaging.distributed;

import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

public class DistributedMessagingImpl<Msg> extends DistributedMessaging<Msg> {

	private volatile int i = 0;

	private final String platformName;

	public DistributedMessagingImpl(String platformName) {
		this.platformName = platformName;
	}

	@Override
	protected Pull<DistRef> generateRef() {
		return new Pull<DistRef>() {
			public DistRef pull() {
				int me = i++;
				return new DistRef("agent" + me, platformName);
			}
		};
	}

	@Override
	protected Send<Msg, DistRef> send() {
		return new Send<Msg, DistRef>() {
			public void send(Msg message, DistRef receiver) {
				DistributedMessage<Msg> m = new DistributedMessage<Msg>(receiver, message);
				broadcast().push(m);
			};
		};
	}

	@Override
	protected Push<DistributedMessage<Msg>> handle() {
		return new Push<DistributedMessage<Msg>>() {
			public void push(DistributedMessage<Msg> thing) {
				if (thing.ref.platform.equals(platformName)) {
					send().send(thing.msg, thing.ref);
				}
			}
		};
	}

	

}
