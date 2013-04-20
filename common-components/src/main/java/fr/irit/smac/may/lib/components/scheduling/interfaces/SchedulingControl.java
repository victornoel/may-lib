package fr.irit.smac.may.lib.components.scheduling.interfaces;

public interface SchedulingControl {
	
	/**
	 * put the execution of the system on pause
	 */
	public void pause();

	/**
	 * let the system run indefinitely
	 * 
	 * @param ms how long to wait before each cycle
	 */
	public void run(int ms);
	
	/**
	 * let the system do one step (all agents run once)
	 * @param synchronous if true, step will return when the step is finished, else if it will return directly
	 */
	public void step(boolean synchronous);
	
	/**
	* prefer to use step(boolean)
	* this one do it in asynchronous mode
	*/
	@Deprecated
	public void step();
}
