package fr.irit.smac.may.lib.components.controlflow;

import fr.irit.smac.may.lib.components.controlflow.Loop;

public abstract class Loop {

	private Loop.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Loop.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor executor() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.executor();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Do handler() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.handler();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

	public static interface Bridge {
		public fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor executor();
		public fr.irit.smac.may.lib.interfaces.Do handler();

	}

	public static interface Component {

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

	private final static class ComponentImpl implements Loop.Component {

		private final Loop.Bridge bridge;

		private final Loop implementation;

		private ComponentImpl(final Loop implem, final Loop.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.stop = implem.make_stop();

		}

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

	public Loop.Component newComponent(Loop.Bridge b) {
		return new Loop.ComponentImpl(this, b);
	}

}
