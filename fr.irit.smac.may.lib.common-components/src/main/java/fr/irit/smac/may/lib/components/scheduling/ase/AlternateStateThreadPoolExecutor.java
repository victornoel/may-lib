package fr.irit.smac.may.lib.components.scheduling.ase;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AlternateStateThreadPoolExecutor extends ThreadPoolExecutor {

	public enum State {
		RUN, PAUSE
	};

	private volatile State currentState = State.PAUSE;

	private final ReentrantLock pauseLock = new ReentrantLock();
	private final Condition unpaused = pauseLock.newCondition();

	private volatile int speed = 0;

	public AlternateStateThreadPoolExecutor() {
		this(5, Integer.MAX_VALUE, 100, TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>());
	}
	
	/**
	 * See {@link ThreadPoolExecutor} and {@link Executors} for example of values to use.
	 */
	public AlternateStateThreadPoolExecutor(int corePoolSize, int completedTaskCount, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, completedTaskCount, keepAliveTime, unit, workQueue);
		currentState = State.PAUSE;
	}

	public State getState() {
		return currentState;
	}

	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);

		pauseLock.lock();
		try {
			switch (currentState) {
			case PAUSE:
				unpaused.await();
				break;
			case RUN:
				break;
			}
		} catch (InterruptedException ie) {
			t.interrupt();
		} finally {
			pauseLock.unlock();
		}
	}

	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		pauseLock.lock();
		try {
			switch (currentState) {
			case RUN:
				unpaused.await(speed, TimeUnit.MILLISECONDS);
				break;
			default:

			}
		} catch (InterruptedException ie) {
		} finally {
			pauseLock.unlock();
		}
	}

	public void pause() {
		if (!currentState.equals(State.PAUSE)) {
			pauseLock.lock();
			try {
				currentState = State.PAUSE;
			} finally {
				pauseLock.unlock();
			}
		}
	}

	public void nextStep() {
		pauseLock.lock();
		try {
			unpaused.signalAll();
		} finally {
			pauseLock.unlock();
		}
	}

	public void go(int ms) {
		pauseLock.lock();
		try {
			currentState = State.RUN;
			speed = ms;
			unpaused.signalAll();
		} finally {
			pauseLock.unlock();
		}
	}

}