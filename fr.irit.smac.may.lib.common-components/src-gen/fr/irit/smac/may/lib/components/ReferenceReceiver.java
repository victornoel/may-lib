package fr.irit.smac.may.lib.components;

public abstract class ReferenceReceiver<MsgType> {
	private Component<MsgType> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialise the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<MsgType, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<MsgType>> deposit();

	public static interface Bridge<MsgType> {

	}

	public static final class Component<MsgType> {

		private final Bridge<MsgType> bridge;

		private final ReferenceReceiver<MsgType> implementation;

		public Component(final ReferenceReceiver<MsgType> implem,
				final Bridge<MsgType> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.deposit = implem.deposit();

		}

		private final fr.irit.smac.may.lib.interfaces.Send<MsgType, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<MsgType>> deposit;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<MsgType, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<MsgType>> deposit() {
			return this.deposit;
		};

		public final void start() {
			this.implementation.start();
		}
	}

	/**
	 * Can be overriden by the implementation
	 * It will be called after the infrastructure part of the transverse has been instantiated
	 * and during the containing infrastructure start() method is called.
	 *
	 * This is not meant to be called by hand
	 */
	protected void start() {
	}

	public static abstract class Agent<MsgType> {
		private Component<MsgType> structure = null;

		/**
		 * This can be called by the implementation to access this required port
		 * It will be initialised before the provided ports are initialised
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final fr.irit.smac.may.lib.interfaces.Push<MsgType> put() {
			assert this.structure != null;
			return this.structure.bridge.put();
		};

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialise the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<MsgType>> me();

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialise the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do stop();

		public static interface Bridge<MsgType> {
			public fr.irit.smac.may.lib.interfaces.Push<MsgType> put();

		}

		public static final class Component<MsgType> {

			private final Bridge<MsgType> bridge;

			private final Agent<MsgType> implementation;

			public Component(final Agent<MsgType> implem,
					final Bridge<MsgType> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.me = implem.me();
				this.stop = implem.stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<MsgType>> me;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<MsgType>> me() {
				return this.me;
			};
			private final fr.irit.smac.may.lib.interfaces.Do stop;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Do stop() {
				return this.stop;
			};

			public final void start() {
				this.implementation.start();
			}
		}

		/**
		 * Can be overriden by the implementation
		 * It will be called after the agent part of the transverse has been instantiated
		 * and during the constructed agent start() method is called.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected void start() {
		}
	}
}
