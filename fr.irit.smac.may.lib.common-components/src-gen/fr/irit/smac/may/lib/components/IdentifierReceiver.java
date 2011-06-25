package fr.irit.smac.may.lib.components;

public abstract class IdentifierReceiver<Msg, RealRef> {
	private Component<Msg, RealRef> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> realDeposit() {
		assert this.structure != null;
		return this.structure.bridge.realDeposit();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> deposit();

	public static interface Bridge<Msg, RealRef> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> realDeposit();

	}

	public static final class Component<Msg, RealRef> {

		private final Bridge<Msg, RealRef> bridge;

		private final IdentifierReceiver<Msg, RealRef> implementation;

		public Component(final IdentifierReceiver<Msg, RealRef> implem,
				final Bridge<Msg, RealRef> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.deposit = implem.deposit();

		}

		private final fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> deposit;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> deposit() {
			return this.deposit;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Can be overridden by the implementation
	 * It will be called after the infrastructure part of the transverse has been instantiated
	 * and during the containing infrastructure start() method is called.
	 *
	 * This is not meant to be called by hand
	 */
	protected void start() {
	}

	public static abstract class Agent<Msg, RealRef> {
		private Component<Msg, RealRef> structure = null;

		/**
		 * This can be called by the implementation to access this required port
		 * It will be initialized before the provided ports are initialized
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final fr.irit.smac.may.lib.interfaces.Pull<RealRef> realMe() {
			assert this.structure != null;
			return this.structure.bridge.realMe();
		};

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> me();

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do disconnect();

		public static interface Bridge<Msg, RealRef> {
			public fr.irit.smac.may.lib.interfaces.Pull<RealRef> realMe();

		}

		public static final class Component<Msg, RealRef> {

			private final Bridge<Msg, RealRef> bridge;

			private final Agent<Msg, RealRef> implementation;

			public Component(final Agent<Msg, RealRef> implem,
					final Bridge<Msg, RealRef> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.me = implem.me();
				this.disconnect = implem.disconnect();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> me;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> me() {
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
		 * It will be called after the agent part of the transverse has been instantiated
		 * and during the constructed agent start() method is called.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected void start() {
		}
	}
}
