package fr.irit.smac.may.lib.components.messaging.distributed;

import fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication;

public abstract class DistributedCommunication<T> {

	private DistributedCommunication.ComponentImpl<T> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected DistributedCommunication.Component<T> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<T> out() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.out();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> broadcast() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.broadcast();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> make_nodeName();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<T, java.lang.String> make_in();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> make_handle();

	public static interface Bridge<T> {
		public fr.irit.smac.may.lib.interfaces.Push<T> out();
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> broadcast();

	}

	public static interface Component<T> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> nodeName();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Send<T, java.lang.String> in();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> handle();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<T>
			implements
				DistributedCommunication.Component<T> {

		private final DistributedCommunication.Bridge<T> bridge;

		private final DistributedCommunication<T> implementation;

		private ComponentImpl(final DistributedCommunication<T> implem,
				final DistributedCommunication.Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.nodeName = implem.make_nodeName();
			this.in = implem.make_in();
			this.handle = implem.make_handle();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> nodeName;

		public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> nodeName() {
			return this.nodeName;
		};
		private final fr.irit.smac.may.lib.interfaces.Send<T, java.lang.String> in;

		public final fr.irit.smac.may.lib.interfaces.Send<T, java.lang.String> in() {
			return this.in;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> handle;

		public final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> handle() {
			return this.handle;
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

	public DistributedCommunication.Component<T> createComponent(
			DistributedCommunication.Bridge<T> b) {
		return new DistributedCommunication.ComponentImpl<T>(this, b);
	}

}
