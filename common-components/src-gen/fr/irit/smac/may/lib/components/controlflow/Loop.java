package fr.irit.smac.may.lib.components.controlflow;

import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.interfaces.Do;

@SuppressWarnings("all")
public abstract class Loop {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public AdvancedExecutor executor();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Do handler();
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
  public interface Component extends fr.irit.smac.may.lib.components.controlflow.Loop.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.controlflow.Loop.Component, fr.irit.smac.may.lib.components.controlflow.Loop.Parts {
    private final fr.irit.smac.may.lib.components.controlflow.Loop.Requires bridge;
    
    private final Loop implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.stop == null;
      this.stop = this.implementation.make_stop();
      
    }
    
    public ComponentImpl(final Loop implem, final fr.irit.smac.may.lib.components.controlflow.Loop.Requires b, final boolean initMakes) {
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
    
    private Do stop;
    
    public final Do stop() {
      return this.stop;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.controlflow.Loop.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.controlflow.Loop.Provides provides() {
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
  protected fr.irit.smac.may.lib.components.controlflow.Loop.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.controlflow.Loop.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.controlflow.Loop.Component newComponent(final fr.irit.smac.may.lib.components.controlflow.Loop.Requires b) {
    fr.irit.smac.may.lib.components.controlflow.Loop.ComponentImpl comp = new fr.irit.smac.may.lib.components.controlflow.Loop.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
