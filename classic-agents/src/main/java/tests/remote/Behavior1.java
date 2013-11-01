package tests.remote;

import java.io.Serializable;

import tests.messages.Other;
import tests.messages.World;
import tests.messages.Zero;
import fr.irit.smac.may.lib.classic.remote.impl.AbstractRemoteClassicBehaviour;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingBehavior;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class Behavior1<Ref> extends AbstractRemoteClassicBehaviour<PatternMatchingMessage,Ref> implements Serializable {

	private static final long serialVersionUID = 7879055266000413093L;

	private final Ref friend;
	private final PatternMatchingBehavior matcher = new PatternMatchingBehavior(this);

	public Behavior1(Ref friend) {
		this.friend = friend;
	}

	public void caseHello() {
		System.out.println(requires().me().pull() + " : received : hello");
		requires().send().send(new World(), friend);
		requires().send().send(new Other("three"), friend);
		requires().send().send(new Other("two"), friend);
		requires().send().send(new Other("one"), friend);
		requires().send().send(new Zero(), friend);

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