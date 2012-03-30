package fr.irit.smac.may.lib.components.messaging;

import fr.irit.smac.may.lib.components.messaging.MapReceiver;

public abstract class MapReceiver<Msg, RealRef, Ref> {

	private MapReceiver.ComponentImpl<Msg, RealRef, Ref> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected MapReceiver.Component<Msg, RealRef, Ref> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> depositValue() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.depositValue();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> make_depositKey();

	public static interface Bridge<Msg, RealRef, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, RealRef> depositValue();

	}

	public static interface Component<Msg, RealRef, Ref> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Msg, RealRef, Ref>
			implements
				MapReceiver.Component<Msg, RealRef, Ref> {

		private final MapReceiver.Bridge<Msg, RealRef, Ref> bridge;

		private final MapReceiver<Msg, RealRef, Ref> implementation;

		private ComponentImpl(final MapReceiver<Msg, RealRef, Ref> implem,
				final MapReceiver.Bridge<Msg, RealRef, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.depositKey = implem.make_depositKey();

		}

		private final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey;

		public final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> depositKey() {
			return this.depositKey;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract MapReceiver.Agent<Msg, RealRef, Ref> make_Agent();

	/**
	 * Should not be called
	 */
	public MapReceiver.Agent<Msg, RealRef, Ref> createImplementationOfAgent() {
		MapReceiver.Agent<Msg, RealRef, Ref> implem = make_Agent();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class Agent<Msg, RealRef, Ref> {

		private MapReceiver.Agent.ComponentImpl<Msg, RealRef, Ref> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected MapReceiver.Agent.Component<Msg, RealRef, Ref> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.interfaces.Pull<RealRef> value() {
			assert this.selfComponent != null;
			return this.selfComponent.bridge.value();
		};
		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.interfaces.Pull<Ref> key() {
			assert this.selfComponent != null;
			return this.selfComponent.bridge.key();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_disconnect();

		private MapReceiver.ComponentImpl<Msg, RealRef, Ref> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected MapReceiver.Component<Msg, RealRef, Ref> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

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

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<Msg, RealRef, Ref>
				implements
					MapReceiver.Agent.Component<Msg, RealRef, Ref> {

			private final MapReceiver.Agent.Bridge<Msg, RealRef, Ref> bridge;

			private final MapReceiver.Agent<Msg, RealRef, Ref> implementation;

			private ComponentImpl(
					final MapReceiver.Agent<Msg, RealRef, Ref> implem,
					final MapReceiver.Agent.Bridge<Msg, RealRef, Ref> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.disconnect = implem.make_disconnect();

			}

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

		public MapReceiver.Agent.Component<Msg, RealRef, Ref> newComponent(
				MapReceiver.Agent.Bridge<Msg, RealRef, Ref> b) {
			return new MapReceiver.Agent.ComponentImpl<Msg, RealRef, Ref>(this,
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

	public MapReceiver.Component<Msg, RealRef, Ref> newComponent(
			MapReceiver.Bridge<Msg, RealRef, Ref> b) {
		return new MapReceiver.ComponentImpl<Msg, RealRef, Ref>(this, b);
	}

}
