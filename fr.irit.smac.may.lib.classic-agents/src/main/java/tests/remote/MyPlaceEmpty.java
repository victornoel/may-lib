package tests.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.impl.RemoteClassicImpl;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class MyPlaceEmpty {

	public static void main(String[] args) {
		RemoteClassic.Component<PatternMatchingMessage> infra = new RemoteClassic.Component<PatternMatchingMessage>(new RemoteClassicImpl<PatternMatchingMessage>(1098), new RemoteClassic.Bridge<PatternMatchingMessage>() {}) ;
		infra.start();
		
		System.out.println("a");
	}
}
