package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;

@SuppressWarnings("all")
public abstract class SchedulingControllerGUI {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public SchedulingControl control();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Component, fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Parts {
    private final fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Requires bridge;
    
    private final SchedulingControllerGUI implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final SchedulingControllerGUI implem, final fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Requires b, final boolean initMakes) {
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
  }
  
  
  private fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Requires b) {
    fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.ComponentImpl comp = new fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
