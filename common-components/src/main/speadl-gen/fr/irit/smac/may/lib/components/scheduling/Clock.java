package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;
import fr.irit.smac.may.lib.interfaces.Do;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class Clock {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Executor sched();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Do tick();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public SchedulingControl control();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends Clock.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements Clock.Component, Clock.Parts {
    private final Clock.Requires bridge;
    
    private final Clock implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.control == null: "This is a bug.";
      this.control = this.implementation.make_control();
      if (this.control == null) {
      	throw new RuntimeException("make_control() in fr.irit.smac.may.lib.components.scheduling.Clock should not return null.");
      }
      
    }
    
    public ComponentImpl(final Clock implem, final Clock.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
      
    }
    
    private SchedulingControl control;
    
    public final SchedulingControl control() {
      return this.control;
    }
  }
  
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   */
  private boolean started = false;;
  
  private Clock.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
    
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected Clock.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract SchedulingControl make_control();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Clock.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected Clock.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Clock.Component _newComponent(final Clock.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Clock has already been used to create a component, use another one.");
    }
    this.init = true;
    Clock.ComponentImpl comp = new Clock.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
