package fr.irit.smac.may.lib.components.interactions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;

public class BroadcastImpl<M, R> extends Broadcast<M, R> {

	private final Set<R> s = Collections.synchronizedSet(new HashSet<R>());
	
	@Override
	protected Push<M> make_broadcast() {
		return new Push<M>() {
			public void push(final M message) {
				synchronized (s) {
					for(R r: s) {
						requires().send().send(message, r);
					}
				}
			}
		};
	}

	@Override
	protected BroadcastTarget<M, R> make_BroadcastTarget() {
		return new BroadcastTarget<M, R>() {
			@Override
			protected void start() {
				super.start();
				s.add(requires().me().pull());
			}
			
			@Override
			protected Do make_stop() {
				return new Do() {
					public void doIt() {
						s.remove(requires().me().pull());
					}
				};
			}
		};
	}

	@Override
	protected Broadcaster<M, R> make_Broadcaster() {
		return new Broadcaster<M, R>() {
			@Override
			protected Push<M> make_broadcast() {
				return new Push<M>() {
					public void push(M m) {
						eco_provides().broadcast().push(m);
					}
				};
			}
		};
	}

}
