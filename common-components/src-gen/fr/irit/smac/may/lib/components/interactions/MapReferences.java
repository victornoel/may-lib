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
  public interface Parts<I, K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I, K> extends fr.irit.smac.may.lib.components.interactions.MapReferences.Provides<I,K> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I, K> implements fr.irit.smac.may.lib.components.interactions.MapReferences.Parts<I,K>, fr.irit.smac.may.lib.components.interactions.MapReferences.Component<I,K> {
    private final fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> bridge;
    
    private final MapReferences<I,K> implementation;
    
    public ComponentImpl(final MapReferences<I,K> implem, final fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<I,K> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.call = implem.make_call();
      
    }
    
    private final Call<I,K> call;
    
    public final Call<I,K> call() {
      return this.call;
    }
    
    public void start() {
      this.implementation.start();
      
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
    public interface Parts<I, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I, K> extends fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Provides<I,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I, K> implements fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Parts<I,K>, fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<I,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<I,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<I,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<I,K> implem, final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<I,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.me = implem.make_me();
        this.stop = implem.make_stop();
        
      }
      
      private final Pull<K> me;
      
      public final Pull<K> me() {
        return this.me;
      }
      
      private final Do stop;
      
      public final Do stop() {
        return this.stop;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.ComponentImpl<I,K> selfComponent;
    
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
      return new fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.ComponentImpl<I,K>(this, b);
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
  public abstract static class CalleePullKey<I, K> {
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
    public interface Parts<I, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I, K> extends fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Provides<I,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I, K> implements fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Parts<I,K>, fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Component<I,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Requires<I,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey<I,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey<I,K> implem, final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Requires<I,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.me = implem.make_me();
        this.stop = implem.make_stop();
        
      }
      
      private final Pull<K> me;
      
      public final Pull<K> me() {
        return this.me;
      }
      
      private final Do stop;
      
      public final Do stop() {
        return this.stop;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.ComponentImpl<I,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Provides<I,K> provides() {
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
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Requires<I,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Parts<I,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Component<I,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.Requires<I,K> b) {
      return new fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey.ComponentImpl<I,K>(this, b);
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
    public interface Parts<I, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I, K> extends fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Provides<I,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I, K> implements fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Parts<I,K>, fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Component<I,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Requires<I,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller<I,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller<I,K> implem, final fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.Requires<I,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.call = implem.make_call();
        
      }
      
      private final Call<I,K> call;
      
      public final Call<I,K> call() {
        return this.call;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.ComponentImpl<I,K> selfComponent;
    
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
      return new fr.irit.smac.may.lib.components.interactions.MapReferences.Caller.ComponentImpl<I,K>(this, b);
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
    return new fr.irit.smac.may.lib.components.interactions.MapReferences.ComponentImpl<I,K>(this, b);
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
  protected abstract fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey<I,K> make_CalleePullKey();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey<I,K> _createImplementationOfCalleePullKey() {
    fr.irit.smac.may.lib.components.interactions.MapReferences.CalleePullKey<I,K> implem = make_CalleePullKey();
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
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <I, K> fr.irit.smac.may.lib.components.interactions.MapReferences.Component<I,K> newComponent(final MapReferences<I,K> _compo) {
    return _compo.newComponent();
  }
}
