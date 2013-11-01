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
		protected Broadcast<T> make_bc() {
			return new Broadcast<T>() {
				public void broadcast(T msg) {
					doBroadcast(msg);
				}
			};
		}
	}
	
	@Override
	protected Push<Ref> make_add() {
		return new Push<Ref>() {
			public void push(Ref t) {
				s.add(t);
			};
		};
	}

	@Override
	protected Push<Ref> make_remove() {
		return new Push<Ref>() {
			public void push(Ref t) {
				s.remove(t);
			};
		};
	}
	
	private void doBroadcast(T t) {
		synchronized (s) {
			for (Ref r : s) {
				requires().deposit().send(t, r);
			}
		}
	}

	@Override
	protected Push<T> make_broadcast() {
		return new Push<T>() {
			public void push(T thing) {
				doBroadcast(thing);
			};
		};
	}


	@Override
	protected Agent<T, Ref> make_Agent() {
		return new AgentSide();
	}


}
