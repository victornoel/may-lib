package fr.irit.smac.may.lib.components.controlflow;

import fr.irit.smac.may.lib.components.controlflow.Loop;

public abstract class Loop {

	private Loop.ComponentImpl structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Loop.Component self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor executor() {
		assert this.structure != null;
		return this.structure.bridge.executor();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Do handler() {
		assert this.structure != null;
		return this.structure.bridge.handler();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do stop();

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

		public void start();

	}

	private static class ComponentImpl implements Loop.Component {

		private final Loop.Bridge bridge;

		private final Loop implementation;

		private ComponentImpl(final Loop implem, final Loop.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.stop = implem.stop();

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

	public Loop.Component createComponent(Loop.Bridge b) {
		return new Loop.ComponentImpl(this, b);
	}

}
