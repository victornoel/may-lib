package fr.irit.smac.may.lib.components.remote.messaging.receiver;

public abstract class RemoteReceiver<Msg, LocalRef> {

	private Component<Msg, LocalRef> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<Msg, LocalRef> localDeposit() {
		assert this.structure != null;
		return this.structure.bridge.localDeposit();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace() {
		assert this.structure != null;
		return this.structure.bridge.myPlace();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> deposit();

	public static interface Bridge<Msg, LocalRef> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, LocalRef> localDeposit();
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace();

	}

	public static final class Component<Msg, LocalRef> {

		private final Bridge<Msg, LocalRef> bridge;

		private final RemoteReceiver<Msg, LocalRef> implementation;

		public Component(final RemoteReceiver<Msg, LocalRef> implem,
				final Bridge<Msg, LocalRef> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.deposit = implem.deposit();

		}

		private final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> deposit;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> deposit() {
			return this.deposit;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	public static abstract class Agent<Msg, LocalRef> {

		private Component<Msg, LocalRef> structure = null;

		/**
		 * This can be called by the implementation to access this required port
		 * It will be initialized before the provided ports are initialized
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final fr.irit.smac.may.lib.interfaces.Pull<LocalRef> localMe() {
			assert this.structure != null;
			return this.structure.bridge.localMe();
		};

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me();

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do disconnect();

		public static interface Bridge<Msg, LocalRef> {
			public fr.irit.smac.may.lib.interfaces.Pull<LocalRef> localMe();

		}

		public static final class Component<Msg, LocalRef> {

			private final Bridge<Msg, LocalRef> bridge;

			private final Agent<Msg, LocalRef> implementation;

			public Component(final Agent<Msg, LocalRef> implem,
					final Bridge<Msg, LocalRef> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.me = implem.me();
				this.disconnect = implem.disconnect();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me() {
				return this.me;
			};
			private final fr.irit.smac.may.lib.interfaces.Do disconnect;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Do disconnect() {
				return this.disconnect;
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
