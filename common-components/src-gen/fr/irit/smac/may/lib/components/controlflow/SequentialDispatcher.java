package fr.irit.smac.may.lib.components.controlflow;

import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;

public abstract class SequentialDispatcher<Truc> {

	private SequentialDispatcher.ComponentImpl<Truc> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected SequentialDispatcher.Component<Truc> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected java.util.concurrent.Executor executor() {
		assert this.structure != null;
		return this.structure.bridge.executor();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Push<Truc> handler() {
		assert this.structure != null;
		return this.structure.bridge.handler();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Truc> dispatch();

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Queue<Truc> make_queue();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Queue.Component<Truc> queue() {
		assert this.structure != null;
		return this.structure.queue;
	}

	public static interface Bridge<Truc> {
		public java.util.concurrent.Executor executor();
		public fr.irit.smac.may.lib.interfaces.Push<Truc> handler();

	}

	public static interface Component<Truc> {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<Truc> dispatch();

		public void start();

	}

	private static class ComponentImpl<Truc>
			implements
				SequentialDispatcher.Component<Truc> {

		private final SequentialDispatcher.Bridge<Truc> bridge;

		private final SequentialDispatcher<Truc> implementation;

		private ComponentImpl(final SequentialDispatcher<Truc> implem,
				final SequentialDispatcher.Bridge<Truc> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.dispatch = implem.dispatch();

			this.queue = implem.make_queue()
					.createComponent(new Bridge_queue());

		}

		private final Queue.Component<Truc> queue;

		private final class Bridge_queue implements Queue.Bridge<Truc> {

		}

		private final fr.irit.smac.may.lib.interfaces.Push<Truc> dispatch;

		public final fr.irit.smac.may.lib.interfaces.Push<Truc> dispatch() {
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

	public SequentialDispatcher.Component<Truc> createComponent(
			SequentialDispatcher.Bridge<Truc> b) {
		return new SequentialDispatcher.ComponentImpl<Truc>(this, b);
	}

}
