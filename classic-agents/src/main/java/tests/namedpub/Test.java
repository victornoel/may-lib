package tests.namedpub;

import java.util.Random;

import fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS;
import fr.irit.smac.may.lib.classic.namedpub.AbstractObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedpub.AbstractObserverBehaviour;
import fr.irit.smac.may.lib.classic.namedpub.NamedPublishMASImpl;

public class Test {

	
	public static void main(String[] args) {
		NamedPublishMAS.Component mas = NamedPublishMAS.newComponent(new NamedPublishMASImpl());
		mas.start();
		
		mas.create().createObserved("agent1", new AbstractObservedBehaviour() {
			@Override
			protected void behaviour() {
				int v = new Random().nextInt(10);
				System.out.println("observed: changing value to "+v);
				changeValue().push(v);
			}
		});
		
		mas.create().createObserver(new AbstractObserverBehaviour<String>() {
			@Override
			protected void behaviour() {
				System.out.println("observer: observing value of "+observe().observe("agent1").orSome(-1));
			}
		});
	}
}
