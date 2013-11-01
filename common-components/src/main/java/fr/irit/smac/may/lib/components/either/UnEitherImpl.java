package fr.irit.smac.may.lib.components.either;

import fr.irit.smac.may.lib.components.either.datatypes.Either;
import fr.irit.smac.may.lib.interfaces.Push;

public class UnEitherImpl<L, R> extends UnEither<L, R> {

	@Override
	protected Push<Either<L, R>> make_in() {
		return new Push<Either<L,R>>() {
			public void push(Either<L, R> thing) {
				if (thing.hasLeft()) {
					requires().left().push(thing.left);
				} else if (thing.hasRight()) {
					requires().right().push(thing.right);
				} else {
					throw new IllegalStateException("The either is neither right nor left!");
				}
			}
		};
	}

}
