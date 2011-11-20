package fr.irit.smac.may.lib.components.meta;

public abstract class Void<I> {

	private Component<I> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract I port();

	public static interface Bridge<I> {

	}

	public static final class Component<I> {

		@SuppressWarnings("unused")
		private final Bridge<I> bridge;

		private final Void<I> implementation;

		public Component(final Void<I> implem, final Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.port = implem.port();

		}

		private final I port;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final I port() {
			return this.port;
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

	public static final <I> Component<I> createComponent(Void<I> _compo) {
		return new Component<I>(_compo, new Bridge<I>() {
		});
	}

}
