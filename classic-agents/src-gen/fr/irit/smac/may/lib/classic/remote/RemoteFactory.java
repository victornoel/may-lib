package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteFactory;

public abstract class RemoteFactory<Msg, Ref> {

	private RemoteFactory.ComponentImpl<Msg, Ref> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected RemoteFactory.Component<Msg, Ref> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> infraCreate() {
		assert this.structure != null;
		return this.structure.bridge.infraCreate();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace() {
		assert this.structure != null;
		return this.structure.bridge.thisPlace();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> factCreate();

	public static interface Bridge<Msg, Ref> {
		public fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> infraCreate();
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace();

	}

	public static interface Component<Msg, Ref> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> factCreate();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Msg, Ref>
			implements
				RemoteFactory.Component<Msg, Ref> {

		private final RemoteFactory.Bridge<Msg, Ref> bridge;

		private final RemoteFactory<Msg, Ref> implementation;

		private ComponentImpl(final RemoteFactory<Msg, Ref> implem,
				final RemoteFactory.Bridge<Msg, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.factCreate = implem.factCreate();

		}

		private final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> factCreate;

		public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> factCreate() {
			return this.factCreate;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract RemoteFactory.Agent<Msg, Ref> make_Agent();

	public RemoteFactory.Agent<Msg, Ref> createImplementationOfAgent() {
		RemoteFactory.Agent<Msg, Ref> implem = make_Agent();
		assert implem.infraStructure == null;
		assert this.structure == null;
		implem.infraStructure = this.structure;

		return implem;
	}

	public RemoteFactory.Agent.Component<Msg, Ref> createAgent() {
		RemoteFactory.Agent<Msg, Ref> implem = createImplementationOfAgent();
		return implem
				.createComponent(new RemoteFactory.Agent.Bridge<Msg, Ref>() {
				});
	}

	public static abstract class Agent<Msg, Ref> {

		private RemoteFactory.Agent.ComponentImpl<Msg, Ref> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected RemoteFactory.Agent.Component<Msg, Ref> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create();

		private RemoteFactory.ComponentImpl<Msg, Ref> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected RemoteFactory.Component<Msg, Ref> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		public static interface Bridge<Msg, Ref> {

		}

		public static interface Component<Msg, Ref> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<Msg, Ref>
				implements
					RemoteFactory.Agent.Component<Msg, Ref> {

			@SuppressWarnings("unused")
			private final RemoteFactory.Agent.Bridge<Msg, Ref> bridge;

			private final RemoteFactory.Agent<Msg, Ref> implementation;

			private ComponentImpl(final RemoteFactory.Agent<Msg, Ref> implem,
					final RemoteFactory.Agent.Bridge<Msg, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.create = implem.create();

			}

			private final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create;

			public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create() {
				return this.create;
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

		public RemoteFactory.Agent.Component<Msg, Ref> createComponent(
				RemoteFactory.Agent.Bridge<Msg, Ref> b) {
			return new RemoteFactory.Agent.ComponentImpl<Msg, Ref>(this, b);
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

	public RemoteFactory.Component<Msg, Ref> createComponent(
			RemoteFactory.Bridge<Msg, Ref> b) {
		return new RemoteFactory.ComponentImpl<Msg, Ref>(this, b);
	}

}
