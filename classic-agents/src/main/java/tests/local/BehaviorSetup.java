package tests.local;

import tests.messages.Hello;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingBehavior;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class BehaviorSetup<Ref> extends ClassicBehaviour<PatternMatchingMessage,Ref> {

	private static final long serialVersionUID = 5268234632750262057L;

	private final PatternMatchingBehavior matcher = new PatternMatchingBehavior(this);
	
	@Override
	protected void start() {
		super.start();
		System.out.println("setup, starting");
	}
	
	public void caseStart(Place p1, Place p2) {
		// Ref pierre = create().create(new Behavior2(), p1);
		// Ref bob = create().create(new Behavior1(pierre), p2);
		System.out.println("setup, start");
		Ref pierre = create().create(new Behavior2<Ref>());
		Ref bob = create().create(new Behavior1<Ref>(pierre));
		System.out.println("setup, created");
		send().send(new Hello(), bob);
		System.out.println("setup, sent");
		
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
