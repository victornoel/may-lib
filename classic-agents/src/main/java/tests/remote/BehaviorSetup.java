package tests.remote;

import java.io.Serializable;

import tests.messages.Hello;
import fr.irit.smac.may.lib.classic.remote.impl.AbstractRemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingBehavior;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class BehaviorSetup<Ref> extends AbstractRemoteClassicBehaviour<PatternMatchingMessage,Ref> implements Serializable {

	private static final long serialVersionUID = 5268234632750262057L;

	private final PatternMatchingBehavior matcher = new PatternMatchingBehavior(this);
	
	@Override
	protected void start() {
		super.start();
		System.out.println("setup, starting");
	}
	
	public void caseStart(Place p1, Place p2) {
		Ref pierre = create().create(new Behavior2<Ref>(), p1);
		Ref bob = create().create(new Behavior1<Ref>(pierre), p2);
		send().send(new Hello(), bob);
		
		System.out.println(me().pull() + " : die");
		die().doIt();
	}
	
	@Override
	protected Push<PatternMatchingMessage> make_cycle() {
		return new Push<PatternMatchingMessage>() {
			public void push(PatternMatchingMessage thing) {
				matcher.match(thing);
			}
		};
	}
}
