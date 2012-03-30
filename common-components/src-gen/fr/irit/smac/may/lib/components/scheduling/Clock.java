package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.Clock;

public abstract class Clock {

	private Clock.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Clock.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected java.util.concurrent.Executor sched() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.sched();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Do tick() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.tick();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl make_control();

	public static interface Bridge {
		public java.util.concurrent.Executor sched();
		public fr.irit.smac.may.lib.interfaces.Do tick();

	}

	public static interface Component {

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

	private final static class ComponentImpl implements Clock.Component {

		private final Clock.Bridge bridge;

		private final Clock implementation;

		private ComponentImpl(final Clock implem, final Clock.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.control = implem.make_control();

		}

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

	public Clock.Component newComponent(Clock.Bridge b) {
		return new Clock.ComponentImpl(this, b);
	}

}
