package fr.irit.smac.may.lib.components.scheduling.ase;

import fr.irit.smac.may.lib.components.scheduling.ase.AlternateStateExecutorService;

public abstract class AlternateStateExecutorService {

	private AlternateStateExecutorService.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected AlternateStateExecutorService.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract java.util.concurrent.Executor make_exec();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl make_control();

	public static interface Bridge {

	}

	public static interface Component {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public java.util.concurrent.Executor exec();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl
			implements
				AlternateStateExecutorService.Component {

		@SuppressWarnings("unused")
		private final AlternateStateExecutorService.Bridge bridge;

		private final AlternateStateExecutorService implementation;

		private ComponentImpl(final AlternateStateExecutorService implem,
				final AlternateStateExecutorService.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.exec = implem.make_exec();
			this.control = implem.make_control();

		}

		private final java.util.concurrent.Executor exec;

		public final java.util.concurrent.Executor exec() {
			return this.exec;
		};
		private final fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control;

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

	public AlternateStateExecutorService.Component newComponent(
			AlternateStateExecutorService.Bridge b) {
		return new AlternateStateExecutorService.ComponentImpl(this, b);
	}

	public AlternateStateExecutorService.Component newComponent() {
		return this.newComponent(new AlternateStateExecutorService.Bridge() {
		});
	}
	public static final AlternateStateExecutorService.Component newComponent(
			AlternateStateExecutorService _compo) {
		return _compo.newComponent();
	}

}
