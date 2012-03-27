package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.JSONTransformer;

public abstract class JSONTransformer<T> {

	private JSONTransformer.ComponentImpl<T> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected JSONTransformer.Component<T> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.distribution.interfaces.Transform<T, java.lang.String> make_serializer();

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.distribution.interfaces.Transform<java.lang.String, T> make_deserializer();

	public static interface Bridge<T> {

	}

	public static interface Component<T> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.distribution.interfaces.Transform<T, java.lang.String> serializer();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.distribution.interfaces.Transform<java.lang.String, T> deserializer();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<T>
			implements
				JSONTransformer.Component<T> {

		@SuppressWarnings("unused")
		private final JSONTransformer.Bridge<T> bridge;

		private final JSONTransformer<T> implementation;

		private ComponentImpl(final JSONTransformer<T> implem,
				final JSONTransformer.Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.serializer = implem.make_serializer();
			this.deserializer = implem.make_deserializer();

		}

		private final fr.irit.smac.may.lib.components.distribution.interfaces.Transform<T, java.lang.String> serializer;

		public final fr.irit.smac.may.lib.components.distribution.interfaces.Transform<T, java.lang.String> serializer() {
			return this.serializer;
		};
		private final fr.irit.smac.may.lib.components.distribution.interfaces.Transform<java.lang.String, T> deserializer;

		public final fr.irit.smac.may.lib.components.distribution.interfaces.Transform<java.lang.String, T> deserializer() {
			return this.deserializer;
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

	public JSONTransformer.Component<T> createComponent(
			JSONTransformer.Bridge<T> b) {
		return new JSONTransformer.ComponentImpl<T>(this, b);
	}

	public JSONTransformer.Component<T> createComponent() {
		return this.createComponent(new JSONTransformer.Bridge<T>() {
		});
	}
	public static final <T> JSONTransformer.Component<T> createComponent(
			JSONTransformer<T> _compo) {
		return _compo.createComponent();
	}

}
