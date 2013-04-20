package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.CollectionOf;

public abstract class CollectionOf<I> {

	private CollectionOf.ComponentImpl<I> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected CollectionOf.Component<I> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<I>> make_get();

	public static interface Bridge<I> {

	}

	public static interface Component<I> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<I>> get();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<I>
			implements
				CollectionOf.Component<I> {

		@SuppressWarnings("unused")
		private final CollectionOf.Bridge<I> bridge;

		private final CollectionOf<I> implementation;

		private ComponentImpl(final CollectionOf<I> implem,
				final CollectionOf.Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.get = implem.make_get();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<I>> get;

		public final fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<I>> get() {
			return this.get;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract CollectionOf.Agent<I> make_Agent();

	/**
	 * Should not be called
	 */
	public CollectionOf.Agent<I> createImplementationOfAgent() {
		CollectionOf.Agent<I> implem = make_Agent();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent != null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class Agent<I> {

		private CollectionOf.Agent.ComponentImpl<I> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected CollectionOf.Agent.Component<I> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected I p() {
			assert this.selfComponent != null;
			return this.selfComponent.bridge.p();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

		private CollectionOf.ComponentImpl<I> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected CollectionOf.Component<I> eco_self() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		public static interface Bridge<I> {
			public I p();

		}

		public static interface Component<I> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Do stop();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<I>
				implements
					CollectionOf.Agent.Component<I> {

			private final CollectionOf.Agent.Bridge<I> bridge;

			private final CollectionOf.Agent<I> implementation;

			private ComponentImpl(final CollectionOf.Agent<I> implem,
					final CollectionOf.Agent.Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.stop = implem.make_stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Do stop;

			public final fr.irit.smac.may.lib.interfaces.Do stop() {
				return this.stop;
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

		public CollectionOf.Agent.Component<I> newComponent(
				CollectionOf.Agent.Bridge<I> b) {
			return new CollectionOf.Agent.ComponentImpl<I>(this, b);
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

	public CollectionOf.Component<I> newComponent(CollectionOf.Bridge<I> b) {
		return new CollectionOf.ComponentImpl<I>(this, b);
	}

	public CollectionOf.Component<I> newComponent() {
		return this.newComponent(new CollectionOf.Bridge<I>() {
		});
	}
	public static final <I> CollectionOf.Component<I> newComponent(
			CollectionOf<I> _compo) {
		return _compo.newComponent();
	}

}
