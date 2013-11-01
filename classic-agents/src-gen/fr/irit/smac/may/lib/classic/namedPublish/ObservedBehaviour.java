package fr.irit.smac.may.lib.classic.namedPublish;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class ObservedBehaviour {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<Integer> changeValue();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do cycle();
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Provides {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Parts, fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Component {
    private final fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Requires bridge;
    
    private final ObservedBehaviour implementation;
    
    public ComponentImpl(final ObservedBehaviour implem, final fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Requires b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.cycle = implem.make_cycle();
      
    }
    
    private final Do cycle;
    
    public final Do cycle() {
      return this.cycle;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Do make_cycle();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Component newComponent(final fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Requires b) {
    return new fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.ComponentImpl(this, b);
  }
}
