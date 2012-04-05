package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.Scheduler;

public abstract class Scheduler {

	private Scheduler.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Scheduler.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor infraSched() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.infraSched();
	};

	public static interface Bridge {
		public fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor infraSched();

	}

	public static interface Component {

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl implements Scheduler.Component {

		private final Scheduler.Bridge bridge;

		private final Scheduler implementation;

		private ComponentImpl(final Scheduler implem, final Scheduler.Bridge b) {
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
	protected abstract Scheduler.Agent make_Agent();

	/**
	 * Should not be called
	 */
	public Scheduler.Agent createImplementationOfAgent() {
		Scheduler.Agent implem = make_Agent();
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
	public Scheduler.Agent.Component newAgent() {
		Scheduler.Agent implem = createImplementationOfAgent();
		return implem.newComponent(new Scheduler.Agent.Bridge() {
		});
	}

	public static abstract class Agent {

		private Scheduler.Agent.ComponentImpl selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Scheduler.Agent.Component self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor make_sched();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

		private Scheduler.ComponentImpl ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Scheduler.Component eco_self() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		/**
		 * This can be called by the implementation to access this required port from the ecosystem.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor eco_infraSched() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.bridge.infraSched();
		};

		public static interface Bridge {

		}

		public static interface Component {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor sched();
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

		private final static class ComponentImpl
				implements
					Scheduler.Agent.Component {

			@SuppressWarnings("unused")
			private final Scheduler.Agent.Bridge bridge;

			private final Scheduler.Agent implementation;

			private ComponentImpl(final Scheduler.Agent implem,
					final Scheduler.Agent.Bridge b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.sched = implem.make_sched();
				this.stop = implem.make_stop();

			}

			private final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor sched;

			public final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor sched() {
				return this.sched;
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

		public Scheduler.Agent.Component newComponent(Scheduler.Agent.Bridge b) {
			return new Scheduler.Agent.ComponentImpl(this, b);
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

	public Scheduler.Component newComponent(Scheduler.Bridge b) {
		return new Scheduler.ComponentImpl(this, b);
	}

}
