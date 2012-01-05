package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.Scheduled;

public abstract class Scheduled {

	private Scheduled.ComponentImpl structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Scheduled.Component self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected java.util.concurrent.Executor sched() {
		assert this.structure != null;
		return this.structure.bridge.sched();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do tick();

	public static interface Bridge {
		public java.util.concurrent.Executor sched();

	}

	public static interface Component {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Do tick();

		public void start();

		public Scheduled.Agent createAgent();

	}

	private static class ComponentImpl implements Scheduled.Component {

		private final Scheduled.Bridge bridge;

		private final Scheduled implementation;

		private ComponentImpl(final Scheduled implem, final Scheduled.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.tick = implem.tick();

		}

		private final fr.irit.smac.may.lib.interfaces.Do tick;

		public final fr.irit.smac.may.lib.interfaces.Do tick() {
			return this.tick;
		};

		public final void start() {

			this.implementation.start();
		}

		public Scheduled.Agent createAgent() {
			Scheduled.Agent agentSide = this.implementation.make_Agent();
			agentSide.infraStructure = this;
			return agentSide;
		}

	}

	public static abstract class Agent {

		private Scheduled.Agent.ComponentImpl structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Scheduled.Agent.Component self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected fr.irit.smac.may.lib.interfaces.Do cycle() {
			assert this.structure != null;
			return this.structure.bridge.cycle();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do stop();

		public static interface Bridge {
			public fr.irit.smac.may.lib.interfaces.Do cycle();

		}

		public static interface Component {
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Do stop();

			public void start();

		}

		private static class ComponentImpl implements Scheduled.Agent.Component {

			private final Scheduled.Agent.Bridge bridge;

			private final Scheduled.Agent implementation;

			private ComponentImpl(final Scheduled.Agent implem,
					final Scheduled.Agent.Bridge b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.stop = implem.stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Do stop;

			public final fr.irit.smac.may.lib.interfaces.Do stop() {
				return this.stop;
			};

			public final void start() {

				this.implementation.start();
			}

		}

		private Scheduled.ComponentImpl infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Scheduled.Component infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		/**
		 * This can be called by the implementation to access this required port from the infrastructure.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected java.util.concurrent.Executor sched() {
			assert this.infraStructure != null;
			return this.infraStructure.bridge.sched();
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

		public Scheduled.Agent.Component createComponent(
				Scheduled.Agent.Bridge b) {
			return new Scheduled.Agent.ComponentImpl(this, b);
		}

	}

	protected abstract Scheduled.Agent make_Agent();

	/**
	 * Can be overridden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}

	public Scheduled.Component createComponent(Scheduled.Bridge b) {
		return new Scheduled.ComponentImpl(this, b);
	}

}
