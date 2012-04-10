package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;

public abstract class RemoteClassicAgentComponent<Msg, Ref> {

	private RemoteClassicAgentComponent.ComponentImpl<Msg, Ref> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected RemoteClassicAgentComponent.Component<Msg, Ref> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.send();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Pull<Ref> me() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.me();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected java.util.concurrent.Executor executor() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.executor();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Do stopExec() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.stopExec();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Do stopReceive() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.stopReceive();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.create();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do make_die();

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract SequentialDispatcher<Msg> make_dispatcher();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final SequentialDispatcher.Component<Msg> dispatcher() {
		assert this.selfComponent != null;
		return this.selfComponent.dispatcher;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract RemoteClassicBehaviour<Msg, Ref> make_beh();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final RemoteClassicBehaviour.Component<Msg, Ref> beh() {
		assert this.selfComponent != null;
		return this.selfComponent.beh;
	}

	public static interface Bridge<Msg, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send();
		public fr.irit.smac.may.lib.interfaces.Pull<Ref> me();
		public java.util.concurrent.Executor executor();
		public fr.irit.smac.may.lib.interfaces.Do stopExec();
		public fr.irit.smac.may.lib.interfaces.Do stopReceive();
		public fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create();

	}

	public static interface Component<Msg, Ref> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<Msg> put();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Do die();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Msg, Ref>
			implements
				RemoteClassicAgentComponent.Component<Msg, Ref> {

		private final RemoteClassicAgentComponent.Bridge<Msg, Ref> bridge;

		private final RemoteClassicAgentComponent<Msg, Ref> implementation;

		private ComponentImpl(
				final RemoteClassicAgentComponent<Msg, Ref> implem,
				final RemoteClassicAgentComponent.Bridge<Msg, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.die = implem.make_die();

			assert this.implem_dispatcher == null;
			this.implem_dispatcher = implem.make_dispatcher();
			this.dispatcher = this.implem_dispatcher
					.newComponent(new BridgeImpl_dispatcher());
			assert this.implem_beh == null;
			this.implem_beh = implem.make_beh();
			this.beh = this.implem_beh.newComponent(new BridgeImpl_beh());
		}

		private final SequentialDispatcher.Component<Msg> dispatcher;

		private SequentialDispatcher<Msg> implem_dispatcher = null;

		private final class BridgeImpl_dispatcher
				implements
					SequentialDispatcher.Bridge<Msg> {

			public final java.util.concurrent.Executor executor() {
				return ComponentImpl.this.bridge.executor();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<Msg> handler() {
				return ComponentImpl.this.beh.cycle();

			};

		}
		private final RemoteClassicBehaviour.Component<Msg, Ref> beh;

		private RemoteClassicBehaviour<Msg, Ref> implem_beh = null;

		private final class BridgeImpl_beh
				implements
					RemoteClassicBehaviour.Bridge<Msg, Ref> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send() {
				return ComponentImpl.this.bridge.send();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<Ref> me() {
				return ComponentImpl.this.bridge.me();

			};

			public final fr.irit.smac.may.lib.interfaces.Do die() {
				return ComponentImpl.this.die();

			};

			public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create() {
				return ComponentImpl.this.bridge.create();

			};

		}

		public final fr.irit.smac.may.lib.interfaces.Push<Msg> put() {

			return ComponentImpl.this.dispatcher.dispatch();

		};
		private final fr.irit.smac.may.lib.interfaces.Do die;

		public final fr.irit.smac.may.lib.interfaces.Do die() {
			return this.die;
		};

		public final void start() {
			this.dispatcher.start();
			this.beh.start();

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

	public RemoteClassicAgentComponent.Component<Msg, Ref> newComponent(
			RemoteClassicAgentComponent.Bridge<Msg, Ref> b) {
		return new RemoteClassicAgentComponent.ComponentImpl<Msg, Ref>(this, b);
	}

}
