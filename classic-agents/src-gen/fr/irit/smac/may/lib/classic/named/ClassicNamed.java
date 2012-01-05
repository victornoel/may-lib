package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.classic.named.ClassicNamed;
import fr.irit.smac.may.lib.classic.named.ClassicNamedAgentComponent;
import fr.irit.smac.may.lib.components.messaging.MapReceiver;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;

public abstract class ClassicNamed<Msg> {

	private ClassicNamed.ComponentImpl<Msg> structure = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected ClassicNamed.Component<Msg> self() {
		assert this.structure != null;
		return this.structure;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> create();

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
	protected abstract Forward<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> make_sender();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> sender() {
		assert this.structure != null;
		return this.structure.sender;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Forward<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> make_fact();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> fact() {
		assert this.structure != null;
		return this.structure.fact;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Receiver<Msg> make_realReceive();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Receiver.Component<Msg> realReceive() {
		assert this.structure != null;
		return this.structure.realReceive;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract MapReceiver<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> make_receive();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final MapReceiver.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> receive() {
		assert this.structure != null;
		return this.structure.receive;
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
		public fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> send();
		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> create();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl<Msg>
			implements
				ClassicNamed.Component<Msg> {

		@SuppressWarnings("unused")
		private final ClassicNamed.Bridge<Msg> bridge;

		private final ClassicNamed<Msg> implementation;

		private ComponentImpl(final ClassicNamed<Msg> implem,
				final ClassicNamed.Bridge<Msg> b) {
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
			assert this.implem_fact == null;
			this.implem_fact = implem.make_fact();
			this.fact = this.implem_fact.createComponent(new BridgeImpl_fact());
			assert this.implem_realReceive == null;
			this.implem_realReceive = implem.make_realReceive();
			this.realReceive = this.implem_realReceive
					.createComponent(new BridgeImpl_realReceive());
			assert this.implem_receive == null;
			this.implem_receive = implem.make_receive();
			this.receive = this.implem_receive
					.createComponent(new BridgeImpl_receive());
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
		private final Forward.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> sender;

		private Forward<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> implem_sender = null;

		private final class BridgeImpl_sender
				implements
					Forward.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> i() {
				return ComponentImpl.this.receive.depositKey();

			};

		}
		private final Forward.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> fact;

		private Forward<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> implem_fact = null;

		private final class BridgeImpl_fact
				implements
					Forward.Bridge<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> {

			public final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> i() {
				return ComponentImpl.this.create();

			};

		}
		private final Receiver.Component<Msg> realReceive;

		private Receiver<Msg> implem_realReceive = null;

		private final class BridgeImpl_realReceive
				implements
					Receiver.Bridge<Msg> {

		}
		private final MapReceiver.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> receive;

		private MapReceiver<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> implem_receive = null;

		private final class BridgeImpl_receive
				implements
					MapReceiver.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> {

			public final fr.irit.smac.may.lib.interfaces.Send<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> depositValue() {
				return ComponentImpl.this.realReceive.deposit();

			};

		}
		private final ExecutorService.Component executor;

		private ExecutorService implem_executor = null;

		private final class BridgeImpl_executor
				implements
					ExecutorService.Bridge {

		}

		public final fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> send() {
			return this.receive.depositKey();
		};
		private final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> create;

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

	/**
	 * Should not be called
	 */
	protected abstract ClassicNamed.ClassicNamedAgent<Msg> make_ClassicNamedAgent(
			fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour<Msg, java.lang.String> beh,
			java.lang.String name);

	public ClassicNamed.ClassicNamedAgent<Msg> createImplementationOfClassicNamedAgent(
			fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour<Msg, java.lang.String> beh,
			java.lang.String name) {
		ClassicNamed.ClassicNamedAgent<Msg> implem = make_ClassicNamedAgent(
				beh, name);
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
				.createImplementationOfAgent();
		assert this.structure.implem_realReceive != null;
		assert implem.with_rr == null;
		implem.with_rr = this.structure.implem_realReceive
				.createImplementationOfAgent(name);
		assert this.structure.implem_sender != null;
		assert implem.with_ss == null;
		implem.with_ss = this.structure.implem_sender
				.createImplementationOfAgent();

		return implem;
	}

	public ClassicNamed.ClassicNamedAgent.Component<Msg> createClassicNamedAgent(
			fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour<Msg, java.lang.String> beh,
			java.lang.String name) {
		ClassicNamed.ClassicNamedAgent<Msg> implem = createImplementationOfClassicNamedAgent(
				beh, name);
		return implem
				.createComponent(new ClassicNamed.ClassicNamedAgent.Bridge<Msg>() {
				});
	}

	public static abstract class ClassicNamedAgent<Msg> {

		private ClassicNamed.ClassicNamedAgent.ComponentImpl<Msg> structure = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected ClassicNamed.ClassicNamedAgent.Component<Msg> self() {
			assert this.structure != null;
			return this.structure;
		};

		/**
		 * This should be overridden by the implementation to define how to create this sub-component.
		 * This will be called once during the construction of the component to initialize this sub-component.
		 */
		protected abstract ClassicNamedAgentComponent<Msg, java.lang.String> make_arch();

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ClassicNamedAgentComponent.Component<Msg, java.lang.String> arch() {
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

		private Forward.Agent<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> with_f = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> f() {
			assert this.structure != null;
			return this.structure.f;
		}

		private MapReceiver.Agent<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> with_r = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final MapReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> r() {
			assert this.structure != null;
			return this.structure.r;
		}

		private Receiver.Agent<Msg> with_rr = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Receiver.Agent.Component<Msg> rr() {
			assert this.structure != null;
			return this.structure.rr;
		}

		private Forward.Agent<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> with_ss = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> ss() {
			assert this.structure != null;
			return this.structure.ss;
		}

		private ClassicNamed.ComponentImpl<Msg> infraStructure = null;

		/**
		 * This can be called by the implementation to access the component of the infrastructure itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected ClassicNamed.Component<Msg> infraSelf() {
			assert this.infraStructure != null;
			return this.infraStructure;
		};

		public static interface Bridge<Msg> {

		}

		public static interface Component<Msg> {

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl<Msg>
				implements
					ClassicNamed.ClassicNamedAgent.Component<Msg> {

			@SuppressWarnings("unused")
			private final ClassicNamed.ClassicNamedAgent.Bridge<Msg> bridge;

			private final ClassicNamed.ClassicNamedAgent<Msg> implementation;

			private ComponentImpl(
					final ClassicNamed.ClassicNamedAgent<Msg> implem,
					final ClassicNamed.ClassicNamedAgent.Bridge<Msg> b) {
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
				assert this.implementation.with_rr != null;
				this.rr = this.implementation.with_rr
						.createComponent(new BridgeImplrealReceiveAgent());
				assert this.implementation.with_ss != null;
				this.ss = this.implementation.with_ss
						.createComponent(new BridgeImplsenderAgent());
			}

			private final ClassicNamedAgentComponent.Component<Msg, java.lang.String> arch;

			private ClassicNamedAgentComponent<Msg, java.lang.String> implem_arch = null;

			private final class BridgeImpl_arch
					implements
						ClassicNamedAgentComponent.Bridge<Msg, java.lang.String> {

				public final fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String> send() {
					return ComponentImpl.this.ss.a();

				};

				public final java.util.concurrent.Executor executor() {
					return ComponentImpl.this.s.sched();

				};

				public final fr.irit.smac.may.lib.interfaces.Do die() {
					return ComponentImpl.this.s.stop();

				};

				public final fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String> create() {
					return ComponentImpl.this.f.a();

				};

			}
			private final Scheduler.Agent.Component s;

			private final class BridgeImplschedulerAgent
					implements
						Scheduler.Agent.Bridge {

			}
			private final Forward.Agent.Component<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> f;

			private final class BridgeImplfactAgent
					implements
						Forward.Agent.Bridge<fr.irit.smac.may.lib.classic.interfaces.CreateNamed<Msg, java.lang.String>> {

			}
			private final MapReceiver.Agent.Component<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> r;

			private final class BridgeImplreceiveAgent
					implements
						MapReceiver.Agent.Bridge<Msg, fr.irit.smac.may.lib.components.messaging.receiver.AgentRef, java.lang.String> {

				public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.String> key() {
					return ComponentImpl.this.arch.me();

				};

				public final fr.irit.smac.may.lib.interfaces.Pull<fr.irit.smac.may.lib.components.messaging.receiver.AgentRef> value() {
					return ComponentImpl.this.rr.me();

				};

			}
			private final Receiver.Agent.Component<Msg> rr;

			private final class BridgeImplrealReceiveAgent
					implements
						Receiver.Agent.Bridge<Msg> {

				public final fr.irit.smac.may.lib.interfaces.Push<Msg> put() {
					return ComponentImpl.this.arch.put();

				};

			}
			private final Forward.Agent.Component<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> ss;

			private final class BridgeImplsenderAgent
					implements
						Forward.Agent.Bridge<fr.irit.smac.may.lib.interfaces.Send<Msg, java.lang.String>> {

			}

			public final void start() {
				this.arch.start();
				this.s.start();
				this.f.start();
				this.r.start();
				this.rr.start();
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

		public ClassicNamed.ClassicNamedAgent.Component<Msg> createComponent(
				ClassicNamed.ClassicNamedAgent.Bridge<Msg> b) {
			return new ClassicNamed.ClassicNamedAgent.ComponentImpl<Msg>(this,
					b);
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

	public ClassicNamed.Component<Msg> createComponent(
			ClassicNamed.Bridge<Msg> b) {
		return new ClassicNamed.ComponentImpl<Msg>(this, b);
	}

	public ClassicNamed.Component<Msg> createComponent() {
		return this.createComponent(new ClassicNamed.Bridge<Msg>() {
		});
	}
	public static final <Msg> ClassicNamed.Component<Msg> createComponent(
			ClassicNamed<Msg> _compo) {
		return _compo.createComponent();
	}

}
