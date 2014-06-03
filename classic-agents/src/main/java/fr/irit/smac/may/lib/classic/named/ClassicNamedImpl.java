package fr.irit.smac.may.lib.classic.named;

import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.classic.interfaces.CreateNamed;
import fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiverImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;

public class ClassicNamedImpl<Msg> extends ClassicNamed<Msg> {
	
	@Override
	protected ExecutorServiceWrapper make_executor() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}
	
	@Override
	protected MapRefAsyncReceiver<Msg, String> make_receive() {
		return new MapRefAsyncReceiverImpl<Msg, String>();
	}

//	@Override
//	protected Forward<CreateNamed<Msg, String>> make_fact() {
//		return new ForwardImpl<CreateNamed<Msg,String>>();
//	}
	
	@Override
	protected CreateNamed<Msg, String> make_create() {
		return new CreateNamed<Msg, String>() {
			public String create(
					final AbstractClassicNamedBehaviour<Msg, String> beh, String name) {
				newClassicNamedAgent(beh, name);
				return name;
			}
		};
	}

	@Override
	protected ClassicNamedAgent<Msg> make_ClassicNamedAgent(
			final ClassicNamedBehaviour<Msg, String> beh, final String name) {
		return new ClassicNamedAgent<Msg>() {
			@Override
			protected ClassicNamedAgentComponent<Msg, String> make_arch() {
				return new ClassicNamedAgentComponentImpl<Msg,String>(beh);
			}
		};
	}

}
