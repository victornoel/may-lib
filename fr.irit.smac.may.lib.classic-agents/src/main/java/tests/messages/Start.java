package tests.messages;

import fr.irit.smac.may.lib.components.remplace.impl.Place;
import fr.irit.smac.may.lib.pmbehaviour.PatternMatchingMessage;

public class Start extends PatternMatchingMessage {

	private static final long serialVersionUID = 9173518334967024508L;

	public Start(Place p1, Place p2) {
		super(p1,p2);
	}
}
