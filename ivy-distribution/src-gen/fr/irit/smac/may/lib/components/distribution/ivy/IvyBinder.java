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
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Provides {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Parts, fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Component {
    private final fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Requires bridge;
    
    private final IvyBinder implementation;
    
    public ComponentImpl(final IvyBinder implem, final fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Requires b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.reBindMsg = implem.make_reBindMsg();
      
    }
    
    private final Push<String> reBindMsg;
    
    public final Push<String> reBindMsg() {
      return this.reBindMsg;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.ComponentImpl selfComponent;
  
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
    return new fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.ComponentImpl(this, b);
  }
}
