package fr.irit.smac.may.lib.components.scheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.interfaces.Do;

public class ExecutorServiceWrapperImpl extends ExecutorService {

	private final java.util.concurrent.ExecutorService service;

	public ExecutorServiceWrapperImpl(java.util.concurrent.ExecutorService service) {
		this.service = service;
	}
	
	@Override
	public AdvancedExecutor make_exec() {
		return new AdvancedExecutor() {
			public void execute(Runnable command) {
				service.execute(command);
			}

			public void executeAfter(final Runnable command, final long time) {
				if (service instanceof ScheduledExecutorService) {
					((ScheduledExecutorService) service).schedule(command, time, TimeUnit.MILLISECONDS);
				} else {
					// time of the first execution
					final long current = System.currentTimeMillis();
					execute(new Runnable() {
						public void run() {
							if (System.currentTimeMillis()>(current+time)) {
								// if it is time, run it
								command.run();
							} else {
								// else, try later
								execute(this);
							}
						}
					});
				}
			}
		};
	}
	
	public static void main(String[] args) {
		Component component = ExecutorService.newComponent(new ExecutorServiceWrapperImpl(Executors.newSingleThreadExecutor()));
		component.start();
		
		// this should not block even with one thread
		component.exec().executeAfter(new Runnable() {
			
			public void run() {
				System.out.println("should be second");
			}
		}, 2000);
		component.exec().executeAfter(new Runnable() {
			
			public void run() {
				System.out.println("should be first");
			}
		}, 1000);
	}

	@Override
	protected Do make_stop() {
		return new Do() {
			public void doIt() {
				service.shutdownNow();
			}
		};
	}
}
