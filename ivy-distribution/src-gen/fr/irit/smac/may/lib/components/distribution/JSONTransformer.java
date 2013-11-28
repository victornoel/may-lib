package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.interfaces.Transform;

@SuppressWarnings("all")
public abstract class JSONTransformer<T> {
  @SuppressWarnings("all")
  public interface Requires<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Transform<T,String> serializer();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Transform<String,T> deserializer();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T> extends fr.irit.smac.may.lib.components.distribution.JSONTransformer.Provides<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.distribution.JSONTransformer.Component<T>, fr.irit.smac.may.lib.components.distribution.JSONTransformer.Parts<T> {
    private final fr.irit.smac.may.lib.components.distribution.JSONTransformer.Requires<T> bridge;
    
    private final JSONTransformer<T> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.serializer == null;
      this.serializer = this.implementation.make_serializer();
      assert this.deserializer == null;
      this.deserializer = this.implementation.make_deserializer();
      
    }
    
    public ComponentImpl(final JSONTransformer<T> implem, final fr.irit.smac.may.lib.components.distribution.JSONTransformer.Requires<T> b, final boolean initMakes) {
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
    
    private Transform<T,String> serializer;
    
    public final Transform<T,String> serializer() {
      return this.serializer;
    }
    
    private Transform<String,T> deserializer;
    
    public final Transform<String,T> deserializer() {
      return this.deserializer;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.distribution.JSONTransformer.ComponentImpl<T> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.distribution.JSONTransformer.Provides<T> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Transform<T,String> make_serializer();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Transform<String,T> make_deserializer();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.JSONTransformer.Requires<T> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.JSONTransformer.Parts<T> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.distribution.JSONTransformer.Component<T> newComponent(final fr.irit.smac.may.lib.components.distribution.JSONTransformer.Requires<T> b) {
    fr.irit.smac.may.lib.components.distribution.JSONTransformer.ComponentImpl<T> comp = new fr.irit.smac.may.lib.components.distribution.JSONTransformer.ComponentImpl<T>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.distribution.JSONTransformer.Component<T> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.distribution.JSONTransformer.Requires<T>() {});
  }
}
