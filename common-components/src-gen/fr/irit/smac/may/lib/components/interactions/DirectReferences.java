package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.DirectReferences;

public abstract class DirectReferences<I> {

	private DirectReferences.ComponentImpl<I> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected DirectReferences.Component<I> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.interactions.interfaces.Call<I, fr.irit.smac.may.lib.components.interactions.directreferences.DirRef> make_call();

	public static interface Bridge<I> {

	}

	public static interface Component<I> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.interactions.interfaces.Call<I, fr.irit.smac.may.lib.components.interactions.directreferences.DirRef> call();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<I>
			implements
				DirectReferences.Component<I> {

		@SuppressWarnings("unused")
		private final DirectReferences.Bridge<I> bridge;

		private final DirectReferences<I> implementation;

		private ComponentImpl(final DirectReferences<I> implem,
				final DirectReferences.Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.call = implem.make_call();

		}

		private final fr.irit.smac.may.lib.components.interactions.interfaces.Call<I, fr.irit.smac.may.lib.components.interactions.directreferences.DirRef> call;

		public final fr.irit.smac.may.lib.components.interactions.interfaces.Call<I, fr.irit.smac.may.lib.components.interactions.directreferences.DirRef> call() {
			return this.call;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract DirectReferences.Callee<I> make_Callee(
			java.lang.String name);

	/**
	 * Should not be called
	 */
	public DirectReferences.Callee<I> createImplementationOfCallee(
			java.lang.String name) {
		DirectReferences.Callee<I> implem = make_Callee(name);
		assert implem.ecosystemComponent == null;
		assert this.selfComponent == null;
		implem.ecosystemComponent = this.selfComponent;

		return implem;
	}

	public static abstract class Callee<I> {

		private DirectReferences.Callee.ComponentImpl<I> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected DirectReferences.Callee.Component<I> self() {
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
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.interactions.directreferences.DirRef> make_me();

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Do make_stop();

		private DirectReferences.ComponentImpl<I> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected DirectReferences.Component<I> ecoSelf() {
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
			public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.interactions.directreferences.DirRef> me();
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
					DirectReferences.Callee.Component<I> {

			private final DirectReferences.Callee.Bridge<I> bridge;

			private final DirectReferences.Callee<I> implementation;

			private ComponentImpl(final DirectReferences.Callee<I> implem,
					final DirectReferences.Callee.Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				this.me = implem.make_me();
				this.stop = implem.make_stop();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.interactions.directreferences.DirRef> me;

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.interactions.directreferences.DirRef> me() {
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

		public DirectReferences.Callee.Component<I> createComponent(
				DirectReferences.Callee.Bridge<I> b) {
			return new DirectReferences.Callee.ComponentImpl<I>(this, b);
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

	public DirectReferences.Component<I> createComponent(
			DirectReferences.Bridge<I> b) {
		return new DirectReferences.ComponentImpl<I>(this, b);
	}

	public DirectReferences.Component<I> createComponent() {
		return this.createComponent(new DirectReferences.Bridge<I>() {
		});
	}
	public static final <I> DirectReferences.Component<I> createComponent(
			DirectReferences<I> _compo) {
		return _compo.createComponent();
	}

}
