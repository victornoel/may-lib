package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;
import fr.irit.smac.may.lib.components.meta.Data;

public abstract class ClassicNamedAgentComponent<Msg, Ref> {

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
	protected final fr.irit.smac.may.lib.interfaces.Do die() {
		assert this.structure != null;
		return this.structure.bridge.die();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, Ref> create() {
		assert this.structure != null;
		return this.structure.bridge.create();
	};

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract Data<Ref> make_name();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Data.Component<Ref> name() {
		assert this.structure != null;
		return this.structure.name;
	}

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
	protected abstract ClassicNamedBehaviour<Msg, Ref> make_beh();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final ClassicNamedBehaviour.Component<Msg, Ref> beh() {
		assert this.structure != null;
		return this.structure.beh;
	}

	public static interface Bridge<Msg, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send();
		public java.util.concurrent.Executor executor();
		public fr.irit.smac.may.lib.interfaces.Do die();
		public fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, Ref> create();

	}

	public static final class Component<Msg, Ref> {

		private final Bridge<Msg, Ref> bridge;

		private final ClassicNamedAgentComponent<Msg, Ref> implementation;

		public Component(final ClassicNamedAgentComponent<Msg, Ref> implem,
				final Bridge<Msg, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.name = new Data.Component<Ref>(implem.make_name(),
					new Bridge_name());
			this.dispatcher = new SequentialDispatcher.Component<Msg>(
					implem.make_dispatcher(), new Bridge_dispatcher());
			this.beh = new ClassicNamedBehaviour.Component<Msg, Ref>(
					implem.make_beh(), new Bridge_beh());

		}

		private final Data.Component<Ref> name;

		private final class Bridge_name implements Data.Bridge<Ref> {

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
		private final ClassicNamedBehaviour.Component<Msg, Ref> beh;

		private final class Bridge_beh
				implements
					ClassicNamedBehaviour.Bridge<Msg, Ref> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send() {
				return Component.this.bridge.send();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<Ref> me() {
				return Component.this.me();

			};

			public final fr.irit.smac.may.lib.interfaces.Do die() {
				return Component.this.bridge.die();

			};

			public final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, Ref> create() {
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

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Pull<Ref> me() {
			return this.name.data();
		};

		public final void start() {
			this.name.start();
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
