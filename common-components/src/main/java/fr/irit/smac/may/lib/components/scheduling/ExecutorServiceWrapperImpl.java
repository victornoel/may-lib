package fr.irit.smac.may.lib.components.scheduling;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.interfaces.Do;

public class ExecutorServiceWrapperImpl extends ExecutorServiceWrapper {

	private final java.util.concurrent.ExecutorService service;

	public ExecutorServiceWrapperImpl(java.util.concurrent.ExecutorService service) {
		this.service = service;
	}
	
	@Override
	public AdvancedExecutor make_executor() {
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

	@Override
	protected Do make_stop() {
		return new Do() {
			public void doIt() {
				service.shutdownNow();
			}
		};
	}
	
	private class ExecutingImpl extends Executing {

		private final AtomicBoolean run = new AtomicBoolean(true);

		private final Set<Future<?>> s = new HashSet<Future<?>>();

		@Override
		protected AdvancedExecutor make_executor() {
			return new AdvancedExecutor() {
				public void execute(final Runnable command) {
					executeAfter(command,0);
				}

				public void executeAfter(final Runnable command, long time) {
					if (run.get()) {
						class MyRunnable implements Runnable {
							private Future<?> f;

							public void run() {
								try {
									if (run.get()) {
										command.run();
									}
								} catch (Exception e) {
									System.err.println("Error when executing task in SchedulerImpl:");
									e.printStackTrace();
								}
								s.remove(f);
							}
						}
						MyRunnable mr = new MyRunnable();
						final FutureTask<?> f = new FutureTask<Object>(mr, null);
						mr.f = f;
						s.add(f);
						eco_provides().executor().executeAfter(f,time);
					}
				}
			};

		}

		@Override
		protected Do make_stop() {
			return new Do() {
				public void doIt() {
					run.set(false);
					for (Future<?> f : s) {
						if (!f.isDone())
							f.cancel(true);
					}
					s.clear();
				}
			};
		}
	}
	
	@Override
	protected Executing make_Executing() {
		return new ExecutingImpl();
	}
	
	
	
	public static void main(String[] args) {
		Component component = new ExecutorServiceWrapperImpl(Executors.newSingleThreadExecutor()).newComponent();
		
		// this should not block even with one thread
		component.executor().executeAfter(new Runnable() {
			
			public void run() {
				System.out.println("should be second");
			}
		}, 2000);
		component.executor().executeAfter(new Runnable() {
			
			public void run() {
				System.out.println("should be first");
			}
		}, 1000);
	}
}
