package fr.irit.smac.may.lib.components.scheduling.interfaces;

public interface SchedulingControl {
	
	public void pause();
	
	// run every ms milliseconds
	public void run(int ms);
	
	public void step();
}
