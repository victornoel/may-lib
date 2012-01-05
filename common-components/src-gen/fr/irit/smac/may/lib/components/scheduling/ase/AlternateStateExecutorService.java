package fr.irit.smac.may.lib.components.scheduling.ase;

import fr.irit.smac.may.lib.components.scheduling.ase.AlternateStateExecutorService;

public abstract class AlternateStateExecutorService {

	private AlternateStateExecutorService.ComponentImpl structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected AlternateStateExecutorService.Component self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract java.util.concurrent.Executor exec();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control();

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

			assert implem.structure == null;
			implem.structure = this;

			this.exec = implem.exec();
			this.control = implem.control();

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

	public AlternateStateExecutorService.Component createComponent(
			AlternateStateExecutorService.Bridge b) {
		return new AlternateStateExecutorService.ComponentImpl(this, b);
	}

	public AlternateStateExecutorService.Component createComponent() {
		return this.createComponent(new AlternateStateExecutorService.Bridge() {
		});
	}
	public static final AlternateStateExecutorService.Component createComponent(
			AlternateStateExecutorService _compo) {
		return _compo.createComponent();
	}

}
