package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.interfaces.Transform;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.List;

@SuppressWarnings("all")
public abstract class IvyBroadcaster<T> {
  @SuppressWarnings("all")
  public interface Requires<T> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<String> ivyBindMsg();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<String> ivySend();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Transform<T,String> serializer();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Transform<String,T> deserializer();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<T> handle();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<List<String>> ivyReceive();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<T> send();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T> extends IvyBroadcaster.Provides<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T> implements IvyBroadcaster.Component<T>, IvyBroadcaster.Parts<T> {
    private final IvyBroadcaster.Requires<T> bridge;
    
    private final IvyBroadcaster<T> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.ivyReceive == null: "This is a bug.";
      this.ivyReceive = this.implementation.make_ivyReceive();
      if (this.ivyReceive == null) {
      	throw new RuntimeException("make_ivyReceive() in fr.irit.smac.may.lib.components.distribution.IvyBroadcaster should not return null.");
      }
      assert this.send == null: "This is a bug.";
      this.send = this.implementation.make_send();
      if (this.send == null) {
      	throw new RuntimeException("make_send() in fr.irit.smac.may.lib.components.distribution.IvyBroadcaster should not return null.");
      }
      
    }
    
    public ComponentImpl(final IvyBroadcaster<T> implem, final IvyBroadcaster.Requires<T> b, final boolean doInits) {
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
    
    private Push<List<String>> ivyReceive;
    
    public final Push<List<String>> ivyReceive() {
      return this.ivyReceive;
    }
    
    private Push<T> send;
    
    public final Push<T> send() {
      return this.send;
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
  
  private IvyBroadcaster.ComponentImpl<T> selfComponent;
  
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
  protected IvyBroadcaster.Provides<T> provides() {
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
  protected abstract Push<List<String>> make_ivyReceive();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<T> make_send();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected IvyBroadcaster.Requires<T> requires() {
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
  protected IvyBroadcaster.Parts<T> parts() {
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
  public synchronized IvyBroadcaster.Component<T> _newComponent(final IvyBroadcaster.Requires<T> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of IvyBroadcaster has already been used to create a component, use another one.");
    }
    this.init = true;
    IvyBroadcaster.ComponentImpl<T> comp = new IvyBroadcaster.ComponentImpl<T>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
