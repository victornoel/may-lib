package fr.irit.smac.may.lib.components.scheduling;

public abstract class SchedulingControllerGUI {

	private Component structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control() {
		assert this.structure != null;
		return this.structure.bridge.control();
	};

	public static interface Bridge {
		public fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control();

	}

	public static final class Component {

		private final Bridge bridge;

		private final SchedulingControllerGUI implementation;

		public Component(final SchedulingControllerGUI implem, final Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

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

}
