package fr.irit.smac.may.lib.components.controlflow;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.interfaces.Do;

public class LoopImpl extends Loop {
	
	private final AtomicBoolean run = new AtomicBoolean(true);
	private final long pause;
	
	public LoopImpl() {
		this(0);
	}
	
	/**
	 * 
	 * @param pause time to wait between calls to handler
	 */
	public LoopImpl(long pause) {
		this.pause = pause;
		
	}
	
	private void loop() {
		executor().executeAfter(new Runnable() {
			public void run() {
				if (run.get()) {
					handler().doIt();
					loop();
				}
			}
		},pause);
	}
	
	@Override
	protected Do make_stop() {
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
