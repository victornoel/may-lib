package fr.irit.smac.may.lib.components.messaging.callable;

import fr.irit.smac.may.lib.components.messaging.callable.Callable;

public abstract class Callable<I> {

	private Callable.ComponentImpl<I> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Callable.Component<I> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.MapGet<fr.irit.smac.may.lib.components.messaging.callable.CallRef, I> make_call();

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

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.call = implem.make_call();

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
	protected abstract Callable.Callee<I> make_Callee();

	/**
	 * Should not be called
	 */
	public Callable.Callee<I> createImplementationOfCallee() {
		Callable.Callee<I> implem = make_Callee();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class Callee<I> {

		private Callable.Callee.ComponentImpl<I> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Callable.Callee.Component<I> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected I toCall() {
			assert this.selfComponent != null;
			return this.selfComponent.bridge.toCall();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.callable.CallRef> make_me();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

		private Callable.ComponentImpl<I> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Callable.Component<I> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
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
					Callable.Callee.Component<I> {

			private final Callable.Callee.Bridge<I> bridge;

			private final Callable.Callee<I> implementation;

			private ComponentImpl(final Callable.Callee<I> implem,
					final Callable.Callee.Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.me = implem.make_me();
				this.stop = implem.make_stop();

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

		public Callable.Callee.Component<I> newComponent(
				Callable.Callee.Bridge<I> b) {
			return new Callable.Callee.ComponentImpl<I>(this, b);
		}

	}

	/**
	 * Should not be called
	 */
	protected abstract Callable.Caller<I> make_Caller();

	/**
	 * Should not be called
	 */
	public Callable.Caller<I> createImplementationOfCaller() {
		Callable.Caller<I> implem = make_Caller();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	/**
	 * This can be called to create an instance of the species from inside the implementation of the ecosystem.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	public Callable.Caller.Component<I> newCaller() {
		Callable.Caller<I> implem = createImplementationOfCaller();
		return implem.newComponent(new Callable.Caller.Bridge<I>() {
		});
	}

	public static abstract class Caller<I> {

		private Callable.Caller.ComponentImpl<I> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Callable.Caller.Component<I> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.MapGet<fr.irit.smac.may.lib.components.messaging.callable.CallRef, I> make_call();

		private Callable.ComponentImpl<I> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Callable.Component<I> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

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
					Callable.Caller.Component<I> {

			@SuppressWarnings("unused")
			private final Callable.Caller.Bridge<I> bridge;

			private final Callable.Caller<I> implementation;

			private ComponentImpl(final Callable.Caller<I> implem,
					final Callable.Caller.Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.call = implem.make_call();

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
		 * Can be overridden by the implementation
		 * It will be called after the component has been instantiated, after the components have been instantiated
		 * and during the containing component start() method is called.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected void start() {
		}

		public Callable.Caller.Component<I> newComponent(
				Callable.Caller.Bridge<I> b) {
			return new Callable.Caller.ComponentImpl<I>(this, b);
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

	public Callable.Component<I> newComponent(Callable.Bridge<I> b) {
		return new Callable.ComponentImpl<I>(this, b);
	}

	public Callable.Component<I> newComponent() {
		return this.newComponent(new Callable.Bridge<I>() {
		});
	}
	public static final <I> Callable.Component<I> newComponent(
			Callable<I> _compo) {
		return _compo.newComponent();
	}

}
