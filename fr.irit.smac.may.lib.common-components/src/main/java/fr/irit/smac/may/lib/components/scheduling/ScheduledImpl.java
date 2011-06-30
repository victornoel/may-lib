package fr.irit.smac.may.lib.components.scheduling;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.interfaces.Do;

public class ScheduledImpl extends Scheduled {

	private final Set<AgentSide> agents = Collections.synchronizedSet(new HashSet<AgentSide>());
	
	public class AgentSide extends Scheduled.Agent {

		private FutureTask<?> currentTask;
		private AtomicBoolean run = new AtomicBoolean(true);

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
		
		private void tick() {
			if (run.get()) {
				currentTask = new FutureTask<Object>(new Runnable() {
					public void run() {
						try {
							cycle().doIt();
						} catch (Exception e) {
							System.err.println("Error when executing cycle in ScheduledImpl:");
							e.printStackTrace();
						}
					}
				}, null);
				sched().execute(currentTask);
			}
		}
	}

	@Override
	protected Do tick() {
		return new Do() {
			public void doIt() {
				synchronized (agents) {
					for(final AgentSide a: agents) {
						a.tick();
					}
				}
			}
		};
	}
}
