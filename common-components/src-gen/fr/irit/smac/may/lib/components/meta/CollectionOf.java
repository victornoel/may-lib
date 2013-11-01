package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import java.util.Collection;

@SuppressWarnings("all")
public abstract class CollectionOf<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Collection<I>> get();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.components.meta.CollectionOf.Provides<I> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.CollectionOf.Parts<I>, fr.irit.smac.may.lib.components.meta.CollectionOf.Component<I> {
    private final fr.irit.smac.may.lib.components.meta.CollectionOf.Requires<I> bridge;
    
    private final CollectionOf<I> implementation;
    
    public ComponentImpl(final CollectionOf<I> implem, final fr.irit.smac.may.lib.components.meta.CollectionOf.Requires<I> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.get = implem.make_get();
      
    }
    
    private final Pull<Collection<I>> get;
    
    public final Pull<Collection<I>> get() {
      return this.get;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent<I> {
    @SuppressWarnings("all")
    public interface Requires<I> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public I p();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<I> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Provides<I> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Parts<I>, fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Component<I> {
      private final fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Requires<I> bridge;
      
      private final fr.irit.smac.may.lib.components.meta.CollectionOf.Agent<I> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.meta.CollectionOf.Agent<I> implem, final fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Requires<I> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.stop = implem.make_stop();
        
      }
      
      private final Do stop;
      
      public final Do stop() {
        return this.stop;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.ComponentImpl<I> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Provides<I> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Do make_stop();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Requires<I> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Parts<I> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.Requires<I> b) {
      return new fr.irit.smac.may.lib.components.meta.CollectionOf.Agent.ComponentImpl<I>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.meta.CollectionOf.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionOf.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionOf.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionOf.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.meta.CollectionOf.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.meta.CollectionOf.Provides<I> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<Collection<I>> make_get();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.CollectionOf.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.CollectionOf.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.meta.CollectionOf.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.CollectionOf.Requires<I> b) {
    return new fr.irit.smac.may.lib.components.meta.CollectionOf.ComponentImpl<I>(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.meta.CollectionOf.Agent<I> make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.meta.CollectionOf.Agent<I> _createImplementationOfAgent() {
    fr.irit.smac.may.lib.components.meta.CollectionOf.Agent<I> implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.meta.CollectionOf.Component<I> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.meta.CollectionOf.Requires<I>() {});
  }
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <I> fr.irit.smac.may.lib.components.meta.CollectionOf.Component<I> newComponent(final CollectionOf<I> _compo) {
    return _compo.newComponent();
  }
}
