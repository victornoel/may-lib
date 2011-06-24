package fr.irit.smac.may.lib.classic.local;

public abstract class Factory<Msg, Ref> {
	private Component<Msg, Ref> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, Ref> infraCreate() {
		assert this.structure != null;
		return this.structure.bridge.infraCreate();
	};

	public static interface Bridge<Msg, Ref> {
		public fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, Ref> infraCreate();

	}

	public static final class Component<Msg, Ref> {

		private final Bridge<Msg, Ref> bridge;

		private final Factory<Msg, Ref> implementation;

		public Component(final Factory<Msg, Ref> implem,
				final Bridge<Msg, Ref> b) {
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
	 * It will be called after the infrastructure part of the transverse has been instantiated
	 * and during the containing infrastructure start() method is called.
	 *
	 * This is not meant to be called by hand
	 */
	protected void start() {
	}

	public static abstract class Agent<Msg, Ref> {
		private Component<Msg, Ref> structure = null;

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, Ref> create();

		public static interface Bridge<Msg, Ref> {

		}

		public static final class Component<Msg, Ref> {

			@SuppressWarnings("unused")
			private final Bridge<Msg, Ref> bridge;

			private final Agent<Msg, Ref> implementation;

			public Component(final Agent<Msg, Ref> implem,
					final Bridge<Msg, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.create = implem.create();

			}

			private final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, Ref> create;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, Ref> create() {
				return this.create;
			};

			public final void start() {
				this.implementation.start();
			}
		}

		/**
		 * Can be overridden by the implementation
		 * It will be called after the agent part of the transverse has been instantiated
		 * and during the constructed agent start() method is called.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected void start() {
		}
	}
}
