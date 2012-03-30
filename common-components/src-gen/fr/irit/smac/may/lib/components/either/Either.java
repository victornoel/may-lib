package fr.irit.smac.may.lib.components.either;

import fr.irit.smac.may.lib.components.either.Either;

public abstract class Either<L, R> {

	private Either.ComponentImpl<L, R> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Either.Component<L, R> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L, R>> out() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.out();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<L> make_left();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<R> make_right();

	public static interface Bridge<L, R> {
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L, R>> out();

	}

	public static interface Component<L, R> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<L> left();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<R> right();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<L, R>
			implements
				Either.Component<L, R> {

		private final Either.Bridge<L, R> bridge;

		private final Either<L, R> implementation;

		private ComponentImpl(final Either<L, R> implem,
				final Either.Bridge<L, R> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.left = implem.make_left();
			this.right = implem.make_right();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<L> left;

		public final fr.irit.smac.may.lib.interfaces.Push<L> left() {
			return this.left;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<R> right;

		public final fr.irit.smac.may.lib.interfaces.Push<R> right() {
			return this.right;
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

	public Either.Component<L, R> newComponent(Either.Bridge<L, R> b) {
		return new Either.ComponentImpl<L, R>(this, b);
	}

}
