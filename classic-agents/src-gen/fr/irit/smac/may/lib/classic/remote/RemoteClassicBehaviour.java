package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;

public abstract class RemoteClassicBehaviour<Msg, Ref> {

	private RemoteClassicBehaviour.ComponentImpl<Msg, Ref> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected RemoteClassicBehaviour.Component<Msg, Ref> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send() {
		assert this.structure != null;
		return this.structure.bridge.send();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Pull<Ref> me() {
		assert this.structure != null;
		return this.structure.bridge.me();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.interfaces.Do die() {
		assert this.structure != null;
		return this.structure.bridge.die();
	};
	/**
	 * This can be called by the implementation to access this required port.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create() {
		assert this.structure != null;
		return this.structure.bridge.create();
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.interfaces.Push<Msg> cycle();

	public static interface Bridge<Msg, Ref> {
		public fr.irit.smac.may.lib.interfaces.Send<Msg, Ref> send();
		public fr.irit.smac.may.lib.interfaces.Pull<Ref> me();
		public fr.irit.smac.may.lib.interfaces.Do die();
		public fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, Ref> create();

	}

	public static interface Component<Msg, Ref> {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Push<Msg> cycle();

		public void start();

	}

	private static class ComponentImpl<Msg, Ref>
			implements
				RemoteClassicBehaviour.Component<Msg, Ref> {

		private final RemoteClassicBehaviour.Bridge<Msg, Ref> bridge;

		private final RemoteClassicBehaviour<Msg, Ref> implementation;

		private ComponentImpl(final RemoteClassicBehaviour<Msg, Ref> implem,
				final RemoteClassicBehaviour.Bridge<Msg, Ref> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.cycle = implem.cycle();

		}

		private final fr.irit.smac.may.lib.interfaces.Push<Msg> cycle;

		public final fr.irit.smac.may.lib.interfaces.Push<Msg> cycle() {
			return this.cycle;
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

	public RemoteClassicBehaviour.Component<Msg, Ref> createComponent(
			RemoteClassicBehaviour.Bridge<Msg, Ref> b) {
		return new RemoteClassicBehaviour.ComponentImpl<Msg, Ref>(this, b);
	}

}
