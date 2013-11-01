package fr.irit.smac.may.lib.components.messaging.distributed;

import fr.irit.smac.may.lib.components.messaging.distributed.DistributedInfo;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class DistributedCommunication<T> {
  @SuppressWarnings("all")
  public interface Requires<T> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<T> out();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<DistributedInfo<T>> broadcast();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<String> nodeName();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<T,String> in();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<DistributedInfo<T>> handle();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T> extends fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Provides<T> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Parts<T>, fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Component<T> {
    private final fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Requires<T> bridge;
    
    private final DistributedCommunication<T> implementation;
    
    public ComponentImpl(final DistributedCommunication<T> implem, final fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Requires<T> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.nodeName = implem.make_nodeName();
      this.in = implem.make_in();
      this.handle = implem.make_handle();
      
    }
    
    private final Pull<String> nodeName;
    
    public final Pull<String> nodeName() {
      return this.nodeName;
    }
    
    private final Send<T,String> in;
    
    public final Send<T,String> in() {
      return this.in;
    }
    
    private final Push<DistributedInfo<T>> handle;
    
    public final Push<DistributedInfo<T>> handle() {
      return this.handle;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.ComponentImpl<T> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Provides<T> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<String> make_nodeName();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Send<T,String> make_in();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<DistributedInfo<T>> make_handle();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Requires<T> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Parts<T> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Component<T> newComponent(final fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.Requires<T> b) {
    return new fr.irit.smac.may.lib.components.messaging.distributed.DistributedCommunication.ComponentImpl<T>(this, b);
  }
}
