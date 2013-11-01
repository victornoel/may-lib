package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
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
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.scheduling.Scheduler.Provides {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Parts, fr.irit.smac.may.lib.components.scheduling.Scheduler.Component {
    private final fr.irit.smac.may.lib.components.scheduling.Scheduler.Requires bridge;
    
    private final Scheduler implementation;
    
    public ComponentImpl(final Scheduler implem, final fr.irit.smac.may.lib.components.scheduling.Scheduler.Requires b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent {
    @SuppressWarnings("all")
    public interface Requires {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public AdvancedExecutor exec();
      
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
    public interface Component extends fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Provides {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Parts, fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Component {
      private final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Requires bridge;
      
      private final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent implem, final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Requires b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.exec = implem.make_exec();
        this.stop = implem.make_stop();
        
      }
      
      private final AdvancedExecutor exec;
      
      public final AdvancedExecutor exec() {
        return this.exec;
      }
      
      private final Do stop;
      
      public final Do stop() {
        return this.stop;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.ComponentImpl selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Provides provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract AdvancedExecutor make_exec();
    
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
    protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Requires requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Parts parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Requires b) {
      return new fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.ComponentImpl(this, b);
    }
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduler.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Provides eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Requires eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Parts eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.scheduling.Scheduler.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.Scheduler.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.Scheduler.Requires b) {
    return new fr.irit.smac.may.lib.components.scheduling.Scheduler.ComponentImpl(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent _createImplementationOfAgent() {
    fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Component newAgent() {
    fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent implem = _createImplementationOfAgent();
    return implem.newComponent(new fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Requires() {});
  }
}
