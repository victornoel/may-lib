package fr.irit.smac.may.lib.classic.namedpub;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS;
import fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour;
import fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher;
import fr.irit.smac.may.lib.components.interactions.MapRefValuePublisherImpl;
import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.ClockImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.SchedulerImpl;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUIImpl;

public class NamedPublishMASImpl extends NamedPublishMAS {

	@Override
	protected ExecutorServiceWrapper make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(10));
	}

	@Override
	protected Scheduler make_schedule() {
		return new SchedulerImpl();
	}

	@Override
	protected Clock make_clock() {
		return new ClockImpl();
	}
	
	@Override
	protected SchedulingControllerGUI make_gui() {
		return new SchedulingControllerGUIImpl(1000);
	}

	@Override
	protected MapRefValuePublisher<Integer, String> make_observeds() {
		return new MapRefValuePublisherImpl<Integer, String>();
	}

	@Override
	protected Observed make_Observed(String name, final AbstractObservedBehaviour beh) {
		return new Observed() {
			@Override
			protected ObservedBehaviour make_beh() {
				return beh;
			}
		};
	}

	@Override
	protected Observer make_Observer(final AbstractObserverBehaviour<String> beh) {
		return new Observer() {
			@Override
			protected ObserverBehaviour<String> make_beh() {
				return beh;
			}
		};
	}

	@Override
	protected NamedPublishMASFactory make_create() {
		return new NamedPublishMASFactory() {

			public void createObserver(AbstractObserverBehaviour<String> beh) {
				newObserver(beh);
			}

			public void createObserved(String name,	AbstractObservedBehaviour beh) {
				newObserved(name,beh);
			}
		};
	}

}
