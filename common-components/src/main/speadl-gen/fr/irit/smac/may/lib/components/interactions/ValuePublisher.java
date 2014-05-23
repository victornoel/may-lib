package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class ValuePublisher<T, K> {
  public interface Requires<T, K> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Call<Pull<T>, K> call();
  }
  
  public interface Parts<T, K> {
  }
  
  public static class ComponentImpl<T, K> implements ValuePublisher.Component<T, K>, ValuePublisher.Parts<T, K> {
    private final ValuePublisher.Requires<T, K> bridge;
    
    private final ValuePublisher<T, K> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.observe == null: "This is a bug.";
      this.observe = this.implementation.make_observe();
      if (this.observe == null) {
      	throw new RuntimeException("make_observe() in fr.irit.smac.may.lib.components.interactions.ValuePublisher should not return null.");
      }
      
    }
    
    public ComponentImpl(final ValuePublisher<T, K> implem, final ValuePublisher.Requires<T, K> b, final boolean doInits) {
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
    
    private ReliableObserve<T, K> observe;
    
    public final ReliableObserve<T, K> observe() {
      return this.observe;
    }
  }
  
  public interface Provides<T, K> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableObserve<T, K> observe();
  }
  
  public interface Component<T, K> extends ValuePublisher.Provides<T, K> {
  }
  
  public abstract static class PublisherPush<T, K> {
    public interface Requires<T, K> {
    }
    
    public interface Parts<T, K> {
    }
    
    public static class ComponentImpl<T, K> implements ValuePublisher.PublisherPush.Component<T, K>, ValuePublisher.PublisherPush.Parts<T, K> {
      private final ValuePublisher.PublisherPush.Requires<T, K> bridge;
      
      private final ValuePublisher.PublisherPush<T, K> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.set == null: "This is a bug.";
        this.set = this.implementation.make_set();
        if (this.set == null) {
        	throw new RuntimeException("make_set() in fr.irit.smac.may.lib.components.interactions.ValuePublisher$PublisherPush should not return null.");
        }
        assert this.get == null: "This is a bug.";
        this.get = this.implementation.make_get();
        if (this.get == null) {
        	throw new RuntimeException("make_get() in fr.irit.smac.may.lib.components.interactions.ValuePublisher$PublisherPush should not return null.");
        }
        
      }
      
      public ComponentImpl(final ValuePublisher.PublisherPush<T, K> implem, final ValuePublisher.PublisherPush.Requires<T, K> b, final boolean doInits) {
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
      
      private Push<T> set;
      
      public final Push<T> set() {
        return this.set;
      }
      
      private Pull<T> get;
      
      public final Pull<T> get() {
        return this.get;
      }
      
      public final Pull<T> toCall() {
        return this.get();
      }
    }
    
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
    
    public interface Component<T, K> extends ValuePublisher.PublisherPush.Provides<T, K> {
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
    
    private ValuePublisher.PublisherPush.ComponentImpl<T, K> selfComponent;
    
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
    protected ValuePublisher.PublisherPush.Provides<T, K> provides() {
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
    protected ValuePublisher.PublisherPush.Requires<T, K> requires() {
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
    protected ValuePublisher.PublisherPush.Parts<T, K> parts() {
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
    public synchronized ValuePublisher.PublisherPush.Component<T, K> _newComponent(final ValuePublisher.PublisherPush.Requires<T, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PublisherPush has already been used to create a component, use another one.");
      }
      this.init = true;
      ValuePublisher.PublisherPush.ComponentImpl<T, K> comp = new ValuePublisher.PublisherPush.ComponentImpl<T, K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private ValuePublisher.ComponentImpl<T, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected ValuePublisher.Provides<T, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected ValuePublisher.Requires<T, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected ValuePublisher.Parts<T, K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  public abstract static class PublisherPull<T, K> {
    public interface Requires<T, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<T> getValue();
    }
    
    public interface Parts<T, K> {
    }
    
    public static class ComponentImpl<T, K> implements ValuePublisher.PublisherPull.Component<T, K>, ValuePublisher.PublisherPull.Parts<T, K> {
      private final ValuePublisher.PublisherPull.Requires<T, K> bridge;
      
      private final ValuePublisher.PublisherPull<T, K> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.get == null: "This is a bug.";
        this.get = this.implementation.make_get();
        if (this.get == null) {
        	throw new RuntimeException("make_get() in fr.irit.smac.may.lib.components.interactions.ValuePublisher$PublisherPull should not return null.");
        }
        
      }
      
      public ComponentImpl(final ValuePublisher.PublisherPull<T, K> implem, final ValuePublisher.PublisherPull.Requires<T, K> b, final boolean doInits) {
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
      
      private Pull<T> get;
      
      public final Pull<T> get() {
        return this.get;
      }
      
      public final Pull<T> toCall() {
        return this.get();
      }
    }
    
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
    
    public interface Component<T, K> extends ValuePublisher.PublisherPull.Provides<T, K> {
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
    
    private ValuePublisher.PublisherPull.ComponentImpl<T, K> selfComponent;
    
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
    protected ValuePublisher.PublisherPull.Provides<T, K> provides() {
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
    protected abstract Pull<T> make_get();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected ValuePublisher.PublisherPull.Requires<T, K> requires() {
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
    protected ValuePublisher.PublisherPull.Parts<T, K> parts() {
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
    public synchronized ValuePublisher.PublisherPull.Component<T, K> _newComponent(final ValuePublisher.PublisherPull.Requires<T, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PublisherPull has already been used to create a component, use another one.");
      }
      this.init = true;
      ValuePublisher.PublisherPull.ComponentImpl<T, K> comp = new ValuePublisher.PublisherPull.ComponentImpl<T, K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private ValuePublisher.ComponentImpl<T, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected ValuePublisher.Provides<T, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected ValuePublisher.Requires<T, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected ValuePublisher.Parts<T, K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  public abstract static class Observer<T, K> {
    public interface Requires<T, K> {
    }
    
    public interface Parts<T, K> {
    }
    
    public static class ComponentImpl<T, K> implements ValuePublisher.Observer.Component<T, K>, ValuePublisher.Observer.Parts<T, K> {
      private final ValuePublisher.Observer.Requires<T, K> bridge;
      
      private final ValuePublisher.Observer<T, K> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.observe == null: "This is a bug.";
        this.observe = this.implementation.make_observe();
        if (this.observe == null) {
        	throw new RuntimeException("make_observe() in fr.irit.smac.may.lib.components.interactions.ValuePublisher$Observer should not return null.");
        }
        
      }
      
      public ComponentImpl(final ValuePublisher.Observer<T, K> implem, final ValuePublisher.Observer.Requires<T, K> b, final boolean doInits) {
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
      
      private ReliableObserve<T, K> observe;
      
      public final ReliableObserve<T, K> observe() {
        return this.observe;
      }
    }
    
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableObserve<T, K> observe();
    }
    
    public interface Component<T, K> extends ValuePublisher.Observer.Provides<T, K> {
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
    
    private ValuePublisher.Observer.ComponentImpl<T, K> selfComponent;
    
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
    protected ValuePublisher.Observer.Provides<T, K> provides() {
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
    protected abstract ReliableObserve<T, K> make_observe();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected ValuePublisher.Observer.Requires<T, K> requires() {
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
    protected ValuePublisher.Observer.Parts<T, K> parts() {
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
    public synchronized ValuePublisher.Observer.Component<T, K> _newComponent(final ValuePublisher.Observer.Requires<T, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Observer has already been used to create a component, use another one.");
      }
      this.init = true;
      ValuePublisher.Observer.ComponentImpl<T, K> comp = new ValuePublisher.Observer.ComponentImpl<T, K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private ValuePublisher.ComponentImpl<T, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected ValuePublisher.Provides<T, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected ValuePublisher.Requires<T, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected ValuePublisher.Parts<T, K> eco_parts() {
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
  
  private ValuePublisher.ComponentImpl<T, K> selfComponent;
  
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
  protected ValuePublisher.Provides<T, K> provides() {
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
  protected abstract ReliableObserve<T, K> make_observe();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ValuePublisher.Requires<T, K> requires() {
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
  protected ValuePublisher.Parts<T, K> parts() {
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
  public synchronized ValuePublisher.Component<T, K> _newComponent(final ValuePublisher.Requires<T, K> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ValuePublisher has already been used to create a component, use another one.");
    }
    this.init = true;
    ValuePublisher.ComponentImpl<T, K> comp = new ValuePublisher.ComponentImpl<T, K>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract ValuePublisher.PublisherPush<T, K> make_PublisherPush();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public ValuePublisher.PublisherPush<T, K> _createImplementationOfPublisherPush() {
    ValuePublisher.PublisherPush<T, K> implem = make_PublisherPush();
    if (implem == null) {
    	throw new RuntimeException("make_PublisherPush() in fr.irit.smac.may.lib.components.interactions.ValuePublisher should not return null.");
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
  protected ValuePublisher.PublisherPush.Component<T, K> newPublisherPush() {
    ValuePublisher.PublisherPush<T, K> implem = _createImplementationOfPublisherPush();
    return implem._newComponent(new ValuePublisher.PublisherPush.Requires<T, K>() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract ValuePublisher.PublisherPull<T, K> make_PublisherPull();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public ValuePublisher.PublisherPull<T, K> _createImplementationOfPublisherPull() {
    ValuePublisher.PublisherPull<T, K> implem = make_PublisherPull();
    if (implem == null) {
    	throw new RuntimeException("make_PublisherPull() in fr.irit.smac.may.lib.components.interactions.ValuePublisher should not return null.");
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
  protected abstract ValuePublisher.Observer<T, K> make_Observer();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public ValuePublisher.Observer<T, K> _createImplementationOfObserver() {
    ValuePublisher.Observer<T, K> implem = make_Observer();
    if (implem == null) {
    	throw new RuntimeException("make_Observer() in fr.irit.smac.may.lib.components.interactions.ValuePublisher should not return null.");
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
  protected ValuePublisher.Observer.Component<T, K> newObserver() {
    ValuePublisher.Observer<T, K> implem = _createImplementationOfObserver();
    return implem._newComponent(new ValuePublisher.Observer.Requires<T, K>() {},true);
  }
}
