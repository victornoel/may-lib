package fr.irit.smac.may.lib.components.either.impl;

import fr.irit.smac.may.lib.components.UnEither;
import fr.irit.smac.may.lib.interfaces.Push;

public class UnEitherImpl<L, R> extends UnEither<L, R> {

	@Override
	protected Push<Either<L, R>> in() {
		return new Push<Either<L,R>>() {
			public void push(Either<L, R> thing) {
				if (thing.isLeft()) {
					left().push(thing.getLeft());
				} else if (thing.isRight()) {
					right().push(thing.getRight());
				} else {
					throw new IllegalStateException("The either is neither right nor left!");
				}
			}
		};
	}

}
