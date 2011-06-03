package fr.irit.smac.may.lib.components.impl;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.Queue;
import fr.irit.smac.may.lib.components.SequentialDispatcher;
import fr.irit.smac.may.lib.interfaces.Push;

public class SequentialDispatcherImpl<Truc> extends SequentialDispatcher<Truc> {

	private AtomicBoolean working = new AtomicBoolean(false);
	
	@Override
	protected Push<Truc> dispatch() {
		return new Push<Truc>() {
			public void push(Truc t) {
				
				queue().put().push(t);
				
				if (working.compareAndSet(false, true)) {
					executor().execute(new Runnable() {
						public void run() {
							Truc truc = queue().get().pull();
							while (truc != null) {
								handler().push(truc);
								truc = queue().get().pull();
							}
							working.set(false);
						}
					});
				}
				
			};
		};
	}

	@Override
	protected Queue<Truc> make_queue() {
		return new ConcurrentQueueImpl<Truc>();
	}

}
