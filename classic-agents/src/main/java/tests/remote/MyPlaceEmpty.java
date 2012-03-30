package tests.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.impl.RemoteClassicImpl;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class MyPlaceEmpty {

	public static void main(String[] args) {
		RemoteClassic.Component<PatternMatchingMessage> infra = RemoteClassic.newComponent(new RemoteClassicImpl<PatternMatchingMessage>(1098));

		System.out.println("a");
	}
}
