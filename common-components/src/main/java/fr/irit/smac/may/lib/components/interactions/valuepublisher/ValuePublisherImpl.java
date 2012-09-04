package fr.irit.smac.may.lib.components.interactions.valuepublisher;

import fj.data.Option;
import fr.irit.smac.may.lib.components.interactions.ValuePublisher;
import fr.irit.smac.may.lib.components.interactions.exceptions.RefDoesNotExistsException;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

public class ValuePublisherImpl<T, K> extends ValuePublisher<T, K> {

	@Override
	protected ReliableObserve<T, K> make_observe() {
		return new ReliableObserve<T, K>() {
			public Option<T> observe(K ref) {
				try {
					return Option.some(self().observe().reliableObserve(ref));
				} catch (RefDoesNotExistsException e) {
					return Option.none();
				}
			}

			public T reliableObserve(K ref) throws RefDoesNotExistsException {
				return call().call(ref).pull();
			}
		};
	}

	@Override
	protected PublisherPull<T, K> make_PublisherPull() {
		return new PublisherPull<T, K>() {
			@Override
			protected Pull<T> make_get() {
				return new Pull<T>() {
					public T pull() {
						return getValue().pull();
					}
				};
			}
		};
	}

	@Override
	protected PublisherPush<T, K> make_PublisherPush() {
		return new PublisherPush<T, K>() {

			// must be volatile to be correctly
			// accessed from different threads
			private T value;

			@Override
			protected Push<T> make_set() {
				return new Push<T>() {
					public void push(T thing) {
						value = thing;
					};
				};
			}

			@Override
			protected Pull<T> make_get() {
				return new Pull<T>() {
					public T pull() {
						return value;
					}
				};
			}

		};
	}

	@Override
	protected Observer<T, K> make_Observer() {
		return new Observer<T, K>() {
			@Override
			protected ReliableObserve<T, K> make_observe() {
				return new ReliableObserve<T, K>() {
					public Option<T> observe(K ref) {
						return eco_self().observe().observe(ref);
					};
					public T reliableObserve(K ref) throws RefDoesNotExistsException {
						return eco_self().observe().reliableObserve(ref);
					};
				};
			}
		};
	}
}
