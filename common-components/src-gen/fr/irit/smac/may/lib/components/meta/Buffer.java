package fr.irit.smac.may.lib.components.meta;

import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class Buffer<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public I realPort();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public I port();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do release();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.components.meta.Buffer.Provides<I> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.Buffer.Parts<I>, fr.irit.smac.may.lib.components.meta.Buffer.Component<I> {
    private final fr.irit.smac.may.lib.components.meta.Buffer.Requires<I> bridge;
    
    private final Buffer<I> implementation;
    
    public ComponentImpl(final Buffer<I> implem, final fr.irit.smac.may.lib.components.meta.Buffer.Requires<I> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.port = implem.make_port();
      this.release = implem.make_release();
      
    }
    
    private final I port;
    
    public final I port() {
      return this.port;
    }
    
    private final Do release;
    
    public final Do release() {
      return this.release;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.meta.Buffer.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.meta.Buffer.Provides<I> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract I make_port();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Do make_release();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.Buffer.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.Buffer.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.meta.Buffer.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.Buffer.Requires<I> b) {
    return new fr.irit.smac.may.lib.components.meta.Buffer.ComponentImpl<I>(this, b);
  }
}
