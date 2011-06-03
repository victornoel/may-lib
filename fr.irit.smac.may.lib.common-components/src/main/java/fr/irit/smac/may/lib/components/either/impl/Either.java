package fr.irit.smac.may.lib.components.either.impl;

public class Either<L, R> {

	private final L left;
	
	private final R right;
	
	Either(L l, R r) {
		this.left = l;
		this.right = r;
	}
	
	public boolean isLeft() {
		return left != null;
	}
	
	public boolean isRight() {
		return right != null;
	}
	
	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
}
