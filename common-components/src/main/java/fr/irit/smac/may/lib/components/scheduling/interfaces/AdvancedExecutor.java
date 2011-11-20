package fr.irit.smac.may.lib.components.scheduling.interfaces;

import java.util.concurrent.Executor;

public interface AdvancedExecutor extends Executor {

	/**
	 * execute command after time has passed
	 * 
	 * @param command command to execute
	 * @param sleep time to wait before executing, in milliseconds
	 */
	void executeAfter(Runnable command, long time);
}
