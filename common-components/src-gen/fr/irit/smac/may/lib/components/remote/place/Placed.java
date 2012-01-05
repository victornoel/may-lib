package fr.irit.smac.may.lib.components.remote.place;

import fr.irit.smac.may.lib.components.remote.place.Placed;

public abstract class Placed {

	private Placed.ComponentImpl structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Placed.Component self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace();

	public static interface Bridge {

	}

	public static interface Component {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace();

		public void start();

		public Placed.Agent createAgent();

	}

	private static class ComponentImpl implements Placed.Component {

		@SuppressWarnings("unused")
		private final Placed.Bridge bridge;

		private final Placed implementation;

		private ComponentImpl(final Placed implem, final Placed.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.thisPlace = implem.thisPlace();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace;

		public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace() {
			return this.thisPlace;
		};

		public final void start() {

			this.implementation.start();
		}

		public Placed.Agent createAgent() {
			Placed.Agent agentSide = this.implementation.make_Agent();
			agentSide.infraStructure = this;
			return agentSide;
		}

	}

	public static abstract class Agent {

		private Placed.Agent.ComponentImpl structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Placed.Agent.Component self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace();

		public static interface Bridge {

		}

		public static interface Component {
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace();

			public void start();

		}

		private static class ComponentImpl implements Placed.Agent.Component {

			@SuppressWarnings("unused")
			private final Placed.Agent.Bridge bridge;

			private final Placed.Agent implementation;

			private ComponentImpl(final Placed.Agent implem,
					final Placed.Agent.Bridge b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.myPlace = implem.myPlace();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace;

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace() {
				return this.myPlace;
			};

			public final void start() {

				this.implementation.start();
			}

		}

		private Placed.ComponentImpl infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Placed.Component infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		/**
		 * Can be overridden by the implementation
		 * It will be called after the component has been instantiated, after the components have been instantiated
		 * and during the containing component start() method is called.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected void start() {
		}

		public Placed.Agent.Component createComponent(Placed.Agent.Bridge b) {
			return new Placed.Agent.ComponentImpl(this, b);
		}

	}

	protected abstract Placed.Agent make_Agent();

	/**
	 * Can be overridden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}

	public Placed.Component createComponent(Placed.Bridge b) {
		return new Placed.ComponentImpl(this, b);
	}

}
