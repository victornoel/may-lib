package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.meta.Forward;
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
	 * This is not meant to be called on from the outside.
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
	protected abstract Forward<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> make_sender();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> sender() {
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

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Msg>
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
			assert this.implem_placed == null;
			this.implem_placed = implem.make_placed();
			this.placed = this.implem_placed
					.createComponent(new BridgeImpl_placed());
			assert this.implem_remReceive == null;
			this.implem_remReceive = implem.make_remReceive();
			this.remReceive = this.implem_remReceive
					.createComponent(new BridgeImpl_remReceive());
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
		private final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> sender;

		private Forward<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> implem_sender = null;

		private final class BridgeImpl_sender
				implements
					Forward.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> i() {
				return ComponentImpl.this.remReceive.deposit();

			};

		}
		private final Receiver.Component<Msg> receive;

		private Receiver<Msg> implem_receive = null;

		private final class BridgeImpl_receive implements Receiver.Bridge<Msg> {

		}
		private final Placed.Component placed;

		private Placed implem_placed = null;

		private final class BridgeImpl_placed implements Placed.Bridge {

		}
		private final RemoteReceiver.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> remReceive;

		private RemoteReceiver<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> implem_remReceive = null;

		private final class BridgeImpl_remReceive
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

		private RemoteFactory<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> implem_fact = null;

		private final class BridgeImpl_fact
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

		private ExecutorService implem_executor = null;

		private final class BridgeImpl_executor
				implements
					ExecutorService.Bridge {

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
	}

	/**
	 * Should not be called
	 */
	protected abstract RemoteClassic.ClassicAgent<Msg> make_ClassicAgent(
			fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> beh,
			java.lang.String name);

	public RemoteClassic.ClassicAgent<Msg> createImplementationOfClassicAgent(
			fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> beh,
			java.lang.String name) {
		RemoteClassic.ClassicAgent<Msg> implem = make_ClassicAgent(beh, name);
		assert implem.infraStructure == null;
		assert this.structure == null;
		implem.infraStructure = this.structure;
		assert this.structure.implem_placed != null;
		assert implem.with_p == null;
		implem.with_p = this.structure.implem_placed
				.createImplementationOfAgent();
		assert this.structure.implem_fact != null;
		assert implem.with_f == null;
		implem.with_f = this.structure.implem_fact
				.createImplementationOfAgent();
		assert this.structure.implem_scheduler != null;
		assert implem.with_s == null;
		implem.with_s = this.structure.implem_scheduler
				.createImplementationOfAgent();
		assert this.structure.implem_receive != null;
		assert implem.with_r == null;
		implem.with_r = this.structure.implem_receive
				.createImplementationOfAgent(name);
		assert this.structure.implem_sender != null;
		assert implem.with_ss == null;
		implem.with_ss = this.structure.implem_sender
				.createImplementationOfAgent();
		assert this.structure.implem_remReceive != null;
		assert implem.with_rr == null;
		implem.with_rr = this.structure.implem_remReceive
				.createImplementationOfAgent();

		return implem;
	}

	public RemoteClassic.ClassicAgent.Component<Msg> createClassicAgent(
			fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> beh,
			java.lang.String name) {
		RemoteClassic.ClassicAgent<Msg> implem = createImplementationOfClassicAgent(
				beh, name);
		return implem
				.createComponent(new RemoteClassic.ClassicAgent.Bridge<Msg>() {
				});
	}

	public static abstract class ClassicAgent<Msg> {

		private RemoteClassic.ClassicAgent.ComponentImpl<Msg> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected RemoteClassic.ClassicAgent.Component<Msg> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define how to create this sub-component.
		 * This will be called once during the construction of the component to initialize this sub-component.
		 */
		protected abstract RemoteClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> make_arch();

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final RemoteClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> arch() {
			assert this.structure != null;
			return this.structure.arch;
		}

		private Placed.Agent with_p = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Placed.Agent.Component p() {
			assert this.structure != null;
			return this.structure.p;
		}

		private RemoteFactory.Agent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> with_f = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final RemoteFactory.Agent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> f() {
			assert this.structure != null;
			return this.structure.f;
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

		private Forward.Agent<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> with_ss = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> ss() {
			assert this.structure != null;
			return this.structure.ss;
		}

		private RemoteReceiver.Agent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> with_rr = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final RemoteReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> rr() {
			assert this.structure != null;
			return this.structure.rr;
		}

		private RemoteClassic.ComponentImpl<Msg> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected RemoteClassic.Component<Msg> infraSelf() {
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
			public fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> ref();

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<Msg>
				implements
					RemoteClassic.ClassicAgent.Component<Msg> {

			@SuppressWarnings("unused")
			private final RemoteClassic.ClassicAgent.Bridge<Msg> bridge;

			private final RemoteClassic.ClassicAgent<Msg> implementation;

			private ComponentImpl(final RemoteClassic.ClassicAgent<Msg> implem,
					final RemoteClassic.ClassicAgent.Bridge<Msg> b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.structure == null;
				implem.structure = this;

				assert this.implem_arch == null;
				this.implem_arch = implem.make_arch();
				this.arch = this.implem_arch
						.createComponent(new BridgeImpl_arch());
				assert this.implementation.with_p != null;
				this.p = this.implementation.with_p
						.createComponent(new BridgeImplplacedAgent());
				assert this.implementation.with_f != null;
				this.f = this.implementation.with_f
						.createComponent(new BridgeImplfactAgent());
				assert this.implementation.with_s != null;
				this.s = this.implementation.with_s
						.createComponent(new BridgeImplschedulerAgent());
				assert this.implementation.with_r != null;
				this.r = this.implementation.with_r
						.createComponent(new BridgeImplreceiveAgent());
				assert this.implementation.with_ss != null;
				this.ss = this.implementation.with_ss
						.createComponent(new BridgeImplsenderAgent());
				assert this.implementation.with_rr != null;
				this.rr = this.implementation.with_rr
						.createComponent(new BridgeImplremReceiveAgent());
			}

			private final RemoteClassicAgentComponent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> arch;

			private RemoteClassicAgentComponent<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> implem_arch = null;

			private final class BridgeImpl_arch
					implements
						RemoteClassicAgentComponent.Bridge<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> {

				public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> send() {
					return ComponentImpl.this.ss.a();

				};

				public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> me() {
					return ComponentImpl.this.rr.me();

				};

				public final fr.irit.smac.may.lib.interfaces.Do stopExec() {
					return ComponentImpl.this.s.stop();

				};

				public final fr.irit.smac.may.lib.interfaces.Do stopReceive() {
					return ComponentImpl.this.rr.disconnect();

				};

				public final java.util.concurrent.Executor executor() {
					return ComponentImpl.this.s.sched();

				};

				public final fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> create() {
					return ComponentImpl.this.f.create();

				};

			}
			private final Placed.Agent.Component p;

			private final class BridgeImplplacedAgent
					implements
						Placed.Agent.Bridge {

			}
			private final RemoteFactory.Agent.Component<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> f;

			private final class BridgeImplfactAgent
					implements
						RemoteFactory.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> {

			}
			private final Scheduler.Agent.Component s;

			private final class BridgeImplschedulerAgent
					implements
						Scheduler.Agent.Bridge {

			}
			private final Receiver.Agent.Component<Msg> r;

			private final class BridgeImplreceiveAgent
					implements
						Receiver.Agent.Bridge<Msg> {

				public final fr.irit.smac.may.lib.interfaces.Push<Msg> put() {
					return ComponentImpl.this.arch.put();

				};

			}
			private final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> ss;

			private final class BridgeImplsenderAgent
					implements
						Forward.Agent.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef>> {

			}
			private final RemoteReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> rr;

			private final class BridgeImplremReceiveAgent
					implements
						RemoteReceiver.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> {

				public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> localMe() {
					return ComponentImpl.this.r.me();

				};

			}

			public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef> ref() {
				return this.rr.me();
			};

			public final void start() {
				this.arch.start();
				this.p.start();
				this.f.start();
				this.s.start();
				this.r.start();
				this.ss.start();
				this.rr.start();

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

		public RemoteClassic.ClassicAgent.Component<Msg> createComponent(
				RemoteClassic.ClassicAgent.Bridge<Msg> b) {
			return new RemoteClassic.ClassicAgent.ComponentImpl<Msg>(this, b);
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
