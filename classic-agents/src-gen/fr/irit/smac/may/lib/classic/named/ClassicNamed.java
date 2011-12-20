package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.classic.named.ClassicNamedAgentComponent;
import fr.irit.smac.may.lib.components.messaging.MapReceiver;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;

public abstract class ClassicNamed<Msg> {

	private Component<Msg> structure = null;

	/**
	 * This should be overridden by the implementation to define the provided port
	 * This will be called once during the construction of the component to initialize the port
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> create();

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
	protected abstract Forward<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> make_sender();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> sender() {
		assert this.structure != null;
		return this.structure.sender;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract Forward<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> make_fact();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> fact() {
		assert this.structure != null;
		return this.structure.fact;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract Receiver<Msg> make_realReceive();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Receiver.Component<Msg> realReceive() {
		assert this.structure != null;
		return this.structure.realReceive;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component
	 * This will be called once during the construction of the component to initialize this sub-component
	 */
	protected abstract MapReceiver<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> make_receive();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final MapReceiver.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> receive() {
		assert this.structure != null;
		return this.structure.receive;
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

		private final ClassicNamed<Msg> implementation;

		public Component(final ClassicNamed<Msg> implem, final Bridge<Msg> b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.structure == null;
			implem.structure = this;

			this.create = implem.create();

			this.scheduler = new Scheduler.Component(implem.make_scheduler(),
					new Bridge_scheduler());
			this.sender = new Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>>(
					implem.make_sender(), new Bridge_sender());
			this.fact = new Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>>(
					implem.make_fact(), new Bridge_fact());
			this.realReceive = new Receiver.Component<Msg>(
					implem.make_realReceive(), new Bridge_realReceive());
			this.receive = new MapReceiver.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String>(
					implem.make_receive(), new Bridge_receive());
			this.executor = new ExecutorService.Component(
					implem.make_executor(), new Bridge_executor());

		}

		private final Scheduler.Component scheduler;

		private final class Bridge_scheduler implements Scheduler.Bridge {

			public final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor infraSched() {
				return Component.this.executor.exec();

			};

		}
		private final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> sender;

		private final class Bridge_sender
				implements
					Forward.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> i() {
				return Component.this.receive.depositKey();

			};

		}
		private final Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> fact;

		private final class Bridge_fact
				implements
					Forward.Bridge<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> {

			public final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> i() {
				return Component.this.create();

			};

		}
		private final Receiver.Component<Msg> realReceive;

		private final class Bridge_realReceive implements Receiver.Bridge<Msg> {

		}
		private final MapReceiver.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> receive;

		private final class Bridge_receive
				implements
					MapReceiver.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> depositValue() {
				return Component.this.realReceive.deposit();

			};

		}
		private final ExecutorService.Component executor;

		private final class Bridge_executor implements ExecutorService.Bridge {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> send() {
			return this.receive.depositKey();
		};
		private final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> create;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> create() {
			return this.create;
		};

		public final void start() {
			this.scheduler.start();
			this.sender.start();
			this.fact.start();
			this.realReceive.start();
			this.receive.start();
			this.executor.start();

			this.implementation.start();
		}
	}

	public static final class ClassicNamedAgent<Msg> {
		public ClassicNamedAgent(
				final ClassicNamedAgentComponent<Msg, java.lang.String> _component,
				final Scheduler.Agent _scheduler,
				final Forward.Agent<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> _fact,
				final MapReceiver.Agent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> _receive,
				final Receiver.Agent<Msg> _realReceive,
				final Forward.Agent<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> _sender) {
			this.component = new ClassicNamedAgentComponent.Component<Msg, java.lang.String>(
					_component, new ClassicNamedAgent_component());

			this.scheduler = new Scheduler.Agent.Component(_scheduler,
					new ClassicNamedAgent_scheduler());

			this.fact = new Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>>(
					_fact, new ClassicNamedAgent_fact());

			this.receive = new MapReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String>(
					_receive, new ClassicNamedAgent_receive());

			this.realReceive = new Receiver.Agent.Component<Msg>(_realReceive,
					new ClassicNamedAgent_realReceive());

			this.sender = new Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>>(
					_sender, new ClassicNamedAgent_sender());

		}

		private final ClassicNamedAgentComponent.Component<Msg, java.lang.String> component;

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final ClassicNamedAgentComponent.Component<Msg, java.lang.String> component() {
			return this.component;
		};

		private final class ClassicNamedAgent_component
				implements
					ClassicNamedAgentComponent.Bridge<Msg, java.lang.String> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> send() {
				return ClassicNamedAgent.this.sender.a();

			};

			public final java.util.concurrent.Executor executor() {
				return ClassicNamedAgent.this.scheduler.sched();

			};

			public final fr.irit.smac.may.lib.interfaces.Do die() {
				return ClassicNamedAgent.this.scheduler.stop();

			};

			public final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> create() {
				return ClassicNamedAgent.this.fact.a();

			};

		}

		private final Scheduler.Agent.Component scheduler;

		private final class ClassicNamedAgent_scheduler
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
		private final Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> fact;

		private final class ClassicNamedAgent_fact
				implements
					Forward.Agent.Bridge<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> fact() {
			return this.fact;
		};
		private final MapReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> receive;

		private final class ClassicNamedAgent_receive
				implements
					MapReceiver.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> {

			public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> key() {
				return ClassicNamedAgent.this.component.me();

			};

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> value() {
				return ClassicNamedAgent.this.realReceive.me();

			};

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final MapReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> receive() {
			return this.receive;
		};
		private final Receiver.Agent.Component<Msg> realReceive;

		private final class ClassicNamedAgent_realReceive
				implements
					Receiver.Agent.Bridge<Msg> {

			public final fr.irit.smac.may.lib.interfaces.Push<Msg> put() {
				return ClassicNamedAgent.this.component.put();

			};

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final Receiver.Agent.Component<Msg> realReceive() {
			return this.realReceive;
		};
		private final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> sender;

		private final class ClassicNamedAgent_sender
				implements
					Forward.Agent.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> {

		}

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> sender() {
			return this.sender;
		};

		/**
		 * This must be called to start the agent and its sub-components
		 */
		public final void start() {

			this.scheduler.start();

			this.fact.start();

			this.receive.start();

			this.realReceive.start();

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

	public static final <Msg> Component<Msg> createComponent(
			ClassicNamed<Msg> _compo) {
		return new Component<Msg>(_compo, new Bridge<Msg>() {
		});
	}

}
