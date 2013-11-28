package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class CollectionInteger<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public MapGet<Integer,I> get();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Integer> size();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.components.meta.CollectionInteger.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.CollectionInteger.Component<I>, fr.irit.smac.may.lib.components.meta.CollectionInteger.Parts<I> {
    private final fr.irit.smac.may.lib.components.meta.CollectionInteger.Requires<I> bridge;
    
    private final CollectionInteger<I> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.get == null;
      this.get = this.implementation.make_get();
      assert this.size == null;
      this.size = this.implementation.make_size();
      
    }
    
    public ComponentImpl(final CollectionInteger<I> implem, final fr.irit.smac.may.lib.components.meta.CollectionInteger.Requires<I> b, final boolean initMakes) {
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
    
    private MapGet<Integer,I> get;
    
    public final MapGet<Integer,I> get() {
      return this.get;
    }
    
    private Pull<Integer> size;
    
    public final Pull<Integer> size() {
      return this.size;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Element<I> {
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
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<Integer> idx();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Provides<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Component<I>, fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Parts<I> {
      private final fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Requires<I> bridge;
      
      private final fr.irit.smac.may.lib.components.meta.CollectionInteger.Element<I> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.idx == null;
        this.idx = this.implementation.make_idx();
        assert this.stop == null;
        this.stop = this.implementation.make_stop();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.meta.CollectionInteger.Element<I> implem, final fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Requires<I> b, final boolean initMakes) {
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
      
      private Pull<Integer> idx;
      
      public final Pull<Integer> idx() {
        return this.idx;
      }
      
      private Do stop;
      
      public final Do stop() {
        return this.stop;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.ComponentImpl<I> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Provides<I> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull<Integer> make_idx();
    
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
    protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Requires<I> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Parts<I> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.Requires<I> b) {
      fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.meta.CollectionInteger.Element.ComponentImpl<I>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.meta.CollectionInteger.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.meta.CollectionInteger.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Provides<I> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract MapGet<Integer,I> make_get();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<Integer> make_size();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.CollectionInteger.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.meta.CollectionInteger.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.CollectionInteger.Requires<I> b) {
    fr.irit.smac.may.lib.components.meta.CollectionInteger.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.meta.CollectionInteger.ComponentImpl<I>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.meta.CollectionInteger.Element<I> make_Element();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.meta.CollectionInteger.Element<I> _createImplementationOfElement() {
    fr.irit.smac.may.lib.components.meta.CollectionInteger.Element<I> implem = make_Element();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.meta.CollectionInteger.Component<I> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.meta.CollectionInteger.Requires<I>() {});
  }
}
