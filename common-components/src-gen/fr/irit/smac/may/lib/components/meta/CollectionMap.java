package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.CollectionMap;

public abstract class CollectionMap<K, I> {

	private CollectionMap.ComponentImpl<K, I> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected CollectionMap.Component<K, I> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<K, I> make_get();

	public static interface Bridge<K, I> {

	}

	public static interface Component<K, I> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.MapGet<K, I> get();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<K, I>
			implements
				CollectionMap.Component<K, I> {

		@SuppressWarnings("unused")
		private final CollectionMap.Bridge<K, I> bridge;

		private final CollectionMap<K, I> implementation;

		private ComponentImpl(final CollectionMap<K, I> implem,
				final CollectionMap.Bridge<K, I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.get = implem.make_get();

		}

		private final fr.irit.smac.may.lib.interfaces.MapGet<K, I> get;

		public final fr.irit.smac.may.lib.interfaces.MapGet<K, I> get() {
			return this.get;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract CollectionMap.Agent<K, I> make_Agent();

	/**
	 * Should not be called
	 */
	public CollectionMap.Agent<K, I> createImplementationOfAgent() {
		CollectionMap.Agent<K, I> implem = make_Agent();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class Agent<K, I> {

		private CollectionMap.Agent.ComponentImpl<K, I> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected CollectionMap.Agent.Component<K, I> self() {
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
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.interfaces.Pull<K> key() {
			assert this.selfComponent != null;
			return this.selfComponent.bridge.key();
		};

		private CollectionMap.ComponentImpl<K, I> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected CollectionMap.Component<K, I> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		public static interface Bridge<K, I> {
			public I p();
			public fr.irit.smac.may.lib.interfaces.Pull<K> key();

		}

		public static interface Component<K, I> {

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<K, I>
				implements
					CollectionMap.Agent.Component<K, I> {

			private final CollectionMap.Agent.Bridge<K, I> bridge;

			private final CollectionMap.Agent<K, I> implementation;

			private ComponentImpl(final CollectionMap.Agent<K, I> implem,
					final CollectionMap.Agent.Bridge<K, I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

			}

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

		public CollectionMap.Agent.Component<K, I> createComponent(
				CollectionMap.Agent.Bridge<K, I> b) {
			return new CollectionMap.Agent.ComponentImpl<K, I>(this, b);
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

	public CollectionMap.Component<K, I> createComponent(
			CollectionMap.Bridge<K, I> b) {
		return new CollectionMap.ComponentImpl<K, I>(this, b);
	}

	public CollectionMap.Component<K, I> createComponent() {
		return this.createComponent(new CollectionMap.Bridge<K, I>() {
		});
	}
	public static final <K, I> CollectionMap.Component<K, I> createComponent(
			CollectionMap<K, I> _compo) {
		return _compo.createComponent();
	}

}
