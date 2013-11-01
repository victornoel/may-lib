package fr.irit.smac.may.lib.components.meta;

@SuppressWarnings("all")
public abstract class Forward<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public I i();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.components.meta.Forward.Provides<I> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.Forward.Parts<I>, fr.irit.smac.may.lib.components.meta.Forward.Component<I> {
    private final fr.irit.smac.may.lib.components.meta.Forward.Requires<I> bridge;
    
    private final Forward<I> implementation;
    
    public ComponentImpl(final Forward<I> implem, final fr.irit.smac.may.lib.components.meta.Forward.Requires<I> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent<I> {
    @SuppressWarnings("all")
    public interface Requires<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<I> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public I a();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends fr.irit.smac.may.lib.components.meta.Forward.Agent.Provides<I> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.Forward.Agent.Parts<I>, fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<I> {
      private final fr.irit.smac.may.lib.components.meta.Forward.Agent.Requires<I> bridge;
      
      private final fr.irit.smac.may.lib.components.meta.Forward.Agent<I> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.meta.Forward.Agent<I> implem, final fr.irit.smac.may.lib.components.meta.Forward.Agent.Requires<I> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.a = implem.make_a();
        
      }
      
      private final I a;
      
      public final I a() {
        return this.a;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.meta.Forward.Agent.ComponentImpl<I> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.meta.Forward.Agent.Provides<I> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract I make_a();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.Forward.Agent.Requires<I> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.Forward.Agent.Parts<I> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.Forward.Agent.Requires<I> b) {
      return new fr.irit.smac.may.lib.components.meta.Forward.Agent.ComponentImpl<I>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.meta.Forward.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.Forward.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.Forward.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.Forward.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.meta.Forward.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.meta.Forward.Provides<I> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.Forward.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.Forward.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.meta.Forward.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.Forward.Requires<I> b) {
    return new fr.irit.smac.may.lib.components.meta.Forward.ComponentImpl<I>(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.meta.Forward.Agent<I> make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.meta.Forward.Agent<I> _createImplementationOfAgent() {
    fr.irit.smac.may.lib.components.meta.Forward.Agent<I> implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<I> newAgent() {
    fr.irit.smac.may.lib.components.meta.Forward.Agent<I> implem = _createImplementationOfAgent();
    return implem.newComponent(new fr.irit.smac.may.lib.components.meta.Forward.Agent.Requires<I>() {});
  }
}
