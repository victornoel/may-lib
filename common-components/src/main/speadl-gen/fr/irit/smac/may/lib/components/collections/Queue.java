package fr.irit.smac.may.lib.components.collections;

import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.Collection;

@SuppressWarnings("all")
public abstract class Queue<Truc> {
  public interface Requires<Truc> {
  }
  
  public interface Component<Truc> extends Queue.Provides<Truc> {
  }
  
  public interface Provides<Truc> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Truc> put();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Truc> get();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Collection<Truc>> getAll();
  }
  
  public interface Parts<Truc> {
  }
  
  public static class ComponentImpl<Truc> implements Queue.Component<Truc>, Queue.Parts<Truc> {
    private final Queue.Requires<Truc> bridge;
    
    private final Queue<Truc> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_put() {
      assert this.put == null: "This is a bug.";
      this.put = this.implementation.make_put();
      if (this.put == null) {
      	throw new RuntimeException("make_put() in fr.irit.smac.may.lib.components.collections.Queue<Truc> should not return null.");
      }
    }
    
    private void init_get() {
      assert this.get == null: "This is a bug.";
      this.get = this.implementation.make_get();
      if (this.get == null) {
      	throw new RuntimeException("make_get() in fr.irit.smac.may.lib.components.collections.Queue<Truc> should not return null.");
      }
    }
    
    private void init_getAll() {
      assert this.getAll == null: "This is a bug.";
      this.getAll = this.implementation.make_getAll();
      if (this.getAll == null) {
      	throw new RuntimeException("make_getAll() in fr.irit.smac.may.lib.components.collections.Queue<Truc> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_put();
      init_get();
      init_getAll();
    }
    
    public ComponentImpl(final Queue<Truc> implem, final Queue.Requires<Truc> b, final boolean doInits) {
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
    
    private Push<Truc> put;
    
    public Push<Truc> put() {
      return this.put;
    }
    
    private Pull<Truc> get;
    
    public Pull<Truc> get() {
      return this.get;
    }
    
    private Pull<Collection<Truc>> getAll;
    
    public Pull<Collection<Truc>> getAll() {
      return this.getAll;
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
  
  private Queue.ComponentImpl<Truc> selfComponent;
  
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
  protected Queue.Provides<Truc> provides() {
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
  protected abstract Push<Truc> make_put();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<Truc> make_get();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<Collection<Truc>> make_getAll();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Queue.Requires<Truc> requires() {
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
  protected Queue.Parts<Truc> parts() {
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
  public synchronized Queue.Component<Truc> _newComponent(final Queue.Requires<Truc> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Queue has already been used to create a component, use another one.");
    }
    this.init = true;
    Queue.ComponentImpl<Truc>  _comp = new Queue.ComponentImpl<Truc>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Queue.Component<Truc> newComponent() {
    return this._newComponent(new Queue.Requires<Truc>() {}, true);
  }
}
