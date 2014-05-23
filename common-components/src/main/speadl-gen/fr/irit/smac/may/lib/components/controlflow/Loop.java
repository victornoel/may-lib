package fr.irit.smac.may.lib.components.controlflow;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class Loop {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public AdvancedExecutor executor();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Do handler();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements Loop.Component, Loop.Parts {
    private final Loop.Requires bridge;
    
    private final Loop implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.stop == null: "This is a bug.";
      this.stop = this.implementation.make_stop();
      if (this.stop == null) {
      	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.controlflow.Loop should not return null.");
      }
      
    }
    
    public ComponentImpl(final Loop implem, final Loop.Requires b, final boolean doInits) {
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
    
    private Do stop;
    
    public Do stop() {
      return this.stop;
    }
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do stop();
  }
  
  public interface Component extends Loop.Provides {
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
  
  private Loop.ComponentImpl selfComponent;
  
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
  protected Loop.Provides provides() {
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
  protected abstract Do make_stop();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Loop.Requires requires() {
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
  protected Loop.Parts parts() {
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
  public synchronized Loop.Component _newComponent(final Loop.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Loop has already been used to create a component, use another one.");
    }
    this.init = true;
    Loop.ComponentImpl comp = new Loop.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
