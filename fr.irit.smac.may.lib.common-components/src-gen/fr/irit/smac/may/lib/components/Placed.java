package fr.irit.smac.may.lib.components;

public abstract class Placed {

	private Component structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remplace.impl.Place> thisPlace();

	public static interface Bridge {

	}

	public static final class Component {

		@SuppressWarnings("unused")
		private final Bridge bridge;

		private final Placed implementation;

		public Component(final Placed implem, final Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.thisPlace = implem.thisPlace();

		}

		private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remplace.impl.Place> thisPlace;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remplace.impl.Place> thisPlace() {
			return this.thisPlace;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	public static abstract class Agent {

		private Component structure = null;

		/**
		 * This should be overridden by the implementation to define the provided port
		 * This will be called once during the construction of the component to initialize the port
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected abstract fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remplace.impl.Place> myPlace();

		public static interface Bridge {

		}

		public static final class Component {

			@SuppressWarnings("unused")
			private final Bridge bridge;

			private final Agent implementation;

			public Component(final Agent implem, final Bridge b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				this.myPlace = implem.myPlace();

			}

			private final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remplace.impl.Place> myPlace;

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remplace.impl.Place> myPlace() {
				return this.myPlace;
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
