package fr.irit.smac.may.lib.components.messaging.callable;

import fr.irit.smac.may.lib.components.messaging.callable.Callable;

public abstract class Callable<I> {

	private Callable.ComponentImpl<I> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Callable.Component<I> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<fr.irit.smac.may.lib.components.messaging.callable.CallRef, I> call();

	public static interface Bridge<I> {

	}

	public static interface Component<I> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.MapGet<fr.irit.smac.may.lib.components.messaging.callable.CallRef, I> call();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<I>
			implements
				Callable.Component<I> {

		@SuppressWarnings("unused")
		private final Callable.Bridge<I> bridge;

		private final Callable<I> implementation;

		private ComponentImpl(final Callable<I> implem,
				final Callable.Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.call = implem.call();

		}

		private final fr.irit.smac.may.lib.interfaces.MapGet<fr.irit.smac.may.lib.components.messaging.callable.CallRef, I> call;

		public final fr.irit.smac.may.lib.interfaces.MapGet<fr.irit.smac.may.lib.components.messaging.callable.CallRef, I> call() {
			return this.call;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract Callable.Agent<I> make_Agent();

	public Callable.Agent<I> createImplementationOfAgent() {
		Callable.Agent<I> implem = make_Agent();
		assert implem.infraStructure == null;
		assert this.structure == null;
		implem.infraStructure = this.structure;

		return implem;
	}

	public static abstract class Agent<I> {

		private Callable.Agent.ComponentImpl<I> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Callable.Agent.Component<I> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected I toCall() {
			assert this.structure != null;
			return this.structure.bridge.toCall();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.callable.CallRef> me();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do stop();

		private Callable.ComponentImpl<I> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Callable.Component<I> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		public static interface Bridge<I> {
			public I toCall();

		}

		public static interface Component<I> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.callable.CallRef> me();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Do stop();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<I>
				implements
					Callable.Agent.Component<I> {

			private final Callable.Agent.Bridge<I> bridge;

			private final Callable.Agent<I> implementation;

			private ComponentImpl(final Callable.Agent<I> implem,
					final Callable.Agent.Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.me = implem.me();
				this.stop = implem.stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.callable.CallRef> me;

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.callable.CallRef> me() {
				return this.me;
			};
			private final fr.irit.smac.may.lib.interfaces.Do stop;

			public final fr.irit.smac.may.lib.interfaces.Do stop() {
				return this.stop;
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

		public Callable.Agent.Component<I> createComponent(
				Callable.Agent.Bridge<I> b) {
			return new Callable.Agent.ComponentImpl<I>(this, b);
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

	public Callable.Component<I> createComponent(Callable.Bridge<I> b) {
		return new Callable.ComponentImpl<I>(this, b);
	}

	public Callable.Component<I> createComponent() {
		return this.createComponent(new Callable.Bridge<I>() {
		});
	}
	public static final <I> Callable.Component<I> createComponent(
			Callable<I> _compo) {
		return _compo.createComponent();
	}

}
