package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import java.util.Collection;

@SuppressWarnings("all")
public abstract class CollectionOf<I> {
  public interface Requires<I> {
  }
  
  public interface Parts<I> {
  }
  
  public static class ComponentImpl<I> implements CollectionOf.Component<I>, CollectionOf.Parts<I> {
    private final CollectionOf.Requires<I> bridge;
    
    private final CollectionOf<I> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.get == null: "This is a bug.";
      this.get = this.implementation.make_get();
      if (this.get == null) {
      	throw new RuntimeException("make_get() in fr.irit.smac.may.lib.components.meta.CollectionOf should not return null.");
      }
      
    }
    
    public ComponentImpl(final CollectionOf<I> implem, final CollectionOf.Requires<I> b, final boolean doInits) {
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
    
    private Pull<Collection<I>> get;
    
    public Pull<Collection<I>> get() {
      return this.get;
    }
  }
  
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Collection<I>> get();
  }
  
  public interface Component<I> extends CollectionOf.Provides<I> {
  }
  
  public abstract static class Element<I> {
    public interface Requires<I> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public I forwardedPort();
    }
    
    public interface Parts<I> {
    }
    
    public static class ComponentImpl<I> implements CollectionOf.Element.Component<I>, CollectionOf.Element.Parts<I> {
      private final CollectionOf.Element.Requires<I> bridge;
      
      private final CollectionOf.Element<I> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.stop == null: "This is a bug.";
        this.stop = this.implementation.make_stop();
        if (this.stop == null) {
        	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.meta.CollectionOf$Element should not return null.");
        }
        
      }
      
      public ComponentImpl(final CollectionOf.Element<I> implem, final CollectionOf.Element.Requires<I> b, final boolean doInits) {
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
      
      private Do stop;
      
      public Do stop() {
        return this.stop;
      }
    }
    
    public interface Provides<I> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    public interface Component<I> extends CollectionOf.Element.Provides<I> {
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
     */
    private boolean started = false;;
    
    private CollectionOf.Element.ComponentImpl<I> selfComponent;
    
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
    protected CollectionOf.Element.Provides<I> provides() {
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
    protected abstract Do make_stop();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected CollectionOf.Element.Requires<I> requires() {
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
    protected CollectionOf.Element.Parts<I> parts() {
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
    public synchronized CollectionOf.Element.Component<I> _newComponent(final CollectionOf.Element.Requires<I> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Element has already been used to create a component, use another one.");
      }
      this.init = true;
      CollectionOf.Element.ComponentImpl<I> comp = new CollectionOf.Element.ComponentImpl<I>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private CollectionOf.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected CollectionOf.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected CollectionOf.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected CollectionOf.Parts<I> eco_parts() {
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
   */
  private boolean started = false;;
  
  private CollectionOf.ComponentImpl<I> selfComponent;
  
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
  protected CollectionOf.Provides<I> provides() {
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
  protected abstract Pull<Collection<I>> make_get();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected CollectionOf.Requires<I> requires() {
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
  protected CollectionOf.Parts<I> parts() {
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
  public synchronized CollectionOf.Component<I> _newComponent(final CollectionOf.Requires<I> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of CollectionOf has already been used to create a component, use another one.");
    }
    this.init = true;
    CollectionOf.ComponentImpl<I> comp = new CollectionOf.ComponentImpl<I>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract CollectionOf.Element<I> make_Element();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public CollectionOf.Element<I> _createImplementationOfElement() {
    CollectionOf.Element<I> implem = make_Element();
    if (implem == null) {
    	throw new RuntimeException("make_Element() in fr.irit.smac.may.lib.components.meta.CollectionOf should not return null.");
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
  public CollectionOf.Component<I> newComponent() {
    return this._newComponent(new CollectionOf.Requires<I>() {}, true);
  }
}
