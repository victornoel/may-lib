package fr.irit.smac.may.lib.interfaces;

import fr.irit.smac.may.lib.exceptions.KeyDoesNotExistException;

/***
 * Kind of Function<K,V> (fj.F from functional java, or scala.Function1 from Scala) but with an exception!
 * 
 */
public interface MapGet<K,V> {

	public V get(K k) throws KeyDoesNotExistException;
}
