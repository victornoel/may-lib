package tests.messages;

import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class WithRef<Ref> extends PatternMatchingMessage {

	private static final long serialVersionUID = 8047779783319916043L;

	public WithRef(Ref r) {
		super(r);
	}
}
