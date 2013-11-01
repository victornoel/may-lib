package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class ValuePublisher<T, K> {
  @SuppressWarnings("all")
  public interface Requires<T, K> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Call<Pull<T>,K> call();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<T, K> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableObserve<T,K> observe();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T, K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.ValuePublisher.Provides<T,K> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.Parts<T,K>, fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<T,K> {
    private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,K> bridge;
    
    private final ValuePublisher<T,K> implementation;
    
    public ComponentImpl(final ValuePublisher<T,K> implem, final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,K> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.observe = implem.make_observe();
      
    }
    
    private final ReliableObserve<T,K> observe;
    
    public final ReliableObserve<T,K> observe() {
      return this.observe;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class PublisherPush<T, K> {
    @SuppressWarnings("all")
    public interface Requires<T, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Push<T> set();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> get();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> toCall();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Provides<T,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Parts<T,K>, fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> {
      private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<T,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,K> implem, final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<T,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.set = implem.make_set();
        this.get = implem.make_get();
        
      }
      
      private final Push<T> set;
      
      public final Push<T> set() {
        return this.set;
      }
      
      private final Pull<T> get;
      
      public final Pull<T> get() {
        return this.get;
      }
      
      public final Pull<T> toCall() {
        return this.get();
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.ComponentImpl<T,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Provides<T,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Push<T> make_set();
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull<T> make_get();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<T,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Parts<T,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<T,K> b) {
      return new fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.ComponentImpl<T,K>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class PublisherPull<T, K> {
    @SuppressWarnings("all")
    public interface Requires<T, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<T> getValue();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> get();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> toCall();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Provides<T,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Parts<T,K>, fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,K> {
      private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Requires<T,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull<T,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull<T,K> implem, final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Requires<T,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.get = implem.make_get();
        
      }
      
      private final Pull<T> get;
      
      public final Pull<T> get() {
        return this.get;
      }
      
      public final Pull<T> toCall() {
        return this.get();
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.ComponentImpl<T,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Provides<T,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull<T> make_get();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Requires<T,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Parts<T,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Requires<T,K> b) {
      return new fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.ComponentImpl<T,K>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Observer<T, K> {
    @SuppressWarnings("all")
    public interface Requires<T, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableObserve<T,K> observe();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Provides<T,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Parts<T,K>, fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,K> {
      private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Requires<T,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<T,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<T,K> implem, final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Requires<T,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.observe = implem.make_observe();
        
      }
      
      private final ReliableObserve<T,K> observe;
      
      public final ReliableObserve<T,K> observe() {
        return this.observe;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.ComponentImpl<T,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Provides<T,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract ReliableObserve<T,K> make_observe();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Requires<T,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Parts<T,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Requires<T,K> b) {
      return new fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.ComponentImpl<T,K>(this, b);
    }
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.interactions.ValuePublisher.ComponentImpl<T,K> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Provides<T,K> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract ReliableObserve<T,K> make_observe();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,K> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Parts<T,K> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,K> b) {
    return new fr.irit.smac.may.lib.components.interactions.ValuePublisher.ComponentImpl<T,K>(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,K> make_PublisherPush();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,K> _createImplementationOfPublisherPush() {
    fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,K> implem = make_PublisherPush();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> newPublisherPush() {
    fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,K> implem = _createImplementationOfPublisherPush();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<T,K>() {});
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull<T,K> make_PublisherPull();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull<T,K> _createImplementationOfPublisherPull() {
    fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull<T,K> implem = make_PublisherPull();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<T,K> make_Observer();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<T,K> _createImplementationOfObserver() {
    fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<T,K> implem = make_Observer();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,K> newObserver() {
    fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<T,K> implem = _createImplementationOfObserver();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Requires<T,K>() {});
  }
}
