package fr.irit.smac.may.lib.components.remote.place;

import fr.irit.smac.may.lib.components.remote.place.Placed;

public abstract class Placed {

	private Placed.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Placed.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> make_thisPlace();

	public static interface Bridge {

	}

	public static interface Component {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl implements Placed.Component {

		@SuppressWarnings("unused")
		private final Placed.Bridge bridge;

		private final Placed implementation;

		private ComponentImpl(final Placed implem, final Placed.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.thisPlace = implem.make_thisPlace();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace;

		public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace() {
			return this.thisPlace;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract Placed.Agent make_Agent();

	/**
	 * Should not be called
	 */
	public Placed.Agent createImplementationOfAgent() {
		Placed.Agent implem = make_Agent();
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
	public Placed.Agent.Component newAgent() {
		Placed.Agent implem = createImplementationOfAgent();
		return implem.newComponent(new Placed.Agent.Bridge() {
		});
	}

	public static abstract class Agent {

		private Placed.Agent.ComponentImpl selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Placed.Agent.Component self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> make_myPlace();

		private Placed.ComponentImpl ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Placed.Component eco_self() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		public static interface Bridge {

		}

		public static interface Component {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl
				implements
					Placed.Agent.Component {

			@SuppressWarnings("unused")
			private final Placed.Agent.Bridge bridge;

			private final Placed.Agent implementation;

			private ComponentImpl(final Placed.Agent implem,
					final Placed.Agent.Bridge b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.myPlace = implem.make_myPlace();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace;

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace() {
				return this.myPlace;
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

		public Placed.Agent.Component newComponent(Placed.Agent.Bridge b) {
			return new Placed.Agent.ComponentImpl(this, b);
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

	public Placed.Component newComponent(Placed.Bridge b) {
		return new Placed.ComponentImpl(this, b);
	}

	public Placed.Component newComponent() {
		return this.newComponent(new Placed.Bridge() {
		});
	}
	public static final Placed.Component newComponent(Placed _compo) {
		return _compo.newComponent();
	}

}
