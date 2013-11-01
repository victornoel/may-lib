package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionStatus;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.List;

@SuppressWarnings("all")
public abstract class IvyReceive {
  @SuppressWarnings("all")
  public interface Requires {
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
    public Push<String> bindMsg();
    
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
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Provides {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Parts, fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Component {
    private final fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Requires bridge;
    
    private final IvyReceive implementation;
    
    public ComponentImpl(final IvyReceive implem, final fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Requires b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.bindMsg = implem.make_bindMsg();
      this.connectionStatus = implem.make_connectionStatus();
      this.connect = implem.make_connect();
      this.disconnect = implem.make_disconnect();
      
    }
    
    private final Push<String> bindMsg;
    
    public final Push<String> bindMsg() {
      return this.bindMsg;
    }
    
    private final Pull<IvyConnectionStatus> connectionStatus;
    
    public final Pull<IvyConnectionStatus> connectionStatus() {
      return this.connectionStatus;
    }
    
    private final Push<IvyConnectionConfig> connect;
    
    public final Push<IvyConnectionConfig> connect() {
      return this.connect;
    }
    
    private final Do disconnect;
    
    public final Do disconnect() {
      return this.disconnect;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<String> make_bindMsg();
  
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
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Component newComponent(final fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.Requires b) {
    return new fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive.ComponentImpl(this, b);
  }
}
