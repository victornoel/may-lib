package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class MapReferences<I, K> {
  public interface Requires<I, K> {
  }
  
  public interface Parts<I, K> {
  }
  
  public static class ComponentImpl<I, K> implements MapReferences.Component<I, K>, MapReferences.Parts<I, K> {
    private final MapReferences.Requires<I, K> bridge;
    
    private final MapReferences<I, K> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.call == null: "This is a bug.";
      this.call = this.implementation.make_call();
      if (this.call == null) {
      	throw new RuntimeException("make_call() in fr.irit.smac.may.lib.components.interactions.MapReferences should not return null.");
      }
      
    }
    
    public ComponentImpl(final MapReferences<I, K> implem, final MapReferences.Requires<I, K> b, final boolean doInits) {
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
    
    private Call<I, K> call;
    
    public final Call<I, K> call() {
      return this.call;
    }
  }
  
  public interface Provides<I, K> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Call<I, K> call();
  }
  
  public interface Component<I, K> extends MapReferences.Provides<I, K> {
  }
  
  public abstract static class Callee<I, K> {
    public interface Requires<I, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public I toCall();
    }
    
    public interface Parts<I, K> {
    }
    
    public static class ComponentImpl<I, K> implements MapReferences.Callee.Component<I, K>, MapReferences.Callee.Parts<I, K> {
      private final MapReferences.Callee.Requires<I, K> bridge;
      
      private final MapReferences.Callee<I, K> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.me == null: "This is a bug.";
        this.me = this.implementation.make_me();
        if (this.me == null) {
        	throw new RuntimeException("make_me() in fr.irit.smac.may.lib.components.interactions.MapReferences$Callee should not return null.");
        }
        assert this.stop == null: "This is a bug.";
        this.stop = this.implementation.make_stop();
        if (this.stop == null) {
        	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.interactions.MapReferences$Callee should not return null.");
        }
        
      }
      
      public ComponentImpl(final MapReferences.Callee<I, K> implem, final MapReferences.Callee.Requires<I, K> b, final boolean doInits) {
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
      
      private Pull<K> me;
      
      public final Pull<K> me() {
        return this.me;
      }
      
      private Do stop;
      
      public final Do stop() {
        return this.stop;
      }
    }
    
    public interface Provides<I, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<K> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    public interface Component<I, K> extends MapReferences.Callee.Provides<I, K> {
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
    
    private MapReferences.Callee.ComponentImpl<I, K> selfComponent;
    
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
    protected MapReferences.Callee.Provides<I, K> provides() {
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
    protected abstract Pull<K> make_me();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Do make_stop();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected MapReferences.Callee.Requires<I, K> requires() {
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
    protected MapReferences.Callee.Parts<I, K> parts() {
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
    public synchronized MapReferences.Callee.Component<I, K> _newComponent(final MapReferences.Callee.Requires<I, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Callee has already been used to create a component, use another one.");
      }
      this.init = true;
      MapReferences.Callee.ComponentImpl<I, K> comp = new MapReferences.Callee.ComponentImpl<I, K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private MapReferences.ComponentImpl<I, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapReferences.Provides<I, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapReferences.Requires<I, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapReferences.Parts<I, K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  public abstract static class CalleeKeyPort<I, K> {
    public interface Requires<I, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public I toCall();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<K> key();
    }
    
    public interface Parts<I, K> {
    }
    
    public static class ComponentImpl<I, K> implements MapReferences.CalleeKeyPort.Component<I, K>, MapReferences.CalleeKeyPort.Parts<I, K> {
      private final MapReferences.CalleeKeyPort.Requires<I, K> bridge;
      
      private final MapReferences.CalleeKeyPort<I, K> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.me == null: "This is a bug.";
        this.me = this.implementation.make_me();
        if (this.me == null) {
        	throw new RuntimeException("make_me() in fr.irit.smac.may.lib.components.interactions.MapReferences$CalleeKeyPort should not return null.");
        }
        assert this.stop == null: "This is a bug.";
        this.stop = this.implementation.make_stop();
        if (this.stop == null) {
        	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.interactions.MapReferences$CalleeKeyPort should not return null.");
        }
        
      }
      
      public ComponentImpl(final MapReferences.CalleeKeyPort<I, K> implem, final MapReferences.CalleeKeyPort.Requires<I, K> b, final boolean doInits) {
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
      
      private Pull<K> me;
      
      public final Pull<K> me() {
        return this.me;
      }
      
      private Do stop;
      
      public final Do stop() {
        return this.stop;
      }
    }
    
    public interface Provides<I, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<K> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    public interface Component<I, K> extends MapReferences.CalleeKeyPort.Provides<I, K> {
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
    
    private MapReferences.CalleeKeyPort.ComponentImpl<I, K> selfComponent;
    
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
    protected MapReferences.CalleeKeyPort.Provides<I, K> provides() {
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
    protected abstract Pull<K> make_me();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Do make_stop();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected MapReferences.CalleeKeyPort.Requires<I, K> requires() {
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
    protected MapReferences.CalleeKeyPort.Parts<I, K> parts() {
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
    public synchronized MapReferences.CalleeKeyPort.Component<I, K> _newComponent(final MapReferences.CalleeKeyPort.Requires<I, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of CalleeKeyPort has already been used to create a component, use another one.");
      }
      this.init = true;
      MapReferences.CalleeKeyPort.ComponentImpl<I, K> comp = new MapReferences.CalleeKeyPort.ComponentImpl<I, K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private MapReferences.ComponentImpl<I, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapReferences.Provides<I, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapReferences.Requires<I, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapReferences.Parts<I, K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  public abstract static class Caller<I, K> {
    public interface Requires<I, K> {
    }
    
    public interface Parts<I, K> {
    }
    
    public static class ComponentImpl<I, K> implements MapReferences.Caller.Component<I, K>, MapReferences.Caller.Parts<I, K> {
      private final MapReferences.Caller.Requires<I, K> bridge;
      
      private final MapReferences.Caller<I, K> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.call == null: "This is a bug.";
        this.call = this.implementation.make_call();
        if (this.call == null) {
        	throw new RuntimeException("make_call() in fr.irit.smac.may.lib.components.interactions.MapReferences$Caller should not return null.");
        }
        
      }
      
      public ComponentImpl(final MapReferences.Caller<I, K> implem, final MapReferences.Caller.Requires<I, K> b, final boolean doInits) {
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
      
      private Call<I, K> call;
      
      public final Call<I, K> call() {
        return this.call;
      }
    }
    
    public interface Provides<I, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Call<I, K> call();
    }
    
    public interface Component<I, K> extends MapReferences.Caller.Provides<I, K> {
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
    
    private MapReferences.Caller.ComponentImpl<I, K> selfComponent;
    
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
    protected MapReferences.Caller.Provides<I, K> provides() {
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
    protected abstract Call<I, K> make_call();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected MapReferences.Caller.Requires<I, K> requires() {
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
    protected MapReferences.Caller.Parts<I, K> parts() {
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
    public synchronized MapReferences.Caller.Component<I, K> _newComponent(final MapReferences.Caller.Requires<I, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Caller has already been used to create a component, use another one.");
      }
      this.init = true;
      MapReferences.Caller.ComponentImpl<I, K> comp = new MapReferences.Caller.ComponentImpl<I, K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private MapReferences.ComponentImpl<I, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapReferences.Provides<I, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapReferences.Requires<I, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapReferences.Parts<I, K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
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
  
  private MapReferences.ComponentImpl<I, K> selfComponent;
  
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
  protected MapReferences.Provides<I, K> provides() {
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
  protected abstract Call<I, K> make_call();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected MapReferences.Requires<I, K> requires() {
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
  protected MapReferences.Parts<I, K> parts() {
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
  public synchronized MapReferences.Component<I, K> _newComponent(final MapReferences.Requires<I, K> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of MapReferences has already been used to create a component, use another one.");
    }
    this.init = true;
    MapReferences.ComponentImpl<I, K> comp = new MapReferences.ComponentImpl<I, K>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract MapReferences.Callee<I, K> make_Callee(final K key);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapReferences.Callee<I, K> _createImplementationOfCallee(final K key) {
    MapReferences.Callee<I, K> implem = make_Callee(key);
    if (implem == null) {
    	throw new RuntimeException("make_Callee() in fr.irit.smac.may.lib.components.interactions.MapReferences should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract MapReferences.CalleeKeyPort<I, K> make_CalleeKeyPort();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapReferences.CalleeKeyPort<I, K> _createImplementationOfCalleeKeyPort() {
    MapReferences.CalleeKeyPort<I, K> implem = make_CalleeKeyPort();
    if (implem == null) {
    	throw new RuntimeException("make_CalleeKeyPort() in fr.irit.smac.may.lib.components.interactions.MapReferences should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract MapReferences.Caller<I, K> make_Caller();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapReferences.Caller<I, K> _createImplementationOfCaller() {
    MapReferences.Caller<I, K> implem = make_Caller();
    if (implem == null) {
    	throw new RuntimeException("make_Caller() in fr.irit.smac.may.lib.components.interactions.MapReferences should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected MapReferences.Caller.Component<I, K> newCaller() {
    MapReferences.Caller<I, K> implem = _createImplementationOfCaller();
    return implem._newComponent(new MapReferences.Caller.Requires<I, K>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public MapReferences.Component<I, K> newComponent() {
    return this._newComponent(new MapReferences.Requires<I, K>() {}, true);
  }
}
