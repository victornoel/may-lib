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
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.components.meta.Void.Provides<I> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.meta.Void.Parts<I>, fr.irit.smac.may.lib.components.meta.Void.Component<I> {
    private final fr.irit.smac.may.lib.components.meta.Void.Requires<I> bridge;
    
    private final Void<I> implementation;
    
    public ComponentImpl(final Void<I> implem, final fr.irit.smac.may.lib.components.meta.Void.Requires<I> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.port = implem.make_port();
      
    }
    
    private final I port;
    
    public final I port() {
      return this.port;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.meta.Void.ComponentImpl<I> selfComponent;
  
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
    return new fr.irit.smac.may.lib.components.meta.Void.ComponentImpl<I>(this, b);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.meta.Void.Component<I> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.meta.Void.Requires<I>() {});
  }
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <I> fr.irit.smac.may.lib.components.meta.Void.Component<I> newComponent(final Void<I> _compo) {
    return _compo.newComponent();
  }
}
