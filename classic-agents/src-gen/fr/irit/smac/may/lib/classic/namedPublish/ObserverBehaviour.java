package fr.irit.smac.may.lib.classic.namedPublish;

import fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour;

public abstract class ObserverBehaviour<Ref> {

	private ObserverBehaviour.ComponentImpl<Ref> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected ObserverBehaviour.Component<Ref> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, Ref> observe() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.observe();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do make_cycle();

	public static interface Bridge<Ref> {
		public fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, Ref> observe();

	}

	public static interface Component<Ref> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Do cycle();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Ref>
			implements
				ObserverBehaviour.Component<Ref> {

		private final ObserverBehaviour.Bridge<Ref> bridge;

		private final ObserverBehaviour<Ref> implementation;

		private ComponentImpl(final ObserverBehaviour<Ref> implem,
				final ObserverBehaviour.Bridge<Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.cycle = implem.make_cycle();

		}

		private final fr.irit.smac.may.lib.interfaces.Do cycle;

		public final fr.irit.smac.may.lib.interfaces.Do cycle() {
			return this.cycle;
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

	public ObserverBehaviour.Component<Ref> newComponent(
			ObserverBehaviour.Bridge<Ref> b) {
		return new ObserverBehaviour.ComponentImpl<Ref>(this, b);
	}

}
