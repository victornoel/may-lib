package fr.irit.smac.may.lib.components.scheduling;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;

public class ClockImpl extends Clock {

	private AtomicBoolean running = new AtomicBoolean(false);
	
	private void myrun(final int ms) {
		if (running.get()) {
			sched().execute(new Runnable() {
				public void run() {
					tick().doIt();
					try {
						Thread.sleep(ms);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myrun(ms);
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
				sched().execute(new Runnable() {
					public void run() {
						tick().doIt();
					}
				});
			}
			
			public void setSlow() {
				if (!running.getAndSet(true)) {
					myrun(1000);
				}
			}
			
			public void setFast() {
				if (!running.getAndSet(true)) {
					myrun(0);
				}
			}
		};
	}
}
