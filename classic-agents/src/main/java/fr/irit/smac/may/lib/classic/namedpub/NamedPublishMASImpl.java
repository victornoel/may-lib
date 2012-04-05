package fr.irit.smac.may.lib.classic.namedpub;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS;
import fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour;
import fr.irit.smac.may.lib.components.interactions.MapReferences;
import fr.irit.smac.may.lib.components.interactions.ValuePublisher;
import fr.irit.smac.may.lib.components.interactions.interfaces.Observe;
import fr.irit.smac.may.lib.components.interactions.mapreferences.MapReferencesImpl;
import fr.irit.smac.may.lib.components.interactions.valuepublisher.ValuePublisherImpl;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.meta.ForwardImpl;
import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.ClockImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;
import fr.irit.smac.may.lib.components.scheduling.Scheduled;
import fr.irit.smac.may.lib.components.scheduling.ScheduledImpl;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUIImpl;
import fr.irit.smac.may.lib.interfaces.Pull;

public class NamedPublishMASImpl extends NamedPublishMAS {

	@Override
	protected ExecutorService make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(10));
	}

	@Override
	protected Scheduled make_schedule() {
		return new ScheduledImpl();
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
	protected MapReferences<Pull<Integer>, String> make_refs() {
		return new MapReferencesImpl<Pull<Integer>, String>();
	}

	@Override
	protected ValuePublisher<Integer, String> make_observeds() {
		return new ValuePublisherImpl<Integer, String>();
	}

	@Override
	protected Forward<Observe<Integer, String>> make_observers() {
		return new ForwardImpl<Observe<Integer,String>>();
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
				Observer.Component agent = newObserver(beh);
				agent.start();
			}

			public void createObserved(String name,	AbstractObservedBehaviour beh) {
				Observed.Component agent = newObserved(name,beh);
				agent.start();
				
			}
		};
	}

}
