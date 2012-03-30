package tests.remote;

import tests.messages.Start;
import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.impl.RemoteClassicImpl;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class MyPlaceStart {

	public static void main(String[] args) {

		RemoteClassic.Component<PatternMatchingMessage> infra = RemoteClassic.newComponent(new RemoteClassicImpl<PatternMatchingMessage>(1099));
		
		RemoteAgentRef starter = infra.create().create(new BehaviorSetup<RemoteAgentRef>());

		infra.send().send(new Start(infra.thisPlace().pull(), new Place("volte",1098)), starter);
		
		System.gc();
		
		System.out.println("a");
	}
}
