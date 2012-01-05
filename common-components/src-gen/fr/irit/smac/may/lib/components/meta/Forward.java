package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.components.meta.Forward;

public abstract class Forward<I> {

	private Forward.ComponentImpl<I> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Forward.Component<I> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected I i() {
		assert this.structure != null;
		return this.structure.bridge.i();
	};

	public static interface Bridge<I> {
		public I i();

	}

	public static interface Component<I> {

		public void start();

		public Forward.Agent<I> createAgent();

	}

	private static class ComponentImpl<I> implements Forward.Component<I> {

		private final Forward.Bridge<I> bridge;

		private final Forward<I> implementation;

		private ComponentImpl(final Forward<I> implem, final Forward.Bridge<I> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

		}

		public final void start() {

			this.implementation.start();
		}

		public Forward.Agent<I> createAgent() {
			Forward.Agent<I> agentSide = this.implementation.make_Agent();
			agentSide.infraStructure = this;
			return agentSide;
		}

	}

	public static abstract class Agent<I> {

		private Forward.Agent.ComponentImpl<I> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Forward.Agent.Component<I> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define the provided port.
		 * This will be called once during the construction of the component to initialize the port.
		 *
		 * This is not meant to be called on from the outside by hand.
		 */
		protected abstract I a();

		public static interface Bridge<I> {

		}

		public static interface Component<I> {
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public I a();

			public void start();

		}

		private static class ComponentImpl<I>
				implements
					Forward.Agent.Component<I> {

			@SuppressWarnings("unused")
			private final Forward.Agent.Bridge<I> bridge;

			private final Forward.Agent<I> implementation;

			private ComponentImpl(final Forward.Agent<I> implem,
					final Forward.Agent.Bridge<I> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.a = implem.a();

			}

			private final I a;

			public final I a() {
				return this.a;
			};

			public final void start() {

				this.implementation.start();
			}

		}

		private Forward.ComponentImpl<I> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Forward.Component<I> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		/**
		 * This can be called by the implementation to access this required port from the infrastructure.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected I i() {
			assert this.infraStructure != null;
			return this.infraStructure.bridge.i();
		};

		/**
		 * Can be overridden by the implementation
		 * It will be called after the component has been instantiated, after the components have been instantiated
		 * and during the containing component start() method is called.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected void start() {
		}

		public Forward.Agent.Component<I> createComponent(
				Forward.Agent.Bridge<I> b) {
			return new Forward.Agent.ComponentImpl<I>(this, b);
		}

	}

	protected abstract Forward.Agent<I> make_Agent();

	/**
	 * Can be overridden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}

	public Forward.Component<I> createComponent(Forward.Bridge<I> b) {
		return new Forward.ComponentImpl<I>(this, b);
	}

}
