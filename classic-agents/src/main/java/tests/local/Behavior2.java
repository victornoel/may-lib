package tests.local;

import tests.messages.WithRef;
import fr.irit.smac.may.lib.classic.impl.AbstractClassicBehaviour;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingBehavior;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class Behavior2<Ref> extends AbstractClassicBehaviour<PatternMatchingMessage,Ref> {

	private static final long serialVersionUID = -7663551892033127305L;

	private final PatternMatchingBehavior matcher = new PatternMatchingBehavior(this);
	
	public void caseWorld() {
		System.out.println(me().pull() + " received : world");

		// Ref wr = create().create(new BehaviorWithRef(), new
		// Place("localhost:1098"));
		Ref wr = create().create(new BehaviorWithRef<Ref>());
		send().send(new WithRef<Ref>(me().pull()), wr);

		// wait for message to get into my mailbox
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// move(new Place("localhost:1098"));
	}

	public void caseOther(String e) {
		System.out.println(me().pull() + " : received other : " + e);
	}

	public void caseZero() {
		System.out.println(me().pull() + " : received : zero");
		
		System.out.println(me().pull() + " : going to...");
		
		/*
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		System.out.println(me().pull() + " : ...die");
		
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