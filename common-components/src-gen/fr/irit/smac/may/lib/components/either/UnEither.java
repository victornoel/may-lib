package fr.irit.smac.may.lib.components.either;

import fr.irit.smac.may.lib.components.either.datatypes.Either;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class UnEither<L, R> {
  @SuppressWarnings("all")
  public interface Requires<L, R> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<L> left();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<R> right();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<L, R> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Either<L,R>> in();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<L, R> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<L, R> extends fr.irit.smac.may.lib.components.either.UnEither.Provides<L,R> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<L, R> implements fr.irit.smac.may.lib.components.either.UnEither.Parts<L,R>, fr.irit.smac.may.lib.components.either.UnEither.Component<L,R> {
    private final fr.irit.smac.may.lib.components.either.UnEither.Requires<L,R> bridge;
    
    private final UnEither<L,R> implementation;
    
    public ComponentImpl(final UnEither<L,R> implem, final fr.irit.smac.may.lib.components.either.UnEither.Requires<L,R> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.in = implem.make_in();
      
    }
    
    private final Push<Either<L,R>> in;
    
    public final Push<Either<L,R>> in() {
      return this.in;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.either.UnEither.ComponentImpl<L,R> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.either.UnEither.Provides<L,R> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<Either<L,R>> make_in();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.either.UnEither.Requires<L,R> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.either.UnEither.Parts<L,R> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.either.UnEither.Component<L,R> newComponent(final fr.irit.smac.may.lib.components.either.UnEither.Requires<L,R> b) {
    return new fr.irit.smac.may.lib.components.either.UnEither.ComponentImpl<L,R>(this, b);
  }
}
