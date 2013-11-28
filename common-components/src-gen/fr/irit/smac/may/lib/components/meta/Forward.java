package fr.irit.smac.may.lib.components.meta;

@SuppressWarnings("all")
public abstract class Forward<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public I forwardedPort();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.components.meta.Forward.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.Forward.Component<I>, fr.irit.smac.may.lib.components.meta.Forward.Parts<I> {
    private final fr.irit.smac.may.lib.components.meta.Forward.Requires<I> bridge;
    
    private final Forward<I> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final Forward<I> implem, final fr.irit.smac.may.lib.components.meta.Forward.Requires<I> b, final boolean initMakes) {
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
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Caller<I> {
    @SuppressWarnings("all")
    public interface Requires<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<I> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public I forwardedPort();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends fr.irit.smac.may.lib.components.meta.Forward.Caller.Provides<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<I>, fr.irit.smac.may.lib.components.meta.Forward.Caller.Parts<I> {
      private final fr.irit.smac.may.lib.components.meta.Forward.Caller.Requires<I> bridge;
      
      private final fr.irit.smac.may.lib.components.meta.Forward.Caller<I> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.forwardedPort == null;
        this.forwardedPort = this.implementation.make_forwardedPort();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.meta.Forward.Caller<I> implem, final fr.irit.smac.may.lib.components.meta.Forward.Caller.Requires<I> b, final boolean initMakes) {
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
      
      private I forwardedPort;
      
      public final I forwardedPort() {
        return this.forwardedPort;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.meta.Forward.Caller.ComponentImpl<I> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.meta.Forward.Caller.Provides<I> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract I make_forwardedPort();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.Forward.Caller.Requires<I> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.Forward.Caller.Parts<I> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.Forward.Caller.Requires<I> b) {
      fr.irit.smac.may.lib.components.meta.Forward.Caller.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.meta.Forward.Caller.ComponentImpl<I>(this, b, true);
      comp.implementation.start();
      return comp;
      
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
   * It will be called automatically after the component has been instantiated.
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
    fr.irit.smac.may.lib.components.meta.Forward.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.meta.Forward.ComponentImpl<I>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.meta.Forward.Caller<I> make_Caller();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.meta.Forward.Caller<I> _createImplementationOfCaller() {
    fr.irit.smac.may.lib.components.meta.Forward.Caller<I> implem = make_Caller();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<I> newCaller() {
    fr.irit.smac.may.lib.components.meta.Forward.Caller<I> implem = _createImplementationOfCaller();
    return implem.newComponent(new fr.irit.smac.may.lib.components.meta.Forward.Caller.Requires<I>() {});
  }
}
