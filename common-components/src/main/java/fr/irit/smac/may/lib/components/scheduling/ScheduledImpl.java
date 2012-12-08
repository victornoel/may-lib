package fr.irit.smac.may.lib.components.scheduling;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.irit.smac.may.lib.interfaces.Do;

public class ScheduledImpl extends Scheduled {

	private final Set<AgentSide> agents = new HashSet<AgentSide>();
	
	private final ConcurrentLinkedQueue<AgentSide> newAgents = new ConcurrentLinkedQueue<AgentSide>();
	
	public class AgentSide extends Scheduled.Agent {

		private FutureTask<?> currentTask = null;
		
		private final AtomicBoolean run = new AtomicBoolean(true);
		
		@Override
		protected void start() {
			super.start();
			newAgents.add(AgentSide.this);
		}
		
		@Override
		protected Do stop() {
			return new Do() {
				public void doIt() {
					run.set(false);
				}
			};
		}
		
		private void tick() {
			currentTask = new FutureTask<Object>(new Runnable() {
				public void run() {
					if (run.get()) {
						cycle().doIt();
					}
				}
			}, null);
			sched().execute(currentTask);
		}
		
		private void waitForEnd() {
			if (this.currentTask != null && !this.currentTask.isCancelled() && !this.currentTask.isDone()) {
				try {
					this.currentTask.get();
				} catch (InterruptedException e) {
					// TODO what to do here?
					System.err.println("For an unknown reason, the thread runnig this task has been interrupted");
					e.printStackTrace();
				} catch (ExecutionException e) {
					System.err.println("Error when executing cycle in ScheduledImpl:");
					e.getCause().printStackTrace();
				} catch (CancellationException e) {
					System.err.println("Strangely, the task was canceled but we entered the condition...");
					e.printStackTrace();
				} finally {
					this.currentTask = null;
				}
			}
		}
	}

	@Override
	protected Do tick() {
		return new Do() {
			public void doIt() {
				while (!newAgents.isEmpty()) {
					agents.add(newAgents.poll());
				}
				// start agents
				Iterator<AgentSide> it = agents.iterator();
				while(it.hasNext()) {
					AgentSide a = it.next();
					if (a.run.get()) a.tick();
					else it.remove();
				}
				// wait for all of them to finish before giving back control
				for(AgentSide a: agents) {
					a.waitForEnd();
				}
			}
		};
	}
}
