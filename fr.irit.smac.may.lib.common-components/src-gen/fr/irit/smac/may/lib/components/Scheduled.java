package fr.irit.smac.may.lib.components;

public abstract class Scheduled {
	private Component structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final java.util.concurrent.Executor sched() {
		assert this.structure != null;
		return this.structure.bridge.sched();
	};

	public static interface Bridge {
		public java.util.concurrent.Executor sched();

	}

	public static final class Component {

		private final Bridge bridge;

		private final Scheduled implementation;

		public Component(final Scheduled implem, final Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

		}

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

	public static abstract class Agent {
		private Component structure = null;

		/**
		 * This can be called by the implementation to access this required port
		 * It will be initialized before the provided ports are initialized
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final fr.irit.smac.may.lib.interfaces.Do cycle() {
			assert this.structure != null;
			return this.structure.bridge.cycle();
		};

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do stop();

		public static interface Bridge {
			public fr.irit.smac.may.lib.interfaces.Do cycle();

		}

		public static final class Component {

			private final Bridge bridge;

			private final Agent implementation;

			public Component(final Agent implem, final Bridge b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.stop = implem.stop();

			}

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
		 * It will be called after the agent part of the transverse has been instantiated
		 * and during the constructed agent start() method is called.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected void start() {
		}
	}
}
