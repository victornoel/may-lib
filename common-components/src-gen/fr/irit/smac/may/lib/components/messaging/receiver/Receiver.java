package fr.irit.smac.may.lib.components.messaging.receiver;

import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;

public abstract class Receiver<MsgType> {

	private Receiver.ComponentImpl<MsgType> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Receiver.Component<MsgType> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> deposit();

	public static interface Bridge<MsgType> {

	}

	public static interface Component<MsgType> {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> deposit();

		public void start();

		public Receiver.Agent<MsgType> createAgent(java.lang.String name);

	}

	private static class ComponentImpl<MsgType>
			implements
				Receiver.Component<MsgType> {

		@SuppressWarnings("unused")
		private final Receiver.Bridge<MsgType> bridge;

		private final Receiver<MsgType> implementation;

		private ComponentImpl(final Receiver<MsgType> implem,
				final Receiver.Bridge<MsgType> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.deposit = implem.deposit();

		}

		private final fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> deposit;

		public final fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> deposit() {
			return this.deposit;
		};

		public final void start() {

			this.implementation.start();
		}

		public Receiver.Agent<MsgType> createAgent(java.lang.String name) {
			Receiver.Agent<MsgType> agentSide = this.implementation
					.make_Agent(name);
			agentSide.infraStructure = this;
			return agentSide;
		}

	}

	public static abstract class Agent<MsgType> {

		private Receiver.Agent.ComponentImpl<MsgType> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Receiver.Agent.Component<MsgType> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected fr.irit.smac.may.lib.interfaces.Push<MsgType> put() {
			assert this.structure != null;
			return this.structure.bridge.put();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> me();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do stop();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend<MsgType, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send();

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

			public void start();

		}

		private static class ComponentImpl<MsgType>
				implements
					Receiver.Agent.Component<MsgType> {

			private final Receiver.Agent.Bridge<MsgType> bridge;

			private final Receiver.Agent<MsgType> implementation;

			private ComponentImpl(final Receiver.Agent<MsgType> implem,
					final Receiver.Agent.Bridge<MsgType> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.me = implem.me();
				this.stop = implem.stop();
				this.send = implem.send();

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

		private Receiver.ComponentImpl<MsgType> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Receiver.Component<MsgType> infraSelf() {
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

		public Receiver.Agent.Component<MsgType> createComponent(
				Receiver.Agent.Bridge<MsgType> b) {
			return new Receiver.Agent.ComponentImpl<MsgType>(this, b);
		}

	}

	protected abstract Receiver.Agent<MsgType> make_Agent(java.lang.String name);

	/**
	 * Can be overridden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}

	public Receiver.Component<MsgType> createComponent(
			Receiver.Bridge<MsgType> b) {
		return new Receiver.ComponentImpl<MsgType>(this, b);
	}

}
