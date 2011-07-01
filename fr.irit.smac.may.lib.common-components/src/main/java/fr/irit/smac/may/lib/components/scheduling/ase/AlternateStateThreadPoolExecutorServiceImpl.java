package fr.irit.smac.may.lib.components.scheduling.ase;

import java.util.concurrent.Executor;

import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;

public class AlternateStateThreadPoolExecutorServiceImpl extends
		AlternateStateExecutorService {

	private final AlternateStateThreadPoolExecutor exec;
	
	protected AlternateStateThreadPoolExecutor makeExec() {
		return new AlternateStateThreadPoolExecutor();
	}
	
	public AlternateStateThreadPoolExecutorServiceImpl() {
		this.exec = makeExec();
	}
	
	@Override
	protected Executor exec() {
		return new Executor() {
			public void execute(Runnable command) {
				exec.execute(command);
			}
		};
	}

	@Override
	protected SchedulingControl control() {
		return new SchedulingControl() {
			
			public void pause() {
				exec.pause();
			}
			
			public void step() {
				exec.nextStep();
			}
			
			public void setSlow() {
				exec.goSlow();
			}
			
			public void setFast() {
				exec.goFast();
			}
		};
	}
}
