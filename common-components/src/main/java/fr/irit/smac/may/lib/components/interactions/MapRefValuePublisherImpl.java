package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.mapreferences.MapReferencesImpl;
import fr.irit.smac.may.lib.components.interactions.valuepublisher.ValuePublisherImpl;
import fr.irit.smac.may.lib.interfaces.Pull;

public class MapRefValuePublisherImpl<T, K> extends MapRefValuePublisher<T, K> {

	@Override
	protected MapReferences<Pull<T>, K> make_mr() {
		return new MapReferencesImpl<Pull<T>, K>();
	}

	@Override
	protected ValuePublisher<T, K> make_vp() {
		return new ValuePublisherImpl<T, K>();
	}

	@Override
	protected PublisherPush<T, K> make_PublisherPush(
			K key) {
		return new PublisherPush<T, K>() {};
	}

	@Override
	protected PublisherPull<T, K> make_PublisherPull(
			K key) {
		return new PublisherPull<T, K>() {};
	}

	@Override
	protected PublisherPushKeyPort<T, K> make_PublisherPushKeyPort() {
		return new PublisherPushKeyPort<T, K>() {};
	}

	@Override
	protected PublisherPullKeyPort<T, K> make_PublisherPullKeyPort() {
		return new PublisherPullKeyPort<T, K>() {};
	}

	@Override
	protected Observer<T, K> make_Observer() {
		return new Observer<T, K>() {};
	}

}
