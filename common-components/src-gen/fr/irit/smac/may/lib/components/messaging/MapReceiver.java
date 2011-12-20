package fr.irit.smac.may.lib.components.messaging;

public abstract class MapReceiver<Msg, RealRef, Ref> {

	private Component<Msg, RealRef, Ref> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> depositValue() {
		assert this.structure != null;
		return this.structure.bridge.depositValue();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey();

	public static interface Bridge<Msg, RealRef, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> depositValue();

	}

	public static final class Component<Msg, RealRef, Ref> {

		private final Bridge<Msg, RealRef, Ref> bridge;

		private final MapReceiver<Msg, RealRef, Ref> implementation;

		public Component(final MapReceiver<Msg, RealRef, Ref> implem,
				final Bridge<Msg, RealRef, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.depositKey = implem.depositKey();

		}

		private final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey() {
			return this.depositKey;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	public static abstract class Agent<Msg, RealRef, Ref> {

		private Component<Msg, RealRef, Ref> structure = null;

		/**
		 * This can be called by the implementation to access this required port
		 * It will be initialized before the provided ports are initialized
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final fr.irit.smac.may.lib.interfaces.Pull<RealRef> value() {
			assert this.structure != null;
			return this.structure.bridge.value();
		};
		/**
		 * This can be called by the implementation to access this required port
		 * It will be initialized before the provided ports are initialized
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final fr.irit.smac.may.lib.interfaces.Pull<Ref> key() {
			assert this.structure != null;
			return this.structure.bridge.key();
		};

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do disconnect();

		public static interface Bridge<Msg, RealRef, Ref> {
			public fr.irit.smac.may.lib.interfaces.Pull<RealRef> value();
			public fr.irit.smac.may.lib.interfaces.Pull<Ref> key();

		}

		public static final class Component<Msg, RealRef, Ref> {

			private final Bridge<Msg, RealRef, Ref> bridge;

			private final Agent<Msg, RealRef, Ref> implementation;

			public Component(final Agent<Msg, RealRef, Ref> implem,
					final Bridge<Msg, RealRef, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.disconnect = implem.disconnect();

			}

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
