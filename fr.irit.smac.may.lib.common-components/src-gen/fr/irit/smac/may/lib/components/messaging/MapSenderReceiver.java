package fr.irit.smac.may.lib.components.messaging;

public abstract class MapSenderReceiver<Msg, LocalRef, Ref> {

	private Component<Msg, LocalRef, Ref> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> outDeposit() {
		assert this.structure != null;
		return this.structure.bridge.outDeposit();
	};
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
	protected final fr.irit.smac.may.lib.interfaces.Pull<Ref> generateRef() {
		assert this.structure != null;
		return this.structure.bridge.generateRef();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> inDeposit();

	public static interface Bridge<Msg, LocalRef, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> outDeposit();
		public fr.irit.smac.may.lib.interfaces.Send<Msg, LocalRef> localDeposit();
		public fr.irit.smac.may.lib.interfaces.Pull<Ref> generateRef();

	}

	public static final class Component<Msg, LocalRef, Ref> {

		private final Bridge<Msg, LocalRef, Ref> bridge;

		private final MapSenderReceiver<Msg, LocalRef, Ref> implementation;

		public Component(final MapSenderReceiver<Msg, LocalRef, Ref> implem,
				final Bridge<Msg, LocalRef, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.inDeposit = implem.inDeposit();

		}

		private final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> inDeposit;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> inDeposit() {
			return this.inDeposit;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	public static abstract class Agent<Msg, LocalRef, Ref> {

		private Component<Msg, LocalRef, Ref> structure = null;

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
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<Ref> me();

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do disconnect();

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send();

		public static interface Bridge<Msg, LocalRef, Ref> {
			public fr.irit.smac.may.lib.interfaces.Pull<LocalRef> localMe();

		}

		public static final class Component<Msg, LocalRef, Ref> {

			private final Bridge<Msg, LocalRef, Ref> bridge;

			private final Agent<Msg, LocalRef, Ref> implementation;

			public Component(final Agent<Msg, LocalRef, Ref> implem,
					final Bridge<Msg, LocalRef, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.me = implem.me();
				this.disconnect = implem.disconnect();
				this.send = implem.send();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<Ref> me;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Pull<Ref> me() {
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
			private final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send() {
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
