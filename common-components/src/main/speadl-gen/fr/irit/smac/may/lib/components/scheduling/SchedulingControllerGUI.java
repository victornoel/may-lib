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
  public interface Component extends SchedulingControllerGUI.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements SchedulingControllerGUI.Component, SchedulingControllerGUI.Parts {
    private final SchedulingControllerGUI.Requires bridge;
    
    private final SchedulingControllerGUI implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final SchedulingControllerGUI implem, final SchedulingControllerGUI.Requires b, final boolean doInits) {
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
  
  private SchedulingControllerGUI.ComponentImpl selfComponent;
  
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
  protected SchedulingControllerGUI.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected SchedulingControllerGUI.Requires requires() {
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
  protected SchedulingControllerGUI.Parts parts() {
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
  public synchronized SchedulingControllerGUI.Component _newComponent(final SchedulingControllerGUI.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of SchedulingControllerGUI has already been used to create a component, use another one.");
    }
    this.init = true;
    SchedulingControllerGUI.ComponentImpl comp = new SchedulingControllerGUI.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
