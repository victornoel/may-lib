package fr.irit.smac.may.lib.classic.remote;

public abstract class RemoteFactory<Msg, Ref> {
	private Component<Msg, Ref> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialised before the provided ports are initialised
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> infraCreate() {
		assert this.structure != null;
		return this.structure.bridge.infraCreate();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialised before the provided ports are initialised
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remplace.impl.Place> thisPlace() {
		assert this.structure != null;
		return this.structure.bridge.thisPlace();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialise the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> factCreate();

	public static interface Bridge<Msg, Ref> {
		public fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> infraCreate();
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remplace.impl.Place> thisPlace();

	}

	public static final class Component<Msg, Ref> {

		private final Bridge<Msg, Ref> bridge;

		private final RemoteFactory<Msg, Ref> implementation;

		public Component(final RemoteFactory<Msg, Ref> implem,
				final Bridge<Msg, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.factCreate = implem.factCreate();

		}

		private final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> factCreate;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> factCreate() {
			return this.factCreate;
		};

		public final void start() {
			this.implementation.start();
		}
	}

	/**
	 * Can be overriden by the implementation
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
		 * This will be called once during the construction of the component to initialise the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create();

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

			private final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create() {
				return this.create;
			};

			public final void start() {
				this.implementation.start();
			}
		}

		/**
		 * Can be overriden by the implementation
		 * It will be called after the agent part of the transverse has been instantiated
		 * and during the constructed agent start() method is called.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected void start() {
		}
	}
}
