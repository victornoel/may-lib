package fr.irit.smac.may.lib.interfaces;

/***
 * Kind of Function<K,V> (fj.F from functional java, or scala.Function1 from Scala)
 * 
 */
public interface MapGet<K,V> {

	public V get(K k);
}
