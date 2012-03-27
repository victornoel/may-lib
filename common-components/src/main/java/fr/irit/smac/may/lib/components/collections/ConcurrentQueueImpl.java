package fr.irit.smac.may.lib.components.collections;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

public class ConcurrentQueueImpl<Truc> extends Queue<Truc> {
	
	private final BlockingQueue<Truc> l = new LinkedBlockingQueue<Truc>();
	
	@Override
	public Push<Truc> make_put() {
		return new Push<Truc>() {
			public void push(Truc t) {
				l.offer(t);
			}
		};
	}

	@Override
	public Pull<Truc> make_get() {
		return new Pull<Truc>() {
			public Truc pull() {
				return l.poll();
			}
		};
	}
	
	@Override
	protected Pull<Collection<Truc>> make_getAll() {
		return new Pull<Collection<Truc>>() {
			public Collection<Truc> pull() {
				Collection<Truc> c = new LinkedList<Truc>();
				l.drainTo(c);
				return c;
			}
		};
	}
	
	public static void main(String[] args) {
		Queue.Component<String> t = Queue.createComponent(new ConcurrentQueueImpl<String>());
		t.put().push("a");
		System.out.println(t.get().pull());
	}
}