package fr.irit.smac.may.lib.components.either;

import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class Either<L, R> {
  @SuppressWarnings("all")
  public interface Requires<L, R> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<fr.irit.smac.may.lib.components.either.datatypes.Either<L,R>> out();
  }
  
  
  @SuppressWarnings("all")
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
  
  
  @SuppressWarnings("all")
  public interface Component<L, R> extends fr.irit.smac.may.lib.components.either.Either.Provides<L,R> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<L, R> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<L, R> implements fr.irit.smac.may.lib.components.either.Either.Component<L,R>, fr.irit.smac.may.lib.components.either.Either.Parts<L,R> {
    private final fr.irit.smac.may.lib.components.either.Either.Requires<L,R> bridge;
    
    private final Either<L,R> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.left == null;
      this.left = this.implementation.make_left();
      assert this.right == null;
      this.right = this.implementation.make_right();
      
    }
    
    public ComponentImpl(final Either<L,R> implem, final fr.irit.smac.may.lib.components.either.Either.Requires<L,R> b, final boolean initMakes) {
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
    
    private Push<L> left;
    
    public final Push<L> left() {
      return this.left;
    }
    
    private Push<R> right;
    
    public final Push<R> right() {
      return this.right;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.either.Either.ComponentImpl<L,R> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.either.Either.Provides<L,R> provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.components.either.Either.Requires<L,R> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.either.Either.Parts<L,R> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.either.Either.Component<L,R> newComponent(final fr.irit.smac.may.lib.components.either.Either.Requires<L,R> b) {
    fr.irit.smac.may.lib.components.either.Either.ComponentImpl<L,R> comp = new fr.irit.smac.may.lib.components.either.Either.ComponentImpl<L,R>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
