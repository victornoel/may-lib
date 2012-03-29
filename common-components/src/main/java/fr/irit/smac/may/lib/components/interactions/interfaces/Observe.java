package fr.irit.smac.may.lib.components.interactions.interfaces;

import fj.data.Option;

public interface Observe<V,R> {
	
	public Option<V> observe(R ref);

}
