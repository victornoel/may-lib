package fr.irit.smac.may.lib.components.distribution;

import java.util.List;

import fr.irit.smac.may.lib.interfaces.Push;

public class IvyBroadcasterImpl<T> extends IvyBroadcaster<T> {

	private final String namespace;

	/**
	 * 
	 * @param namespace a namespace to avoid conflicting instances of IvyBroadcaster exchanging messages
	 */
	public IvyBroadcasterImpl(String namespace) {
		this.namespace = namespace;
	}
	
	@Override
	protected void start() {
		super.start();
		ivyBindMsg().push("^fr.irit.smac.may.lib.components.distribution.IvyBroadcasterImpl[" + namespace + "]: (.*)$");
	}
	
	@Override
	protected Push<List<String>> ivyReceive() {
		return new Push<List<String>>() {
			public void push(List<String> thing) {
				// there should be only one String
				T m = deserializer().transform(thing.get(0));
				handle().push(m);
				// TODO handle errors in logs
			}
		};
	}

	@Override
	protected Push<T> send() {
		return new Push<T>() {
			public void push(T thing) {
				String m = serializer().transform(thing);
				ivySend().push("fr.irit.smac.may.lib.components.distribution.IvyBroadcasterImpl[" + namespace + "]: " + m);
			};
		};
	}

}
