package fr.irit.smac.may.lib.components.messaging;

import fr.irit.smac.may.lib.components.messaging.Broadcaster;

public abstract class Broadcaster<T, Ref> {

	private Broadcaster.ComponentImpl<T, Ref> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Broadcaster.Component<T, Ref> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<T, Ref> deposit() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.deposit();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<T> make_broadcast();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Ref> make_add();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Ref> make_remove();

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

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<T, Ref>
			implements
				Broadcaster.Component<T, Ref> {

		private final Broadcaster.Bridge<T, Ref> bridge;

		private final Broadcaster<T, Ref> implementation;

		private ComponentImpl(final Broadcaster<T, Ref> implem,
				final Broadcaster.Bridge<T, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.broadcast = implem.make_broadcast();
			this.add = implem.make_add();
			this.remove = implem.make_remove();

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
	}

	/**
	 * Should not be called
	 */
	protected abstract Broadcaster.Agent<T, Ref> make_Agent();

	/**
	 * Should not be called
	 */
	public Broadcaster.Agent<T, Ref> createImplementationOfAgent() {
		Broadcaster.Agent<T, Ref> implem = make_Agent();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	/**
	 * This can be called to create an instance of the species from inside the implementation of the ecosystem.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	public Broadcaster.Agent.Component<T, Ref> createAgent() {
		Broadcaster.Agent<T, Ref> implem = createImplementationOfAgent();
		return implem.createComponent(new Broadcaster.Agent.Bridge<T, Ref>() {
		});
	}

	public static abstract class Agent<T, Ref> {

		private Broadcaster.Agent.ComponentImpl<T, Ref> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Broadcaster.Agent.Component<T, Ref> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Broadcast<T> make_bc();

		private Broadcaster.ComponentImpl<T, Ref> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Broadcaster.Component<T, Ref> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		public static interface Bridge<T, Ref> {

		}

		public static interface Component<T, Ref> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Broadcast<T> bc();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<T, Ref>
				implements
					Broadcaster.Agent.Component<T, Ref> {

			@SuppressWarnings("unused")
			private final Broadcaster.Agent.Bridge<T, Ref> bridge;

			private final Broadcaster.Agent<T, Ref> implementation;

			private ComponentImpl(final Broadcaster.Agent<T, Ref> implem,
					final Broadcaster.Agent.Bridge<T, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.bc = implem.make_bc();

			}

			private final fr.irit.smac.may.lib.interfaces.Broadcast<T> bc;

			public final fr.irit.smac.may.lib.interfaces.Broadcast<T> bc() {
				return this.bc;
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

		public Broadcaster.Agent.Component<T, Ref> createComponent(
				Broadcaster.Agent.Bridge<T, Ref> b) {
			return new Broadcaster.Agent.ComponentImpl<T, Ref>(this, b);
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

	public Broadcaster.Component<T, Ref> createComponent(
			Broadcaster.Bridge<T, Ref> b) {
		return new Broadcaster.ComponentImpl<T, Ref>(this, b);
	}

}
