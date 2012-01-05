package fr.irit.smac.may.lib.webservices;

import fr.irit.smac.may.lib.webservices.WebServiceEndpoint;

public abstract class WebServiceEndpoint<I> {

	private WebServiceEndpoint.ComponentImpl<I> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected WebServiceEndpoint.Component<I> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected I service() {
		assert this.structure != null;
		return this.structure.bridge.service();
	};

	public static interface Bridge<I> {
		public I service();

	}

	public static interface Component<I> {

		public void start();

	}

	private static class ComponentImpl<I>
			implements
				WebServiceEndpoint.Component<I> {

		private final WebServiceEndpoint.Bridge<I> bridge;

		private final WebServiceEndpoint<I> implementation;

		private ComponentImpl(final WebServiceEndpoint<I> implem,
				final WebServiceEndpoint.Bridge<I> b) {
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

	public WebServiceEndpoint.Component<I> createComponent(
			WebServiceEndpoint.Bridge<I> b) {
		return new WebServiceEndpoint.ComponentImpl<I>(this, b);
	}

}
