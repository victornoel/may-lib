package fr.irit.smac.may.lib.components.scheduling.ase;

public abstract class AlternateStateExecutorService {

	private Component structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract java.util.concurrent.Executor exec();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control();

	public static interface Bridge {

	}

	public static final class Component {

		@SuppressWarnings("unused")
		private final Bridge bridge;

		private final AlternateStateExecutorService implementation;

		public Component(final AlternateStateExecutorService implem,
				final Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.exec = implem.exec();
			this.control = implem.control();

		}

		private final java.util.concurrent.Executor exec;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final java.util.concurrent.Executor exec() {
			return this.exec;
		};
		private final fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control() {
			return this.control;
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

	public static final Component createComponent(
			AlternateStateExecutorService _compo) {
		return new Component(_compo, new Bridge() {
		});
	}

}
