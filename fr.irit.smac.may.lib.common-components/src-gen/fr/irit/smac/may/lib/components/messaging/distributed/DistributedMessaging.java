package fr.irit.smac.may.lib.components.messaging.distributed;

public abstract class DistributedMessaging<Msg> {

	private Component<Msg> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef> deposit() {
		assert this.structure != null;
		return this.structure.bridge.deposit();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg>> broadcast() {
		assert this.structure != null;
		return this.structure.bridge.broadcast();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.distributed.DistRef> generateRef();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef> send();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg>> handle();

	public static interface Bridge<Msg> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef> deposit();
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg>> broadcast();

	}

	public static final class Component<Msg> {

		private final Bridge<Msg> bridge;

		private final DistributedMessaging<Msg> implementation;

		public Component(final DistributedMessaging<Msg> implem,
				final Bridge<Msg> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.generateRef = implem.generateRef();
			this.send = implem.send();
			this.handle = implem.handle();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.distributed.DistRef> generateRef;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.distributed.DistRef> generateRef() {
			return this.generateRef;
		};
		private final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef> send;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.distributed.DistRef> send() {
			return this.send;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg>> handle;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage<Msg>> handle() {
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
