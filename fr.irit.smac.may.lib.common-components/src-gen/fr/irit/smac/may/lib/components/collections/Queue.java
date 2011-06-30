package fr.irit.smac.may.lib.components.collections;

public abstract class Queue<Truc> {

	private Component<Truc> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Truc> put();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<Truc> get();

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<Truc>> getAll();

	public static interface Bridge<Truc> {

	}

	public static final class Component<Truc> {

		@SuppressWarnings("unused")
		private final Bridge<Truc> bridge;

		private final Queue<Truc> implementation;

		public Component(final Queue<Truc> implem, final Bridge<Truc> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.put = implem.put();
			this.get = implem.get();
			this.getAll = implem.getAll();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<Truc> put;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Push<Truc> put() {
			return this.put;
		};
		private final fr.irit.smac.may.lib.interfaces.Pull<Truc> get;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Pull<Truc> get() {
			return this.get;
		};
		private final fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<Truc>> getAll;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<Truc>> getAll() {
			return this.getAll;
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

	public static final <Truc> Component<Truc> createComponent(
			Queue<Truc> _compo) {
		return new Component<Truc>(_compo, new Bridge<Truc>() {
		});
	}

}
