package fr.irit.smac.may.lib.components.controlflow;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.collections.ConcurrentQueueImpl;
import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.interfaces.Push;

public class SequentialDispatcherImpl<Truc> extends SequentialDispatcher<Truc> {

	private AtomicBoolean working = new AtomicBoolean(false);
	
	@Override
	protected Push<Truc> make_dispatch() {
		return new Push<Truc>() {
			public void push(Truc t) {
				
				queue().put().push(t);
				
				if (working.compareAndSet(false, true)) {
					emptyQueue();
				}
			};
		};
	}

	private void emptyQueue() {
		final Truc truc = queue().get().pull();
		if (truc != null) {
			executor().execute(new Runnable() {
				public void run() {
					handler().push(truc);
					emptyQueue();
				}
			});
		} else {
			working.set(false);
		}
	}

	@Override
	protected Queue<Truc> make_queue() {
		return new ConcurrentQueueImpl<Truc>();
	}

}
