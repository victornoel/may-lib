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
  public interface Component<I> extends fr.irit.smac.may.lib.components.interactions.DirectReferences.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<I>, fr.irit.smac.may.lib.components.interactions.DirectReferences.Parts<I> {
    private final fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<I> bridge;
    
    private final DirectReferences<I> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.call == null;
      this.call = this.implementation.make_call();
      
    }
    
    public ComponentImpl(final DirectReferences<I> implem, final fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<I> b, final boolean initMakes) {
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
    public interface Component<I> extends fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Provides<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<I>, fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Parts<I> {
      private final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Requires<I> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<I> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.me == null;
        this.me = this.implementation.make_me();
        assert this.stop == null;
        this.stop = this.implementation.make_stop();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<I> implem, final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Requires<I> b, final boolean initMakes) {
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
      
      private Pull<DirRef> me;
      
      public final Pull<DirRef> me() {
        return this.me;
      }
      
      private Do stop;
      
      public final Do stop() {
        return this.stop;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.ComponentImpl<I> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Provides<I> provides() {
      assert this.selfComponent != null;
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
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Requires<I> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Parts<I> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<I> newComponent(final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Requires<I> b) {
      fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.ComponentImpl<I>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Parts<I> eco_parts() {
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
      public Call<I,DirRef> call();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<I> extends fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Provides<I> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<I> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<I> implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Component<I>, fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Parts<I> {
      private final fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Requires<I> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller<I> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.call == null;
        this.call = this.implementation.make_call();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller<I> implem, final fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Requires<I> b, final boolean initMakes) {
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
      
      private Call<I,DirRef> call;
      
      public final Call<I,DirRef> call() {
        return this.call;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.ComponentImpl<I> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Provides<I> provides() {
      assert this.selfComponent != null;
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
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Requires<I> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Parts<I> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Component<I> newComponent(final fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Requires<I> b) {
      fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.ComponentImpl<I>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.ComponentImpl<I> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Provides<I> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<I> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Parts<I> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.interactions.DirectReferences.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Provides<I> provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<I> newComponent(final fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<I> b) {
    fr.irit.smac.may.lib.components.interactions.DirectReferences.ComponentImpl<I> comp = new fr.irit.smac.may.lib.components.interactions.DirectReferences.ComponentImpl<I>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<I> make_Callee(final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<I> _createImplementationOfCallee(final String name) {
    fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<I> implem = make_Callee(name);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller<I> make_Caller();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller<I> _createImplementationOfCaller() {
    fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller<I> implem = make_Caller();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Component<I> newCaller() {
    fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller<I> implem = _createImplementationOfCaller();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.DirectReferences.Caller.Requires<I>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<I> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<I>() {});
  }
}
