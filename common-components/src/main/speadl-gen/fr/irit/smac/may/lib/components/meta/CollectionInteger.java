package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class CollectionInteger<I> {
  public interface Requires<I> {
  }
  
  public interface Component<I> extends CollectionInteger.Provides<I> {
  }
  
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public MapGet<Integer, I> get();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Integer> size();
  }
  
  public interface Parts<I> {
  }
  
  public static class ComponentImpl<I> implements CollectionInteger.Component<I>, CollectionInteger.Parts<I> {
    private final CollectionInteger.Requires<I> bridge;
    
    private final CollectionInteger<I> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_get() {
      assert this.get == null: "This is a bug.";
      this.get = this.implementation.make_get();
      if (this.get == null) {
      	throw new RuntimeException("make_get() in fr.irit.smac.may.lib.components.meta.CollectionInteger<I> should not return null.");
      }
    }
    
    private void init_size() {
      assert this.size == null: "This is a bug.";
      this.size = this.implementation.make_size();
      if (this.size == null) {
      	throw new RuntimeException("make_size() in fr.irit.smac.may.lib.components.meta.CollectionInteger<I> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_get();
      init_size();
    }
    
    public ComponentImpl(final CollectionInteger<I> implem, final CollectionInteger.Requires<I> b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    private MapGet<Integer, I> get;
    
    public MapGet<Integer, I> get() {
      return this.get;
    }
    
    private Pull<Integer> size;
    
    public Pull<Integer> size() {
      return this.size;
    }
  }
  
  public abstract static class Element<I> {
    public interface Requires<I> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public I forwardedPort();
    }
    
    public interface Component<I> extends CollectionInteger.Element.Provides<I> {
    }
    
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
    
    public interface Parts<I> {
    }
    
    public static class ComponentImpl<I> implements CollectionInteger.Element.Component<I>, CollectionInteger.Element.Parts<I> {
      private final CollectionInteger.Element.Requires<I> bridge;
      
      private final CollectionInteger.Element<I> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_idx() {
        assert this.idx == null: "This is a bug.";
        this.idx = this.implementation.make_idx();
        if (this.idx == null) {
        	throw new RuntimeException("make_idx() in fr.irit.smac.may.lib.components.meta.CollectionInteger$Element<I> should not return null.");
        }
      }
      
      private void init_stop() {
        assert this.stop == null: "This is a bug.";
        this.stop = this.implementation.make_stop();
        if (this.stop == null) {
        	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.meta.CollectionInteger$Element<I> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_idx();
        init_stop();
      }
      
      public ComponentImpl(final CollectionInteger.Element<I> implem, final CollectionInteger.Element.Requires<I> b, final boolean doInits) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null: "This is a bug.";
        implem.selfComponent = this;
        
        // prevent them to be called twice if we are in
        // a specialized component: only the last of the
        // hierarchy will call them after everything is initialised
        if (doInits) {
        	initParts();
        	initProvidedPorts();
        }
      }
      
      private Pull<Integer> idx;
      
      public Pull<Integer> idx() {
        return this.idx;
      }
      
      private Do stop;
      
      public Do stop() {
        return this.stop;
      }
    }
    
    /**
     * Used to check that two components are not created from the same implementation,
     * that the component has been started to call requires(), provides() and parts()
     * and that the component is not started by hand.
     * 
     */
    private boolean init = false;;
    
    /**
     * Used to check that the component is not started by hand.
     * 
     */
    private boolean started = false;;
    
    private CollectionInteger.Element.ComponentImpl<I> selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called automatically after the component has been instantiated.
     * 
     */
    protected void start() {
      if (!this.init || this.started) {
      	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
      }
    }
    
    /**
     * This can be called by the implementation to access the provided ports.
     * 
     */
    protected CollectionInteger.Element.Provides<I> provides() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
      }
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
    protected CollectionInteger.Element.Requires<I> requires() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
      }
      return this.selfComponent.bridge;
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected CollectionInteger.Element.Parts<I> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized CollectionInteger.Element.Component<I> _newComponent(final CollectionInteger.Element.Requires<I> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Element has already been used to create a component, use another one.");
      }
      this.init = true;
      CollectionInteger.Element.ComponentImpl<I>  _comp = new CollectionInteger.Element.ComponentImpl<I>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private CollectionInteger.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected CollectionInteger.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected CollectionInteger.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected CollectionInteger.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private CollectionInteger.ComponentImpl<I> selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected CollectionInteger.Provides<I> provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract MapGet<Integer, I> make_get();
  
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
  protected CollectionInteger.Requires<I> requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected CollectionInteger.Parts<I> parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized CollectionInteger.Component<I> _newComponent(final CollectionInteger.Requires<I> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of CollectionInteger has already been used to create a component, use another one.");
    }
    this.init = true;
    CollectionInteger.ComponentImpl<I>  _comp = new CollectionInteger.ComponentImpl<I>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract CollectionInteger.Element<I> make_Element();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public CollectionInteger.Element<I> _createImplementationOfElement() {
    CollectionInteger.Element<I> implem = make_Element();
    if (implem == null) {
    	throw new RuntimeException("make_Element() in fr.irit.smac.may.lib.components.meta.CollectionInteger should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public CollectionInteger.Component<I> newComponent() {
    return this._newComponent(new CollectionInteger.Requires<I>() {}, true);
  }
}
