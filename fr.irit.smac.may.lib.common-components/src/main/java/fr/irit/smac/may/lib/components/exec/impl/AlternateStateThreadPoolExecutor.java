package fr.irit.smac.may.lib.components.exec.impl;

import java.util.concurrent.ArrayBlockingQueue;
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

	/*
	 * les parametres d'initialisation meritent d'etre affin�s, leur valeur est
	 * une valeur par d�faut
	 */
	public static final int CORE_POOL_SIZE = 5;
	public static final int MAX_POOL_SIZE = 50;
	public static final long KEEP_ALIVE_TIME = 100;
	public static final int BLOCKING_QUEUE_SIZE = 999;

	public static final int SLOW_SPEED = 100;
	public static final int FAST_SPEED = 0;

	public AlternateStateThreadPoolExecutor() {
		super(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
				TimeUnit.MICROSECONDS, new ArrayBlockingQueue<Runnable>(
						BLOCKING_QUEUE_SIZE));
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
				unpaused.await(SLOW_SPEED, TimeUnit.MILLISECONDS);
				break;
			case FAST:
				unpaused.await(FAST_SPEED, TimeUnit.MILLISECONDS);
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

}