package fr.irit.smac.may.lib.classic.local;

import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.components.messaging.Sender;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;

public abstract class Classic<Msg> {

	private Component<Msg> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> create();

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract Scheduler make_scheduler();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Scheduler.Component scheduler() {
		assert this.structure != null;
		return this.structure.scheduler;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract Sender<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> make_sender();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Sender.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> sender() {
		assert this.structure != null;
		return this.structure.sender;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract Receiver<Msg> make_receive();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Receiver.Component<Msg> receive() {
		assert this.structure != null;
		return this.structure.receive;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract Forward<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> make_fact();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> fact() {
		assert this.structure != null;
		return this.structure.fact;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract ExecutorService make_executor();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final ExecutorService.Component executor() {
		assert this.structure != null;
		return this.structure.executor;
	}

	public static interface Bridge<Msg> {

	}

	public static final class Component<Msg> {

		@SuppressWarnings("unused")
		private final Bridge<Msg> bridge;

		private final Classic<Msg> implementation;

		public Component(final Classic<Msg> implem, final Bridge<Msg> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.create = implem.create();

			this.scheduler = new Scheduler.Component(implem.make_scheduler(),
					new Bridge_scheduler());
			this.sender = new Sender.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>(
					implem.make_sender(), new Bridge_sender());
			this.receive = new Receiver.Component<Msg>(implem.make_receive(),
					new Bridge_receive());
			this.fact = new Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>>(
					implem.make_fact(), new Bridge_fact());
			this.executor = new ExecutorService.Component(
					implem.make_executor(), new Bridge_executor());

		}

		private final Scheduler.Component scheduler;

		private final class Bridge_scheduler implements Scheduler.Bridge {

			public final java.util.concurrent.Executor infraSched() {
				return Component.this.executor.exec();

			};

		}
		private final Sender.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> sender;

		private final class Bridge_sender
				implements
					Sender.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> deposit() {
				return Component.this.receive.deposit();

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
				return Component.this.create();

			};

		}
		private final ExecutorService.Component executor;

		private final class Bridge_executor implements ExecutorService.Bridge {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send() {
			return this.receive.deposit();
		};
		private final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> create;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
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
	}

	public static final class ClassicAgent<Msg> {
		public ClassicAgent(
				final ClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> _component,
				final Scheduler.Agent _scheduler,
				final Forward.Agent<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> _fact,
				final Receiver.Agent<Msg> _receive,
				final Sender.Agent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> _sender) {
			this.component = new ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>(
					_component, new ClassicAgent_component());

			this.scheduler = new Scheduler.Agent.Component(_scheduler,
					new ClassicAgent_scheduler());

			this.fact = new Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>>(
					_fact, new ClassicAgent_fact());

			this.receive = new Receiver.Agent.Component<Msg>(_receive,
					new ClassicAgent_receive());

			this.sender = new Sender.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>(
					_sender, new ClassicAgent_sender());

		}

		private final ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> component;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> component() {
			return this.component;
		};

		private final class ClassicAgent_component
				implements
					ClassicAgentComponent.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send() {
				return ClassicAgent.this.sender.send();

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
		 * This can be called to access the provided port
		 * start() must have been called before
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
		 * This can be called to access the provided port
		 * start() must have been called before
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
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final Receiver.Agent.Component<Msg> receive() {
			return this.receive;
		};
		private final Sender.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> sender;

		private final class ClassicAgent_sender
				implements
					Sender.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final Sender.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> sender() {
			return this.sender;
		};

		/**
		 * This must be called to start the agent and its sub-components
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

	public static final <Msg> Component<Msg> createComponent(Classic<Msg> _compo) {
		return new Component<Msg>(_compo, new Bridge<Msg>() {
		});
	}

}
