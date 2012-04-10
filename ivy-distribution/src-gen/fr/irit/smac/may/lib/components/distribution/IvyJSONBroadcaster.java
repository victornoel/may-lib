package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.IvyBroadcaster;
import fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster;
import fr.irit.smac.may.lib.components.distribution.JSONTransformer;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBus;

public abstract class IvyJSONBroadcaster<T> {

	private IvyJSONBroadcaster.ComponentImpl<T> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected IvyJSONBroadcaster.Component<T> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<T> handle() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.handle();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected java.util.concurrent.Executor exec() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.exec();
	};

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract IvyBus make_ivy();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final IvyBus.Component ivy() {
		assert this.selfComponent != null;
		return this.selfComponent.ivy;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract JSONTransformer<T> make_json();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final JSONTransformer.Component<T> json() {
		assert this.selfComponent != null;
		return this.selfComponent.json;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract IvyBinder make_binder();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final IvyBinder.Component binder() {
		assert this.selfComponent != null;
		return this.selfComponent.binder;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract IvyBroadcaster<T> make_bc();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final IvyBroadcaster.Component<T> bc() {
		assert this.selfComponent != null;
		return this.selfComponent.bc;
	}

	public static interface Bridge<T> {
		public fr.irit.smac.may.lib.interfaces.Push<T> handle();
		public java.util.concurrent.Executor exec();

	}

	public static interface Component<T> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<T> send();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<T>
			implements
				IvyJSONBroadcaster.Component<T> {

		private final IvyJSONBroadcaster.Bridge<T> bridge;

		private final IvyJSONBroadcaster<T> implementation;

		private ComponentImpl(final IvyJSONBroadcaster<T> implem,
				final IvyJSONBroadcaster.Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			assert this.implem_ivy == null;
			this.implem_ivy = implem.make_ivy();
			this.ivy = this.implem_ivy.newComponent(new BridgeImpl_ivy());
			assert this.implem_json == null;
			this.implem_json = implem.make_json();
			this.json = this.implem_json.newComponent(new BridgeImpl_json());
			assert this.implem_binder == null;
			this.implem_binder = implem.make_binder();
			this.binder = this.implem_binder
					.newComponent(new BridgeImpl_binder());
			assert this.implem_bc == null;
			this.implem_bc = implem.make_bc();
			this.bc = this.implem_bc.newComponent(new BridgeImpl_bc());
		}

		private final IvyBus.Component ivy;

		private IvyBus implem_ivy = null;

		private final class BridgeImpl_ivy implements IvyBus.Bridge {

			public final java.util.concurrent.Executor exec() {
				return ComponentImpl.this.bridge.exec();

			};

		}
		private final JSONTransformer.Component<T> json;

		private JSONTransformer<T> implem_json = null;

		private final class BridgeImpl_json
				implements
					JSONTransformer.Bridge<T> {

		}
		private final IvyBinder.Component binder;

		private IvyBinder implem_binder = null;

		private final class BridgeImpl_binder implements IvyBinder.Bridge {

			public final fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind bindMsg() {
				return ComponentImpl.this.ivy.bindMsg();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> receive() {
				return ComponentImpl.this.bc.ivyReceive();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg() {
				return ComponentImpl.this.ivy.unBindMsg();

			};

		}
		private final IvyBroadcaster.Component<T> bc;

		private IvyBroadcaster<T> implem_bc = null;

		private final class BridgeImpl_bc implements IvyBroadcaster.Bridge<T> {

			public final fr.irit.smac.may.lib.components.distribution.interfaces.Transform<java.lang.String, T> deserializer() {
				return ComponentImpl.this.json.deserializer();

			};

			public final fr.irit.smac.may.lib.components.distribution.interfaces.Transform<T, java.lang.String> serializer() {
				return ComponentImpl.this.json.serializer();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<T> handle() {
				return ComponentImpl.this.bridge.handle();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivyBindMsg() {
				return ComponentImpl.this.binder.reBindMsg();

			};

			public final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> ivySend() {
				return ComponentImpl.this.ivy.send();

			};

		}

		public final fr.irit.smac.may.lib.interfaces.Push<T> send() {

			return ComponentImpl.this.bc.send();

		};

		public final void start() {
			this.ivy.start();
			this.json.start();
			this.binder.start();
			this.bc.start();

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

	public IvyJSONBroadcaster.Component<T> newComponent(
			IvyJSONBroadcaster.Bridge<T> b) {
		return new IvyJSONBroadcaster.ComponentImpl<T>(this, b);
	}

}
