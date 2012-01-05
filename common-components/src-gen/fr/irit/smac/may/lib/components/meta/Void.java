package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.Void;

public abstract class Void<I> {

	private Void.ComponentImpl<I> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Void.Component<I> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract I port();

	public static interface Bridge<I> {

	}

	public static interface Component<I> {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public I port();

		public void start();

	}

	private static class ComponentImpl<I> implements Void.Component<I> {

		@SuppressWarnings("unused")
		private final Void.Bridge<I> bridge;

		private final Void<I> implementation;

		private ComponentImpl(final Void<I> implem, final Void.Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.port = implem.port();

		}

		private final I port;

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

	public Void.Component<I> createComponent(Void.Bridge<I> b) {
		return new Void.ComponentImpl<I>(this, b);
	}

	public Void.Component<I> createComponent() {
		return this.createComponent(new Void.Bridge<I>() {
		});
	}

	public static final <I> Void.Component<I> createComponent(Void<I> _compo) {
		return _compo.createComponent();
	}

}
