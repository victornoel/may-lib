package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.Buffer;

public abstract class Buffer<I> {

	private Buffer.ComponentImpl<I> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Buffer.Component<I> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected I realPort() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.realPort();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract I make_port();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do make_release();

	public static interface Bridge<I> {
		public I realPort();

	}

	public static interface Component<I> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public I port();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Do release();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<I> implements Buffer.Component<I> {

		private final Buffer.Bridge<I> bridge;

		private final Buffer<I> implementation;

		private ComponentImpl(final Buffer<I> implem, final Buffer.Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.port = implem.make_port();
			this.release = implem.make_release();

		}

		private final I port;

		public final I port() {
			return this.port;
		};
		private final fr.irit.smac.may.lib.interfaces.Do release;

		public final fr.irit.smac.may.lib.interfaces.Do release() {
			return this.release;
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

	public Buffer.Component<I> newComponent(Buffer.Bridge<I> b) {
		return new Buffer.ComponentImpl<I>(this, b);
	}

}
