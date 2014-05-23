package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionStatus;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.List;

@SuppressWarnings("all")
public abstract class IvyReceive {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<List<String>> receive();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements IvyReceive.Component, IvyReceive.Parts {
    private final IvyReceive.Requires bridge;
    
    private final IvyReceive implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.bindMsg == null: "This is a bug.";
      this.bindMsg = this.implementation.make_bindMsg();
      if (this.bindMsg == null) {
      	throw new RuntimeException("make_bindMsg() in fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive should not return null.");
      }
      assert this.connectionStatus == null: "This is a bug.";
      this.connectionStatus = this.implementation.make_connectionStatus();
      if (this.connectionStatus == null) {
      	throw new RuntimeException("make_connectionStatus() in fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive should not return null.");
      }
      assert this.connect == null: "This is a bug.";
      this.connect = this.implementation.make_connect();
      if (this.connect == null) {
      	throw new RuntimeException("make_connect() in fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive should not return null.");
      }
      assert this.disconnect == null: "This is a bug.";
      this.disconnect = this.implementation.make_disconnect();
      if (this.disconnect == null) {
      	throw new RuntimeException("make_disconnect() in fr.irit.smac.may.lib.components.distribution.ivy.IvyReceive should not return null.");
      }
      
    }
    
    public ComponentImpl(final IvyReceive implem, final IvyReceive.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
      
    }
    
    private Push<String> bindMsg;
    
    public Push<String> bindMsg() {
      return this.bindMsg;
    }
    
    private Pull<IvyConnectionStatus> connectionStatus;
    
    public Pull<IvyConnectionStatus> connectionStatus() {
      return this.connectionStatus;
    }
    
    private Push<IvyConnectionConfig> connect;
    
    public Push<IvyConnectionConfig> connect() {
      return this.connect;
    }
    
    private Do disconnect;
    
    public Do disconnect() {
      return this.disconnect;
    }
  }
  
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
  
  public interface Component extends IvyReceive.Provides {
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   */
  private boolean started = false;;
  
  private IvyReceive.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
    
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected IvyReceive.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
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
  protected IvyReceive.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected IvyReceive.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized IvyReceive.Component _newComponent(final IvyReceive.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of IvyReceive has already been used to create a component, use another one.");
    }
    this.init = true;
    IvyReceive.ComponentImpl comp = new IvyReceive.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
