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
	 * This is not meant to be called on from the outside.
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

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Msg>
			implements
				Classic.Component<Msg> {

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

			assert this.implem_scheduler == null;
			this.implem_scheduler = implem.make_scheduler();
			this.scheduler = this.implem_scheduler
					.createComponent(new BridgeImpl_scheduler());
			assert this.implem_sender == null;
			this.implem_sender = implem.make_sender();
			this.sender = this.implem_sender
					.createComponent(new BridgeImpl_sender());
			assert this.implem_receive == null;
			this.implem_receive = implem.make_receive();
			this.receive = this.implem_receive
					.createComponent(new BridgeImpl_receive());
			assert this.implem_fact == null;
			this.implem_fact = implem.make_fact();
			this.fact = this.implem_fact.createComponent(new BridgeImpl_fact());
			assert this.implem_executor == null;
			this.implem_executor = implem.make_executor();
			this.executor = this.implem_executor
					.createComponent(new BridgeImpl_executor());
		}

		private final Scheduler.Component scheduler;

		private Scheduler implem_scheduler = null;

		private final class BridgeImpl_scheduler implements Scheduler.Bridge {

			public final fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor infraSched() {
				return ComponentImpl.this.executor.exec();

			};

		}
		private final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> sender;

		private Forward<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> implem_sender = null;

		private final class BridgeImpl_sender
				implements
					Forward.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> i() {
				return ComponentImpl.this.receive.deposit();

			};

		}
		private final Receiver.Component<Msg> receive;

		private Receiver<Msg> implem_receive = null;

		private final class BridgeImpl_receive implements Receiver.Bridge<Msg> {

		}
		private final Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> fact;

		private Forward<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> implem_fact = null;

		private final class BridgeImpl_fact
				implements
					Forward.Bridge<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> {

			public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> i() {
				return ComponentImpl.this.create();

			};

		}
		private final ExecutorService.Component executor;

		private ExecutorService implem_executor = null;

		private final class BridgeImpl_executor
				implements
					ExecutorService.Bridge {

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
	}

	/**
	 * Should not be called
	 */
	protected abstract Classic.ClassicAgent<Msg> make_ClassicAgent(
			fr.irit.smac.may.lib.classic.local.ClassicBehaviour<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> beh,
			java.lang.String name);

	public Classic.ClassicAgent<Msg> createImplementationOfClassicAgent(
			fr.irit.smac.may.lib.classic.local.ClassicBehaviour<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> beh,
			java.lang.String name) {
		Classic.ClassicAgent<Msg> implem = make_ClassicAgent(beh, name);
		assert implem.infraStructure == null;
		assert this.structure == null;
		implem.infraStructure = this.structure;
		assert this.structure.implem_scheduler != null;
		assert implem.with_s == null;
		implem.with_s = this.structure.implem_scheduler
				.createImplementationOfAgent();
		assert this.structure.implem_fact != null;
		assert implem.with_f == null;
		implem.with_f = this.structure.implem_fact
				.createImplementationOfAgent();
		assert this.structure.implem_receive != null;
		assert implem.with_r == null;
		implem.with_r = this.structure.implem_receive
				.createImplementationOfAgent(name);
		assert this.structure.implem_sender != null;
		assert implem.with_ss == null;
		implem.with_ss = this.structure.implem_sender
				.createImplementationOfAgent();

		return implem;
	}

	public Classic.ClassicAgent.Component<Msg> createClassicAgent(
			fr.irit.smac.may.lib.classic.local.ClassicBehaviour<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> beh,
			java.lang.String name) {
		Classic.ClassicAgent<Msg> implem = createImplementationOfClassicAgent(
				beh, name);
		return implem.createComponent(new Classic.ClassicAgent.Bridge<Msg>() {
		});
	}

	public static abstract class ClassicAgent<Msg> {

		private Classic.ClassicAgent.ComponentImpl<Msg> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Classic.ClassicAgent.Component<Msg> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define how to create this sub-component.
		 * This will be called once during the construction of the component to initialize this sub-component.
		 */
		protected abstract ClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> make_arch();

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> arch() {
			assert this.structure != null;
			return this.structure.arch;
		}

		private Scheduler.Agent with_s = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Scheduler.Agent.Component s() {
			assert this.structure != null;
			return this.structure.s;
		}

		private Forward.Agent<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> with_f = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> f() {
			assert this.structure != null;
			return this.structure.f;
		}

		private Receiver.Agent<Msg> with_r = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Receiver.Agent.Component<Msg> r() {
			assert this.structure != null;
			return this.structure.r;
		}

		private Forward.Agent<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> with_ss = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> ss() {
			assert this.structure != null;
			return this.structure.ss;
		}

		private Classic.ComponentImpl<Msg> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected Classic.Component<Msg> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		public static interface Bridge<Msg> {

		}

		public static interface Component<Msg> {

			/**
			 * This can be called to access the provided port
			 * start() must have been called before
			 */
			public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> ref();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<Msg>
				implements
					Classic.ClassicAgent.Component<Msg> {

			@SuppressWarnings("unused")
			private final Classic.ClassicAgent.Bridge<Msg> bridge;

			private final Classic.ClassicAgent<Msg> implementation;

			private ComponentImpl(final Classic.ClassicAgent<Msg> implem,
					final Classic.ClassicAgent.Bridge<Msg> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				assert this.implem_arch == null;
				this.implem_arch = implem.make_arch();
				this.arch = this.implem_arch
						.createComponent(new BridgeImpl_arch());
				assert this.implementation.with_s != null;
				this.s = this.implementation.with_s
						.createComponent(new BridgeImplschedulerAgent());
				assert this.implementation.with_f != null;
				this.f = this.implementation.with_f
						.createComponent(new BridgeImplfactAgent());
				assert this.implementation.with_r != null;
				this.r = this.implementation.with_r
						.createComponent(new BridgeImplreceiveAgent());
				assert this.implementation.with_ss != null;
				this.ss = this.implementation.with_ss
						.createComponent(new BridgeImplsenderAgent());
			}

			private final ClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> arch;

			private ClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> implem_arch = null;

			private final class BridgeImpl_arch
					implements
						ClassicAgentComponent.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> {

				public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> send() {
					return ComponentImpl.this.ss.a();

				};

				public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> me() {
					return ComponentImpl.this.r.me();

				};

				public final java.util.concurrent.Executor executor() {
					return ComponentImpl.this.s.sched();

				};

				public final fr.irit.smac.may.lib.interfaces.Do die() {
					return ComponentImpl.this.s.stop();

				};

				public final fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> create() {
					return ComponentImpl.this.f.a();

				};

			}
			private final Scheduler.Agent.Component s;

			private final class BridgeImplschedulerAgent
					implements
						Scheduler.Agent.Bridge {

			}
			private final Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> f;

			private final class BridgeImplfactAgent
					implements
						Forward.Agent.Bridge<fr.irit.smac.may.lib.classic.interfaces.CreateClassic<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> {

			}
			private final Receiver.Agent.Component<Msg> r;

			private final class BridgeImplreceiveAgent
					implements
						Receiver.Agent.Bridge<Msg> {

				public final fr.irit.smac.may.lib.interfaces.Push<Msg> put() {
					return ComponentImpl.this.arch.put();

				};

			}
			private final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> ss;

			private final class BridgeImplsenderAgent
					implements
						Forward.Agent.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef>> {

			}

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> ref() {
				return this.r.me();
			};

			public final void start() {
				this.arch.start();
				this.s.start();
				this.f.start();
				this.r.start();
				this.ss.start();

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

		public Classic.ClassicAgent.Component<Msg> createComponent(
				Classic.ClassicAgent.Bridge<Msg> b) {
			return new Classic.ClassicAgent.ComponentImpl<Msg>(this, b);
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
