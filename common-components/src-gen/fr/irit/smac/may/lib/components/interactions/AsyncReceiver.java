package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.components.interactions.AsyncReceiver;

public abstract class AsyncReceiver<M, K> {

	private AsyncReceiver.ComponentImpl<M, K> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected AsyncReceiver.Component<M, K> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.components.interactions.interfaces.Call<fr.irit.smac.may.lib.interfaces.Push<M>, K> call() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.call();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend<M, K> make_deposit();

	public static interface Bridge<M, K> {
		public fr.irit.smac.may.lib.components.interactions.interfaces.Call<fr.irit.smac.may.lib.interfaces.Push<M>, K> call();

	}

	public static interface Component<M, K> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend<M, K> deposit();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<M, K>
			implements
				AsyncReceiver.Component<M, K> {

		private final AsyncReceiver.Bridge<M, K> bridge;

		private final AsyncReceiver<M, K> implementation;

		private ComponentImpl(final AsyncReceiver<M, K> implem,
				final AsyncReceiver.Bridge<M, K> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.deposit = implem.make_deposit();

		}

		private final fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend<M, K> deposit;

		public final fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend<M, K> deposit() {
			return this.deposit;
		};

		public final void start() {

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract AsyncReceiver.ReceiverBuf<M, K> make_ReceiverBuf();

	/**
	 * Should not be called
	 */
	public AsyncReceiver.ReceiverBuf<M, K> createImplementationOfReceiverBuf() {
		AsyncReceiver.ReceiverBuf<M, K> implem = make_ReceiverBuf();
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
	public AsyncReceiver.ReceiverBuf.Component<M, K> createReceiverBuf() {
		AsyncReceiver.ReceiverBuf<M, K> implem = createImplementationOfReceiverBuf();
		return implem
				.createComponent(new AsyncReceiver.ReceiverBuf.Bridge<M, K>() {
				});
	}

	public static abstract class ReceiverBuf<M, K> {

		private AsyncReceiver.ReceiverBuf.ComponentImpl<M, K> selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected AsyncReceiver.ReceiverBuf.Component<M, K> self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		/**
		 * This should be overridden by the implementation to define how to create this sub-component.
		 * This will be called once during the construction of the component to initialize this sub-component.
		 */
		protected abstract Queue<M> make_q();

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Queue.Component<M> q() {
			assert this.selfComponent != null;
			return this.selfComponent.q;
		}

		private AsyncReceiver.ComponentImpl<M, K> ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected AsyncReceiver.Component<M, K> ecoSelf() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		public static interface Bridge<M, K> {

		}

		public static interface Component<M, K> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<M> get();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<M>> getAll();
			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Push<M> put();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<M, K>
				implements
					AsyncReceiver.ReceiverBuf.Component<M, K> {

			@SuppressWarnings("unused")
			private final AsyncReceiver.ReceiverBuf.Bridge<M, K> bridge;

			private final AsyncReceiver.ReceiverBuf<M, K> implementation;

			private ComponentImpl(final AsyncReceiver.ReceiverBuf<M, K> implem,
					final AsyncReceiver.ReceiverBuf.Bridge<M, K> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				assert this.implem_q == null;
				this.implem_q = implem.make_q();
				this.q = this.implem_q.createComponent(new BridgeImpl_q());
			}

			private final Queue.Component<M> q;

			private Queue<M> implem_q = null;

			private final class BridgeImpl_q implements Queue.Bridge<M> {

			}

			public final fr.irit.smac.may.lib.interfaces.Pull<M> get() {
				return this.q.get();
			};

			public final fr.irit.smac.may.lib.interfaces.Pull<java.util.Collection<M>> getAll() {
				return this.q.getAll();
			};

			public final fr.irit.smac.may.lib.interfaces.Push<M> put() {
				return this.q.put();
			};

			public final void start() {
				this.q.start();

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

		public AsyncReceiver.ReceiverBuf.Component<M, K> createComponent(
				AsyncReceiver.ReceiverBuf.Bridge<M, K> b) {
			return new AsyncReceiver.ReceiverBuf.ComponentImpl<M, K>(this, b);
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

	public AsyncReceiver.Component<M, K> createComponent(
			AsyncReceiver.Bridge<M, K> b) {
		return new AsyncReceiver.ComponentImpl<M, K>(this, b);
	}

}
