package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionStatus;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class IvySend {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<String> send();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<IvyConnectionStatus> connectionStatus();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<IvyConnectionConfig> connect();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do disconnect();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Component, fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Parts {
    private final fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Requires bridge;
    
    private final IvySend implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.send == null;
      this.send = this.implementation.make_send();
      assert this.connectionStatus == null;
      this.connectionStatus = this.implementation.make_connectionStatus();
      assert this.connect == null;
      this.connect = this.implementation.make_connect();
      assert this.disconnect == null;
      this.disconnect = this.implementation.make_disconnect();
      
    }
    
    public ComponentImpl(final IvySend implem, final fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Requires b, final boolean initMakes) {
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
    
    private Push<String> send;
    
    public final Push<String> send() {
      return this.send;
    }
    
    private Pull<IvyConnectionStatus> connectionStatus;
    
    public final Pull<IvyConnectionStatus> connectionStatus() {
      return this.connectionStatus;
    }
    
    private Push<IvyConnectionConfig> connect;
    
    public final Push<IvyConnectionConfig> connect() {
      return this.connect;
    }
    
    private Do disconnect;
    
    public final Do disconnect() {
      return this.disconnect;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.distribution.ivy.IvySend.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<String> make_send();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<IvyConnectionStatus> make_connectionStatus();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<IvyConnectionConfig> make_connect();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Do make_disconnect();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Component newComponent(final fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Requires b) {
    fr.irit.smac.may.lib.components.distribution.ivy.IvySend.ComponentImpl comp = new fr.irit.smac.may.lib.components.distribution.ivy.IvySend.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Component newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.distribution.ivy.IvySend.Requires() {});
  }
}
