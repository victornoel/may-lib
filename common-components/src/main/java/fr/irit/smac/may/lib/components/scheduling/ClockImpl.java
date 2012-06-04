package fr.irit.smac.may.lib.components.scheduling;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;

public class ClockImpl extends Clock {

	private AtomicBoolean running = new AtomicBoolean(false);
	
	private volatile int sleep = 0;
	
	private void myrun() {
		if (running.get()) {
			sched().execute(new Runnable() {
				public void run() {
					tick().doIt();
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myrun();
				}
			});
		}
	}
	
	@Override
	protected SchedulingControl make_control() {
		return new SchedulingControl() {
			
			public void pause() {
				running.set(false);
			}
			
			public void step() {
				//running.set(false);
				// TODO maybe wait for the previous one to finish before executing the step?
				sched().execute(new Runnable() {
					public void run() {
						if (!running.getAndSet(true)) {
							tick().doIt();
							running.set(false);
						}
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
