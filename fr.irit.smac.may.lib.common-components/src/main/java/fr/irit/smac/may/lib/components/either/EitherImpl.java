package fr.irit.smac.may.lib.components.either;

import fr.irit.smac.may.lib.components.either.datatypes.Either;
import fr.irit.smac.may.lib.interfaces.Push;

public class EitherImpl<L, R> extends fr.irit.smac.may.lib.components.either.Either<L, R> {

	@Override
	protected Push<L> left() {
		return new Push<L>() {
			public void push(L thing) {
				out().push(Either.<L, R>left(thing));
			};
		};
	}

	@Override
	protected Push<R> right() {
		return new Push<R>() {
			public void push(R thing) {
				out().push(Either.<L, R>right(thing));
			};
		};
	}

}
