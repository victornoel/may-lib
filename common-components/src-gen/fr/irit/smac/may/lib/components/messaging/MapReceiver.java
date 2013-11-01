package fr.irit.smac.may.lib.components.messaging;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class MapReceiver<Msg, RealRef, Ref> {
  @SuppressWarnings("all")
  public interface Requires<Msg, RealRef, Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<Msg,RealRef> depositValue();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg, RealRef, Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg,Ref> depositKey();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Msg, RealRef, Ref> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg, RealRef, Ref> extends fr.irit.smac.may.lib.components.messaging.MapReceiver.Provides<Msg,RealRef,Ref> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg, RealRef, Ref> implements fr.irit.smac.may.lib.components.messaging.MapReceiver.Parts<Msg,RealRef,Ref>, fr.irit.smac.may.lib.components.messaging.MapReceiver.Component<Msg,RealRef,Ref> {
    private final fr.irit.smac.may.lib.components.messaging.MapReceiver.Requires<Msg,RealRef,Ref> bridge;
    
    private final MapReceiver<Msg,RealRef,Ref> implementation;
    
    public ComponentImpl(final MapReceiver<Msg,RealRef,Ref> implem, final fr.irit.smac.may.lib.components.messaging.MapReceiver.Requires<Msg,RealRef,Ref> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.depositKey = implem.make_depositKey();
      
    }
    
    private final Send<Msg,Ref> depositKey;
    
    public final Send<Msg,Ref> depositKey() {
      return this.depositKey;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent<Msg, RealRef, Ref> {
    @SuppressWarnings("all")
    public interface Requires<Msg, RealRef, Ref> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<RealRef> value();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<Ref> key();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<Msg, RealRef, Ref> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do disconnect();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<Msg, RealRef, Ref> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<Msg, RealRef, Ref> extends fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Provides<Msg,RealRef,Ref> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<Msg, RealRef, Ref> implements fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Parts<Msg,RealRef,Ref>, fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Component<Msg,RealRef,Ref> {
      private final fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Requires<Msg,RealRef,Ref> bridge;
      
      private final fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent<Msg,RealRef,Ref> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent<Msg,RealRef,Ref> implem, final fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Requires<Msg,RealRef,Ref> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.disconnect = implem.make_disconnect();
        
      }
      
      private final Do disconnect;
      
      public final Do disconnect() {
        return this.disconnect;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.ComponentImpl<Msg,RealRef,Ref> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Provides<Msg,RealRef,Ref> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Do make_disconnect();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Requires<Msg,RealRef,Ref> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Parts<Msg,RealRef,Ref> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Component<Msg,RealRef,Ref> newComponent(final fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.Requires<Msg,RealRef,Ref> b) {
      return new fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent.ComponentImpl<Msg,RealRef,Ref>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.messaging.MapReceiver.ComponentImpl<Msg,RealRef,Ref> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Provides<Msg,RealRef,Ref> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Requires<Msg,RealRef,Ref> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Parts<Msg,RealRef,Ref> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.messaging.MapReceiver.ComponentImpl<Msg,RealRef,Ref> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Provides<Msg,RealRef,Ref> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Send<Msg,Ref> make_depositKey();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Requires<Msg,RealRef,Ref> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.MapReceiver.Parts<Msg,RealRef,Ref> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.MapReceiver.Component<Msg,RealRef,Ref> newComponent(final fr.irit.smac.may.lib.components.messaging.MapReceiver.Requires<Msg,RealRef,Ref> b) {
    return new fr.irit.smac.may.lib.components.messaging.MapReceiver.ComponentImpl<Msg,RealRef,Ref>(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent<Msg,RealRef,Ref> make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent<Msg,RealRef,Ref> _createImplementationOfAgent() {
    fr.irit.smac.may.lib.components.messaging.MapReceiver.Agent<Msg,RealRef,Ref> implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
}
