package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;
import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class Scheduled {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public AdvancedExecutor sched();
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
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.scheduling.Scheduled.Provides {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.Scheduled.Parts, fr.irit.smac.may.lib.components.scheduling.Scheduled.Component {
    private final fr.irit.smac.may.lib.components.scheduling.Scheduled.Requires bridge;
    
    private final Scheduled implementation;
    
    public ComponentImpl(final Scheduled implem, final fr.irit.smac.may.lib.components.scheduling.Scheduled.Requires b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.tick = implem.make_tick();
      this.async = implem.make_async();
      
    }
    
    private final Do tick;
    
    public final Do tick() {
      return this.tick;
    }
    
    private final SchedulingControl async;
    
    public final SchedulingControl async() {
      return this.async;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent {
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
    public interface Parts {
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Provides {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Parts, fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Component {
      private final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Requires bridge;
      
      private final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent implem, final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Requires b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.stop = implem.make_stop();
        
      }
      
      private final Do stop;
      
      public final Do stop() {
        return this.stop;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.ComponentImpl selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Provides provides() {
      assert this.selfComponent != null;
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
    protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Requires requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Parts parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Requires b) {
      return new fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.ComponentImpl(this, b);
    }
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduled.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Provides eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Requires eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Parts eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.scheduling.Scheduled.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Provides provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.Scheduled.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.Scheduled.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.Scheduled.Requires b) {
    return new fr.irit.smac.may.lib.components.scheduling.Scheduled.ComponentImpl(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent _createImplementationOfAgent() {
    fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
}
