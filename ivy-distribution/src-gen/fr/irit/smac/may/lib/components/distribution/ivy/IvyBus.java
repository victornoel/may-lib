package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class IvyBus {
  @SuppressWarnings("all")
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Executor exec();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do disconnect();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Bind bindMsg();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Integer> unBindMsg();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<String> send();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Component, fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Parts {
    private final fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Requires bridge;
    
    private final IvyBus implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.disconnect == null;
      this.disconnect = this.implementation.make_disconnect();
      assert this.bindMsg == null;
      this.bindMsg = this.implementation.make_bindMsg();
      assert this.unBindMsg == null;
      this.unBindMsg = this.implementation.make_unBindMsg();
      assert this.send == null;
      this.send = this.implementation.make_send();
      
    }
    
    public ComponentImpl(final IvyBus implem, final fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Requires b, final boolean initMakes) {
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
    
    private Do disconnect;
    
    public final Do disconnect() {
      return this.disconnect;
    }
    
    private Bind bindMsg;
    
    public final Bind bindMsg() {
      return this.bindMsg;
    }
    
    private Push<Integer> unBindMsg;
    
    public final Push<Integer> unBindMsg() {
      return this.unBindMsg;
    }
    
    private Push<String> send;
    
    public final Push<String> send() {
      return this.send;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Do make_disconnect();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Bind make_bindMsg();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<Integer> make_unBindMsg();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<String> make_send();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Component newComponent(final fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Requires b) {
    fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.ComponentImpl comp = new fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
