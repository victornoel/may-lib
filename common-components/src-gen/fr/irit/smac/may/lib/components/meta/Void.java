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
  public interface Component<I> extends fr.irit.smac.may.lib.components.meta.Void.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.Void.Component<I>, fr.irit.smac.may.lib.components.meta.Void.Parts<I> {
    private final fr.irit.smac.may.lib.components.meta.Void.Requires<I> bridge;
    
    private final Void<I> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.port == null;
      this.port = this.implementation.make_port();
      
    }
    
    public ComponentImpl(final Void<I> implem, final fr.irit.smac.may.lib.components.meta.Void.Requires<I> b, final boolean initMakes) {
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
  }
  
  
  private fr.irit.smac.may.lib.components.meta.Void.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.meta.Void.Provides<I> provides() {
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
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.Void.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.meta.Void.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.meta.Void.Component<I> newComponent(final fr.irit.smac.may.lib.components.meta.Void.Requires<I> b) {
    fr.irit.smac.may.lib.components.meta.Void.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.meta.Void.ComponentImpl<I>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.meta.Void.Component<I> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.meta.Void.Requires<I>() {});
  }
}
