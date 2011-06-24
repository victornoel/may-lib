package fr.irit.smac.may.lib.components;

public abstract class UnEither<L, R> {

	private Component<L, R> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<L> left() {
		assert this.structure != null;
		return this.structure.bridge.left();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<R> right() {
		assert this.structure != null;
		return this.structure.bridge.right();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.impl.Either<L, R>> in();

	public static interface Bridge<L, R> {
		public fr.irit.smac.may.lib.interfaces.Push<L> left();
		public fr.irit.smac.may.lib.interfaces.Push<R> right();

	}

	public static final class Component<L, R> {

		private final Bridge<L, R> bridge;

		private final UnEither<L, R> implementation;

		/**
		 * This constructor can be called directly to instantiate stand-alone components
		 */
		public Component(final UnEither<L, R> implem, final Bridge<L, R> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.in = implem.in();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.impl.Either<L, R>> in;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<fr.irit.smac.may.lib.components.either.impl.Either<L, R>> in() {
			return this.in;
		};

		/**
		 * This must be called to start the component and its sub-components.
		 */
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
