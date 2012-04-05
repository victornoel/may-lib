package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.CollectionInteger;

public abstract class CollectionInteger<I> {

	private CollectionInteger.ComponentImpl<I> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected CollectionInteger.Component<I> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<java.lang.Integer, I> make_get();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> make_size();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<fj.F<I, fj.Unit>> make_forAll();

	public static interface Bridge<I> {

	}

	public static interface Component<I> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.MapGet<java.lang.Integer, I> get();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> size();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<fj.F<I, fj.Unit>> forAll();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<I>
			implements
				CollectionInteger.Component<I> {

		@SuppressWarnings("unused")
		private final CollectionInteger.Bridge<I> bridge;

		private final CollectionInteger<I> implementation;

		private ComponentImpl(final CollectionInteger<I> implem,
				final CollectionInteger.Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.get = implem.make_get();
			this.size = implem.make_size();
			this.forAll = implem.make_forAll();

		}

		private final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.Integer, I> get;

		public final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.Integer, I> get() {
			return this.get;
		};
		private final fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> size;

		public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> size() {
			return this.size;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<fj.F<I, fj.Unit>> forAll;

		public final fr.irit.smac.may.lib.interfaces.Push<fj.F<I, fj.Unit>> forAll() {
			return this.forAll;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract CollectionInteger.Agent<I> make_Agent();

	/**
	 * Should not be called
	 */
	public CollectionInteger.Agent<I> createImplementationOfAgent() {
		CollectionInteger.Agent<I> implem = make_Agent();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent != null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class Agent<I> {

		private CollectionInteger.Agent.ComponentImpl<I> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected CollectionInteger.Agent.Component<I> self() {
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
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> make_idx();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

		private CollectionInteger.ComponentImpl<I> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected CollectionInteger.Component<I> eco_self() {
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
			public fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> idx();
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
					CollectionInteger.Agent.Component<I> {

			private final CollectionInteger.Agent.Bridge<I> bridge;

			private final CollectionInteger.Agent<I> implementation;

			private ComponentImpl(final CollectionInteger.Agent<I> implem,
					final CollectionInteger.Agent.Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.idx = implem.make_idx();
				this.stop = implem.make_stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> idx;

			public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> idx() {
				return this.idx;
			};
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

		public CollectionInteger.Agent.Component<I> newComponent(
				CollectionInteger.Agent.Bridge<I> b) {
			return new CollectionInteger.Agent.ComponentImpl<I>(this, b);
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

	public CollectionInteger.Component<I> newComponent(
			CollectionInteger.Bridge<I> b) {
		return new CollectionInteger.ComponentImpl<I>(this, b);
	}

	public CollectionInteger.Component<I> newComponent() {
		return this.newComponent(new CollectionInteger.Bridge<I>() {
		});
	}
	public static final <I> CollectionInteger.Component<I> newComponent(
			CollectionInteger<I> _compo) {
		return _compo.newComponent();
	}

}
