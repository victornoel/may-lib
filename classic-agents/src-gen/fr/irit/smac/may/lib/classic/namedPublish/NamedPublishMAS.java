package fr.irit.smac.may.lib.classic.namedPublish;

import fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS;
import fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour;
import fr.irit.smac.may.lib.components.interactions.MapReferences;
import fr.irit.smac.may.lib.components.interactions.ValuePublisher;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduled;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI;

public abstract class NamedPublishMAS {

	private NamedPublishMAS.ComponentImpl selfComponent = null;

	/**
	 * This can be called by the implementation to access the component itself and its provided ports.
	 *
	 * This is not meant to be called from the outside by hand.
	 */
	protected NamedPublishMAS.Component self() {
		assert this.selfComponent != null;
		return this.selfComponent;
	};

	/**
	 * This should be overridden by the implementation to define the provided port.
	 * This will be called once during the construction of the component to initialize the port.
	 *
	 * This is not meant to be called on from the outside.
	 */
	protected abstract fr.irit.smac.may.lib.classic.namedpub.NamedPublishMASFactory make_create();

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract MapReferences<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> make_refs();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final MapReferences.Component<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> refs() {
		assert this.selfComponent != null;
		return this.selfComponent.refs;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract ValuePublisher<java.lang.Integer, java.lang.String> make_observeds();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final ValuePublisher.Component<java.lang.Integer, java.lang.String> observeds() {
		assert this.selfComponent != null;
		return this.selfComponent.observeds;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Forward<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> make_observers();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Forward.Component<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> observers() {
		assert this.selfComponent != null;
		return this.selfComponent.observers;
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
		assert this.selfComponent != null;
		return this.selfComponent.executor;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Scheduled make_schedule();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Scheduled.Component schedule() {
		assert this.selfComponent != null;
		return this.selfComponent.schedule;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract Clock make_clock();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final Clock.Component clock() {
		assert this.selfComponent != null;
		return this.selfComponent.clock;
	}

	/**
	 * This should be overridden by the implementation to define how to create this sub-component.
	 * This will be called once during the construction of the component to initialize this sub-component.
	 */
	protected abstract SchedulingControllerGUI make_gui();

	/**
	 * This can be called by the implementation to access the sub-component instance and its provided ports.
	 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	protected final SchedulingControllerGUI.Component gui() {
		assert this.selfComponent != null;
		return this.selfComponent.gui;
	}

	public static interface Bridge {

	}

	public static interface Component {

		/**
		 * This can be called to access the provided port
		 * start() must have been called before
		 */
		public fr.irit.smac.may.lib.classic.namedpub.NamedPublishMASFactory create();

		/**
		 * This should be called to start the component
		 */
		public void start();
	}

	private final static class ComponentImpl
			implements
				NamedPublishMAS.Component {

		@SuppressWarnings("unused")
		private final NamedPublishMAS.Bridge bridge;

		private final NamedPublishMAS implementation;

		private ComponentImpl(final NamedPublishMAS implem,
				final NamedPublishMAS.Bridge b) {
			this.bridge = b;

			this.implementation = implem;

			assert implem.selfComponent == null;
			implem.selfComponent = this;

			this.create = implem.make_create();

			assert this.implem_refs == null;
			this.implem_refs = implem.make_refs();
			this.refs = this.implem_refs.newComponent(new BridgeImpl_refs());
			assert this.implem_observeds == null;
			this.implem_observeds = implem.make_observeds();
			this.observeds = this.implem_observeds
					.newComponent(new BridgeImpl_observeds());
			assert this.implem_observers == null;
			this.implem_observers = implem.make_observers();
			this.observers = this.implem_observers
					.newComponent(new BridgeImpl_observers());
			assert this.implem_executor == null;
			this.implem_executor = implem.make_executor();
			this.executor = this.implem_executor
					.newComponent(new BridgeImpl_executor());
			assert this.implem_schedule == null;
			this.implem_schedule = implem.make_schedule();
			this.schedule = this.implem_schedule
					.newComponent(new BridgeImpl_schedule());
			assert this.implem_clock == null;
			this.implem_clock = implem.make_clock();
			this.clock = this.implem_clock.newComponent(new BridgeImpl_clock());
			assert this.implem_gui == null;
			this.implem_gui = implem.make_gui();
			this.gui = this.implem_gui.newComponent(new BridgeImpl_gui());
		}

		private final MapReferences.Component<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> refs;

		private MapReferences<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> implem_refs = null;

		private final class BridgeImpl_refs
				implements
					MapReferences.Bridge<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> {

		}
		private final ValuePublisher.Component<java.lang.Integer, java.lang.String> observeds;

		private ValuePublisher<java.lang.Integer, java.lang.String> implem_observeds = null;

		private final class BridgeImpl_observeds
				implements
					ValuePublisher.Bridge<java.lang.Integer, java.lang.String> {

			public final fr.irit.smac.may.lib.components.interactions.interfaces.Call<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> call() {
				return ComponentImpl.this.refs.call();

			};

		}
		private final Forward.Component<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> observers;

		private Forward<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> implem_observers = null;

		private final class BridgeImpl_observers
				implements
					Forward.Bridge<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> {

			public final fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String> i() {
				return ComponentImpl.this.observeds.observe();

			};

		}
		private final ExecutorService.Component executor;

		private ExecutorService implem_executor = null;

		private final class BridgeImpl_executor
				implements
					ExecutorService.Bridge {

		}
		private final Scheduled.Component schedule;

		private Scheduled implem_schedule = null;

		private final class BridgeImpl_schedule implements Scheduled.Bridge {

			public final java.util.concurrent.Executor sched() {
				return ComponentImpl.this.executor.exec();

			};

		}
		private final Clock.Component clock;

		private Clock implem_clock = null;

		private final class BridgeImpl_clock implements Clock.Bridge {

			public final java.util.concurrent.Executor sched() {
				return ComponentImpl.this.executor.exec();

			};

			public final fr.irit.smac.may.lib.interfaces.Do tick() {
				return ComponentImpl.this.schedule.tick();

			};

		}
		private final SchedulingControllerGUI.Component gui;

		private SchedulingControllerGUI implem_gui = null;

		private final class BridgeImpl_gui
				implements
					SchedulingControllerGUI.Bridge {

			public final fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl control() {
				return ComponentImpl.this.clock.control();

			};

		}

		private final fr.irit.smac.may.lib.classic.namedpub.NamedPublishMASFactory create;

		public final fr.irit.smac.may.lib.classic.namedpub.NamedPublishMASFactory create() {
			return this.create;
		};

		public final void start() {
			this.refs.start();
			this.observeds.start();
			this.observers.start();
			this.executor.start();
			this.schedule.start();
			this.clock.start();
			this.gui.start();

			this.implementation.start();
		}
	}

	/**
	 * Should not be called
	 */
	protected abstract NamedPublishMAS.Observed make_Observed(
			java.lang.String name,
			fr.irit.smac.may.lib.classic.namedpub.AbstractObservedBehaviour beha);

	/**
	 * Should not be called
	 */
	public NamedPublishMAS.Observed createImplementationOfObserved(
			java.lang.String name,
			fr.irit.smac.may.lib.classic.namedpub.AbstractObservedBehaviour beha) {
		NamedPublishMAS.Observed implem = make_Observed(name, beha);
		assert implem.ecosystemComponent == null;
		assert this.selfComponent != null;
		implem.ecosystemComponent = this.selfComponent;
		assert this.selfComponent.implem_schedule != null;
		assert implem.use_sched == null;
		implem.use_sched = this.selfComponent.implem_schedule
				.createImplementationOfAgent();
		assert this.selfComponent.implem_refs != null;
		assert implem.use_ref == null;
		implem.use_ref = this.selfComponent.implem_refs
				.createImplementationOfCallee(name);
		assert this.selfComponent.implem_observeds != null;
		assert implem.use_observed == null;
		implem.use_observed = this.selfComponent.implem_observeds
				.createImplementationOfPublisherPush();

		return implem;
	}

	/**
	 * This can be called to create an instance of the species from inside the implementation of the ecosystem.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	public NamedPublishMAS.Observed.Component newObserved(
			java.lang.String name,
			fr.irit.smac.may.lib.classic.namedpub.AbstractObservedBehaviour beha) {
		NamedPublishMAS.Observed implem = createImplementationOfObserved(name,
				beha);
		return implem.newComponent(new NamedPublishMAS.Observed.Bridge() {
		});
	}

	public static abstract class Observed {

		private NamedPublishMAS.Observed.ComponentImpl selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected NamedPublishMAS.Observed.Component self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		private Scheduled.Agent use_sched = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Scheduled.Agent.Component sched() {
			assert this.selfComponent != null;
			return this.selfComponent.sched;
		}

		/**
		 * This should be overridden by the implementation to define how to create this sub-component.
		 * This will be called once during the construction of the component to initialize this sub-component.
		 */
		protected abstract ObservedBehaviour make_beh();

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ObservedBehaviour.Component beh() {
			assert this.selfComponent != null;
			return this.selfComponent.beh;
		}

		private MapReferences.Callee<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> use_ref = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final MapReferences.Callee.Component<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> ref() {
			assert this.selfComponent != null;
			return this.selfComponent.ref;
		}

		private ValuePublisher.PublisherPush<java.lang.Integer, java.lang.String> use_observed = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ValuePublisher.PublisherPush.Component<java.lang.Integer, java.lang.String> observed() {
			assert this.selfComponent != null;
			return this.selfComponent.observed;
		}

		private NamedPublishMAS.ComponentImpl ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected NamedPublishMAS.Component eco_self() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final MapReferences.Component<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> eco_refs() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.refs;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ValuePublisher.Component<java.lang.Integer, java.lang.String> eco_observeds() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.observeds;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Forward.Component<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> eco_observers() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.observers;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ExecutorService.Component eco_executor() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.executor;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Scheduled.Component eco_schedule() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.schedule;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Clock.Component eco_clock() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.clock;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final SchedulingControllerGUI.Component eco_gui() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.gui;
		}

		public static interface Bridge {

		}

		public static interface Component {

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl
				implements
					NamedPublishMAS.Observed.Component {

			@SuppressWarnings("unused")
			private final NamedPublishMAS.Observed.Bridge bridge;

			private final NamedPublishMAS.Observed implementation;

			private ComponentImpl(final NamedPublishMAS.Observed implem,
					final NamedPublishMAS.Observed.Bridge b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				assert this.implementation.use_sched != null;
				this.sched = this.implementation.use_sched
						.newComponent(new BridgeImplscheduleAgent());
				assert this.implem_beh == null;
				this.implem_beh = implem.make_beh();
				this.beh = this.implem_beh.newComponent(new BridgeImpl_beh());
				assert this.implementation.use_ref != null;
				this.ref = this.implementation.use_ref
						.newComponent(new BridgeImplrefsCallee());
				assert this.implementation.use_observed != null;
				this.observed = this.implementation.use_observed
						.newComponent(new BridgeImplobservedsPublisherPush());
			}

			private final Scheduled.Agent.Component sched;

			private final class BridgeImplscheduleAgent
					implements
						Scheduled.Agent.Bridge {

				public final fr.irit.smac.may.lib.interfaces.Do cycle() {
					return ComponentImpl.this.beh.cycle();

				};

			}
			private final ObservedBehaviour.Component beh;

			private ObservedBehaviour implem_beh = null;

			private final class BridgeImpl_beh
					implements
						ObservedBehaviour.Bridge {

				public final fr.irit.smac.may.lib.interfaces.Push<java.lang.Integer> changeValue() {
					return ComponentImpl.this.observed.set();

				};

			}
			private final MapReferences.Callee.Component<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> ref;

			private final class BridgeImplrefsCallee
					implements
						MapReferences.Callee.Bridge<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> {

				public final fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer> toCall() {
					return ComponentImpl.this.observed.toCall();

				};

			}
			private final ValuePublisher.PublisherPush.Component<java.lang.Integer, java.lang.String> observed;

			private final class BridgeImplobservedsPublisherPush
					implements
						ValuePublisher.PublisherPush.Bridge<java.lang.Integer, java.lang.String> {

			}

			public final void start() {
				this.sched.start();
				this.beh.start();
				this.ref.start();
				this.observed.start();

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

		public NamedPublishMAS.Observed.Component newComponent(
				NamedPublishMAS.Observed.Bridge b) {
			return new NamedPublishMAS.Observed.ComponentImpl(this, b);
		}

	}

	/**
	 * Should not be called
	 */
	protected abstract NamedPublishMAS.Observer make_Observer(
			fr.irit.smac.may.lib.classic.namedpub.AbstractObserverBehaviour<java.lang.String> beha);

	/**
	 * Should not be called
	 */
	public NamedPublishMAS.Observer createImplementationOfObserver(
			fr.irit.smac.may.lib.classic.namedpub.AbstractObserverBehaviour<java.lang.String> beha) {
		NamedPublishMAS.Observer implem = make_Observer(beha);
		assert implem.ecosystemComponent == null;
		assert this.selfComponent != null;
		implem.ecosystemComponent = this.selfComponent;
		assert this.selfComponent.implem_schedule != null;
		assert implem.use_sched == null;
		implem.use_sched = this.selfComponent.implem_schedule
				.createImplementationOfAgent();
		assert this.selfComponent.implem_observers != null;
		assert implem.use_observer == null;
		implem.use_observer = this.selfComponent.implem_observers
				.createImplementationOfAgent();

		return implem;
	}

	/**
	 * This can be called to create an instance of the species from inside the implementation of the ecosystem.
	 *
	 * This is not meant to be called on the object by hand.
	 */
	public NamedPublishMAS.Observer.Component newObserver(
			fr.irit.smac.may.lib.classic.namedpub.AbstractObserverBehaviour<java.lang.String> beha) {
		NamedPublishMAS.Observer implem = createImplementationOfObserver(beha);
		return implem.newComponent(new NamedPublishMAS.Observer.Bridge() {
		});
	}

	public static abstract class Observer {

		private NamedPublishMAS.Observer.ComponentImpl selfComponent = null;

		/**
		 * This can be called by the implementation to access the component itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected NamedPublishMAS.Observer.Component self() {
			assert this.selfComponent != null;
			return this.selfComponent;
		};

		private Scheduled.Agent use_sched = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Scheduled.Agent.Component sched() {
			assert this.selfComponent != null;
			return this.selfComponent.sched;
		}

		/**
		 * This should be overridden by the implementation to define how to create this sub-component.
		 * This will be called once during the construction of the component to initialize this sub-component.
		 */
		protected abstract ObserverBehaviour<java.lang.String> make_beh();

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ObserverBehaviour.Component<java.lang.String> beh() {
			assert this.selfComponent != null;
			return this.selfComponent.beh;
		}

		private Forward.Agent<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> use_observer = null;

		/**
		 * This can be called by the implementation to access the sub-component instance and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Forward.Agent.Component<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> observer() {
			assert this.selfComponent != null;
			return this.selfComponent.observer;
		}

		private NamedPublishMAS.ComponentImpl ecosystemComponent = null;

		/**
		 * This can be called by the implementation to access the component of the ecosystem itself and its provided ports.
		 *
		 * This is not meant to be called from the outside by hand.
		 */
		protected NamedPublishMAS.Component eco_self() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent;
		};

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final MapReferences.Component<fr.irit.smac.may.lib.interfaces.Pull<java.lang.Integer>, java.lang.String> eco_refs() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.refs;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ValuePublisher.Component<java.lang.Integer, java.lang.String> eco_observeds() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.observeds;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Forward.Component<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> eco_observers() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.observers;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final ExecutorService.Component eco_executor() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.executor;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Scheduled.Component eco_schedule() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.schedule;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final Clock.Component eco_clock() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.clock;
		}

		/**
		 * This can be called by the implementation to access the sub-component instance of the ecosystemComponent and its provided ports.
		 * It will be initialized after the required ports are initialized and before the provided ports are initialized.
		 *
		 * This is not meant to be called on the object by hand.
		 */
		protected final SchedulingControllerGUI.Component eco_gui() {
			assert this.ecosystemComponent != null;
			return this.ecosystemComponent.gui;
		}

		public static interface Bridge {

		}

		public static interface Component {

			/**
			 * This should be called to start the component
			 */
			public void start();
		}

		private final static class ComponentImpl
				implements
					NamedPublishMAS.Observer.Component {

			@SuppressWarnings("unused")
			private final NamedPublishMAS.Observer.Bridge bridge;

			private final NamedPublishMAS.Observer implementation;

			private ComponentImpl(final NamedPublishMAS.Observer implem,
					final NamedPublishMAS.Observer.Bridge b) {
				this.bridge = b;

				this.implementation = implem;

				assert implem.selfComponent == null;
				implem.selfComponent = this;

				assert this.implementation.use_sched != null;
				this.sched = this.implementation.use_sched
						.newComponent(new BridgeImplscheduleAgent());
				assert this.implem_beh == null;
				this.implem_beh = implem.make_beh();
				this.beh = this.implem_beh.newComponent(new BridgeImpl_beh());
				assert this.implementation.use_observer != null;
				this.observer = this.implementation.use_observer
						.newComponent(new BridgeImplobserversAgent());
			}

			private final Scheduled.Agent.Component sched;

			private final class BridgeImplscheduleAgent
					implements
						Scheduled.Agent.Bridge {

				public final fr.irit.smac.may.lib.interfaces.Do cycle() {
					return ComponentImpl.this.beh.cycle();

				};

			}
			private final ObserverBehaviour.Component<java.lang.String> beh;

			private ObserverBehaviour<java.lang.String> implem_beh = null;

			private final class BridgeImpl_beh
					implements
						ObserverBehaviour.Bridge<java.lang.String> {

				public final fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String> observe() {
					return ComponentImpl.this.observer.a();

				};

			}
			private final Forward.Agent.Component<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> observer;

			private final class BridgeImplobserversAgent
					implements
						Forward.Agent.Bridge<fr.irit.smac.may.lib.components.interactions.interfaces.Observe<java.lang.Integer, java.lang.String>> {

			}

			public final void start() {
				this.sched.start();
				this.beh.start();
				this.observer.start();

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

		public NamedPublishMAS.Observer.Component newComponent(
				NamedPublishMAS.Observer.Bridge b) {
			return new NamedPublishMAS.Observer.ComponentImpl(this, b);
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

	public NamedPublishMAS.Component newComponent(NamedPublishMAS.Bridge b) {
		return new NamedPublishMAS.ComponentImpl(this, b);
	}

	public NamedPublishMAS.Component newComponent() {
		return this.newComponent(new NamedPublishMAS.Bridge() {
		});
	}
	public static final NamedPublishMAS.Component newComponent(
			NamedPublishMAS _compo) {
		return _compo.newComponent();
	}

}
