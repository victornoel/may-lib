package fr.irit.smac.may.lib.components.meta;

public abstract class Forward<I> {

	private Component<I> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final I i() {
		assert this.structure != null;
		return this.structure.bridge.i();
	};

	public static interface Bridge<I> {
		public I i();

	}

	public static final class Component<I> {

		private final Bridge<I> bridge;

		private final Forward<I> implementation;

		public Component(final Forward<I> implem, final Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

		}

		public final void start() {

			this.implementation.start();
		}
	}

	public static abstract class Agent<I> {

		private Component<I> structure = null;

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract I a();

		public static interface Bridge<I> {

		}

		public static final class Component<I> {

			@SuppressWarnings("unused")
			private final Bridge<I> bridge;

			private final Agent<I> implementation;

			public Component(final Agent<I> implem, final Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.a = implem.a();

			}

			private final I a;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final I a() {
				return this.a;
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
