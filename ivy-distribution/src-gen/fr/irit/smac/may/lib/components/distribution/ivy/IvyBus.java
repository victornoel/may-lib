package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.IvyBus;

public abstract class IvyBus {

	private IvyBus.ComponentImpl structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected IvyBus.Component self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected java.util.concurrent.Executor exec() {
		assert this.structure != null;
		return this.structure.bridge.exec();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do disconnect();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind bindMsg();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<java.lang.String> send();

	public static interface Bridge {
		public java.util.concurrent.Executor exec();

	}

	public static interface Component {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Do disconnect();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind bindMsg();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> send();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl implements IvyBus.Component {

		private final IvyBus.Bridge bridge;

		private final IvyBus implementation;

		private ComponentImpl(final IvyBus implem, final IvyBus.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.disconnect = implem.disconnect();
			this.bindMsg = implem.bindMsg();
			this.unBindMsg = implem.unBindMsg();
			this.send = implem.send();

		}

		private final fr.irit.smac.may.lib.interfaces.Do disconnect;

		public final fr.irit.smac.may.lib.interfaces.Do disconnect() {
			return this.disconnect;
		};
		private final fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind bindMsg;

		public final fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind bindMsg() {
			return this.bindMsg;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg;

		public final fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg() {
			return this.unBindMsg;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> send;

		public final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> send() {
			return this.send;
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

	public IvyBus.Component createComponent(IvyBus.Bridge b) {
		return new IvyBus.ComponentImpl(this, b);
	}

}
