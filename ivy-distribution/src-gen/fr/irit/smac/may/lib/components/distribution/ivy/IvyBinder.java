package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.List;

@SuppressWarnings("all")
public abstract class IvyBinder {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Bind bindMsg();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<Integer> unBindMsg();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<List<String>> receive();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<String> reBindMsg();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Component, fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Parts {
    private final fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Requires bridge;
    
    private final IvyBinder implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.reBindMsg == null;
      this.reBindMsg = this.implementation.make_reBindMsg();
      
    }
    
    public ComponentImpl(final IvyBinder implem, final fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Requires b, final boolean initMakes) {
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
    
    private Push<String> reBindMsg;
    
    public final Push<String> reBindMsg() {
      return this.reBindMsg;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<String> make_reBindMsg();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Component newComponent(final fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Requires b) {
    fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.ComponentImpl comp = new fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
