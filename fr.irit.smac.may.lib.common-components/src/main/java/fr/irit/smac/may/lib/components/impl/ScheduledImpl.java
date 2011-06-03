package fr.irit.smac.may.lib.components.impl;

import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.components.Scheduled;
import fr.irit.smac.may.lib.interfaces.Do;

public class ScheduledImpl extends Scheduled {

	public class AgentSide extends Scheduled.Agent {

		private FutureTask<?> currentTask;
		private AtomicBoolean run = new AtomicBoolean(true);

		@Override
		protected void start() {
			if (run.get()) {
				currentTask = new FutureTask<Object>(new Runnable() {
					public void run() {
						try {
							cycle().doIt();
						} catch (Exception e) {
							System.err.println("Error when executing cycle in ScheduledImpl:");
							e.printStackTrace();
						}
						start();
					}
				}, null);
				sched().execute(currentTask);
			}
		}

		@Override
		protected Do stop() {
			return new Do() {
				public void doIt() {
					run.set(false);
					if (currentTask != null && !currentTask.isDone())
						currentTask.cancel(true);
				}
			};
		}
	}
}
