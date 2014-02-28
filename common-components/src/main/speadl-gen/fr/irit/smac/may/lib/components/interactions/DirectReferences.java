package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class DirectReferences<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Call<I,DirRef> call();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends DirectReferences.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements DirectReferences.Component<I>, DirectReferences.Parts<I> {
    private final DirectReferences.Requires<I> bridge;
    
    private final DirectReferences<I> implementation;
    
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
      	throw new RuntimeException("make_call() in fr.irit.smac.may.lib.components.interactions.DirectReferences should not return null.");
      }
      
    }
    
    public ComponentImpl(final DirectReferences<I> implem, final DirectReferences.Requires<I> b, final boolean doInits) {
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
    
    private Call<I,DirRef> call;
    
    public final Call<I,DirRef> call() {
      return this.call;
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
      public Pull<DirRef> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends DirectReferences.Callee.Provides<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements DirectReferences.Callee.Component<I>, DirectReferences.Callee.Parts<I> {
      private final DirectReferences.Callee.Requires<I> bridge;
      
      private final DirectReferences.Callee<I> implementation;
      
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
        	throw new RuntimeException("make_me() in fr.irit.smac.may.lib.components.interactions.DirectReferences$Callee should not return null.");
        }
        assert this.stop == null: "This is a bug.";
        this.stop = this.implementation.make_stop();
        if (this.stop == null) {
        	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.interactions.DirectReferences$Callee should not return null.");
        }
        
      }
      
      public ComponentImpl(final DirectReferences.Callee<I> implem, final DirectReferences.Callee.Requires<I> b, final boolean doInits) {
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
      
      private Pull<DirRef> me;
      
      public final Pull<DirRef> me() {
        return this.me;
      }
      
      private Do stop;
      
      public final Do stop() {
        return this.stop;
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
    
    private DirectReferences.Callee.ComponentImpl<I> selfComponent;
    
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
    protected DirectReferences.Callee.Provides<I> provides() {
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
    protected abstract Pull<DirRef> make_me();
    
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
    protected DirectReferences.Callee.Requires<I> requires() {
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
    protected DirectReferences.Callee.Parts<I> parts() {
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
    public synchronized DirectReferences.Callee.Component<I> _newComponent(final DirectReferences.Callee.Requires<I> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Callee has already been used to create a component, use another one.");
      }
      this.init = true;
      DirectReferences.Callee.ComponentImpl<I> comp = new DirectReferences.Callee.ComponentImpl<I>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private DirectReferences.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected DirectReferences.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected DirectReferences.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected DirectReferences.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
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
      public Call<I,DirRef> call();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends DirectReferences.Caller.Provides<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements DirectReferences.Caller.Component<I>, DirectReferences.Caller.Parts<I> {
      private final DirectReferences.Caller.Requires<I> bridge;
      
      private final DirectReferences.Caller<I> implementation;
      
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
        	throw new RuntimeException("make_call() in fr.irit.smac.may.lib.components.interactions.DirectReferences$Caller should not return null.");
        }
        
      }
      
      public ComponentImpl(final DirectReferences.Caller<I> implem, final DirectReferences.Caller.Requires<I> b, final boolean doInits) {
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
      
      private Call<I,DirRef> call;
      
      public final Call<I,DirRef> call() {
        return this.call;
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
    
    private DirectReferences.Caller.ComponentImpl<I> selfComponent;
    
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
    protected DirectReferences.Caller.Provides<I> provides() {
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
    protected abstract Call<I,DirRef> make_call();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected DirectReferences.Caller.Requires<I> requires() {
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
    protected DirectReferences.Caller.Parts<I> parts() {
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
    public synchronized DirectReferences.Caller.Component<I> _newComponent(final DirectReferences.Caller.Requires<I> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Caller has already been used to create a component, use another one.");
      }
      this.init = true;
      DirectReferences.Caller.ComponentImpl<I> comp = new DirectReferences.Caller.ComponentImpl<I>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private DirectReferences.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected DirectReferences.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected DirectReferences.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected DirectReferences.Parts<I> eco_parts() {
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
  
  private DirectReferences.ComponentImpl<I> selfComponent;
  
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
  protected DirectReferences.Provides<I> provides() {
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
  protected abstract Call<I,DirRef> make_call();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected DirectReferences.Requires<I> requires() {
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
  protected DirectReferences.Parts<I> parts() {
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
  public synchronized DirectReferences.Component<I> _newComponent(final DirectReferences.Requires<I> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of DirectReferences has already been used to create a component, use another one.");
    }
    this.init = true;
    DirectReferences.ComponentImpl<I> comp = new DirectReferences.ComponentImpl<I>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract DirectReferences.Callee<I> make_Callee(final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public DirectReferences.Callee<I> _createImplementationOfCallee(final String name) {
    DirectReferences.Callee<I> implem = make_Callee(name);
    if (implem == null) {
    	throw new RuntimeException("make_Callee() in fr.irit.smac.may.lib.components.interactions.DirectReferences should not return null.");
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
  protected abstract DirectReferences.Caller<I> make_Caller();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public DirectReferences.Caller<I> _createImplementationOfCaller() {
    DirectReferences.Caller<I> implem = make_Caller();
    if (implem == null) {
    	throw new RuntimeException("make_Caller() in fr.irit.smac.may.lib.components.interactions.DirectReferences should not return null.");
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
  protected DirectReferences.Caller.Component<I> newCaller() {
    DirectReferences.Caller<I> implem = _createImplementationOfCaller();
    return implem._newComponent(new DirectReferences.Caller.Requires<I>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public DirectReferences.Component<I> newComponent() {
    return this._newComponent(new DirectReferences.Requires<I>() {}, true);
  }
}
