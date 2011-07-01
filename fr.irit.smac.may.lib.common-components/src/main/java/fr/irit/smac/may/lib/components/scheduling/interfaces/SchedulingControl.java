package fr.irit.smac.may.lib.components.scheduling.interfaces;

public interface SchedulingControl {
	
	public void pause();
	
	public void setSlow();
	
	public void setFast();
	
	public void step();
}
