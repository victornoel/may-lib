package tests.local;

import tests.messages.Other;
import fr.irit.smac.may.lib.classic.impl.AbstractClassicBehaviour;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingBehavior;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class BehaviorWithRef<Ref> extends AbstractClassicBehaviour<PatternMatchingMessage,Ref> {

	private final PatternMatchingBehavior matcher = new PatternMatchingBehavior(this);
	
	public void caseWithRef(Ref r) {
		System.out.println(requires().me().pull() + " : got ref of " + r);

		/*
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		requires().send().send(new Other("ahahah"), r);

		System.out.println(requires().me().pull() + " : die");
		requires().die().doIt();
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
