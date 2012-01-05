package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.ExecutorService;

public abstract class ExecutorService {

	private ExecutorService.ComponentImpl structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected ExecutorService.Component self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor exec();

	public static interface Bridge {

	}

	public static interface Component {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor exec();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl
			implements
				ExecutorService.Component {

		@SuppressWarnings("unused")
		private final ExecutorService.Bridge bridge;

		private final ExecutorService implementation;

		private ComponentImpl(final ExecutorService implem,
				final ExecutorService.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.exec = implem.exec();

		}

		private final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor exec;

		public final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor exec() {
			return this.exec;
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

	public ExecutorService.Component createComponent(ExecutorService.Bridge b) {
		return new ExecutorService.ComponentImpl(this, b);
	}

	public ExecutorService.Component createComponent() {
		return this.createComponent(new ExecutorService.Bridge() {
		});
	}
	public static final ExecutorService.Component createComponent(
			ExecutorService _compo) {
		return _compo.createComponent();
	}

}
