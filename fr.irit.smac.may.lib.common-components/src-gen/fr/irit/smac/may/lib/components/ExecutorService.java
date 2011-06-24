package fr.irit.smac.may.lib.components;

public abstract class ExecutorService {

	private Component structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract java.util.concurrent.Executor exec();

	public static interface Bridge {

	}

	public static final class Component {

		@SuppressWarnings("unused")
		private final Bridge bridge;

		private final ExecutorService implementation;

		/**
		 * This constructor can be called directly to instantiate stand-alone components
		 */
		public Component(final ExecutorService implem, final Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.exec = implem.exec();

		}

		private final java.util.concurrent.Executor exec;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final java.util.concurrent.Executor exec() {
			return this.exec;
		};

		/**
		 * This must be called to start the component and its sub-components.
		 */
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

	public static final Component createComponent(ExecutorService _compo) {
		return new Component(_compo, new Bridge() {
		});
	}

}
