package fr.irit.smac.may.lib.components.scheduling;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class ExecutorService {
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
  public interface Component extends fr.irit.smac.may.lib.components.scheduling.ExecutorService.Provides {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.scheduling.ExecutorService.Parts, fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component {
    private final fr.irit.smac.may.lib.components.scheduling.ExecutorService.Requires bridge;
    
    private final ExecutorService implementation;
    
    public ComponentImpl(final ExecutorService implem, final fr.irit.smac.may.lib.components.scheduling.ExecutorService.Requires b) {
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
  
  
  private fr.irit.smac.may.lib.components.scheduling.ExecutorService.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.scheduling.ExecutorService.Provides provides() {
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
  protected fr.irit.smac.may.lib.components.scheduling.ExecutorService.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.scheduling.ExecutorService.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component newComponent(final fr.irit.smac.may.lib.components.scheduling.ExecutorService.Requires b) {
    return new fr.irit.smac.may.lib.components.scheduling.ExecutorService.ComponentImpl(this, b);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.scheduling.ExecutorService.Requires() {});
  }
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component newComponent(final ExecutorService _compo) {
    return _compo.newComponent();
  }
}
