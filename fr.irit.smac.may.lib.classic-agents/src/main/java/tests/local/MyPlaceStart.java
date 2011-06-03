package tests.local;

import tests.messages.Start;
import fr.irit.smac.may.lib.classic.impl.ClassicImpl;
import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.components.refreceive.impl.AgentRef;
import fr.irit.smac.may.lib.components.remplace.impl.Place;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class MyPlaceStart {

	public static void main(String[] args) {

		Classic.Component<PatternMatchingMessage> infra = new Classic.Component<PatternMatchingMessage>(new ClassicImpl<PatternMatchingMessage>(), new Classic.Bridge<PatternMatchingMessage>() {}) ;
		infra.start();
		
		AgentRef<PatternMatchingMessage> starter = infra.create().create(new BehaviorSetup<AgentRef<PatternMatchingMessage>>());

		infra.send().send(new Start(new Place(null, 0), new Place(null, 0)), starter);
		
		System.gc();
		
		System.out.println("a");
	}
}
