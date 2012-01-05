package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.IvyBroadcaster;
import fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster;
import fr.irit.smac.may.lib.components.distribution.JSONTransformer;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBus;

public abstract class IvyJSONBroadcaster<T> {

	private IvyJSONBroadcaster.ComponentImpl<T> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected IvyJSONBroadcaster.Component<T> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<T> handle() {
		assert this.structure != null;
		return this.structure.bridge.handle();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected java.util.concurrent.Executor exec() {
		assert this.structure != null;
		return this.structure.bridge.exec();
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
		assert this.structure != null;
		return this.structure.ivy;
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
		assert this.structure != null;
		return this.structure.json;
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
		assert this.structure != null;
		return this.structure.binder;
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
		assert this.structure != null;
		return this.structure.bc;
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

		public void start();

	}

	private static class ComponentImpl<T>
			implements
				IvyJSONBroadcaster.Component<T> {

		private final IvyJSONBroadcaster.Bridge<T> bridge;

		private final IvyJSONBroadcaster<T> implementation;

		private ComponentImpl(final IvyJSONBroadcaster<T> implem,
				final IvyJSONBroadcaster.Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.ivy = implem.make_ivy().createComponent(new Bridge_ivy());
			this.json = implem.make_json().createComponent(new Bridge_json());
			this.binder = implem.make_binder().createComponent(
					new Bridge_binder());
			this.bc = implem.make_bc().createComponent(new Bridge_bc());

		}

		private final IvyBus.Component ivy;

		private final class Bridge_ivy implements IvyBus.Bridge {

			public final java.util.concurrent.Executor exec() {
				return ComponentImpl.this.bridge.exec();

			};

		}
		private final JSONTransformer.Component<T> json;

		private final class Bridge_json implements JSONTransformer.Bridge<T> {

		}
		private final IvyBinder.Component binder;

		private final class Bridge_binder implements IvyBinder.Bridge {

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

		private final class Bridge_bc implements IvyBroadcaster.Bridge<T> {

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
			return this.bc.send();
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

	public IvyJSONBroadcaster.Component<T> createComponent(
			IvyJSONBroadcaster.Bridge<T> b) {
		return new IvyJSONBroadcaster.ComponentImpl<T>(this, b);
	}

}
