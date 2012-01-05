package fr.irit.smac.may.lib.components.either;

import fr.irit.smac.may.lib.components.either.UnEither;

public abstract class UnEither<L, R> {

	private UnEither.ComponentImpl<L, R> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected UnEither.Component<L, R> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<L> left() {
		assert this.structure != null;
		return this.structure.bridge.left();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<R> right() {
		assert this.structure != null;
		return this.structure.bridge.right();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L, R>> in();

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

		public void start();

	}

	private static class ComponentImpl<L, R>
			implements
				UnEither.Component<L, R> {

		private final UnEither.Bridge<L, R> bridge;

		private final UnEither<L, R> implementation;

		private ComponentImpl(final UnEither<L, R> implem,
				final UnEither.Bridge<L, R> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.in = implem.in();

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

	public UnEither.Component<L, R> createComponent(UnEither.Bridge<L, R> b) {
		return new UnEither.ComponentImpl<L, R>(this, b);
	}

}
