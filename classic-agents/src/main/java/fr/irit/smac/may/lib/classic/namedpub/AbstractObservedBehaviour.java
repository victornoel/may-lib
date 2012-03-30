package fr.irit.smac.may.lib.classic.namedpub;

import fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour;
import fr.irit.smac.may.lib.interfaces.Do;

public abstract class AbstractObservedBehaviour extends ObservedBehaviour {

	@Override
	protected Do make_cycle() {
		return new Do() {
			public void doIt() {
				behaviour();
			}
		};
	}
	
	protected abstract void behaviour();
	
}
