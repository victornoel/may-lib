package fr.irit.smac.may.lib.components.scheduling;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;

public class ClockImpl extends Clock {

	private AtomicBoolean running = new AtomicBoolean(false);
	
	private volatile int sleep = 0;
	
	private void doOneStep() {
		synchronized (this) {
			tick().doIt();
		}
	}
	
	private void myrun() {
		if (running.get()) {
			sched().execute(new Runnable() {
				public void run() {
					final long start = System.currentTimeMillis();

					doOneStep();
					
					final long end = System.currentTimeMillis();
					final long diffWithSleep = sleep - (end - start);

					if (diffWithSleep > 0) {
						try {
							Thread.sleep(diffWithSleep);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					myrun();
				}
			});
		}
	}
	
	@Override
	protected SchedulingControl control() {
		return new SchedulingControl() {
			
			public void pause() {
				running.set(false);
			}
			
			public void step() {
				running.set(false);
				// TODO maybe wait for the previous one to finish before executing the step?
				sched().execute(new Runnable() {
					public void run() {
						doOneStep();
					}
				});
			}
			
			public void run(int ms) {
				sleep = ms;
				if (!running.getAndSet(true)) {
					myrun();
				}
			}
		};
	}
}
