package fr.irit.smac.may.lib.components.distribution.interfaces;

public interface Transform<From,To> {

	/**
	 * Transform something of type From to type To
	 * 
	 * @param f the thing to transform
	 * @return the result of the transformation
	 */
	public To transform(From f);
}
