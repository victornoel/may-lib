package fr.irit.smac.may.lib.components.either.datatypes;

public class Either<L, R> {

	private final L left;
	
	private final R right;
	
	private Either(L l, R r) {
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
	
	public static <L,R> Either<L,R> right(R r) {
		return new Either<L,R>(null,r);
	}
	
	public static <L,R> Either<L,R> left(L l) {
		return new Either<L,R>(l,null);
	}
}
