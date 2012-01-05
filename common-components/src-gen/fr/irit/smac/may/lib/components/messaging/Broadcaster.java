package fr.irit.smac.may.lib.components.messaging;

import fr.irit.smac.may.lib.components.messaging.Broadcaster;

public abstract class Broadcaster<T, Ref> {

	private Broadcaster.ComponentImpl<T, Ref> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Broadcaster.Component<T, Ref> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<T, Ref> deposit() {
		assert this.structure != null;
		return this.structure.bridge.deposit();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<T> broadcast();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Ref> add();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Ref> remove();

	public static interface Bridge<T, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<T, Ref> deposit();

	}

	public static interface Component<T, Ref> {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<T> broadcast();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<Ref> add();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<Ref> remove();

		public void start();

		public Broadcaster.Agent<T, Ref> createAgent();

	}

	private static class ComponentImpl<T, Ref>
			implements
				Broadcaster.Component<T, Ref> {

		private final Broadcaster.Bridge<T, Ref> bridge;

		private final Broadcaster<T, Ref> implementation;

		private ComponentImpl(final Broadcaster<T, Ref> implem,
				final Broadcaster.Bridge<T, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.broadcast = implem.broadcast();
			this.add = implem.add();
			this.remove = implem.remove();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<T> broadcast;

		public final fr.irit.smac.may.lib.interfaces.Push<T> broadcast() {
			return this.broadcast;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<Ref> add;

		public final fr.irit.smac.may.lib.interfaces.Push<Ref> add() {
			return this.add;
		};
		private final fr.irit.smac.may.lib.interfaces.Push<Ref> remove;

		public final fr.irit.smac.may.lib.interfaces.Push<Ref> remove() {
			return this.remove;
		};

		public final void start() {

			this.implementation.start();
		}

		public Broadcaster.Agent<T, Ref> createAgent() {
			Broadcaster.Agent<T, Ref> agentSide = this.implementation
					.make_Agent();
			agentSide.infraStructure = this;
			return agentSide;
		}

	}

	public static abstract class Agent<T, Ref> {

		private Broadcaster.Agent.ComponentImpl<T, Ref> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Broadcaster.Agent.Component<T, Ref> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Broadcast<T> bc();

		public static interface Bridge<T, Ref> {

		}

		public static interface Component<T, Ref> {
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Broadcast<T> bc();

			public void start();

		}

		private static class ComponentImpl<T, Ref>
				implements
					Broadcaster.Agent.Component<T, Ref> {

			@SuppressWarnings("unused")
			private final Broadcaster.Agent.Bridge<T, Ref> bridge;

			private final Broadcaster.Agent<T, Ref> implementation;

			private ComponentImpl(final Broadcaster.Agent<T, Ref> implem,
					final Broadcaster.Agent.Bridge<T, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.bc = implem.bc();

			}

			private final fr.irit.smac.may.lib.interfaces.Broadcast<T> bc;

			public final fr.irit.smac.may.lib.interfaces.Broadcast<T> bc() {
				return this.bc;
			};

			public final void start() {

				this.implementation.start();
			}

		}

		private Broadcaster.ComponentImpl<T, Ref> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Broadcaster.Component<T, Ref> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		/**
		 * This can be called by the implementation to access this required port from the infrastructure.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected fr.irit.smac.may.lib.interfaces.Send<T, Ref> deposit() {
			assert this.infraStructure != null;
			return this.infraStructure.bridge.deposit();
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

		public Broadcaster.Agent.Component<T, Ref> createComponent(
				Broadcaster.Agent.Bridge<T, Ref> b) {
			return new Broadcaster.Agent.ComponentImpl<T, Ref>(this, b);
		}

	}

	protected abstract Broadcaster.Agent<T, Ref> make_Agent();

	/**
	 * Can be overridden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}

	public Broadcaster.Component<T, Ref> createComponent(
			Broadcaster.Bridge<T, Ref> b) {
		return new Broadcaster.ComponentImpl<T, Ref>(this, b);
	}

}
