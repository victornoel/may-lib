package fr.irit.smac.may.lib.components.messaging.callable;

public abstract class Callable<I> {

	private Component<I> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSynchronousCall<I, fr.irit.smac.may.lib.components.messaging.callable.CallRef> call();

	public static interface Bridge<I> {

	}

	public static final class Component<I> {

		@SuppressWarnings("unused")
		private final Bridge<I> bridge;

		private final Callable<I> implementation;

		public Component(final Callable<I> implem, final Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.call = implem.call();

		}

		private final fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSynchronousCall<I, fr.irit.smac.may.lib.components.messaging.callable.CallRef> call;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSynchronousCall<I, fr.irit.smac.may.lib.components.messaging.callable.CallRef> call() {
			return this.call;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	public static abstract class Agent<I> {

		private Component<I> structure = null;

		/**
		 * This can be called by the implementation to access this required port
		 * It will be initialized before the provided ports are initialized
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final I toCall() {
			assert this.structure != null;
			return this.structure.bridge.toCall();
		};

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.callable.CallRef> me();

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do stop();

		public static interface Bridge<I> {
			public I toCall();

		}

		public static final class Component<I> {

			private final Bridge<I> bridge;

			private final Agent<I> implementation;

			public Component(final Agent<I> implem, final Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.me = implem.me();
				this.stop = implem.stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.callable.CallRef> me;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.callable.CallRef> me() {
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
