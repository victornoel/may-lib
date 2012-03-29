package fr.irit.smac.may.lib.classic.named;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.interfaces.CreateNamed;
import fr.irit.smac.may.lib.components.interactions.AsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.MapReferences;
import fr.irit.smac.may.lib.components.interactions.asyncreceiver.AsyncReceiverImpl;
import fr.irit.smac.may.lib.components.interactions.mapreferences.MapReferencesImpl;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.meta.ForwardImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.SchedulerImpl;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

public class ClassicNamedImpl<Msg> extends ClassicNamed<Msg> {
	
	@Override
	protected Scheduler make_scheduler() {
		return new SchedulerImpl();
	}

	@Override
	protected ExecutorService make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}

	@Override
	protected Forward<Send<Msg, String>> make_sender() {
		return new ForwardImpl<Send<Msg, String>>();
	}

	@Override
	protected AsyncReceiver<Msg, String> make_receive() {
		return new AsyncReceiverImpl<Msg, String>();
	}
	
	@Override
	protected MapReferences<Push<Msg>, String> make_refs() {
		return new MapReferencesImpl<Push<Msg>, String>();
	}

	@Override
	protected Forward<CreateNamed<Msg, String>> make_fact() {
		return new ForwardImpl<CreateNamed<Msg,String>>();
	}
	@Override
	protected CreateNamed<Msg, String> make_create() {
		return new CreateNamed<Msg, String>() {
			public String create(
					final ClassicNamedBehaviour<Msg, String> beh, String name) {
				ClassicNamedAgent.Component<Msg> agent = createClassicNamedAgent(beh, name);
				agent.start();
				return name;
			}
		};
	}

	@Override
	protected fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent<Msg> make_ClassicNamedAgent(
			final ClassicNamedBehaviour<Msg, String> beh, final String name) {
		return new ClassicNamedAgent<Msg>() {
			@Override
			protected ClassicNamedAgentComponent<Msg, String> make_arch() {
				return new ClassicNamedAgentComponentImpl<Msg,String>(beh);
			}
		};
	}

}
