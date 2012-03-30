package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.ExecutorService;

public abstract class ExecutorService {

	private ExecutorService.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected ExecutorService.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor make_exec();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

	public static interface Bridge {

	}

	public static interface Component {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor exec();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Do stop();

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

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.exec = implem.make_exec();
			this.stop = implem.make_stop();

		}

		private final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor exec;

		public final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor exec() {
			return this.exec;
		};
		private final fr.irit.smac.may.lib.interfaces.Do stop;

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

	public ExecutorService.Component newComponent(ExecutorService.Bridge b) {
		return new ExecutorService.ComponentImpl(this, b);
	}

	public ExecutorService.Component newComponent() {
		return this.newComponent(new ExecutorService.Bridge() {
		});
	}
	public static final ExecutorService.Component newComponent(
			ExecutorService _compo) {
		return _compo.newComponent();
	}

}
