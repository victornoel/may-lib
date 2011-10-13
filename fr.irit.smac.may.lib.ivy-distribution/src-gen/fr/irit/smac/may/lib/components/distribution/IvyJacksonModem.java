package fr.irit.smac.may.lib.components.distribution;

public abstract class IvyJacksonModem<Msg> {

	private Component<Msg> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.distribution.DistRef> deposit() {
		assert this.structure != null;
		return this.structure.bridge.deposit();
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
	protected final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig> ivyConnect() {
		assert this.structure != null;
		return this.structure.bridge.ivyConnect();
	};
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
	protected final fr.irit.smac.may.lib.interfaces.MapGet<Msg, java.lang.String> serializer() {
		assert this.structure != null;
		return this.structure.bridge.serializer();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, Msg> deserializer() {
		assert this.structure != null;
		return this.structure.bridge.deserializer();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.distribution.DistRef> generateRef();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.distribution.DistRef> send();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive();

	public static interface Bridge<Msg> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.distribution.DistRef> deposit();
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivySend();
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig> ivyConnect();
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivyBindMsg();
		public fr.irit.smac.may.lib.interfaces.MapGet<Msg, java.lang.String> serializer();
		public fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, Msg> deserializer();

	}

	public static final class Component<Msg> {

		private final Bridge<Msg> bridge;

		private final IvyJacksonModem<Msg> implementation;

		public Component(final IvyJacksonModem<Msg> implem, final Bridge<Msg> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.generateRef = implem.generateRef();
			this.send = implem.send();
			this.ivyReceive = implem.ivyReceive();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.distribution.DistRef> generateRef;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.distribution.DistRef> generateRef() {
			return this.generateRef;
		};
		private final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.distribution.DistRef> send;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.distribution.DistRef> send() {
			return this.send;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> ivyReceive() {
			return this.ivyReceive;
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
