package fr.irit.smac.may.lib.components.distribution;

public abstract class IvyBroadcaster<T> {

	private Component<T> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivyBindMsg() {
		assert this.structure != null;
		return this.structure.bridge.ivyBindMsg();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivySend() {
		assert this.structure != null;
		return this.structure.bridge.ivySend();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.MapGet<T, java.lang.String> serializer() {
		assert this.structure != null;
		return this.structure.bridge.serializer();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, T> deserializer() {
		assert this.structure != null;
		return this.structure.bridge.deserializer();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<T> handle() {
		assert this.structure != null;
		return this.structure.bridge.handle();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<T> send();

	public static interface Bridge<T> {
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivyBindMsg();
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivySend();
		public fr.irit.smac.may.lib.interfaces.MapGet<T, java.lang.String> serializer();
		public fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, T> deserializer();
		public fr.irit.smac.may.lib.interfaces.Push<T> handle();

	}

	public static final class Component<T> {

		private final Bridge<T> bridge;

		private final IvyBroadcaster<T> implementation;

		public Component(final IvyBroadcaster<T> implem, final Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.ivyReceive = implem.ivyReceive();
			this.send = implem.send();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive() {
			return this.ivyReceive;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<T> send;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
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

}
