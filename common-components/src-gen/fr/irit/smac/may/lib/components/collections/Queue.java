package fr.irit.smac.may.lib.components.collections;

import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.Collection;

@SuppressWarnings("all")
public abstract class Queue<Truc> {
  @SuppressWarnings("all")
  public interface Requires<Truc> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Truc> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Truc> put();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Truc> get();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Collection<Truc>> getAll();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Truc> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Truc> extends fr.irit.smac.may.lib.components.collections.Queue.Provides<Truc> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Truc> implements fr.irit.smac.may.lib.components.collections.Queue.Parts<Truc>, fr.irit.smac.may.lib.components.collections.Queue.Component<Truc> {
    private final fr.irit.smac.may.lib.components.collections.Queue.Requires<Truc> bridge;
    
    private final Queue<Truc> implementation;
    
    public ComponentImpl(final Queue<Truc> implem, final fr.irit.smac.may.lib.components.collections.Queue.Requires<Truc> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.put = implem.make_put();
      this.get = implem.make_get();
      this.getAll = implem.make_getAll();
      
    }
    
    private final Push<Truc> put;
    
    public final Push<Truc> put() {
      return this.put;
    }
    
    private final Pull<Truc> get;
    
    public final Pull<Truc> get() {
      return this.get;
    }
    
    private final Pull<Collection<Truc>> getAll;
    
    public final Pull<Collection<Truc>> getAll() {
      return this.getAll;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.collections.Queue.ComponentImpl<Truc> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.collections.Queue.Provides<Truc> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<Truc> make_put();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<Truc> make_get();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<Collection<Truc>> make_getAll();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.collections.Queue.Requires<Truc> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.collections.Queue.Parts<Truc> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.collections.Queue.Component<Truc> newComponent(final fr.irit.smac.may.lib.components.collections.Queue.Requires<Truc> b) {
    return new fr.irit.smac.may.lib.components.collections.Queue.ComponentImpl<Truc>(this, b);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.collections.Queue.Component<Truc> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.collections.Queue.Requires<Truc>() {});
  }
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <Truc> fr.irit.smac.may.lib.components.collections.Queue.Component<Truc> newComponent(final Queue<Truc> _compo) {
    return _compo.newComponent();
  }
}
