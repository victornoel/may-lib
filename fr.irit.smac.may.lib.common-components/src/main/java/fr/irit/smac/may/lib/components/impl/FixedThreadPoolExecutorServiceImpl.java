package fr.irit.smac.may.lib.components.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.irit.smac.may.lib.components.ExecutorService;

public class FixedThreadPoolExecutorServiceImpl extends ExecutorService {

	private final java.util.concurrent.ExecutorService service;

	public FixedThreadPoolExecutorServiceImpl(int numberOfThread) {
		service = Executors.newFixedThreadPool(numberOfThread);
	}

	@Override
	public java.util.concurrent.Executor exec() {
		return new Executor() {
			public void execute(Runnable command) {
				service.execute(command);
			}
		};
	}
}
