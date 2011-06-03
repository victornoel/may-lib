package fr.irit.smac.may.lib.components.either.impl;

import fr.irit.smac.may.lib.interfaces.Push;

public class EitherImpl<L, R> extends fr.irit.smac.may.lib.components.Either<L, R> {

	@Override
	protected Push<L> left() {
		return new Push<L>() {
			public void push(L thing) {
				out().push(new Either<L, R>(thing, null));
			};
		};
	}

	@Override
	protected Push<R> right() {
		return new Push<R>() {
			public void push(R thing) {
				out().push(new Either<L, R>(null,thing));
			};
		};
	}

}
