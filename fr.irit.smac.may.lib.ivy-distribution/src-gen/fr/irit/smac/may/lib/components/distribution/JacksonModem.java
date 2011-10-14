package fr.irit.smac.may.lib.components.distribution;

public abstract class JacksonModem<Msg> {

	private Component<Msg> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<Msg, java.lang.String> serializer();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, Msg> deserializer();

	public static interface Bridge<Msg> {

	}

	public static final class Component<Msg> {

		@SuppressWarnings("unused")
		private final Bridge<Msg> bridge;

		private final JacksonModem<Msg> implementation;

		public Component(final JacksonModem<Msg> implem, final Bridge<Msg> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.serializer = implem.serializer();
			this.deserializer = implem.deserializer();

		}

		private final fr.irit.smac.may.lib.interfaces.MapGet<Msg, java.lang.String> serializer;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.MapGet<Msg, java.lang.String> serializer() {
			return this.serializer;
		};
		private final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, Msg> deserializer;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.MapGet<java.lang.String, Msg> deserializer() {
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

	public static final <Msg> Component<Msg> createComponent(
			JacksonModem<Msg> _compo) {
		return new Component<Msg>(_compo, new Bridge<Msg>() {
		});
	}

}
