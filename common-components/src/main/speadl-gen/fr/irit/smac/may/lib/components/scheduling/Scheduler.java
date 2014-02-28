package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;
import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class Scheduler {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public AdvancedExecutor executor();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do tick();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public SchedulingControl async();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends Scheduler.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements Scheduler.Component, Scheduler.Parts {
    private final Scheduler.Requires bridge;
    
    private final Scheduler implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.tick == null: "This is a bug.";
      this.tick = this.implementation.make_tick();
      if (this.tick == null) {
      	throw new RuntimeException("make_tick() in fr.irit.smac.may.lib.components.scheduling.Scheduler should not return null.");
      }
      assert this.async == null: "This is a bug.";
      this.async = this.implementation.make_async();
      if (this.async == null) {
      	throw new RuntimeException("make_async() in fr.irit.smac.may.lib.components.scheduling.Scheduler should not return null.");
      }
      
    }
    
    public ComponentImpl(final Scheduler implem, final Scheduler.Requires b, final boolean doInits) {
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
    
    private Do tick;
    
    public final Do tick() {
      return this.tick;
    }
    
    private SchedulingControl async;
    
    public final SchedulingControl async() {
      return this.async;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Scheduled {
    @SuppressWarnings("all")
    public interface Requires {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Do cycle();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends Scheduler.Scheduled.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements Scheduler.Scheduled.Component, Scheduler.Scheduled.Parts {
      private final Scheduler.Scheduled.Requires bridge;
      
      private final Scheduler.Scheduled implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.stop == null: "This is a bug.";
        this.stop = this.implementation.make_stop();
        if (this.stop == null) {
        	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.scheduling.Scheduler$Scheduled should not return null.");
        }
        
      }
      
      public ComponentImpl(final Scheduler.Scheduled implem, final Scheduler.Scheduled.Requires b, final boolean doInits) {
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
    
    private Scheduler.Scheduled.ComponentImpl selfComponent;
    
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
    protected Scheduler.Scheduled.Provides provides() {
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
    protected abstract Do make_stop();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Scheduler.Scheduled.Requires requires() {
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
    protected Scheduler.Scheduled.Parts parts() {
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
    public synchronized Scheduler.Scheduled.Component _newComponent(final Scheduler.Scheduled.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Scheduled has already been used to create a component, use another one.");
      }
      this.init = true;
      Scheduler.Scheduled.ComponentImpl comp = new Scheduler.Scheduled.ComponentImpl(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private Scheduler.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Scheduler.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Scheduler.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Scheduler.Parts eco_parts() {
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
  
  private Scheduler.ComponentImpl selfComponent;
  
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
  protected Scheduler.Provides provides() {
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
  protected abstract Do make_tick();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract SchedulingControl make_async();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Scheduler.Requires requires() {
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
  protected Scheduler.Parts parts() {
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
  public synchronized Scheduler.Component _newComponent(final Scheduler.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Scheduler has already been used to create a component, use another one.");
    }
    this.init = true;
    Scheduler.ComponentImpl comp = new Scheduler.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Scheduler.Scheduled make_Scheduled();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Scheduler.Scheduled _createImplementationOfScheduled() {
    Scheduler.Scheduled implem = make_Scheduled();
    if (implem == null) {
    	throw new RuntimeException("make_Scheduled() in fr.irit.smac.may.lib.components.scheduling.Scheduler should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
}
