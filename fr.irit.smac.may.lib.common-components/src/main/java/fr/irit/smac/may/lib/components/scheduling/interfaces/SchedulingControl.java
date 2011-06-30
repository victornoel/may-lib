package fr.irit.smac.may.lib.components.scheduling.interfaces;

public interface SchedulingControl {

	public void start();
	
	public void stop();
	
	public void setSlow();
	
	public void setFast();
	
	public void step();
}
