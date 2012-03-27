package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.Data;

public abstract class Data<T> {

	private Data.ComponentImpl<T> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Data.Component<T> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<T> make_data();

	public static interface Bridge<T> {

	}

	public static interface Component<T> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<T> data();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<T> implements Data.Component<T> {

		@SuppressWarnings("unused")
		private final Data.Bridge<T> bridge;

		private final Data<T> implementation;

		private ComponentImpl(final Data<T> implem, final Data.Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.data = implem.make_data();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<T> data;

		public final fr.irit.smac.may.lib.interfaces.Pull<T> data() {
			return this.data;
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

	public Data.Component<T> createComponent(Data.Bridge<T> b) {
		return new Data.ComponentImpl<T>(this, b);
	}

	public Data.Component<T> createComponent() {
		return this.createComponent(new Data.Bridge<T>() {
		});
	}
	public static final <T> Data.Component<T> createComponent(Data<T> _compo) {
		return _compo.createComponent();
	}

}
