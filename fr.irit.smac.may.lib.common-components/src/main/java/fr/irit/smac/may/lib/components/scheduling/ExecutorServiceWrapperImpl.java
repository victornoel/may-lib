package fr.irit.smac.may.lib.components.scheduling;

import java.util.concurrent.Executor;

public class ExecutorServiceWrapperImpl extends ExecutorService {

	private final java.util.concurrent.ExecutorService service;

	public ExecutorServiceWrapperImpl(java.util.concurrent.ExecutorService service) {
		this.service = service;
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
