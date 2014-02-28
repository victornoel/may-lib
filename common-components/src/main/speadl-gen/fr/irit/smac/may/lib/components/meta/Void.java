package fr.irit.smac.may.lib.components.meta;

@SuppressWarnings("all")
public abstract class Void<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public I port();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends Void.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements Void.Component<I>, Void.Parts<I> {
    private final Void.Requires<I> bridge;
    
    private final Void<I> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.port == null: "This is a bug.";
      this.port = this.implementation.make_port();
      if (this.port == null) {
      	throw new RuntimeException("make_port() in fr.irit.smac.may.lib.components.meta.Void should not return null.");
      }
      
    }
    
    public ComponentImpl(final Void<I> implem, final Void.Requires<I> b, final boolean doInits) {
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
    
    private I port;
    
    public final I port() {
      return this.port;
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
  
  private Void.ComponentImpl<I> selfComponent;
  
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
  protected Void.Provides<I> provides() {
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
  protected abstract I make_port();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Void.Requires<I> requires() {
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
  protected Void.Parts<I> parts() {
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
  public synchronized Void.Component<I> _newComponent(final Void.Requires<I> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Void has already been used to create a component, use another one.");
    }
    this.init = true;
    Void.ComponentImpl<I> comp = new Void.ComponentImpl<I>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Void.Component<I> newComponent() {
    return this._newComponent(new Void.Requires<I>() {}, true);
  }
}
