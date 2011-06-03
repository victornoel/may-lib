package tests.remote;

import tests.messages.Start;
import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.impl.RemoteClassicImpl;
import fr.irit.smac.may.lib.components.remplace.impl.Place;
import fr.irit.smac.may.lib.components.remrefreceive.impl.RemoteAgentRef;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class MyPlaceStart {

	public static void main(String[] args) {

		RemoteClassic.Component<PatternMatchingMessage> infra = new RemoteClassic.Component<PatternMatchingMessage>(new RemoteClassicImpl<PatternMatchingMessage>(1099), new RemoteClassic.Bridge<PatternMatchingMessage>() {}) ;
		infra.start();
		
		RemoteAgentRef<PatternMatchingMessage> starter = infra.create().create(new BehaviorSetup<RemoteAgentRef<PatternMatchingMessage>>());

		infra.send().send(new Start(infra.thisPlace().pull(), new Place("volte",1098)), starter);
		
		System.gc();
		
		System.out.println("a");
	}
}
