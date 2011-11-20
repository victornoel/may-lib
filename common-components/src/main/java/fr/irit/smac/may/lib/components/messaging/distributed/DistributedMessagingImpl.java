package fr.irit.smac.may.lib.components.messaging.distributed;

import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

public class DistributedMessagingImpl<Msg,NodeRef> extends DistributedMessaging<Msg,NodeRef> {

	private volatile int i = 0;
	
	@Override
	protected Pull<DistRef<NodeRef>> generateRef() {
		return new Pull<DistRef<NodeRef>>() {
			public DistRef<NodeRef> pull() {
				int me = i++;
				return new DistRef<NodeRef>("agent" + me, myNode().pull());
			}
		};
	}

	@Override
	protected Send<Msg, DistRef<NodeRef>> send() {
		return new Send<Msg, DistRef<NodeRef>>() {
			public void send(Msg message, DistRef<NodeRef> receiver) {
				DistributedMessage<Msg,NodeRef> m = new DistributedMessage<Msg,NodeRef>(receiver, message);
				distOut().push(m);
			};
		};
	}

	@Override
	protected Push<DistributedMessage<Msg, NodeRef>> distIn() {
		return new Push<DistributedMessage<Msg, NodeRef>>() {
			public void push(DistributedMessage<Msg, NodeRef> thing) {
				deposit().send(thing.msg, thing.ref);
			}
		};
	}

	

}
