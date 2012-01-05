package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.CollectionInteger;

public abstract class CollectionInteger<I> {

	private CollectionInteger.ComponentImpl<I> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected CollectionInteger.Component<I> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<java.lang.Integer, I> get();

	public static interface Bridge<I> {

	}

	public static interface Component<I> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.MapGet<java.lang.Integer, I> get();

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

			assert implem.structure == null;
			implem.structure = this;

			this.get = implem.get();

		}

		private final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.Integer, I> get;

		public final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.Integer, I> get() {
			return this.get;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract CollectionInteger.Agent<I> make_Agent();

	public CollectionInteger.Agent<I> createImplementationOfAgent() {
		CollectionInteger.Agent<I> implem = make_Agent();
		assert implem.infraStructure == null;
		assert this.structure == null;
		implem.infraStructure = this.structure;

		return implem;
	}

	public static abstract class Agent<I> {

		private CollectionInteger.Agent.ComponentImpl<I> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected CollectionInteger.Agent.Component<I> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected I p() {
			assert this.structure != null;
			return this.structure.bridge.p();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> idx();

		private CollectionInteger.ComponentImpl<I> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected CollectionInteger.Component<I> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
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

				assert implem.structure == null;
				implem.structure = this;

				this.idx = implem.idx();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> idx;

			public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> idx() {
				return this.idx;
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

		public CollectionInteger.Agent.Component<I> createComponent(
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

	public CollectionInteger.Component<I> createComponent(
			CollectionInteger.Bridge<I> b) {
		return new CollectionInteger.ComponentImpl<I>(this, b);
	}

	public CollectionInteger.Component<I> createComponent() {
		return this.createComponent(new CollectionInteger.Bridge<I>() {
		});
	}
	public static final <I> CollectionInteger.Component<I> createComponent(
			CollectionInteger<I> _compo) {
		return _compo.createComponent();
	}

}
