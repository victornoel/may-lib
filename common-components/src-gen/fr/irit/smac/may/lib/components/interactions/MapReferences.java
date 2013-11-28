package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class MapReferences<I, K> {
  @SuppressWarnings("all")
  public interface Requires<I, K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I, K> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Call<I,K> call();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I, K> extends fr.irit.smac.may.lib.components.interactions.MapReferences.Provides<I,K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I, K> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I, K> implements fr.irit.smac.may.lib.components.interactions.MapReferences.Component<I,K>, fr.irit.smac.may.lib.components.interactions.MapReferences.Parts<I,K> {
    private final fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> bridge;
    
    private final MapReferences<I,K> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.call == null;
      this.call = this.implementation.make_call();
      
    }
    
    public ComponentImpl(final MapReferences<I,K> implem, final fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> b, final boolean initMakes) {
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
    
    private Call<I,K> call;
    
    public final Call<I,K> call() {
      return this.call;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Callee<I, K> {
    @SuppressWarnings("all")
    public interface Requires<I, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public I toCall();
    }
    
    
    @SuppressWarnings("all")
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
    
    
    @SuppressWarnings("all")
    public interface Component<I, K> extends fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Provides<I,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I, K> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I, K> implements fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<I,K>, fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Parts<I,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<I,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<I,K> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.me == null;
        this.me = this.implementation.make_me();
        assert this.stop == null;
        this.stop = this.implementation.make_stop();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<I,K> implem, final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<I,K> b, final boolean initMakes) {
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
      
      private Pull<K> me;
      
      public final Pull<K> me() {
        return this.me;
      }
      
      private Do stop;
      
      public final Do stop() {
        return this.stop;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.ComponentImpl<I,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Provides<I,K> provides() {
      assert this.selfComponent != null;
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
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<I,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Parts<I,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<I,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<I,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.ComponentImpl<I,K> comp = new fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.ComponentImpl<I,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.ComponentImpl<I,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Provides<I,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Parts<I,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class CalleeKeyPort<I, K> {
    @SuppressWarnings("all")
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
    
    
    @SuppressWarnings("all")
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
    
    
    @SuppressWarnings("all")
    public interface Component<I, K> extends fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Provides<I,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I, K> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I, K> implements fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<I,K>, fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Parts<I,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Requires<I,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort<I,K> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.me == null;
        this.me = this.implementation.make_me();
        assert this.stop == null;
        this.stop = this.implementation.make_stop();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort<I,K> implem, final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Requires<I,K> b, final boolean initMakes) {
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
      
      private Pull<K> me;
      
      public final Pull<K> me() {
        return this.me;
      }
      
      private Do stop;
      
      public final Do stop() {
        return this.stop;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.ComponentImpl<I,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Provides<I,K> provides() {
      assert this.selfComponent != null;
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
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Requires<I,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Parts<I,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<I,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Requires<I,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.ComponentImpl<I,K> comp = new fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.ComponentImpl<I,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.ComponentImpl<I,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Provides<I,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Parts<I,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Caller<I, K> {
    @SuppressWarnings("all")
    public interface Requires<I, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<I, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Call<I,K> call();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I, K> extends fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Provides<I,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I, K> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I, K> implements fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Component<I,K>, fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Parts<I,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Requires<I,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller<I,K> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.call == null;
        this.call = this.implementation.make_call();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller<I,K> implem, final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Requires<I,K> b, final boolean initMakes) {
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
      
      private Call<I,K> call;
      
      public final Call<I,K> call() {
        return this.call;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.ComponentImpl<I,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Provides<I,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Call<I,K> make_call();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Requires<I,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Parts<I,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Component<I,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Requires<I,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.ComponentImpl<I,K> comp = new fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.ComponentImpl<I,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.ComponentImpl<I,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Provides<I,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.Parts<I,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.interactions.MapReferences.ComponentImpl<I,K> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.interactions.MapReferences.Provides<I,K> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Call<I,K> make_call();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapReferences.Parts<I,K> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapReferences.Component<I,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> b) {
    fr.irit.smac.may.lib.components.interactions.MapReferences.ComponentImpl<I,K> comp = new fr.irit.smac.may.lib.components.interactions.MapReferences.ComponentImpl<I,K>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<I,K> make_Callee(final K key);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<I,K> _createImplementationOfCallee(final K key) {
    fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<I,K> implem = make_Callee(key);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort<I,K> make_CalleeKeyPort();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort<I,K> _createImplementationOfCalleeKeyPort() {
    fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort<I,K> implem = make_CalleeKeyPort();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapReferences.Caller<I,K> make_Caller();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapReferences.Caller<I,K> _createImplementationOfCaller() {
    fr.irit.smac.may.lib.components.interactions.MapReferences.Caller<I,K> implem = make_Caller();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Component<I,K> newCaller() {
    fr.irit.smac.may.lib.components.interactions.MapReferences.Caller<I,K> implem = _createImplementationOfCaller();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Requires<I,K>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapReferences.Component<I,K> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K>() {});
  }
}
