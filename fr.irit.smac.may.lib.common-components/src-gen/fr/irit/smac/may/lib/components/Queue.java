package fr.irit.smac.may.lib.components;

public abstract class Queue<Truc> {

	private final void init() {

	}

	private Component<Truc> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialise the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Truc> put();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialise the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<Truc> get();

	public static interface Bridge<Truc> {

	}

	public static final class Component<Truc> {

		private final Bridge<Truc> bridge;

		private final Queue<Truc> implementation;

		/**
		 * This constructor can be called directly to instantiate stand-alone components
		 */
		public Component(final Queue<Truc> implem, final Bridge<Truc> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;
			implem.init();

			this.put = implem.put();
			this.get = implem.get();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<Truc> put;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<Truc> put() {
			return this.put;
		};
		private final fr.irit.smac.may.lib.interfaces.Pull<Truc> get;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Pull<Truc> get() {
			return this.get;
		};

		/**
		 * This must be called to start the component and its sub-components.
		 */
		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Can be overriden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}
}
