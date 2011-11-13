package fr.irit.smac.may.lib.components.distribution;

public abstract class JSONTransformer<T> {

	private Component<T> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<T, java.lang.String> serializer();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, T> deserializer();

	public static interface Bridge<T> {

	}

	public static final class Component<T> {

		@SuppressWarnings("unused")
		private final Bridge<T> bridge;

		private final JSONTransformer<T> implementation;

		public Component(final JSONTransformer<T> implem, final Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.serializer = implem.serializer();
			this.deserializer = implem.deserializer();

		}

		private final fr.irit.smac.may.lib.interfaces.MapGet<T, java.lang.String> serializer;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.MapGet<T, java.lang.String> serializer() {
			return this.serializer;
		};
		private final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, T> deserializer;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, T> deserializer() {
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

	public static final <T> Component<T> createComponent(
			JSONTransformer<T> _compo) {
		return new Component<T>(_compo, new Bridge<T>() {
		});
	}

}
