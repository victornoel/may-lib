package fr.irit.smac.may.lib.components.distribution.ivy;

public abstract class IvyBinder {

	private Component structure = null;

	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.MapGet<org.javatuples.Pair<java.lang.String, fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>>>, java.lang.Integer> bindMsg() {
		assert this.structure != null;
		return this.structure.bridge.bindMsg();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg() {
		assert this.structure != null;
		return this.structure.bridge.unBindMsg();
	};
	/**
	 * This can be called by the implementation to access this required port
	 * It will be initialized before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> receive() {
		assert this.structure != null;
		return this.structure.bridge.receive();
	};

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<java.lang.String> reBindMsg();

	public static interface Bridge {
		public fr.irit.smac.may.lib.interfaces.MapGet<org.javatuples.Pair<java.lang.String, fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>>>, java.lang.Integer> bindMsg();
		public fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> unBindMsg();
		public fr.irit.smac.may.lib.interfaces.Push<java.util.List<java.lang.String>> receive();

	}

	public static final class Component {

		private final Bridge bridge;

		private final IvyBinder implementation;

		public Component(final IvyBinder implem, final Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.reBindMsg = implem.reBindMsg();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> reBindMsg;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<java.lang.String> reBindMsg() {
			return this.reBindMsg;
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

}
