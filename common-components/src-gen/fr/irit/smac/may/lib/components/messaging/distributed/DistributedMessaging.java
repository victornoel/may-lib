package fr.irit.smac.may.lib.components.messaging.distributed;

import fr.irit.smac.may.lib.components.messaging.distributed.DistRef;
import fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessage;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class DistributedMessaging<Msg, NodeRef> {
  @SuppressWarnings("all")
  public interface Requires<Msg, NodeRef> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Pull<NodeRef> myNode();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<Msg,DistRef<NodeRef>> deposit();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<DistributedMessage<Msg,NodeRef>,NodeRef> distOut();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg, NodeRef> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<DistRef<NodeRef>> generateRef();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg,DistRef<NodeRef>> send();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<DistributedMessage<Msg,NodeRef>> distIn();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Msg, NodeRef> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg, NodeRef> extends fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Provides<Msg,NodeRef> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg, NodeRef> implements fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Parts<Msg,NodeRef>, fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Component<Msg,NodeRef> {
    private final fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Requires<Msg,NodeRef> bridge;
    
    private final DistributedMessaging<Msg,NodeRef> implementation;
    
    public ComponentImpl(final DistributedMessaging<Msg,NodeRef> implem, final fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Requires<Msg,NodeRef> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.generateRef = implem.make_generateRef();
      this.send = implem.make_send();
      this.distIn = implem.make_distIn();
      
    }
    
    private final Pull<DistRef<NodeRef>> generateRef;
    
    public final Pull<DistRef<NodeRef>> generateRef() {
      return this.generateRef;
    }
    
    private final Send<Msg,DistRef<NodeRef>> send;
    
    public final Send<Msg,DistRef<NodeRef>> send() {
      return this.send;
    }
    
    private final Push<DistributedMessage<Msg,NodeRef>> distIn;
    
    public final Push<DistributedMessage<Msg,NodeRef>> distIn() {
      return this.distIn;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.ComponentImpl<Msg,NodeRef> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Provides<Msg,NodeRef> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<DistRef<NodeRef>> make_generateRef();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Send<Msg,DistRef<NodeRef>> make_send();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<DistributedMessage<Msg,NodeRef>> make_distIn();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Requires<Msg,NodeRef> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Parts<Msg,NodeRef> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Component<Msg,NodeRef> newComponent(final fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.Requires<Msg,NodeRef> b) {
    return new fr.irit.smac.may.lib.components.messaging.distributed.DistributedMessaging.ComponentImpl<Msg,NodeRef>(this, b);
  }
}
