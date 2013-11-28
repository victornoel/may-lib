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
  public interface Component extends fr.irit.smac.may.lib.components.scheduling.Clock.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.Clock.Component, fr.irit.smac.may.lib.components.scheduling.Clock.Parts {
    private final fr.irit.smac.may.lib.components.scheduling.Clock.Requires bridge;
    
    private final Clock implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.control == null;
      this.control = this.implementation.make_control();
      
    }
    
    public ComponentImpl(final Clock implem, final fr.irit.smac.may.lib.components.scheduling.Clock.Requires b, final boolean initMakes) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (initMakes) {
      	initParts();
      	initProvidedPorts();
      }
      
    }
    
    private SchedulingControl control;
    
    public final SchedulingControl control() {
      return this.control;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.scheduling.Clock.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.Clock.Provides provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.components.scheduling.Clock.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.Clock.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.Clock.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.Clock.Requires b) {
    fr.irit.smac.may.lib.components.scheduling.Clock.ComponentImpl comp = new fr.irit.smac.may.lib.components.scheduling.Clock.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
