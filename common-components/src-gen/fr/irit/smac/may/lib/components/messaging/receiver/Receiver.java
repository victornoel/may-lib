package fr.irit.smac.may.lib.components.messaging.receiver;

import fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSend;
import fr.irit.smac.may.lib.components.messaging.receiver.AgentRef;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class Receiver<MsgType> {
  @SuppressWarnings("all")
  public interface Requires<MsgType> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<MsgType> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableSend<MsgType,AgentRef> deposit();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<MsgType> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<MsgType> extends fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Provides<MsgType> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<MsgType> implements fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Parts<MsgType>, fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Component<MsgType> {
    private final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Requires<MsgType> bridge;
    
    private final Receiver<MsgType> implementation;
    
    public ComponentImpl(final Receiver<MsgType> implem, final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Requires<MsgType> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.deposit = implem.make_deposit();
      
    }
    
    private final ReliableSend<MsgType,AgentRef> deposit;
    
    public final ReliableSend<MsgType,AgentRef> deposit() {
      return this.deposit;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent<MsgType> {
    @SuppressWarnings("all")
    public interface Requires<MsgType> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Push<MsgType> put();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<MsgType> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<AgentRef> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableSend<MsgType,AgentRef> send();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<MsgType> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<MsgType> extends fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Provides<MsgType> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<MsgType> implements fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Parts<MsgType>, fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Component<MsgType> {
      private final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Requires<MsgType> bridge;
      
      private final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent<MsgType> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent<MsgType> implem, final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Requires<MsgType> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.me = implem.make_me();
        this.stop = implem.make_stop();
        this.send = implem.make_send();
        
      }
      
      private final Pull<AgentRef> me;
      
      public final Pull<AgentRef> me() {
        return this.me;
      }
      
      private final Do stop;
      
      public final Do stop() {
        return this.stop;
      }
      
      private final ReliableSend<MsgType,AgentRef> send;
      
      public final ReliableSend<MsgType,AgentRef> send() {
        return this.send;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.ComponentImpl<MsgType> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Provides<MsgType> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull<AgentRef> make_me();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Do make_stop();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract ReliableSend<MsgType,AgentRef> make_send();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Requires<MsgType> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Parts<MsgType> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Component<MsgType> newComponent(final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Requires<MsgType> b) {
      return new fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.ComponentImpl<MsgType>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.messaging.receiver.Receiver.ComponentImpl<MsgType> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Provides<MsgType> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Requires<MsgType> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Parts<MsgType> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.messaging.receiver.Receiver.ComponentImpl<MsgType> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Provides<MsgType> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract ReliableSend<MsgType,AgentRef> make_deposit();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Requires<MsgType> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Parts<MsgType> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Component<MsgType> newComponent(final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Requires<MsgType> b) {
    return new fr.irit.smac.may.lib.components.messaging.receiver.Receiver.ComponentImpl<MsgType>(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent<MsgType> make_Agent(final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent<MsgType> _createImplementationOfAgent(final String name) {
    fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent<MsgType> implem = make_Agent(name);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Component<MsgType> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Requires<MsgType>() {});
  }
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <MsgType> fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Component<MsgType> newComponent(final Receiver<MsgType> _compo) {
    return _compo.newComponent();
  }
}
