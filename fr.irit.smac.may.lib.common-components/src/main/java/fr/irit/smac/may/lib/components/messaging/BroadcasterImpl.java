package fr.irit.smac.may.lib.components.messaging;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.irit.smac.may.lib.interfaces.Broadcast;
import fr.irit.smac.may.lib.interfaces.Push;

public class BroadcasterImpl<T,Ref> extends Broadcaster<T, Ref> {

	// TODO exporter ça dans un autre composant, éventuellement faire un trip avec les weakref… ou pas !
	//private final Set<Ref> s = Collections.newSetFromMap(new WeakHashMap<Ref, Boolean>());
	private final Set<Ref> s = Collections.synchronizedSet(new HashSet<Ref>());

	public class AgentSide extends Broadcaster.Agent<T, Ref> {
		@Override
		protected Broadcast<T> bc() {
			return new Broadcast<T>() {
				public void broadcast(T msg) {
					for (Ref r : s) {
						deposit().send(msg, r);
					}
				}
			};
		}
	}
	
	@Override
	protected Push<Ref> add() {
		return new Push<Ref>() {
			public void push(Ref t) {
				s.add(t);
			};
		};
	}

	@Override
	protected Push<Ref> remove() {
		return new Push<Ref>() {
			public void push(Ref t) {
				s.remove(t);
			};
		};
	}

	@Override
	protected Push<T> broadcast() {
		return new Push<T>() {
			public void push(T thing) {
				for (Ref r : s) {
					deposit().send(thing, r);
				}
			};
		};
	}

}
