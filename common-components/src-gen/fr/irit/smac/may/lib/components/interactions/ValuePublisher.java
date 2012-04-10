package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.ValuePublisher;

public abstract class ValuePublisher<T, K> {

	private ValuePublisher.ComponentImpl<T, K> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected ValuePublisher.Component<T, K> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.interactions.interfaces.Call<fr.irit.smac.may.lib.interfaces.Pull<T>, K> call() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.call();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve<T, K> make_observe();

	public static interface Bridge<T, K> {
		public fr.irit.smac.may.lib.components.interactions.interfaces.Call<fr.irit.smac.may.lib.interfaces.Pull<T>, K> call();

	}

	public static interface Component<T, K> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve<T, K> observe();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<T, K>
			implements
				ValuePublisher.Component<T, K> {

		private final ValuePublisher.Bridge<T, K> bridge;

		private final ValuePublisher<T, K> implementation;

		private ComponentImpl(final ValuePublisher<T, K> implem,
				final ValuePublisher.Bridge<T, K> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.observe = implem.make_observe();

		}

		private final fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve<T, K> observe;

		public final fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve<T, K> observe() {
			return this.observe;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract ValuePublisher.PublisherPush<T, K> make_PublisherPush();

	/**
	 * Should not be called
	 */
	public ValuePublisher.PublisherPush<T, K> createImplementationOfPublisherPush() {
		ValuePublisher.PublisherPush<T, K> implem = make_PublisherPush();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent != null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	/**
	 * This can be called to create an instance of the species from inside the implementation of the ecosystem.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	public ValuePublisher.PublisherPush.Component<T, K> newPublisherPush() {
		ValuePublisher.PublisherPush<T, K> implem = createImplementationOfPublisherPush();
		return implem
				.newComponent(new ValuePublisher.PublisherPush.Bridge<T, K>() {
				});
	}

	public static abstract class PublisherPush<T, K> {

		private ValuePublisher.PublisherPush.ComponentImpl<T, K> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected ValuePublisher.PublisherPush.Component<T, K> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Push<T> make_set();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<T> make_get();

		private ValuePublisher.ComponentImpl<T, K> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected ValuePublisher.Component<T, K> eco_self() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		/**
		 * This can be called by the implementation to access this required port from the ecosystem.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.components.interactions.interfaces.Call<fr.irit.smac.may.lib.interfaces.Pull<T>, K> eco_call() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.bridge.call();
		};

		public static interface Bridge<T, K> {

		}

		public static interface Component<T, K> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Push<T> set();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<T> get();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<T> toCall();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<T, K>
				implements
					ValuePublisher.PublisherPush.Component<T, K> {

			@SuppressWarnings("unused")
			private final ValuePublisher.PublisherPush.Bridge<T, K> bridge;

			private final ValuePublisher.PublisherPush<T, K> implementation;

			private ComponentImpl(
					final ValuePublisher.PublisherPush<T, K> implem,
					final ValuePublisher.PublisherPush.Bridge<T, K> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.set = implem.make_set();
				this.get = implem.make_get();

			}

			private final fr.irit.smac.may.lib.interfaces.Push<T> set;

			public final fr.irit.smac.may.lib.interfaces.Push<T> set() {
				return this.set;
			};
			private final fr.irit.smac.may.lib.interfaces.Pull<T> get;

			public final fr.irit.smac.may.lib.interfaces.Pull<T> get() {
				return this.get;
			};

			public final fr.irit.smac.may.lib.interfaces.Pull<T> toCall() {

				return ComponentImpl.this.get();

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

		public ValuePublisher.PublisherPush.Component<T, K> newComponent(
				ValuePublisher.PublisherPush.Bridge<T, K> b) {
			return new ValuePublisher.PublisherPush.ComponentImpl<T, K>(this, b);
		}

	}

	/**
	 * Should not be called
	 */
	protected abstract ValuePublisher.PublisherPull<T, K> make_PublisherPull();

	/**
	 * Should not be called
	 */
	public ValuePublisher.PublisherPull<T, K> createImplementationOfPublisherPull() {
		ValuePublisher.PublisherPull<T, K> implem = make_PublisherPull();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent != null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class PublisherPull<T, K> {

		private ValuePublisher.PublisherPull.ComponentImpl<T, K> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected ValuePublisher.PublisherPull.Component<T, K> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.interfaces.Pull<T> getValue() {
			assert this.selfComponent != null;
			return this.selfComponent.bridge.getValue();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<T> make_get();

		private ValuePublisher.ComponentImpl<T, K> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected ValuePublisher.Component<T, K> eco_self() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		/**
		 * This can be called by the implementation to access this required port from the ecosystem.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.components.interactions.interfaces.Call<fr.irit.smac.may.lib.interfaces.Pull<T>, K> eco_call() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.bridge.call();
		};

		public static interface Bridge<T, K> {
			public fr.irit.smac.may.lib.interfaces.Pull<T> getValue();

		}

		public static interface Component<T, K> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<T> get();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<T> toCall();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<T, K>
				implements
					ValuePublisher.PublisherPull.Component<T, K> {

			private final ValuePublisher.PublisherPull.Bridge<T, K> bridge;

			private final ValuePublisher.PublisherPull<T, K> implementation;

			private ComponentImpl(
					final ValuePublisher.PublisherPull<T, K> implem,
					final ValuePublisher.PublisherPull.Bridge<T, K> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.get = implem.make_get();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<T> get;

			public final fr.irit.smac.may.lib.interfaces.Pull<T> get() {
				return this.get;
			};

			public final fr.irit.smac.may.lib.interfaces.Pull<T> toCall() {

				return ComponentImpl.this.get();

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

		public ValuePublisher.PublisherPull.Component<T, K> newComponent(
				ValuePublisher.PublisherPull.Bridge<T, K> b) {
			return new ValuePublisher.PublisherPull.ComponentImpl<T, K>(this, b);
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

	public ValuePublisher.Component<T, K> newComponent(
			ValuePublisher.Bridge<T, K> b) {
		return new ValuePublisher.ComponentImpl<T, K>(this, b);
	}

}
