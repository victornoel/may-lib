package tests.local;

import tests.messages.Start;
import fr.irit.smac.may.lib.classic.impl.ClassicImpl;
import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.components.messaging.receiver.AgentRef;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class MyPlaceStart {

	public static void main(String[] args) {

		Classic.Component<PatternMatchingMessage> infra = Classic.createComponent(new ClassicImpl<PatternMatchingMessage>());
		
		AgentRef starter = infra.create().create(new BehaviorSetup<AgentRef>());

		infra.send().send(new Start(new Place(null, 0), new Place(null, 0)), starter);
		
		System.gc();
		
		System.out.println("a");
	}
}
