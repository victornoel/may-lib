package fr.irit.smac.may.lib.components.messaging.distributed;

import fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging;

public abstract class DistributedMessaging<Msg, NodeRef> {

	private DistributedMessaging.ComponentImpl<Msg, NodeRef> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected DistributedMessaging.Component<Msg, NodeRef> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Pull<NodeRef> myNode() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.myNode();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> deposit() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.deposit();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg, NodeRef>, NodeRef> distOut() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.distOut();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> make_generateRef();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> make_send();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg, NodeRef>> make_distIn();

	public static interface Bridge<Msg, NodeRef> {
		public fr.irit.smac.may.lib.interfaces.Pull<NodeRef> myNode();
		public fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> deposit();
		public fr.irit.smac.may.lib.interfaces.Send<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg, NodeRef>, NodeRef> distOut();

	}

	public static interface Component<Msg, NodeRef> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> generateRef();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> send();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg, NodeRef>> distIn();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Msg, NodeRef>
			implements
				DistributedMessaging.Component<Msg, NodeRef> {

		private final DistributedMessaging.Bridge<Msg, NodeRef> bridge;

		private final DistributedMessaging<Msg, NodeRef> implementation;

		private ComponentImpl(final DistributedMessaging<Msg, NodeRef> implem,
				final DistributedMessaging.Bridge<Msg, NodeRef> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.generateRef = implem.make_generateRef();
			this.send = implem.make_send();
			this.distIn = implem.make_distIn();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> generateRef;

		public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> generateRef() {
			return this.generateRef;
		};
		private final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> send;

		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef<NodeRef>> send() {
			return this.send;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg, NodeRef>> distIn;

		public final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg, NodeRef>> distIn() {
			return this.distIn;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Can be overridden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}

	public DistributedMessaging.Component<Msg, NodeRef> newComponent(
			DistributedMessaging.Bridge<Msg, NodeRef> b) {
		return new DistributedMessaging.ComponentImpl<Msg, NodeRef>(this, b);
	}

}
