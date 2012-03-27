package fr.irit.smac.may.lib.webservices;

import fr.irit.smac.may.lib.webservices.WebServiceClient;

public abstract class WebServiceClient<I> {

	private WebServiceClient.ComponentImpl<I> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected WebServiceClient.Component<I> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.RemoteCall<I, java.lang.String> make_service();

	public static interface Bridge<I> {

	}

	public static interface Component<I> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.RemoteCall<I, java.lang.String> service();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<I>
			implements
				WebServiceClient.Component<I> {

		@SuppressWarnings("unused")
		private final WebServiceClient.Bridge<I> bridge;

		private final WebServiceClient<I> implementation;

		private ComponentImpl(final WebServiceClient<I> implem,
				final WebServiceClient.Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.service = implem.make_service();

		}

		private final fr.irit.smac.may.lib.interfaces.RemoteCall<I, java.lang.String> service;

		public final fr.irit.smac.may.lib.interfaces.RemoteCall<I, java.lang.String> service() {
			return this.service;
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

	public WebServiceClient.Component<I> createComponent(
			WebServiceClient.Bridge<I> b) {
		return new WebServiceClient.ComponentImpl<I>(this, b);
	}

	public WebServiceClient.Component<I> createComponent() {
		return this.createComponent(new WebServiceClient.Bridge<I>() {
		});
	}
	public static final <I> WebServiceClient.Component<I> createComponent(
			WebServiceClient<I> _compo) {
		return _compo.createComponent();
	}

}
