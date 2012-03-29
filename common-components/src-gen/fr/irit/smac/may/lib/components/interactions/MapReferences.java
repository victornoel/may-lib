package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.MapReferences;

public abstract class MapReferences<I, K> {

	private MapReferences.ComponentImpl<I, K> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected MapReferences.Component<I, K> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.interactions.interfaces.Call<I, K> make_call();

	public static interface Bridge<I, K> {

	}

	public static interface Component<I, K> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.interactions.interfaces.Call<I, K> call();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<I, K>
			implements
				MapReferences.Component<I, K> {

		@SuppressWarnings("unused")
		private final MapReferences.Bridge<I, K> bridge;

		private final MapReferences<I, K> implementation;

		private ComponentImpl(final MapReferences<I, K> implem,
				final MapReferences.Bridge<I, K> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.call = implem.make_call();

		}

		private final fr.irit.smac.may.lib.components.interactions.interfaces.Call<I, K> call;

		public final fr.irit.smac.may.lib.components.interactions.interfaces.Call<I, K> call() {
			return this.call;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract MapReferences.Callee<I, K> make_Callee(K key);

	/**
	 * Should not be called
	 */
	public MapReferences.Callee<I, K> createImplementationOfCallee(K key) {
		MapReferences.Callee<I, K> implem = make_Callee(key);
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class Callee<I, K> {

		private MapReferences.Callee.ComponentImpl<I, K> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected MapReferences.Callee.Component<I, K> self() {
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
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<K> make_me();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

		private MapReferences.ComponentImpl<I, K> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected MapReferences.Component<I, K> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		public static interface Bridge<I, K> {
			public I toCall();

		}

		public static interface Component<I, K> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<K> me();
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

		private final static class ComponentImpl<I, K>
				implements
					MapReferences.Callee.Component<I, K> {

			private final MapReferences.Callee.Bridge<I, K> bridge;

			private final MapReferences.Callee<I, K> implementation;

			private ComponentImpl(final MapReferences.Callee<I, K> implem,
					final MapReferences.Callee.Bridge<I, K> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.me = implem.make_me();
				this.stop = implem.make_stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<K> me;

			public final fr.irit.smac.may.lib.interfaces.Pull<K> me() {
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

		public MapReferences.Callee.Component<I, K> createComponent(
				MapReferences.Callee.Bridge<I, K> b) {
			return new MapReferences.Callee.ComponentImpl<I, K>(this, b);
		}

	}

	/**
	 * Should not be called
	 */
	protected abstract MapReferences.CalleePullKey<I, K> make_CalleePullKey();

	/**
	 * Should not be called
	 */
	public MapReferences.CalleePullKey<I, K> createImplementationOfCalleePullKey() {
		MapReferences.CalleePullKey<I, K> implem = make_CalleePullKey();
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class CalleePullKey<I, K> {

		private MapReferences.CalleePullKey.ComponentImpl<I, K> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected MapReferences.CalleePullKey.Component<I, K> self() {
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
		 * This can be called by the implementation to access this required port.
		 *
		 * This is not meant to be called from the outside.
		 */
		protected fr.irit.smac.may.lib.interfaces.Pull<K> key() {
			assert this.selfComponent != null;
			return this.selfComponent.bridge.key();
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<K> make_me();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

		private MapReferences.ComponentImpl<I, K> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected MapReferences.Component<I, K> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		public static interface Bridge<I, K> {
			public I toCall();
			public fr.irit.smac.may.lib.interfaces.Pull<K> key();

		}

		public static interface Component<I, K> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<K> me();
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

		private final static class ComponentImpl<I, K>
				implements
					MapReferences.CalleePullKey.Component<I, K> {

			private final MapReferences.CalleePullKey.Bridge<I, K> bridge;

			private final MapReferences.CalleePullKey<I, K> implementation;

			private ComponentImpl(
					final MapReferences.CalleePullKey<I, K> implem,
					final MapReferences.CalleePullKey.Bridge<I, K> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.me = implem.make_me();
				this.stop = implem.make_stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<K> me;

			public final fr.irit.smac.may.lib.interfaces.Pull<K> me() {
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

		public MapReferences.CalleePullKey.Component<I, K> createComponent(
				MapReferences.CalleePullKey.Bridge<I, K> b) {
			return new MapReferences.CalleePullKey.ComponentImpl<I, K>(this, b);
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

	public MapReferences.Component<I, K> createComponent(
			MapReferences.Bridge<I, K> b) {
		return new MapReferences.ComponentImpl<I, K>(this, b);
	}

	public MapReferences.Component<I, K> createComponent() {
		return this.createComponent(new MapReferences.Bridge<I, K>() {
		});
	}
	public static final <I, K> MapReferences.Component<I, K> createComponent(
			MapReferences<I, K> _compo) {
		return _compo.createComponent();
	}

}
