package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.Scheduler;

public abstract class Scheduler {

	private Scheduler.ComponentImpl structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Scheduler.Component self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor infraSched() {
		assert this.structure != null;
		return this.structure.bridge.infraSched();
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

			assert implem.structure == null;
			implem.structure = this;

		}

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract Scheduler.Agent make_Agent();

	public Scheduler.Agent createImplementationOfAgent() {
		Scheduler.Agent implem = make_Agent();
		assert implem.infraStructure == null;
		assert this.structure == null;
		implem.infraStructure = this.structure;

		return implem;
	}

	public Scheduler.Agent.Component createAgent() {
		Scheduler.Agent implem = createImplementationOfAgent();
		return implem.createComponent(new Scheduler.Agent.Bridge() {
		});
	}

	public static abstract class Agent {

		private Scheduler.Agent.ComponentImpl structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Scheduler.Agent.Component self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor sched();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do stop();

		private Scheduler.ComponentImpl infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Scheduler.Component infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
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

				assert implem.structure == null;
				implem.structure = this;

				this.sched = implem.sched();
				this.stop = implem.stop();

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

		public Scheduler.Agent.Component createComponent(
				Scheduler.Agent.Bridge b) {
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

	public Scheduler.Component createComponent(Scheduler.Bridge b) {
		return new Scheduler.ComponentImpl(this, b);
	}

}
