package fr.irit.smac.may.lib.classic.local;

import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.classic.local.Factory;
import fr.irit.smac.may.lib.components.ExecutorService;
import fr.irit.smac.may.lib.components.ReferenceReceiver;
import fr.irit.smac.may.lib.components.ReferenceSender;
import fr.irit.smac.may.lib.components.Scheduler;

public abstract class Classic<Msg> {

	private final void init() {
		this.scheduler = make_scheduler();
		this.sender = make_sender();
		this.receive = make_receive();
		this.fact = make_fact();
		this.executor = make_executor();

	}

	private Component<Msg> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialise the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> create();

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialise this sub-component
	 */
	protected abstract Scheduler make_scheduler();

	private Scheduler scheduler;

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialised after the required ports are initialised and before the provided ports are initialised
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Scheduler.Component scheduler() {
		assert this.structure != null;
		return this.structure.scheduler;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialise this sub-component
	 */
	protected abstract ReferenceSender<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> make_sender();

	private ReferenceSender<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> sender;

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialised after the required ports are initialised and before the provided ports are initialised
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final ReferenceSender.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> sender() {
		assert this.structure != null;
		return this.structure.sender;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialise this sub-component
	 */
	protected abstract ReferenceReceiver<Msg> make_receive();

	private ReferenceReceiver<Msg> receive;

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialised after the required ports are initialised and before the provided ports are initialised
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final ReferenceReceiver.Component<Msg> receive() {
		assert this.structure != null;
		return this.structure.receive;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialise this sub-component
	 */
	protected abstract Factory<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> make_fact();

	private Factory<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> fact;

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialised after the required ports are initialised and before the provided ports are initialised
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Factory.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> fact() {
		assert this.structure != null;
		return this.structure.fact;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialise this sub-component
	 */
	protected abstract ExecutorService make_executor();

	private ExecutorService executor;

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialised after the required ports are initialised and before the provided ports are initialised
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

		private final Bridge<Msg> bridge;

		private final Classic<Msg> implementation;

		/**
		 * This constructor can be called directly to instantiate stand-alone components
		 */
		public Component(final Classic<Msg> implem, final Bridge<Msg> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;
			implem.init();

			this.create = implem.create();

			this.scheduler = new Scheduler.Component(implem.scheduler,
					new Classic_scheduler());
			this.sender = new ReferenceSender.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>>(
					implem.sender, new Classic_sender());
			this.receive = new ReferenceReceiver.Component<Msg>(implem.receive,
					new Classic_receive());
			this.fact = new Factory.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>>(
					implem.fact, new Classic_fact());
			this.executor = new ExecutorService.Component(implem.executor,
					new Classic_executor());

		}

		private final Scheduler.Component scheduler;

		private final class Classic_scheduler implements Scheduler.Bridge {

			public final java.util.concurrent.Executor infraSched() {
				return Component.this.executor.exec();

			};

		}
		private final ReferenceSender.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> sender;

		private final class Classic_sender
				implements
					ReferenceSender.Bridge<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> deposit() {
				return Component.this.receive.deposit();

			};

		}
		private final ReferenceReceiver.Component<Msg> receive;

		private final class Classic_receive
				implements
					ReferenceReceiver.Bridge<Msg> {

		}
		private final Factory.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> fact;

		private final class Classic_fact
				implements
					Factory.Bridge<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> {

			public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> infraCreate() {
				return Component.this.create();

			};

		}
		private final ExecutorService.Component executor;

		private final class Classic_executor implements ExecutorService.Bridge {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> send() {
			return this.receive.deposit();
		};
		private final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> create;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> create() {
			return this.create;
		};

		/**
		 * This must be called to start the component and its sub-components.
		 */
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
				final ClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> _component,
				final Scheduler.Agent _scheduler,
				final Factory.Agent<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> _fact,
				final ReferenceReceiver.Agent<Msg> _receive,
				final ReferenceSender.Agent<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> _sender) {
			this.component = new ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>>(
					_component, new ClassicAgent_component());

			this.scheduler = new Scheduler.Agent.Component(_scheduler,
					new ClassicAgent_scheduler());

			this.fact = new Factory.Agent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>>(
					_fact, new ClassicAgent_fact());

			this.receive = new ReferenceReceiver.Agent.Component<Msg>(_receive,
					new ClassicAgent_receive());

			this.sender = new ReferenceSender.Agent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>>(
					_sender, new ClassicAgent_sender());

		}

		private final ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> component;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> component() {
			return this.component;
		};

		private final class ClassicAgent_component
				implements
					ClassicAgentComponent.Bridge<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> send() {
				return ClassicAgent.this.sender.send();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> me() {
				return ClassicAgent.this.receive.me();

			};

			public final java.util.concurrent.Executor executor() {
				return ClassicAgent.this.scheduler.sched();

			};

			public final fr.irit.smac.may.lib.interfaces.Do die() {
				return ClassicAgent.this.scheduler.stop();

			};

			public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> create() {
				return ClassicAgent.this.fact.create();

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
		private final Factory.Agent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> fact;

		private final class ClassicAgent_fact
				implements
					Factory.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final Factory.Agent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> fact() {
			return this.fact;
		};
		private final ReferenceReceiver.Agent.Component<Msg> receive;

		private final class ClassicAgent_receive
				implements
					ReferenceReceiver.Agent.Bridge<Msg> {

			public final fr.irit.smac.may.lib.interfaces.Push<Msg> put() {
				return ClassicAgent.this.component.put();

			};

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final ReferenceReceiver.Agent.Component<Msg> receive() {
			return this.receive;
		};
		private final ReferenceSender.Agent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> sender;

		private final class ClassicAgent_sender
				implements
					ReferenceSender.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final ReferenceSender.Agent.Component<Msg, fr.irit.smac.may.lib.components.refreceive.impl.AgentRef<Msg>> sender() {
			return this.sender;
		};

		/**
		 * This must be called to start the agent and its sub-components
		 */
		public final void start() {
			this.component.start();

			this.scheduler.start();

			this.fact.start();

			this.receive.start();

			this.sender.start();

		}
	}

	/**
	 * Can be overriden by the implementation
	 * It will be called after the component has been instantiated, after the components have been instantiated
	 * and during the containing component start() method is called.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected void start() {
	}
}
