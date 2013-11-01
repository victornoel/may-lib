package fr.irit.smac.may.lib.components.messaging;

import fr.irit.smac.may.lib.interfaces.Broadcast;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class Broadcaster<T, Ref> {
  @SuppressWarnings("all")
  public interface Requires<T, Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<T,Ref> deposit();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<T, Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<T> broadcast();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Ref> add();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Ref> remove();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T, Ref> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T, Ref> extends fr.irit.smac.may.lib.components.messaging.Broadcaster.Provides<T,Ref> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T, Ref> implements fr.irit.smac.may.lib.components.messaging.Broadcaster.Parts<T,Ref>, fr.irit.smac.may.lib.components.messaging.Broadcaster.Component<T,Ref> {
    private final fr.irit.smac.may.lib.components.messaging.Broadcaster.Requires<T,Ref> bridge;
    
    private final Broadcaster<T,Ref> implementation;
    
    public ComponentImpl(final Broadcaster<T,Ref> implem, final fr.irit.smac.may.lib.components.messaging.Broadcaster.Requires<T,Ref> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.broadcast = implem.make_broadcast();
      this.add = implem.make_add();
      this.remove = implem.make_remove();
      
    }
    
    private final Push<T> broadcast;
    
    public final Push<T> broadcast() {
      return this.broadcast;
    }
    
    private final Push<Ref> add;
    
    public final Push<Ref> add() {
      return this.add;
    }
    
    private final Push<Ref> remove;
    
    public final Push<Ref> remove() {
      return this.remove;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent<T, Ref> {
    @SuppressWarnings("all")
    public interface Requires<T, Ref> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, Ref> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Broadcast<T> bc();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, Ref> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, Ref> extends fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Provides<T,Ref> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, Ref> implements fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Parts<T,Ref>, fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Component<T,Ref> {
      private final fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Requires<T,Ref> bridge;
      
      private final fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent<T,Ref> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent<T,Ref> implem, final fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Requires<T,Ref> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.bc = implem.make_bc();
        
      }
      
      private final Broadcast<T> bc;
      
      public final Broadcast<T> bc() {
        return this.bc;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.ComponentImpl<T,Ref> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Provides<T,Ref> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Broadcast<T> make_bc();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Requires<T,Ref> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Parts<T,Ref> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Component<T,Ref> newComponent(final fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Requires<T,Ref> b) {
      return new fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.ComponentImpl<T,Ref>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.messaging.Broadcaster.ComponentImpl<T,Ref> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Provides<T,Ref> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Requires<T,Ref> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Parts<T,Ref> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.messaging.Broadcaster.ComponentImpl<T,Ref> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Provides<T,Ref> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<T> make_broadcast();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<Ref> make_add();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<Ref> make_remove();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Requires<T,Ref> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Parts<T,Ref> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.Broadcaster.Component<T,Ref> newComponent(final fr.irit.smac.may.lib.components.messaging.Broadcaster.Requires<T,Ref> b) {
    return new fr.irit.smac.may.lib.components.messaging.Broadcaster.ComponentImpl<T,Ref>(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent<T,Ref> make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent<T,Ref> _createImplementationOfAgent() {
    fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent<T,Ref> implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Component<T,Ref> newAgent() {
    fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent<T,Ref> implem = _createImplementationOfAgent();
    return implem.newComponent(new fr.irit.smac.may.lib.components.messaging.Broadcaster.Agent.Requires<T,Ref>() {});
  }
}
