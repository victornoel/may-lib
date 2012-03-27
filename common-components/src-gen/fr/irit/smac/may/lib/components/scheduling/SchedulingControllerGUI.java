package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI;

public abstract class SchedulingControllerGUI {

	private SchedulingControllerGUI.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected SchedulingControllerGUI.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.control();
	};

	public static interface Bridge {
		public fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control();

	}

	public static interface Component {

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl
			implements
				SchedulingControllerGUI.Component {

		private final SchedulingControllerGUI.Bridge bridge;

		private final SchedulingControllerGUI implementation;

		private ComponentImpl(final SchedulingControllerGUI implem,
				final SchedulingControllerGUI.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

		}

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

	public SchedulingControllerGUI.Component createComponent(
			SchedulingControllerGUI.Bridge b) {
		return new SchedulingControllerGUI.ComponentImpl(this, b);
	}

}
