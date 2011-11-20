package fr.irit.smac.may.lib.components.distribution;

import org.codehaus.jackson.type.TypeReference;

import fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBinderImpl;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBus;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBusImpl;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig;

public class IvyJSONBroadcasterImpl<T> extends IvyJSONBroadcaster<T> {

	private final String namespace;
	private final IvyConnectionConfig config;
	private final TypeReference<T> clazz;

	public IvyJSONBroadcasterImpl(String namespace, IvyConnectionConfig config, TypeReference<T> clazz) {
		this.namespace = namespace;
		this.config = config;
		this.clazz = clazz;
	}
	
	@Override
	protected IvyBroadcaster<T> make_bc() {
		return new IvyBroadcasterImpl<T>(namespace);
	}

	@Override
	protected IvyBinder make_binder() {
		return new IvyBinderImpl();
	}

	@Override
	protected IvyBus make_ivy() {
		return new IvyBusImpl(config);
	}

	@Override
	protected JSONTransformer<T> make_json() {
		return new JSONTransformerImpl<T>(clazz);
	}

}
