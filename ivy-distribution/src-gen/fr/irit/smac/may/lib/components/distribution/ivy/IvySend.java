package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.IvySend;

public abstract class IvySend {

	private IvySend.ComponentImpl structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected IvySend.Component self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<java.lang.String> send();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionStatus> connectionStatus();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig> connect();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do disconnect();

	public static interface Bridge {

	}

	public static interface Component {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.String> send();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionStatus> connectionStatus();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig> connect();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Do disconnect();

		public void start();

	}

	private static class ComponentImpl implements IvySend.Component {

		@SuppressWarnings("unused")
		private final IvySend.Bridge bridge;

		private final IvySend implementation;

		private ComponentImpl(final IvySend implem, final IvySend.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.send = implem.send();
			this.connectionStatus = implem.connectionStatus();
			this.connect = implem.connect();
			this.disconnect = implem.disconnect();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> send;

		public final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> send() {
			return this.send;
		};
		private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionStatus> connectionStatus;

		public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionStatus> connectionStatus() {
			return this.connectionStatus;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig> connect;

		public final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig> connect() {
			return this.connect;
		};
		private final fr.irit.smac.may.lib.interfaces.Do disconnect;

		public final fr.irit.smac.may.lib.interfaces.Do disconnect() {
			return this.disconnect;
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

	public IvySend.Component createComponent(IvySend.Bridge b) {
		return new IvySend.ComponentImpl(this, b);
	}

	public IvySend.Component createComponent() {
		return this.createComponent(new IvySend.Bridge() {
		});
	}

	public static final IvySend.Component createComponent(IvySend _compo) {
		return _compo.createComponent();
	}

}
