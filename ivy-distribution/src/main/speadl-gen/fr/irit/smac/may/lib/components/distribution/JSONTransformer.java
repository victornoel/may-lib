package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.interfaces.Transform;

@SuppressWarnings("all")
public abstract class JSONTransformer<T> {
  public interface Requires<T> {
  }
  
  public interface Parts<T> {
  }
  
  public static class ComponentImpl<T> implements JSONTransformer.Component<T>, JSONTransformer.Parts<T> {
    private final JSONTransformer.Requires<T> bridge;
    
    private final JSONTransformer<T> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.serializer == null: "This is a bug.";
      this.serializer = this.implementation.make_serializer();
      if (this.serializer == null) {
      	throw new RuntimeException("make_serializer() in fr.irit.smac.may.lib.components.distribution.JSONTransformer should not return null.");
      }
      assert this.deserializer == null: "This is a bug.";
      this.deserializer = this.implementation.make_deserializer();
      if (this.deserializer == null) {
      	throw new RuntimeException("make_deserializer() in fr.irit.smac.may.lib.components.distribution.JSONTransformer should not return null.");
      }
      
    }
    
    public ComponentImpl(final JSONTransformer<T> implem, final JSONTransformer.Requires<T> b, final boolean doInits) {
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
    
    private Transform<T, String> serializer;
    
    public Transform<T, String> serializer() {
      return this.serializer;
    }
    
    private Transform<String, T> deserializer;
    
    public Transform<String, T> deserializer() {
      return this.deserializer;
    }
  }
  
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Transform<T, String> serializer();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Transform<String, T> deserializer();
  }
  
  public interface Component<T> extends JSONTransformer.Provides<T> {
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
  
  private JSONTransformer.ComponentImpl<T> selfComponent;
  
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
  protected JSONTransformer.Provides<T> provides() {
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
  protected abstract Transform<T, String> make_serializer();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Transform<String, T> make_deserializer();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected JSONTransformer.Requires<T> requires() {
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
  protected JSONTransformer.Parts<T> parts() {
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
  public synchronized JSONTransformer.Component<T> _newComponent(final JSONTransformer.Requires<T> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of JSONTransformer has already been used to create a component, use another one.");
    }
    this.init = true;
    JSONTransformer.ComponentImpl<T> comp = new JSONTransformer.ComponentImpl<T>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public JSONTransformer.Component<T> newComponent() {
    return this._newComponent(new JSONTransformer.Requires<T>() {}, true);
  }
}
