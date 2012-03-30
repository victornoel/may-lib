package fr.irit.smac.may.lib.components.messaging.receiver;

import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;

public abstract class Receiver<MsgType> {

	private Receiver.ComponentImpl<MsgType> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Receiver.Component<MsgType> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> make_deposit();

	public static interface Bridge<MsgType> {

	}

	public static interface Component<MsgType> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> deposit();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<MsgType>
			implements
				Receiver.Component<MsgType> {

		@SuppressWarnings("unused")
		private final Receiver.Bridge<MsgType> bridge;

		private final Receiver<MsgType> implementation;

		private ComponentImpl(final Receiver<MsgType> implem,
				final Receiver.Bridge<MsgType> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.deposit = implem.make_deposit();

		}

		private final fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> deposit;

		public final fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> deposit() {
			return this.deposit;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract Receiver.Agent<MsgType> make_Agent(java.lang.String name);

	/**
	 * Should not be called
	 */
	public Receiver.Agent<MsgType> createImplementationOfAgent(
			java.lang.String name) {
		Receiver.Agent<MsgType> implem = make_Agent(name);
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class Agent<MsgType> {

		private Receiver.Agent.ComponentImpl<MsgType> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Receiver.Agent.Component<MsgType> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.interfaces.Push<MsgType> put() {
			assert this.selfComponent != null;
			return this.selfComponent.bridge.put();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> make_me();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> make_send();

		private Receiver.ComponentImpl<MsgType> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Receiver.Component<MsgType> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		public static interface Bridge<MsgType> {
			public fr.irit.smac.may.lib.interfaces.Push<MsgType> put();

		}

		public static interface Component<MsgType> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> me();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Do stop();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<MsgType>
				implements
					Receiver.Agent.Component<MsgType> {

			private final Receiver.Agent.Bridge<MsgType> bridge;

			private final Receiver.Agent<MsgType> implementation;

			private ComponentImpl(final Receiver.Agent<MsgType> implem,
					final Receiver.Agent.Bridge<MsgType> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.me = implem.make_me();
				this.stop = implem.make_stop();
				this.send = implem.make_send();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> me;

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> me() {
				return this.me;
			};
			private final fr.irit.smac.may.lib.interfaces.Do stop;

			public final fr.irit.smac.may.lib.interfaces.Do stop() {
				return this.stop;
			};
			private final fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send;

			public final fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send() {
				return this.send;
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

		public Receiver.Agent.Component<MsgType> newComponent(
				Receiver.Agent.Bridge<MsgType> b) {
			return new Receiver.Agent.ComponentImpl<MsgType>(this, b);
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

	public Receiver.Component<MsgType> newComponent(Receiver.Bridge<MsgType> b) {
		return new Receiver.ComponentImpl<MsgType>(this, b);
	}

	public Receiver.Component<MsgType> newComponent() {
		return this.newComponent(new Receiver.Bridge<MsgType>() {
		});
	}
	public static final <MsgType> Receiver.Component<MsgType> newComponent(
			Receiver<MsgType> _compo) {
		return _compo.newComponent();
	}

}
