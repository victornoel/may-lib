package fr.irit.smac.may.lib.classic.namedPublish;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class ObservedBehaviour {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<Integer> changeValue();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements ObservedBehaviour.Component, ObservedBehaviour.Parts {
    private final ObservedBehaviour.Requires bridge;
    
    private final ObservedBehaviour implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.cycle == null: "This is a bug.";
      this.cycle = this.implementation.make_cycle();
      if (this.cycle == null) {
      	throw new RuntimeException("make_cycle() in fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour should not return null.");
      }
      
    }
    
    public ComponentImpl(final ObservedBehaviour implem, final ObservedBehaviour.Requires b, final boolean doInits) {
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
    
    private Do cycle;
    
    public final Do cycle() {
      return this.cycle;
    }
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do cycle();
  }
  
  public interface Component extends ObservedBehaviour.Provides {
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
  
  private ObservedBehaviour.ComponentImpl selfComponent;
  
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
  protected ObservedBehaviour.Provides provides() {
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
  protected abstract Do make_cycle();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ObservedBehaviour.Requires requires() {
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
  protected ObservedBehaviour.Parts parts() {
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
  public synchronized ObservedBehaviour.Component _newComponent(final ObservedBehaviour.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ObservedBehaviour has already been used to create a component, use another one.");
    }
    this.init = true;
    ObservedBehaviour.ComponentImpl comp = new ObservedBehaviour.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}