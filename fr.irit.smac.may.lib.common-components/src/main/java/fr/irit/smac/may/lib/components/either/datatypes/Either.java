package fr.irit.smac.may.lib.components.either.datatypes;

public class Either<L, R> {

	public final L left;
	
	public final R right;
	
	private Either(L l, R r) {
		this.left = l;
		this.right = r;
	}
	
	public boolean hasLeft() {
		return left != null;
	}
	
	public boolean hasRight() {
		return right != null;
	}
	
	public static <L,R> Either<L,R> right(R r) {
		return new Either<L,R>(null,r);
	}
	
	public static <L,R> Either<L,R> left(L l) {
		return new Either<L,R>(l,null);
	}
}
