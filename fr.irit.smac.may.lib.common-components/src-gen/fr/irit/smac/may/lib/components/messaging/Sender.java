package fr.irit.smac.may.lib.components.messaging;

public abstract class Sender<MsgType, Ref> {

	private Component<MsgType, Ref> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> deposit() {
		assert this.structure != null;
		return this.structure.bridge.deposit();
	};

	public static interface Bridge<MsgType, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> deposit();

	}

	public static final class Component<MsgType, Ref> {

		private final Bridge<MsgType, Ref> bridge;

		private final Sender<MsgType, Ref> implementation;

		public Component(final Sender<MsgType, Ref> implem,
				final Bridge<MsgType, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

		}

		public final void start() {

			this.implementation.start();
		}
	}

	public static abstract class Agent<MsgType, Ref> {

		private Component<MsgType, Ref> structure = null;

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> send();

		public static interface Bridge<MsgType, Ref> {

		}

		public static final class Component<MsgType, Ref> {

			@SuppressWarnings("unused")
			private final Bridge<MsgType, Ref> bridge;

			private final Agent<MsgType, Ref> implementation;

			public Component(final Agent<MsgType, Ref> implem,
					final Bridge<MsgType, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.send = implem.send();

			}

			private final fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> send;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> send() {
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
