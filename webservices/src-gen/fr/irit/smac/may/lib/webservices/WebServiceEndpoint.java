package fr.irit.smac.may.lib.webservices;

public abstract class WebServiceEndpoint<I> {

	private Component<I> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final I service() {
		assert this.structure != null;
		return this.structure.bridge.service();
	};

	public static interface Bridge<I> {
		public I service();

	}

	public static final class Component<I> {

		private final Bridge<I> bridge;

		private final WebServiceEndpoint<I> implementation;

		public Component(final WebServiceEndpoint<I> implem, final Bridge<I> b) {
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
