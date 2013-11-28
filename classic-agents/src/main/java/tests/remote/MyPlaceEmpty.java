package tests.remote;

import fr.irit.smac.may.lib.classic.remote.impl.RemoteClassicImpl;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class MyPlaceEmpty {

	public static void main(String[] args) {
		new RemoteClassicImpl<PatternMatchingMessage>(1098).newComponent();

		System.out.println("a");
	}
}
