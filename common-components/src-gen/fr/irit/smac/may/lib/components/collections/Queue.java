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
  public interface Component<Truc> extends fr.irit.smac.may.lib.components.collections.Queue.Provides<Truc> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Truc> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Truc> implements fr.irit.smac.may.lib.components.collections.Queue.Component<Truc>, fr.irit.smac.may.lib.components.collections.Queue.Parts<Truc> {
    private final fr.irit.smac.may.lib.components.collections.Queue.Requires<Truc> bridge;
    
    private final Queue<Truc> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.put == null;
      this.put = this.implementation.make_put();
      assert this.get == null;
      this.get = this.implementation.make_get();
      assert this.getAll == null;
      this.getAll = this.implementation.make_getAll();
      
    }
    
    public ComponentImpl(final Queue<Truc> implem, final fr.irit.smac.may.lib.components.collections.Queue.Requires<Truc> b, final boolean initMakes) {
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
    
    private Push<Truc> put;
    
    public final Push<Truc> put() {
      return this.put;
    }
    
    private Pull<Truc> get;
    
    public final Pull<Truc> get() {
      return this.get;
    }
    
    private Pull<Collection<Truc>> getAll;
    
    public final Pull<Collection<Truc>> getAll() {
      return this.getAll;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.collections.Queue.ComponentImpl<Truc> selfComponent;
  
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
    fr.irit.smac.may.lib.components.collections.Queue.ComponentImpl<Truc> comp = new fr.irit.smac.may.lib.components.collections.Queue.ComponentImpl<Truc>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.collections.Queue.Component<Truc> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.collections.Queue.Requires<Truc>() {});
  }
}
