package fr.irit.smac.may.lib.components.messaging;

import fr.irit.smac.may.lib.components.messaging.Sender;

public abstract class Sender<MsgType, Ref> {

	private Sender.ComponentImpl<MsgType, Ref> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Sender.Component<MsgType, Ref> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> deposit() {
		assert this.structure != null;
		return this.structure.bridge.deposit();
	};

	public static interface Bridge<MsgType, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> deposit();

	}

	public static interface Component<MsgType, Ref> {

		public void start();

		public Sender.Agent<MsgType, Ref> createAgent();

	}

	private static class ComponentImpl<MsgType, Ref>
			implements
				Sender.Component<MsgType, Ref> {

		private final Sender.Bridge<MsgType, Ref> bridge;

		private final Sender<MsgType, Ref> implementation;

		private ComponentImpl(final Sender<MsgType, Ref> implem,
				final Sender.Bridge<MsgType, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

		}

		public final void start() {

			this.implementation.start();
		}

		public Sender.Agent<MsgType, Ref> createAgent() {
			Sender.Agent<MsgType, Ref> agentSide = this.implementation
					.make_Agent();
			agentSide.infraStructure = this;
			return agentSide;
		}

	}

	public static abstract class Agent<MsgType, Ref> {

		private Sender.Agent.ComponentImpl<MsgType, Ref> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Sender.Agent.Component<MsgType, Ref> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> send();

		public static interface Bridge<MsgType, Ref> {

		}

		public static interface Component<MsgType, Ref> {
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> send();

			public void start();

		}

		private static class ComponentImpl<MsgType, Ref>
				implements
					Sender.Agent.Component<MsgType, Ref> {

			@SuppressWarnings("unused")
			private final Sender.Agent.Bridge<MsgType, Ref> bridge;

			private final Sender.Agent<MsgType, Ref> implementation;

			private ComponentImpl(final Sender.Agent<MsgType, Ref> implem,
					final Sender.Agent.Bridge<MsgType, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.send = implem.send();

			}

			private final fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> send;

			public final fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> send() {
				return this.send;
			};

			public final void start() {

				this.implementation.start();
			}

		}

		private Sender.ComponentImpl<MsgType, Ref> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Sender.Component<MsgType, Ref> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		/**
		 * This can be called by the implementation to access this required port from the infrastructure.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected fr.irit.smac.may.lib.interfaces.Send<MsgType, Ref> deposit() {
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

		public Sender.Agent.Component<MsgType, Ref> createComponent(
				Sender.Agent.Bridge<MsgType, Ref> b) {
			return new Sender.Agent.ComponentImpl<MsgType, Ref>(this, b);
		}

	}

	protected abstract Sender.Agent<MsgType, Ref> make_Agent();

	/**
	 * Can be overridden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}

	public Sender.Component<MsgType, Ref> createComponent(
			Sender.Bridge<MsgType, Ref> b) {
		return new Sender.ComponentImpl<MsgType, Ref>(this, b);
	}

}
