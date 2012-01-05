package fr.irit.smac.may.lib.components.remote.messaging.receiver;

import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver;

public abstract class RemoteReceiver<Msg, LocalRef> {

	private RemoteReceiver.ComponentImpl<Msg, LocalRef> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected RemoteReceiver.Component<Msg, LocalRef> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<Msg, LocalRef> localDeposit() {
		assert this.structure != null;
		return this.structure.bridge.localDeposit();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace() {
		assert this.structure != null;
		return this.structure.bridge.myPlace();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> deposit();

	public static interface Bridge<Msg, LocalRef> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, LocalRef> localDeposit();
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace();

	}

	public static interface Component<Msg, LocalRef> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> deposit();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Msg, LocalRef>
			implements
				RemoteReceiver.Component<Msg, LocalRef> {

		private final RemoteReceiver.Bridge<Msg, LocalRef> bridge;

		private final RemoteReceiver<Msg, LocalRef> implementation;

		private ComponentImpl(final RemoteReceiver<Msg, LocalRef> implem,
				final RemoteReceiver.Bridge<Msg, LocalRef> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.deposit = implem.deposit();

		}

		private final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> deposit;

		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> deposit() {
			return this.deposit;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract RemoteReceiver.Agent<Msg, LocalRef> make_Agent();

	public RemoteReceiver.Agent<Msg, LocalRef> createImplementationOfAgent() {
		RemoteReceiver.Agent<Msg, LocalRef> implem = make_Agent();
		assert implem.infraStructure == null;
		assert this.structure == null;
		implem.infraStructure = this.structure;

		return implem;
	}

	public static abstract class Agent<Msg, LocalRef> {

		private RemoteReceiver.Agent.ComponentImpl<Msg, LocalRef> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected RemoteReceiver.Agent.Component<Msg, LocalRef> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.interfaces.Pull<LocalRef> localMe() {
			assert this.structure != null;
			return this.structure.bridge.localMe();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do disconnect();

		private RemoteReceiver.ComponentImpl<Msg, LocalRef> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected RemoteReceiver.Component<Msg, LocalRef> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		public static interface Bridge<Msg, LocalRef> {
			public fr.irit.smac.may.lib.interfaces.Pull<LocalRef> localMe();

		}

		public static interface Component<Msg, LocalRef> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Do disconnect();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<Msg, LocalRef>
				implements
					RemoteReceiver.Agent.Component<Msg, LocalRef> {

			private final RemoteReceiver.Agent.Bridge<Msg, LocalRef> bridge;

			private final RemoteReceiver.Agent<Msg, LocalRef> implementation;

			private ComponentImpl(
					final RemoteReceiver.Agent<Msg, LocalRef> implem,
					final RemoteReceiver.Agent.Bridge<Msg, LocalRef> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.me = implem.me();
				this.disconnect = implem.disconnect();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me;

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me() {
				return this.me;
			};
			private final fr.irit.smac.may.lib.interfaces.Do disconnect;

			public final fr.irit.smac.may.lib.interfaces.Do disconnect() {
				return this.disconnect;
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

		public RemoteReceiver.Agent.Component<Msg, LocalRef> createComponent(
				RemoteReceiver.Agent.Bridge<Msg, LocalRef> b) {
			return new RemoteReceiver.Agent.ComponentImpl<Msg, LocalRef>(this,
					b);
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

	public RemoteReceiver.Component<Msg, LocalRef> createComponent(
			RemoteReceiver.Bridge<Msg, LocalRef> b) {
		return new RemoteReceiver.ComponentImpl<Msg, LocalRef>(this, b);
	}

}
