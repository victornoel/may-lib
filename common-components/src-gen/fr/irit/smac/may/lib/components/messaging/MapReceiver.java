package fr.irit.smac.may.lib.components.messaging;

import fr.irit.smac.may.lib.components.messaging.MapReceiver;

public abstract class MapReceiver<Msg, RealRef, Ref> {

	private MapReceiver.ComponentImpl<Msg, RealRef, Ref> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected MapReceiver.Component<Msg, RealRef, Ref> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> depositValue() {
		assert this.structure != null;
		return this.structure.bridge.depositValue();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey();

	public static interface Bridge<Msg, RealRef, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> depositValue();

	}

	public static interface Component<Msg, RealRef, Ref> {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey();

		public void start();

		public MapReceiver.Agent<Msg, RealRef, Ref> createAgent();

	}

	private static class ComponentImpl<Msg, RealRef, Ref>
			implements
				MapReceiver.Component<Msg, RealRef, Ref> {

		private final MapReceiver.Bridge<Msg, RealRef, Ref> bridge;

		private final MapReceiver<Msg, RealRef, Ref> implementation;

		private ComponentImpl(final MapReceiver<Msg, RealRef, Ref> implem,
				final MapReceiver.Bridge<Msg, RealRef, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.depositKey = implem.depositKey();

		}

		private final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey;

		public final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey() {
			return this.depositKey;
		};

		public final void start() {

			this.implementation.start();
		}

		public MapReceiver.Agent<Msg, RealRef, Ref> createAgent() {
			MapReceiver.Agent<Msg, RealRef, Ref> agentSide = this.implementation
					.make_Agent();
			agentSide.infraStructure = this;
			return agentSide;
		}

	}

	public static abstract class Agent<Msg, RealRef, Ref> {

		private MapReceiver.Agent.ComponentImpl<Msg, RealRef, Ref> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected MapReceiver.Agent.Component<Msg, RealRef, Ref> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected fr.irit.smac.may.lib.interfaces.Pull<RealRef> value() {
			assert this.structure != null;
			return this.structure.bridge.value();
		};
		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected fr.irit.smac.may.lib.interfaces.Pull<Ref> key() {
			assert this.structure != null;
			return this.structure.bridge.key();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do disconnect();

		public static interface Bridge<Msg, RealRef, Ref> {
			public fr.irit.smac.may.lib.interfaces.Pull<RealRef> value();
			public fr.irit.smac.may.lib.interfaces.Pull<Ref> key();

		}

		public static interface Component<Msg, RealRef, Ref> {
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Do disconnect();

			public void start();

		}

		private static class ComponentImpl<Msg, RealRef, Ref>
				implements
					MapReceiver.Agent.Component<Msg, RealRef, Ref> {

			private final MapReceiver.Agent.Bridge<Msg, RealRef, Ref> bridge;

			private final MapReceiver.Agent<Msg, RealRef, Ref> implementation;

			private ComponentImpl(
					final MapReceiver.Agent<Msg, RealRef, Ref> implem,
					final MapReceiver.Agent.Bridge<Msg, RealRef, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.disconnect = implem.disconnect();

			}

			private final fr.irit.smac.may.lib.interfaces.Do disconnect;

			public final fr.irit.smac.may.lib.interfaces.Do disconnect() {
				return this.disconnect;
			};

			public final void start() {

				this.implementation.start();
			}

		}

		private MapReceiver.ComponentImpl<Msg, RealRef, Ref> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected MapReceiver.Component<Msg, RealRef, Ref> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		/**
		 * This can be called by the implementation to access this required port from the infrastructure.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> depositValue() {
			assert this.infraStructure != null;
			return this.infraStructure.bridge.depositValue();
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

		public MapReceiver.Agent.Component<Msg, RealRef, Ref> createComponent(
				MapReceiver.Agent.Bridge<Msg, RealRef, Ref> b) {
			return new MapReceiver.Agent.ComponentImpl<Msg, RealRef, Ref>(this,
					b);
		}

	}

	protected abstract MapReceiver.Agent<Msg, RealRef, Ref> make_Agent();

	/**
	 * Can be overridden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}

	public MapReceiver.Component<Msg, RealRef, Ref> createComponent(
			MapReceiver.Bridge<Msg, RealRef, Ref> b) {
		return new MapReceiver.ComponentImpl<Msg, RealRef, Ref>(this, b);
	}

}
