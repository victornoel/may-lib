package fr.irit.smac.may.lib.classic.local;

import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;

public abstract class Classic<Msg> {

	private Classic.ComponentImpl<Msg> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected Classic.Component<Msg> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside by hand.
	 */
	protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> create();

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
	protected abstract Forward<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> make_sender();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> sender() {
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
	protected abstract Forward<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> make_fact();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> fact() {
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
		public fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> create();

		public void start();

		public Classic.ClassicAgent<Msg> createClassicAgent(
				ClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> _component,
				java.lang.String name);

	}

	private static class ComponentImpl<Msg> implements Classic.Component<Msg> {

		@SuppressWarnings("unused")
		private final Classic.Bridge<Msg> bridge;

		private final Classic<Msg> implementation;

		private ComponentImpl(final Classic<Msg> implem,
				final Classic.Bridge<Msg> b) {
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
		private final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> sender;

		private final class Bridge_sender
				implements
					Forward.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> i() {
				return ComponentImpl.this.receive.deposit();

			};

		}
		private final Receiver.Component<Msg> receive;

		private final class Bridge_receive implements Receiver.Bridge<Msg> {

		}
		private final Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> fact;

		private final class Bridge_fact
				implements
					Forward.Bridge<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> {

			public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> i() {
				return ComponentImpl.this.create();

			};

		}
		private final ExecutorService.Component executor;

		private final class Bridge_executor implements ExecutorService.Bridge {

		}

		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send() {
			return this.receive.deposit();
		};
		private final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> create;

		public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> create() {
			return this.create;
		};

		public final void start() {
			this.scheduler.start();
			this.sender.start();
			this.receive.start();
			this.fact.start();
			this.executor.start();

			this.implementation.start();
		}

		public Classic.ClassicAgent<Msg> createClassicAgent(
				ClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> _component,
				java.lang.String name) {
			return new Classic.ClassicAgent<Msg>(_component,
					this.scheduler.createAgent(), this.fact.createAgent(),
					this.receive.createAgent(name), this.sender.createAgent());
		}

	}

	protected Classic.ClassicAgent<Msg> createClassicAgent(
			ClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> _component,
			java.lang.String name) {
		return this.structure.createClassicAgent(_component, name);
	}

	public static final class ClassicAgent<Msg> {
		public ClassicAgent(
				final ClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> _component,
				final Scheduler.Agent _scheduler,
				final Forward.Agent<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> _fact,
				final Receiver.Agent<Msg> _receive,
				final Forward.Agent<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> _sender) {
			this.component = _component
					.createComponent(new ClassicAgent_component());

			this.scheduler = _scheduler
					.createComponent(new ClassicAgent_scheduler());

			this.fact = _fact.createComponent(new ClassicAgent_fact());

			this.receive = _receive.createComponent(new ClassicAgent_receive());

			this.sender = _sender.createComponent(new ClassicAgent_sender());

		}

		private final ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> component;

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> component() {
			return this.component;
		};

		private final class ClassicAgent_component
				implements
					ClassicAgentComponent.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send() {
				return ClassicAgent.this.sender.a();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> me() {
				return ClassicAgent.this.receive.me();

			};

			public final java.util.concurrent.Executor executor() {
				return ClassicAgent.this.scheduler.sched();

			};

			public final fr.irit.smac.may.lib.interfaces.Do die() {
				return ClassicAgent.this.scheduler.stop();

			};

			public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> create() {
				return ClassicAgent.this.fact.a();

			};

		}

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
		private final Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> fact;

		private final class ClassicAgent_fact
				implements
					Forward.Agent.Bridge<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> {

		}

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> fact() {
			return this.fact;
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
		private final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> sender;

		private final class ClassicAgent_sender
				implements
					Forward.Agent.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> {

		}

		/**
		 * This can be called to access the provided port.
		 * start() must have been called before.
		 */
		public final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> sender() {
			return this.sender;
		};

		/**
		 * This must be called to start the agent and its sub-components.
		 */
		public final void start() {

			this.scheduler.start();

			this.fact.start();

			this.receive.start();

			this.sender.start();

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

	public Classic.Component<Msg> createComponent(Classic.Bridge<Msg> b) {
		return new Classic.ComponentImpl<Msg>(this, b);
	}

	public Classic.Component<Msg> createComponent() {
		return this.createComponent(new Classic.Bridge<Msg>() {
		});
	}

	public static final <Msg> Classic.Component<Msg> createComponent(
			Classic<Msg> _compo) {
		return _compo.createComponent();
	}

}
