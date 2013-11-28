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
  public interface Component<I> extends fr.irit.smac.may.lib.components.meta.Buffer.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.Buffer.Component<I>, fr.irit.smac.may.lib.components.meta.Buffer.Parts<I> {
    private final fr.irit.smac.may.lib.components.meta.Buffer.Requires<I> bridge;
    
    private final Buffer<I> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.port == null;
      this.port = this.implementation.make_port();
      assert this.release == null;
      this.release = this.implementation.make_release();
      
    }
    
    public ComponentImpl(final Buffer<I> implem, final fr.irit.smac.may.lib.components.meta.Buffer.Requires<I> b, final boolean initMakes) {
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
    
    private I port;
    
    public final I port() {
      return this.port;
    }
    
    private Do release;
    
    public final Do release() {
      return this.release;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.meta.Buffer.ComponentImpl<I> selfComponent;
  
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
    fr.irit.smac.may.lib.components.meta.Buffer.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.meta.Buffer.ComponentImpl<I>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
