package fr.irit.smac.may.lib.components.scheduling;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;
import fr.irit.smac.may.lib.interfaces.Do;

public class SchedulerImpl extends Scheduler {
	
	// using a concurrent queue because there won't be a lot of producer during execution
	// (offer relies on a cas mechanism: retries until it works)
	// and on this class, iterator is ok with concurrent modifications
	private final ConcurrentLinkedQueue<ScheduledImpl> agents = new ConcurrentLinkedQueue<ScheduledImpl>();
	
	private class ScheduledImpl extends Scheduled {
		
		// if this is true, then the agents won't run ever again
		private final AtomicBoolean killed = new AtomicBoolean(false);
		
		// if true, the agent is already in a cycle loop
		private final AtomicBoolean meRunning = new AtomicBoolean(false);
		
		@Override
		protected void start() {
			super.start();
			agents.offer(this);

			if (running.get()) {
				startRunning(null);
			}
		}
		
		// this starts a new cycle loop
		// countdown must be null if running is true
		public void startRunning(final CountDownLatch countdown) {
			if (meRunning.compareAndSet(false, true)) {
				iterativeRun(countdown);
			} else {
				if (countdown != null) {
					countdown.countDown();
				}
			}
		}
		
		private void iterativeRun(final CountDownLatch countdown) {
			eco_requires().executor().executeAfter(new Runnable() {
				public void run() {
					if (!killed.get()) {
						try {
							requires().cycle().doIt();
						} catch (Throwable t) {
							System.err.println("Error when executing cycle in ScheduledImpl:");
							t.printStackTrace();
						}
						if (running.get()) {
							assert countdown == null;
							iterativeRun(null);
						} else {
							meRunning.set(false);
						}
					}
					if (countdown != null) {
						countdown.countDown();
					}
				}
			}, sleep);
		}
		
		@Override
		protected Do make_stop() {
			return new Do() {
				public void doIt() {
					killed.set(true);
					agents.remove(ScheduledImpl.this);
				}
			};
		}
	}
	
	private volatile int sleep = 0;
	
	// this is true when it is running in cycle loops
	private final AtomicBoolean running = new AtomicBoolean(false);
	
	private synchronized void step(boolean synchronous) {
		if (!running.get()) {
			sleep = 0;
			CountDownLatch countdown = null;
			if (synchronous) {
				countdown = new CountDownLatch(agents.size());
			}
			for(ScheduledImpl a: agents) {
				a.startRunning(countdown);
			}
			if (synchronous) {
				try {
					countdown.await();
				} catch (InterruptedException e) {
					// TODO what to do here?
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	protected SchedulingControl make_async() {
		
		return new SchedulingControl() {
			
			public void step() {
				step(false);
			}
			
			public void step(boolean synchronous) {
				SchedulerImpl.this.step(synchronous);
			}
			
			public void run(final int ms) {
				sleep = ms;
				if (running.compareAndSet(false, true)) {
					for(ScheduledImpl a: agents) {
						a.startRunning(null);
					}
				}
			}
			
			public void pause() {
				running.set(false);
			}
		};
	}
	
	@Override
	protected Do make_tick() {
		return new Do() {
			public void doIt() {
				step(true);
			}
		};
	}
	
	@Override
	protected Scheduled make_Scheduled() {
		return new ScheduledImpl();
	}
}
