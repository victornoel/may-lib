package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.SequentialDispatcher;

public abstract class RemoteClassicAgentComponent<Msg, Ref> {

	private Component<Msg, Ref> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send() {
		assert this.structure != null;
		return this.structure.bridge.send();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Pull<Ref> me() {
		assert this.structure != null;
		return this.structure.bridge.me();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final java.util.concurrent.Executor executor() {
		assert this.structure != null;
		return this.structure.bridge.executor();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Do stopExec() {
		assert this.structure != null;
		return this.structure.bridge.stopExec();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Do stopReceive() {
		assert this.structure != null;
		return this.structure.bridge.stopReceive();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create() {
		assert this.structure != null;
		return this.structure.bridge.create();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Do die();

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract SequentialDispatcher<Msg> make_dispatcher();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final SequentialDispatcher.Component<Msg> dispatcher() {
		assert this.structure != null;
		return this.structure.dispatcher;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract RemoteClassicBehaviour<Msg, Ref> make_beh();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final RemoteClassicBehaviour.Component<Msg, Ref> beh() {
		assert this.structure != null;
		return this.structure.beh;
	}

	public static interface Bridge<Msg, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send();
		public fr.irit.smac.may.lib.interfaces.Pull<Ref> me();
		public java.util.concurrent.Executor executor();
		public fr.irit.smac.may.lib.interfaces.Do stopExec();
		public fr.irit.smac.may.lib.interfaces.Do stopReceive();
		public fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create();

	}

	public static final class Component<Msg, Ref> {

		private final Bridge<Msg, Ref> bridge;

		private final RemoteClassicAgentComponent<Msg, Ref> implementation;

		public Component(final RemoteClassicAgentComponent<Msg, Ref> implem,
				final Bridge<Msg, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.die = implem.die();

			this.dispatcher = new SequentialDispatcher.Component<Msg>(
					implem.make_dispatcher(), new Bridge_dispatcher());
			this.beh = new RemoteClassicBehaviour.Component<Msg, Ref>(
					implem.make_beh(), new Bridge_beh());

		}

		private final SequentialDispatcher.Component<Msg> dispatcher;

		private final class Bridge_dispatcher
				implements
					SequentialDispatcher.Bridge<Msg> {

			public final java.util.concurrent.Executor executor() {
				return Component.this.bridge.executor();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<Msg> handler() {
				return Component.this.beh.cycle();

			};

		}
		private final RemoteClassicBehaviour.Component<Msg, Ref> beh;

		private final class Bridge_beh
				implements
					RemoteClassicBehaviour.Bridge<Msg, Ref> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send() {
				return Component.this.bridge.send();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<Ref> me() {
				return Component.this.bridge.me();

			};

			public final fr.irit.smac.may.lib.interfaces.Do die() {
				return Component.this.die();

			};

			public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create() {
				return Component.this.bridge.create();

			};

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<Msg> put() {
			return this.dispatcher.dispatch();
		};
		private final fr.irit.smac.may.lib.interfaces.Do die;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
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

}
