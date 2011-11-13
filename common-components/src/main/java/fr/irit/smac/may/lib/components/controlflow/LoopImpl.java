package fr.irit.smac.may.lib.components.controlflow;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.interfaces.Do;

public class LoopImpl extends Loop {
	
	private final AtomicBoolean run = new AtomicBoolean(true);
	
	private void loop() {
		executor().execute(new Runnable() {
			public void run() {
				if (run.get()) {
					handler().doIt();
					loop();
				}
			}
		});
	}
	
	@Override
	protected Do stop() {
		return new Do() {
			public void doIt() {
				run.set(false);
			}
		};
	}

	@Override
	protected void start() {
		super.start();
		loop();
	}
}
