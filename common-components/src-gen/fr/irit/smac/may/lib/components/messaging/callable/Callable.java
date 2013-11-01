package fr.irit.smac.may.lib.components.messaging.callable;

import fr.irit.smac.may.lib.components.messaging.callable.CallRef;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class Callable<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public MapGet<CallRef,I> call();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.components.messaging.callable.Callable.Provides<I> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.messaging.callable.Callable.Parts<I>, fr.irit.smac.may.lib.components.messaging.callable.Callable.Component<I> {
    private final fr.irit.smac.may.lib.components.messaging.callable.Callable.Requires<I> bridge;
    
    private final Callable<I> implementation;
    
    public ComponentImpl(final Callable<I> implem, final fr.irit.smac.may.lib.components.messaging.callable.Callable.Requires<I> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.call = implem.make_call();
      
    }
    
    private final MapGet<CallRef,I> call;
    
    public final MapGet<CallRef,I> call() {
      return this.call;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Callee<I> {
    @SuppressWarnings("all")
    public interface Requires<I> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public I toCall();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<I> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<CallRef> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Provides<I> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Parts<I>, fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Component<I> {
      private final fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Requires<I> bridge;
      
      private final fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee<I> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee<I> implem, final fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Requires<I> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.me = implem.make_me();
        this.stop = implem.make_stop();
        
      }
      
      private final Pull<CallRef> me;
      
      public final Pull<CallRef> me() {
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
    
    
    private fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.ComponentImpl<I> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Provides<I> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull<CallRef> make_me();
    
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
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Requires<I> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Parts<I> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Component<I> newComponent(final fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.Requires<I> b) {
      return new fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee.ComponentImpl<I>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.messaging.callable.Callable.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Caller<I> {
    @SuppressWarnings("all")
    public interface Requires<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<I> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public MapGet<CallRef,I> call();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Provides<I> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Parts<I>, fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Component<I> {
      private final fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Requires<I> bridge;
      
      private final fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller<I> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller<I> implem, final fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Requires<I> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.call = implem.make_call();
        
      }
      
      private final MapGet<CallRef,I> call;
      
      public final MapGet<CallRef,I> call() {
        return this.call;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.ComponentImpl<I> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Provides<I> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract MapGet<CallRef,I> make_call();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Requires<I> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Parts<I> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Component<I> newComponent(final fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Requires<I> b) {
      return new fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.ComponentImpl<I>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.messaging.callable.Callable.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.messaging.callable.Callable.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Provides<I> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract MapGet<CallRef,I> make_call();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.callable.Callable.Component<I> newComponent(final fr.irit.smac.may.lib.components.messaging.callable.Callable.Requires<I> b) {
    return new fr.irit.smac.may.lib.components.messaging.callable.Callable.ComponentImpl<I>(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee<I> make_Callee();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee<I> _createImplementationOfCallee() {
    fr.irit.smac.may.lib.components.messaging.callable.Callable.Callee<I> implem = make_Callee();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller<I> make_Caller();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller<I> _createImplementationOfCaller() {
    fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller<I> implem = make_Caller();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Component<I> newCaller() {
    fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller<I> implem = _createImplementationOfCaller();
    return implem.newComponent(new fr.irit.smac.may.lib.components.messaging.callable.Callable.Caller.Requires<I>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.messaging.callable.Callable.Component<I> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.messaging.callable.Callable.Requires<I>() {});
  }
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <I> fr.irit.smac.may.lib.components.messaging.callable.Callable.Component<I> newComponent(final Callable<I> _compo) {
    return _compo.newComponent();
  }
}
