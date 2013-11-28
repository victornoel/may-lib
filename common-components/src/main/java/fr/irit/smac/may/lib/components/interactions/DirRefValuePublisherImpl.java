package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirectReferencesImpl;
import fr.irit.smac.may.lib.components.interactions.valuepublisher.ValuePublisherImpl;
import fr.irit.smac.may.lib.interfaces.Pull;

public class DirRefValuePublisherImpl<T> extends DirRefValuePublisher<T> {

	@Override
	protected DirectReferences<Pull<T>> make_dr() {
		return new DirectReferencesImpl<Pull<T>>();
	}

	@Override
	protected ValuePublisher<T, DirRef> make_vp() {
		return new ValuePublisherImpl<T, DirRef>();
	}

	@Override
	protected PublisherPush<T> make_PublisherPush(
			String name) {
		return new PublisherPush<T>() {};
	}

	@Override
	protected PublisherPull<T> make_PublisherPull(
			String name) {
		return new PublisherPull<T>() {};
	}

	@Override
	protected Observer<T> make_Observer() {
		return new Observer<T>() {};
	}

}
