package fr.irit.smac.may.lib.components.either;

import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class Either<L, R> {
  public interface Requires<L, R> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L, R>> out();
  }
  
  public interface Parts<L, R> {
  }
  
  public static class ComponentImpl<L, R> implements Either.Component<L, R>, Either.Parts<L, R> {
    private final Either.Requires<L, R> bridge;
    
    private final Either<L, R> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.left == null: "This is a bug.";
      this.left = this.implementation.make_left();
      if (this.left == null) {
      	throw new RuntimeException("make_left() in fr.irit.smac.may.lib.components.either.Either should not return null.");
      }
      assert this.right == null: "This is a bug.";
      this.right = this.implementation.make_right();
      if (this.right == null) {
      	throw new RuntimeException("make_right() in fr.irit.smac.may.lib.components.either.Either should not return null.");
      }
      
    }
    
    public ComponentImpl(final Either<L, R> implem, final Either.Requires<L, R> b, final boolean doInits) {
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
    
    private Push<L> left;
    
    public final Push<L> left() {
      return this.left;
    }
    
    private Push<R> right;
    
    public final Push<R> right() {
      return this.right;
    }
  }
  
  public interface Provides<L, R> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<L> left();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<R> right();
  }
  
  public interface Component<L, R> extends Either.Provides<L, R> {
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
  
  private Either.ComponentImpl<L, R> selfComponent;
  
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
  protected Either.Provides<L, R> provides() {
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
  protected abstract Push<L> make_left();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<R> make_right();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Either.Requires<L, R> requires() {
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
  protected Either.Parts<L, R> parts() {
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
  public synchronized Either.Component<L, R> _newComponent(final Either.Requires<L, R> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Either has already been used to create a component, use another one.");
    }
    this.init = true;
    Either.ComponentImpl<L, R> comp = new Either.ComponentImpl<L, R>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
