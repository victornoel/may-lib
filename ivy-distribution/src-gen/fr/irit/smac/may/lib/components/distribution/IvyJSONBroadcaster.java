package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.IvyBroadcaster;
import fr.irit.smac.may.lib.components.distribution.JSONTransformer;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBus;

public abstract class IvyJSONBroadcaster<T> {

	private Component<T> structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<T> handle() {
		assert this.structure != null;
		return this.structure.bridge.handle();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final java.util.concurrent.Executor exec() {
		assert this.structure != null;
		return this.structure.bridge.exec();
	};

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract IvyBroadcaster<T> make_bc();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final IvyBroadcaster.Component<T> bc() {
		assert this.structure != null;
		return this.structure.bc;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract IvyBinder make_binder();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final IvyBinder.Component binder() {
		assert this.structure != null;
		return this.structure.binder;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract IvyBus make_ivy();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final IvyBus.Component ivy() {
		assert this.structure != null;
		return this.structure.ivy;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract JSONTransformer<T> make_json();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final JSONTransformer.Component<T> json() {
		assert this.structure != null;
		return this.structure.json;
	}

	public static interface Bridge<T> {
		public fr.irit.smac.may.lib.interfaces.Push<T> handle();
		public java.util.concurrent.Executor exec();

	}

	public static final class Component<T> {

		private final Bridge<T> bridge;

		private final IvyJSONBroadcaster<T> implementation;

		public Component(final IvyJSONBroadcaster<T> implem, final Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.bc = new IvyBroadcaster.Component<T>(implem.make_bc(),
					new Bridge_bc());
			this.binder = new IvyBinder.Component(implem.make_binder(),
					new Bridge_binder());
			this.ivy = new IvyBus.Component(implem.make_ivy(), new Bridge_ivy());
			this.json = new JSONTransformer.Component<T>(implem.make_json(),
					new Bridge_json());

		}

		private final IvyBroadcaster.Component<T> bc;

		private final class Bridge_bc implements IvyBroadcaster.Bridge<T> {

			public final fr.irit.smac.may.lib.components.distribution.interfaces.Transform<java.lang.String, T> deserializer() {
				return Component.this.json.deserializer();

			};

			public final fr.irit.smac.may.lib.components.distribution.interfaces.Transform<T, java.lang.String> serializer() {
				return Component.this.json.serializer();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<T> handle() {
				return Component.this.bridge.handle();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivyBindMsg() {
				return Component.this.binder.reBindMsg();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivySend() {
				return Component.this.ivy.send();

			};

		}
		private final IvyBinder.Component binder;

		private final class Bridge_binder implements IvyBinder.Bridge {

			public final fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind bindMsg() {
				return Component.this.ivy.bindMsg();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> receive() {
				return Component.this.bc.ivyReceive();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg() {
				return Component.this.ivy.unBindMsg();

			};

		}
		private final IvyBus.Component ivy;

		private final class Bridge_ivy implements IvyBus.Bridge {

			public final java.util.concurrent.Executor exec() {
				return Component.this.bridge.exec();

			};

		}
		private final JSONTransformer.Component<T> json;

		private final class Bridge_json implements JSONTransformer.Bridge<T> {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<T> send() {
			return this.bc.send();
		};

		public final void start() {
			this.bc.start();
			this.binder.start();
			this.ivy.start();
			this.json.start();

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
