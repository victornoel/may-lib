package fr.irit.smac.may.lib.classic.namedPublish;

import fr.irit.smac.may.lib.components.interactions.interfaces.Observe;
import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class ObserverBehaviour<Ref> {
  public interface Requires<Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Observe<Integer, Ref> observe();
  }
  
  public interface Component<Ref> extends ObserverBehaviour.Provides<Ref> {
  }
  
  public interface Provides<Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do cycle();
  }
  
  public interface Parts<Ref> {
  }
  
  public static class ComponentImpl<Ref> implements ObserverBehaviour.Component<Ref>, ObserverBehaviour.Parts<Ref> {
    private final ObserverBehaviour.Requires<Ref> bridge;
    
    private final ObserverBehaviour<Ref> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void init_cycle() {
      assert this.cycle == null: "This is a bug.";
      this.cycle = this.implementation.make_cycle();
      if (this.cycle == null) {
      	throw new RuntimeException("make_cycle() in fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour<Ref> shall not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_cycle();
    }
    
    public ComponentImpl(final ObserverBehaviour<Ref> implem, final ObserverBehaviour.Requires<Ref> b, final boolean doInits) {
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
    
    public Do cycle() {
      return this.cycle;
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
   * 
   */
  private boolean started = false;;
  
  private ObserverBehaviour.ComponentImpl<Ref> selfComponent;
  
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
  protected ObserverBehaviour.Provides<Ref> provides() {
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
  protected ObserverBehaviour.Requires<Ref> requires() {
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
  protected ObserverBehaviour.Parts<Ref> parts() {
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
  public synchronized ObserverBehaviour.Component<Ref> _newComponent(final ObserverBehaviour.Requires<Ref> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ObserverBehaviour has already been used to create a component, use another one.");
    }
    this.init = true;
    ObserverBehaviour.ComponentImpl<Ref>  _comp = new ObserverBehaviour.ComponentImpl<Ref>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
