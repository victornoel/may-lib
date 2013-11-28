package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class ExecutorServiceWrapper {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public AdvancedExecutor executor();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do stop();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component, fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Parts {
    private final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Requires bridge;
    
    private final ExecutorServiceWrapper implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.executor == null;
      this.executor = this.implementation.make_executor();
      assert this.stop == null;
      this.stop = this.implementation.make_stop();
      
    }
    
    public ComponentImpl(final ExecutorServiceWrapper implem, final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Requires b, final boolean initMakes) {
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
    
    private AdvancedExecutor executor;
    
    public final AdvancedExecutor executor() {
      return this.executor;
    }
    
    private Do stop;
    
    public final Do stop() {
      return this.stop;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Executing {
    @SuppressWarnings("all")
    public interface Requires {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public AdvancedExecutor executor();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Component, fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Parts {
      private final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Requires bridge;
      
      private final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.executor == null;
        this.executor = this.implementation.make_executor();
        assert this.stop == null;
        this.stop = this.implementation.make_stop();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing implem, final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Requires b, final boolean initMakes) {
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
      
      private AdvancedExecutor executor;
      
      public final AdvancedExecutor executor() {
        return this.executor;
      }
      
      private Do stop;
      
      public final Do stop() {
        return this.stop;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.ComponentImpl selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Provides provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract AdvancedExecutor make_executor();
    
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
    protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Requires requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Parts parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Requires b) {
      fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.ComponentImpl comp = new fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.ComponentImpl(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Provides eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Requires eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Parts eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract AdvancedExecutor make_executor();
  
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
  protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Requires b) {
    fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.ComponentImpl comp = new fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing make_Executing();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing _createImplementationOfExecuting() {
    fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing implem = make_Executing();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Component newExecuting() {
    fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing implem = _createImplementationOfExecuting();
    return implem.newComponent(new fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Requires() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Requires() {});
  }
}
