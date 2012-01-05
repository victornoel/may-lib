package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.components.messaging.Sender;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver;
import fr.irit.smac.may.lib.components.remote.place.Placed;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;

public abstract class RemoteClassic<Msg> {

	private RemoteClassic.ComponentImpl<Msg> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected RemoteClassic.Component<Msg> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> create();

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Scheduler make_scheduler();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Scheduler.Component scheduler() {
		assert this.structure != null;
		return this.structure.scheduler;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Sender<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> make_sender();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Sender.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> sender() {
		assert this.structure != null;
		return this.structure.sender;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Receiver<Msg> make_receive();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Receiver.Component<Msg> receive() {
		assert this.structure != null;
		return this.structure.receive;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Placed make_placed();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Placed.Component placed() {
		assert this.structure != null;
		return this.structure.placed;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract RemoteReceiver<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> make_remReceive();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final RemoteReceiver.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> remReceive() {
		assert this.structure != null;
		return this.structure.remReceive;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract RemoteFactory<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> make_fact();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final RemoteFactory.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> fact() {
		assert this.structure != null;
		return this.structure.fact;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract ExecutorService make_executor();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final ExecutorService.Component executor() {
		assert this.structure != null;
		return this.structure.executor;
	}

	public static interface Bridge<Msg> {

	}

	public static interface Component<Msg> {
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> send();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> create();

		public void start();

		public RemoteClassic.ClassicAgent<Msg> createClassicAgent(
				RemoteClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> _component,
				java.lang.String name);

	}

	private static class ComponentImpl<Msg>
			implements
				RemoteClassic.Component<Msg> {

		@SuppressWarnings("unused")
		private final RemoteClassic.Bridge<Msg> bridge;

		private final RemoteClassic<Msg> implementation;

		private ComponentImpl(final RemoteClassic<Msg> implem,
				final RemoteClassic.Bridge<Msg> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.create = implem.create();

			this.scheduler = implem.make_scheduler().createComponent(
					new Bridge_scheduler());
			this.sender = implem.make_sender().createComponent(
					new Bridge_sender());
			this.receive = implem.make_receive().createComponent(
					new Bridge_receive());
			this.placed = implem.make_placed().createComponent(
					new Bridge_placed());
			this.remReceive = implem.make_remReceive().createComponent(
					new Bridge_remReceive());
			this.fact = implem.make_fact().createComponent(new Bridge_fact());
			this.executor = implem.make_executor().createComponent(
					new Bridge_executor());

		}

		private final Scheduler.Component scheduler;

		private final class Bridge_scheduler implements Scheduler.Bridge {

			public final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor infraSched() {
				return ComponentImpl.this.executor.exec();

			};

		}
		private final Sender.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> sender;

		private final class Bridge_sender
				implements
					Sender.Bridge<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> deposit() {
				return ComponentImpl.this.remReceive.deposit();

			};

		}
		private final Receiver.Component<Msg> receive;

		private final class Bridge_receive implements Receiver.Bridge<Msg> {

		}
		private final Placed.Component placed;

		private final class Bridge_placed implements Placed.Bridge {

		}
		private final RemoteReceiver.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> remReceive;

		private final class Bridge_remReceive
				implements
					RemoteReceiver.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> localDeposit() {
				return ComponentImpl.this.receive.deposit();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> myPlace() {
				return ComponentImpl.this.placed.thisPlace();

			};

		}
		private final RemoteFactory.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> fact;

		private final class Bridge_fact
				implements
					RemoteFactory.Bridge<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> {

			public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> infraCreate() {
				return ComponentImpl.this.create();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace() {
				return ComponentImpl.this.placed.thisPlace();

			};

		}
		private final ExecutorService.Component executor;

		private final class Bridge_executor implements ExecutorService.Bridge {

		}

		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> send() {
			return this.remReceive.deposit();
		};

		public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.place.Place> thisPlace() {
			return this.placed.thisPlace();
		};
		private final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> create;

		public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> create() {
			return this.create;
		};

		public final void start() {
			this.scheduler.start();
			this.sender.start();
			this.receive.start();
			this.placed.start();
			this.remReceive.start();
			this.fact.start();
			this.executor.start();

			this.implementation.start();
		}

		public RemoteClassic.ClassicAgent<Msg> createClassicAgent(
				RemoteClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> _component,
				java.lang.String name) {
			return new RemoteClassic.ClassicAgent<Msg>(_component,
					this.placed.createAgent(), this.fact.createAgent(),
					this.scheduler.createAgent(),
					this.receive.createAgent(name), this.sender.createAgent(),
					this.remReceive.createAgent());
		}

	}

	protected RemoteClassic.ClassicAgent<Msg> createClassicAgent(
			RemoteClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> _component,
			java.lang.String name) {
		return this.structure.createClassicAgent(_component, name);
	}

	public static final class ClassicAgent<Msg> {
		public ClassicAgent(
				final RemoteClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> _component,
				final Placed.Agent _placed,
				final RemoteFactory.Agent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> _fact,
				final Scheduler.Agent _scheduler,
				final Receiver.Agent<Msg> _receive,
				final Sender.Agent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> _sender,
				final RemoteReceiver.Agent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> _remReceive) {
			this.component = _component
					.createComponent(new ClassicAgent_component());

			this.placed = _placed.createComponent(new ClassicAgent_placed());

			this.fact = _fact.createComponent(new ClassicAgent_fact());

			this.scheduler = _scheduler
					.createComponent(new ClassicAgent_scheduler());

			this.receive = _receive.createComponent(new ClassicAgent_receive());

			this.sender = _sender.createComponent(new ClassicAgent_sender());

			this.remReceive = _remReceive
					.createComponent(new ClassicAgent_remReceive());

		}

		private final RemoteClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> component;

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final RemoteClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> component() {
			return this.component;
		};

		private final class ClassicAgent_component
				implements
					RemoteClassicAgentComponent.Bridge<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> send() {
				return ClassicAgent.this.sender.send();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me() {
				return ClassicAgent.this.remReceive.me();

			};

			public final fr.irit.smac.may.lib.interfaces.Do stopExec() {
				return ClassicAgent.this.scheduler.stop();

			};

			public final fr.irit.smac.may.lib.interfaces.Do stopReceive() {
				return ClassicAgent.this.remReceive.disconnect();

			};

			public final java.util.concurrent.Executor executor() {
				return ClassicAgent.this.scheduler.sched();

			};

			public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> create() {
				return ClassicAgent.this.fact.create();

			};

		}

		private final Placed.Agent.Component placed;

		private final class ClassicAgent_placed implements Placed.Agent.Bridge {

		}

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final Placed.Agent.Component placed() {
			return this.placed;
		};
		private final RemoteFactory.Agent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> fact;

		private final class ClassicAgent_fact
				implements
					RemoteFactory.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> {

		}

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final RemoteFactory.Agent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> fact() {
			return this.fact;
		};
		private final Scheduler.Agent.Component scheduler;

		private final class ClassicAgent_scheduler
				implements
					Scheduler.Agent.Bridge {

		}

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final Scheduler.Agent.Component scheduler() {
			return this.scheduler;
		};
		private final Receiver.Agent.Component<Msg> receive;

		private final class ClassicAgent_receive
				implements
					Receiver.Agent.Bridge<Msg> {

			public final fr.irit.smac.may.lib.interfaces.Push<Msg> put() {
				return ClassicAgent.this.component.put();

			};

		}

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final Receiver.Agent.Component<Msg> receive() {
			return this.receive;
		};
		private final Sender.Agent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> sender;

		private final class ClassicAgent_sender
				implements
					Sender.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> {

		}

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final Sender.Agent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> sender() {
			return this.sender;
		};
		private final RemoteReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> remReceive;

		private final class ClassicAgent_remReceive
				implements
					RemoteReceiver.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> {

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> localMe() {
				return ClassicAgent.this.receive.me();

			};

		}

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final RemoteReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> remReceive() {
			return this.remReceive;
		};

		/**
		 * This must be called to start the agent and its sub-components.
		 */
		public final void start() {

			this.placed.start();

			this.fact.start();

			this.scheduler.start();

			this.receive.start();

			this.sender.start();

			this.remReceive.start();

			this.component.start();
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

	public RemoteClassic.Component<Msg> createComponent(
			RemoteClassic.Bridge<Msg> b) {
		return new RemoteClassic.ComponentImpl<Msg>(this, b);
	}

	public RemoteClassic.Component<Msg> createComponent() {
		return this.createComponent(new RemoteClassic.Bridge<Msg>() {
		});
	}

	public static final <Msg> RemoteClassic.Component<Msg> createComponent(
			RemoteClassic<Msg> _compo) {
		return _compo.createComponent();
	}

}
