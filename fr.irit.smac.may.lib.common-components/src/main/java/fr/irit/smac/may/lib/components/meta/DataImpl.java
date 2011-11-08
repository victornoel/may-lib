package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.interfaces.Pull;

public class DataImpl<T> extends Data<T> {

	private final T data;
	
	public DataImpl(T data) {
		this.data = data;
	}
	
	@Override
	protected Pull<T> data() {
		return new Pull<T>() {
			public T pull() {
				return data;
			}
		};
	}

}
