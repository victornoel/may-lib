package fr.irit.smac.may.lib.components.either;

import fr.irit.smac.may.lib.components.either.UnEither;

public abstract class UnEither<L, R> {

	private UnEither.ComponentImpl<L, R> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected UnEither.Component<L, R> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<L> left() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.left();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<R> right() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.right();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L, R>> make_in();

	public static interface Bridge<L, R> {
		public fr.irit.smac.may.lib.interfaces.Push<L> left();
		public fr.irit.smac.may.lib.interfaces.Push<R> right();

	}

	public static interface Component<L, R> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L, R>> in();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<L, R>
			implements
				UnEither.Component<L, R> {

		private final UnEither.Bridge<L, R> bridge;

		private final UnEither<L, R> implementation;

		private ComponentImpl(final UnEither<L, R> implem,
				final UnEither.Bridge<L, R> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.in = implem.make_in();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L, R>> in;

		public final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L, R>> in() {
			return this.in;
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

	public UnEither.Component<L, R> newComponent(UnEither.Bridge<L, R> b) {
		return new UnEither.ComponentImpl<L, R>(this, b);
	}

}
