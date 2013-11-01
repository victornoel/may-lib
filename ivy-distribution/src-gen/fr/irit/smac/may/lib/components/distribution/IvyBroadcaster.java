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
  public interface Parts<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T> extends fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Provides<T> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Parts<T>, fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Component<T> {
    private final fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Requires<T> bridge;
    
    private final IvyBroadcaster<T> implementation;
    
    public ComponentImpl(final IvyBroadcaster<T> implem, final fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Requires<T> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.ivyReceive = implem.make_ivyReceive();
      this.send = implem.make_send();
      
    }
    
    private final Push<List<String>> ivyReceive;
    
    public final Push<List<String>> ivyReceive() {
      return this.ivyReceive;
    }
    
    private final Push<T> send;
    
    public final Push<T> send() {
      return this.send;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.ComponentImpl<T> selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called after the component has been instantiated, after the components have been instantiated
   * and during the containing component start() method is called.
   * 
   */
  protected void start() {
    
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Provides<T> provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Requires<T> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Parts<T> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Component<T> newComponent(final fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Requires<T> b) {
    return new fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.ComponentImpl<T>(this, b);
  }
}
