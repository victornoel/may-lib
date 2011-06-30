package fr.irit.smac.may.lib.components.exec.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AlternateStateThreadPoolExecutor extends ThreadPoolExecutor {

	public enum STATE {
		FAST, SLOW, PAUSE
	};

	private volatile STATE currentState = STATE.PAUSE;

	private final ReentrantLock pauseLock = new ReentrantLock();
	private final Condition unpaused = pauseLock.newCondition();

	private volatile int slowSpeed = 100;
	private volatile int fastSpeed = 0;

	public AlternateStateThreadPoolExecutor() {
		this(5, Integer.MAX_VALUE, 100, TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>());
	}
	
	/**
	 * See {@link ThreadPoolExecutor} and {@link Executors} for example of values to use.
	 */
	public AlternateStateThreadPoolExecutor(int corePoolSize, int completedTaskCount, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, completedTaskCount, keepAliveTime, unit, workQueue);
		currentState = STATE.PAUSE;
	}

	public STATE getState() {
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
			case SLOW:
			case FAST:
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
			case SLOW:
				unpaused.await(getSlowSpeed(), TimeUnit.MILLISECONDS);
				break;
			case FAST:
				unpaused.await(getFastSpeed(), TimeUnit.MILLISECONDS);
				break;
			default:

			}
		} catch (InterruptedException ie) {
		} finally {
			pauseLock.unlock();
		}
	}

	public void pause() {
		if (!currentState.equals(STATE.PAUSE)) {
			pauseLock.lock();
			try {
				currentState = STATE.PAUSE;
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

	public void goSlow() {
		if (!currentState.equals(STATE.SLOW)) {
			pauseLock.lock();
			try {
				currentState = STATE.SLOW;
				unpaused.signalAll();
			} finally {
				pauseLock.unlock();
			}
		}
	}

	public void goFast() {
		if (!currentState.equals(STATE.FAST)) {
			pauseLock.lock();
			try {
				currentState = STATE.FAST;
				unpaused.signalAll();
			} finally {
				pauseLock.unlock();
			}
		}
	}

	public void setFastSpeed(int fastSpeed) {
		this.fastSpeed = fastSpeed;
	}

	public int getFastSpeed() {
		return fastSpeed;
	}

	public void setSlowSpeed(int slowSpeed) {
		this.slowSpeed = slowSpeed;
	}

	public int getSlowSpeed() {
		return slowSpeed;
	}

}