package fr.irit.smac.may.lib.components.messaging.distributed;

public abstract class DistributedCommunication<T> {

	private Component<T> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<T> out() {
		assert this.structure != null;
		return this.structure.bridge.out();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> broadcast() {
		assert this.structure != null;
		return this.structure.bridge.broadcast();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> nodeName();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<T, java.lang.String> in();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> handle();

	public static interface Bridge<T> {
		public fr.irit.smac.may.lib.interfaces.Push<T> out();
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> broadcast();

	}

	public static final class Component<T> {

		private final Bridge<T> bridge;

		private final DistributedCommunication<T> implementation;

		public Component(final DistributedCommunication<T> implem,
				final Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.nodeName = implem.nodeName();
			this.in = implem.in();
			this.handle = implem.handle();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> nodeName;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> nodeName() {
			return this.nodeName;
		};
		private final fr.irit.smac.may.lib.interfaces.Send<T, java.lang.String> in;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<T, java.lang.String> in() {
			return this.in;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo<T>> handle;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
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

}
