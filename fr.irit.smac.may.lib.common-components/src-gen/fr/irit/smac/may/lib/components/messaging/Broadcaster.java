package fr.irit.smac.may.lib.components.messaging;

public abstract class Broadcaster<T, Ref> {

	private Component<T, Ref> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<T, Ref> deposit() {
		assert this.structure != null;
		return this.structure.bridge.deposit();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Ref> add();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Ref> remove();

	public static interface Bridge<T, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<T, Ref> deposit();

	}

	public static final class Component<T, Ref> {

		private final Bridge<T, Ref> bridge;

		private final Broadcaster<T, Ref> implementation;

		public Component(final Broadcaster<T, Ref> implem,
				final Bridge<T, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.add = implem.add();
			this.remove = implem.remove();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<Ref> add;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<Ref> add() {
			return this.add;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<Ref> remove;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<Ref> remove() {
			return this.remove;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	public static abstract class Agent<T, Ref> {

		private Component<T, Ref> structure = null;

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Broadcast<T> bc();

		public static interface Bridge<T, Ref> {

		}

		public static final class Component<T, Ref> {

			@SuppressWarnings("unused")
			private final Bridge<T, Ref> bridge;

			private final Agent<T, Ref> implementation;

			public Component(final Agent<T, Ref> implem, final Bridge<T, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.bc = implem.bc();

			}

			private final fr.irit.smac.may.lib.interfaces.Broadcast<T> bc;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Broadcast<T> bc() {
				return this.bc;
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
