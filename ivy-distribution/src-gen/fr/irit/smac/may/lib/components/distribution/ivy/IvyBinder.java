package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder;

public abstract class IvyBinder {

	private IvyBinder.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected IvyBinder.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind bindMsg() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.bindMsg();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.unBindMsg();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> receive() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.receive();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<java.lang.String> make_reBindMsg();

	public static interface Bridge {
		public fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind bindMsg();
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg();
		public fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> receive();

	}

	public static interface Component {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> reBindMsg();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl implements IvyBinder.Component {

		private final IvyBinder.Bridge bridge;

		private final IvyBinder implementation;

		private ComponentImpl(final IvyBinder implem, final IvyBinder.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.reBindMsg = implem.make_reBindMsg();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> reBindMsg;

		public final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> reBindMsg() {
			return this.reBindMsg;
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

	public IvyBinder.Component createComponent(IvyBinder.Bridge b) {
		return new IvyBinder.ComponentImpl(this, b);
	}

}
