package fr.irit.smac.may.lib.components.exec.impl;

import java.awt.FlowLayout;
import java.util.concurrent.Executor;

import javax.swing.JFrame;

import fr.irit.smac.may.lib.components.ExecutorService;

public class AlternateStateThreadPoolExecutorServiceImpl extends
		ExecutorService {

	private final AlternateStateThreadPoolExecutor exec = new AlternateStateThreadPoolExecutor();

	private final JFrame frame;
	
	public AlternateStateThreadPoolExecutorServiceImpl() {
		frame = new JFrame();
		frame.add(new SchedulerControlComponent(exec));
		frame.setLayout(new FlowLayout());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	protected void start() {
		super.start();
		frame.setVisible(true);
	}
}
