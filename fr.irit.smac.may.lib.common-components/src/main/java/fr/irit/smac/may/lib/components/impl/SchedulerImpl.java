package fr.irit.smac.may.lib.components.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.Scheduler;
import fr.irit.smac.may.lib.interfaces.Do;

/**
 * Will schedule as much as runnable as needed Stop will stop any runnable not
 * finished
 */
public class SchedulerImpl extends Scheduler {

	public class AgentSide extends Scheduler.Agent {

		private AtomicBoolean run = new AtomicBoolean(true);

		private final Set<Future<?>> s = new HashSet<Future<?>>();

		@Override
		protected Executor sched() {
			return new Executor() {
				public void execute(final Runnable command) {
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
						infraSched().execute(f);
					}
				}
			};

		}

		@Override
		protected Do stop() {
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

}
