package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.Forward;

public abstract class Forward<I> {

	private Forward.ComponentImpl<I> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Forward.Component<I> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected I i() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.i();
	};

	public static interface Bridge<I> {
		public I i();

	}

	public static interface Component<I> {

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<I> implements Forward.Component<I> {

		private final Forward.Bridge<I> bridge;

		private final Forward<I> implementation;

		private ComponentImpl(final Forward<I> implem, final Forward.Bridge<I> b) {
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
	 * Should not be called
	 */
	protected abstract Forward.Agent<I> make_Agent();

	/**
	 * Should not be called
	 */
	public Forward.Agent<I> createImplementationOfAgent() {
		Forward.Agent<I> implem = make_Agent();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent != null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	/**
	 * This can be called to create an instance of the species from inside the implementation of the ecosystem.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	public Forward.Agent.Component<I> newAgent() {
		Forward.Agent<I> implem = createImplementationOfAgent();
		return implem.newComponent(new Forward.Agent.Bridge<I>() {
		});
	}

	public static abstract class Agent<I> {

		private Forward.Agent.ComponentImpl<I> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Forward.Agent.Component<I> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract I make_a();

		private Forward.ComponentImpl<I> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Forward.Component<I> eco_self() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		/**
		 * This can be called by the implementation to access this required port from the ecosystem.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected I eco_i() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.bridge.i();
		};

		public static interface Bridge<I> {

		}

		public static interface Component<I> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public I a();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<I>
				implements
					Forward.Agent.Component<I> {

			@SuppressWarnings("unused")
			private final Forward.Agent.Bridge<I> bridge;

			private final Forward.Agent<I> implementation;

			private ComponentImpl(final Forward.Agent<I> implem,
					final Forward.Agent.Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.a = implem.make_a();

			}

			private final I a;

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

		public Forward.Agent.Component<I> newComponent(Forward.Agent.Bridge<I> b) {
			return new Forward.Agent.ComponentImpl<I>(this, b);
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

	public Forward.Component<I> newComponent(Forward.Bridge<I> b) {
		return new Forward.ComponentImpl<I>(this, b);
	}

}
