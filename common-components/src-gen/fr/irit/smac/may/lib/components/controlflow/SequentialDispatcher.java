package fr.irit.smac.may.lib.components.controlflow;

import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;

public abstract class SequentialDispatcher<T> {

	private SequentialDispatcher.ComponentImpl<T> selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected SequentialDispatcher.Component<T> self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected java.util.concurrent.Executor executor() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.executor();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<T> handler() {
		assert this.selfComponent != null;
		return this.selfComponent.bridge.handler();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<T> make_dispatch();

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Queue<T> make_queue();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Queue.Component<T> queue() {
		assert this.selfComponent != null;
		return this.selfComponent.queue;
	}

	public static interface Bridge<T> {
		public java.util.concurrent.Executor executor();
		public fr.irit.smac.may.lib.interfaces.Push<T> handler();

	}

	public static interface Component<T> {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<T> dispatch();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<T>
			implements
				SequentialDispatcher.Component<T> {

		private final SequentialDispatcher.Bridge<T> bridge;

		private final SequentialDispatcher<T> implementation;

		private ComponentImpl(final SequentialDispatcher<T> implem,
				final SequentialDispatcher.Bridge<T> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.dispatch = implem.make_dispatch();

			assert this.implem_queue == null;
			this.implem_queue = implem.make_queue();
			this.queue = this.implem_queue.newComponent(new BridgeImpl_queue());
		}

		private final Queue.Component<T> queue;

		private Queue<T> implem_queue = null;

		private final class BridgeImpl_queue implements Queue.Bridge<T> {

		}

		private final fr.irit.smac.may.lib.interfaces.Push<T> dispatch;

		public final fr.irit.smac.may.lib.interfaces.Push<T> dispatch() {
			return this.dispatch;
		};

		public final void start() {
			this.queue.start();

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

	public SequentialDispatcher.Component<T> newComponent(
			SequentialDispatcher.Bridge<T> b) {
		return new SequentialDispatcher.ComponentImpl<T>(this, b);
	}

}
