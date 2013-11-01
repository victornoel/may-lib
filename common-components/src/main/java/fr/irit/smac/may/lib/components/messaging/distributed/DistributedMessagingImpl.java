package fr.irit.smac.may.lib.components.messaging.distributed;

import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

public class DistributedMessagingImpl<Msg,NodeRef> extends DistributedMessaging<Msg,NodeRef> {

	private volatile int i = 0;
	
	@Override
	protected Pull<DistRef<NodeRef>> make_generateRef() {
		return new Pull<DistRef<NodeRef>>() {
			public DistRef<NodeRef> pull() {
				int me = i++;
				return new DistRef<NodeRef>("agent" + me, requires().myNode().pull());
			}
		};
	}

	@Override
	protected Send<Msg, DistRef<NodeRef>> make_send() {
		return new Send<Msg, DistRef<NodeRef>>() {
			public void send(Msg message, DistRef<NodeRef> receiver) {
				DistributedMessage<Msg,NodeRef> m = new DistributedMessage<Msg,NodeRef>(receiver, message);
				requires().distOut().send(m,receiver.platform);
			};
		};
	}

	@Override
	protected Push<DistributedMessage<Msg, NodeRef>> make_distIn() {
		return new Push<DistributedMessage<Msg, NodeRef>>() {
			public void push(DistributedMessage<Msg, NodeRef> thing) {
				requires().deposit().send(thing.msg, thing.ref);
			}
		};
	}

	

}
