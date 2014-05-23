package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class ExecutorServiceWrapper {
  public interface Requires {
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements ExecutorServiceWrapper.Component, ExecutorServiceWrapper.Parts {
    private final ExecutorServiceWrapper.Requires bridge;
    
    private final ExecutorServiceWrapper implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.executor == null: "This is a bug.";
      this.executor = this.implementation.make_executor();
      if (this.executor == null) {
      	throw new RuntimeException("make_executor() in fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper should not return null.");
      }
      assert this.stop == null: "This is a bug.";
      this.stop = this.implementation.make_stop();
      if (this.stop == null) {
      	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper should not return null.");
      }
      
    }
    
    public ComponentImpl(final ExecutorServiceWrapper implem, final ExecutorServiceWrapper.Requires b, final boolean doInits) {
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
    
    private AdvancedExecutor executor;
    
    public AdvancedExecutor executor() {
      return this.executor;
    }
    
    private Do stop;
    
    public Do stop() {
      return this.stop;
    }
  }
  
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
  
  public interface Component extends ExecutorServiceWrapper.Provides {
  }
  
  public abstract static class Executing {
    public interface Requires {
    }
    
    public interface Parts {
    }
    
    public static class ComponentImpl implements ExecutorServiceWrapper.Executing.Component, ExecutorServiceWrapper.Executing.Parts {
      private final ExecutorServiceWrapper.Executing.Requires bridge;
      
      private final ExecutorServiceWrapper.Executing implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.executor == null: "This is a bug.";
        this.executor = this.implementation.make_executor();
        if (this.executor == null) {
        	throw new RuntimeException("make_executor() in fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper$Executing should not return null.");
        }
        assert this.stop == null: "This is a bug.";
        this.stop = this.implementation.make_stop();
        if (this.stop == null) {
        	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper$Executing should not return null.");
        }
        
      }
      
      public ComponentImpl(final ExecutorServiceWrapper.Executing implem, final ExecutorServiceWrapper.Executing.Requires b, final boolean doInits) {
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
      
      private AdvancedExecutor executor;
      
      public AdvancedExecutor executor() {
        return this.executor;
      }
      
      private Do stop;
      
      public Do stop() {
        return this.stop;
      }
    }
    
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
    
    public interface Component extends ExecutorServiceWrapper.Executing.Provides {
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
    
    private ExecutorServiceWrapper.Executing.ComponentImpl selfComponent;
    
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
    protected ExecutorServiceWrapper.Executing.Provides provides() {
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
    protected ExecutorServiceWrapper.Executing.Requires requires() {
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
    protected ExecutorServiceWrapper.Executing.Parts parts() {
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
    public synchronized ExecutorServiceWrapper.Executing.Component _newComponent(final ExecutorServiceWrapper.Executing.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Executing has already been used to create a component, use another one.");
      }
      this.init = true;
      ExecutorServiceWrapper.Executing.ComponentImpl comp = new ExecutorServiceWrapper.Executing.ComponentImpl(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private ExecutorServiceWrapper.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected ExecutorServiceWrapper.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected ExecutorServiceWrapper.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected ExecutorServiceWrapper.Parts eco_parts() {
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
  
  private ExecutorServiceWrapper.ComponentImpl selfComponent;
  
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
  protected ExecutorServiceWrapper.Provides provides() {
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
  protected ExecutorServiceWrapper.Requires requires() {
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
  protected ExecutorServiceWrapper.Parts parts() {
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
  public synchronized ExecutorServiceWrapper.Component _newComponent(final ExecutorServiceWrapper.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ExecutorServiceWrapper has already been used to create a component, use another one.");
    }
    this.init = true;
    ExecutorServiceWrapper.ComponentImpl comp = new ExecutorServiceWrapper.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract ExecutorServiceWrapper.Executing make_Executing();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public ExecutorServiceWrapper.Executing _createImplementationOfExecuting() {
    ExecutorServiceWrapper.Executing implem = make_Executing();
    if (implem == null) {
    	throw new RuntimeException("make_Executing() in fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper should not return null.");
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
  protected ExecutorServiceWrapper.Executing.Component newExecuting() {
    ExecutorServiceWrapper.Executing implem = _createImplementationOfExecuting();
    return implem._newComponent(new ExecutorServiceWrapper.Executing.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public ExecutorServiceWrapper.Component newComponent() {
    return this._newComponent(new ExecutorServiceWrapper.Requires() {}, true);
  }
}
