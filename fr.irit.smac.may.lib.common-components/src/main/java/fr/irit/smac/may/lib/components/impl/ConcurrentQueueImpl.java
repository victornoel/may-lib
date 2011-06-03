package fr.irit.smac.may.lib.components.impl;

import java.util.concurrent.ConcurrentLinkedQueue;

import fr.irit.smac.may.lib.components.Queue;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

public class ConcurrentQueueImpl<Truc> extends Queue<Truc> {
	
	private final ConcurrentLinkedQueue<Truc> l = new ConcurrentLinkedQueue<Truc>();
	
	@Override
	public Push<Truc> put() {
		return new Push<Truc>() {
			public void push(Truc t) {
				l.offer(t);
			}
		};
	}

	@Override
	public Pull<Truc> get() {
		return new Pull<Truc>() {
			public Truc pull() {
				return l.poll();
			}
		};
	}
	
	public static void main(String[] args) {
		Queue.Component<String> t = new Queue.Component<String>(new ConcurrentQueueImpl<String>(), new Queue.Bridge<String>() {});
		t.put().push("a");
		System.out.println(t.get().pull());
	}
}