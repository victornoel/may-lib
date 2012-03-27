package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.IvyBroadcaster;

public abstract class IvyBroadcaster<T> {

	private IvyBroadcaster.ComponentImpl<T> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected IvyBroadcaster.Component<T> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivyBindMsg() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.ivyBindMsg();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivySend() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.ivySend();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.distribution.interfaces.Transform<T, java.lang.String> serializer() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.serializer();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.distribution.interfaces.Transform<java.lang.String, T> deserializer() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.deserializer();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<T> handle() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.handle();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> make_ivyReceive();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<T> make_send();

	public static interface Bridge<T> {
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivyBindMsg();
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivySend();
		public fr.irit.smac.may.lib.components.distribution.interfaces.Transform<T, java.lang.String> serializer();
		public fr.irit.smac.may.lib.components.distribution.interfaces.Transform<java.lang.String, T> deserializer();
		public fr.irit.smac.may.lib.interfaces.Push<T> handle();

	}

	public static interface Component<T> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<T> send();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<T>
			implements
				IvyBroadcaster.Component<T> {

		private final IvyBroadcaster.Bridge<T> bridge;

		private final IvyBroadcaster<T> implementation;

		private ComponentImpl(final IvyBroadcaster<T> implem,
				final IvyBroadcaster.Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.ivyReceive = implem.make_ivyReceive();
			this.send = implem.make_send();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive;

		public final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive() {
			return this.ivyReceive;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<T> send;

		public final fr.irit.smac.may.lib.interfaces.Push<T> send() {
			return this.send;
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

	public IvyBroadcaster.Component<T> createComponent(
			IvyBroadcaster.Bridge<T> b) {
		return new IvyBroadcaster.ComponentImpl<T>(this, b);
	}

}
