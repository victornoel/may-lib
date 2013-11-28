package fr.irit.smac.may.lib.components.remote.messaging.receiver;

import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class RemoteReceiver<Msg, LocalRef> {
  @SuppressWarnings("all")
  public interface Requires<Msg, LocalRef> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<Msg,LocalRef> localDeposit();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Pull<Place> myPlace();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg, LocalRef> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg,RemoteAgentRef> deposit();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg, LocalRef> extends fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Provides<Msg,LocalRef> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Msg, LocalRef> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg, LocalRef> implements fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Component<Msg,LocalRef>, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Parts<Msg,LocalRef> {
    private final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Requires<Msg,LocalRef> bridge;
    
    private final RemoteReceiver<Msg,LocalRef> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.deposit == null;
      this.deposit = this.implementation.make_deposit();
      
    }
    
    public ComponentImpl(final RemoteReceiver<Msg,LocalRef> implem, final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Requires<Msg,LocalRef> b, final boolean initMakes) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (initMakes) {
      	initParts();
      	initProvidedPorts();
      }
      
    }
    
    private Send<Msg,RemoteAgentRef> deposit;
    
    public final Send<Msg,RemoteAgentRef> deposit() {
      return this.deposit;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent<Msg, LocalRef> {
    @SuppressWarnings("all")
    public interface Requires<Msg, LocalRef> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<LocalRef> localMe();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<Msg, LocalRef> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<RemoteAgentRef> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do disconnect();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<Msg, LocalRef> extends fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Provides<Msg,LocalRef> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<Msg, LocalRef> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<Msg, LocalRef> implements fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Component<Msg,LocalRef>, fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Parts<Msg,LocalRef> {
      private final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Requires<Msg,LocalRef> bridge;
      
      private final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent<Msg,LocalRef> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.me == null;
        this.me = this.implementation.make_me();
        assert this.disconnect == null;
        this.disconnect = this.implementation.make_disconnect();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent<Msg,LocalRef> implem, final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Requires<Msg,LocalRef> b, final boolean initMakes) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        // prevent them to be called twice if we are in
        // a specialized component: only the last of the
        // hierarchy will call them after everything is initialised
        if (initMakes) {
        	initParts();
        	initProvidedPorts();
        }
        
      }
      
      private Pull<RemoteAgentRef> me;
      
      public final Pull<RemoteAgentRef> me() {
        return this.me;
      }
      
      private Do disconnect;
      
      public final Do disconnect() {
        return this.disconnect;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.ComponentImpl<Msg,LocalRef> selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called automatically after the component has been instantiated.
     * 
     */
    protected void start() {
      
    }
    
    /**
     * This can be called by the implementation to access the provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Provides<Msg,LocalRef> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull<RemoteAgentRef> make_me();
    
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
    protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Requires<Msg,LocalRef> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Parts<Msg,LocalRef> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Component<Msg,LocalRef> newComponent(final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Requires<Msg,LocalRef> b) {
      fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.ComponentImpl<Msg,LocalRef> comp = new fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.ComponentImpl<Msg,LocalRef>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.ComponentImpl<Msg,LocalRef> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Provides<Msg,LocalRef> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Requires<Msg,LocalRef> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Parts<Msg,LocalRef> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.ComponentImpl<Msg,LocalRef> selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Provides<Msg,LocalRef> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Send<Msg,RemoteAgentRef> make_deposit();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Requires<Msg,LocalRef> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Parts<Msg,LocalRef> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Component<Msg,LocalRef> newComponent(final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Requires<Msg,LocalRef> b) {
    fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.ComponentImpl<Msg,LocalRef> comp = new fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.ComponentImpl<Msg,LocalRef>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent<Msg,LocalRef> make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent<Msg,LocalRef> _createImplementationOfAgent() {
    fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent<Msg,LocalRef> implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
}
