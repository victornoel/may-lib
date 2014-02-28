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
  public interface Component<I> extends Forward.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements Forward.Component<I>, Forward.Parts<I> {
    private final Forward.Requires<I> bridge;
    
    private final Forward<I> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final Forward<I> implem, final Forward.Requires<I> b, final boolean doInits) {
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
    public interface Component<I> extends Forward.Caller.Provides<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements Forward.Caller.Component<I>, Forward.Caller.Parts<I> {
      private final Forward.Caller.Requires<I> bridge;
      
      private final Forward.Caller<I> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.forwardedPort == null: "This is a bug.";
        this.forwardedPort = this.implementation.make_forwardedPort();
        if (this.forwardedPort == null) {
        	throw new RuntimeException("make_forwardedPort() in fr.irit.smac.may.lib.components.meta.Forward$Caller should not return null.");
        }
        
      }
      
      public ComponentImpl(final Forward.Caller<I> implem, final Forward.Caller.Requires<I> b, final boolean doInits) {
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
      
      private I forwardedPort;
      
      public final I forwardedPort() {
        return this.forwardedPort;
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
    
    private Forward.Caller.ComponentImpl<I> selfComponent;
    
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
    protected Forward.Caller.Provides<I> provides() {
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
    protected abstract I make_forwardedPort();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Forward.Caller.Requires<I> requires() {
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
    protected Forward.Caller.Parts<I> parts() {
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
    public synchronized Forward.Caller.Component<I> _newComponent(final Forward.Caller.Requires<I> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Caller has already been used to create a component, use another one.");
      }
      this.init = true;
      Forward.Caller.ComponentImpl<I> comp = new Forward.Caller.ComponentImpl<I>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private Forward.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Forward.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Forward.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Forward.Parts<I> eco_parts() {
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
  
  private Forward.ComponentImpl<I> selfComponent;
  
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
  protected Forward.Provides<I> provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Forward.Requires<I> requires() {
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
  protected Forward.Parts<I> parts() {
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
  public synchronized Forward.Component<I> _newComponent(final Forward.Requires<I> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Forward has already been used to create a component, use another one.");
    }
    this.init = true;
    Forward.ComponentImpl<I> comp = new Forward.ComponentImpl<I>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Forward.Caller<I> make_Caller();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Forward.Caller<I> _createImplementationOfCaller() {
    Forward.Caller<I> implem = make_Caller();
    if (implem == null) {
    	throw new RuntimeException("make_Caller() in fr.irit.smac.may.lib.components.meta.Forward should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected Forward.Caller.Component<I> newCaller() {
    Forward.Caller<I> implem = _createImplementationOfCaller();
    return implem._newComponent(new Forward.Caller.Requires<I>() {},true);
  }
}
