package tests.local;

import tests.messages.Other;
import tests.messages.World;
import tests.messages.Zero;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingBehavior;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class Behavior1<Ref> extends ClassicBehaviour<PatternMatchingMessage,Ref> {

	private static final long serialVersionUID = 7879055266000413093L;

	private final Ref friend;
	private final PatternMatchingBehavior matcher = new PatternMatchingBehavior(this);

	public Behavior1(Ref friend) {
		this.friend = friend;
	}

	public void caseHello() {
		System.out.println(me().pull() + " : received : hello");
		send().send(new World(), friend);
		send().send(new Other("three"), friend);
		send().send(new Other("two"), friend);
		send().send(new Other("one"), friend);
		send().send(new Zero(), friend);

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