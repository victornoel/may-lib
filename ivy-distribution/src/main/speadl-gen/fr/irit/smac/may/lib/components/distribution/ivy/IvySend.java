package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionConfig;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyConnectionStatus;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class IvySend {
  public interface Requires {
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements IvySend.Component, IvySend.Parts {
    private final IvySend.Requires bridge;
    
    private final IvySend implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.send == null: "This is a bug.";
      this.send = this.implementation.make_send();
      if (this.send == null) {
      	throw new RuntimeException("make_send() in fr.irit.smac.may.lib.components.distribution.ivy.IvySend should not return null.");
      }
      assert this.connectionStatus == null: "This is a bug.";
      this.connectionStatus = this.implementation.make_connectionStatus();
      if (this.connectionStatus == null) {
      	throw new RuntimeException("make_connectionStatus() in fr.irit.smac.may.lib.components.distribution.ivy.IvySend should not return null.");
      }
      assert this.connect == null: "This is a bug.";
      this.connect = this.implementation.make_connect();
      if (this.connect == null) {
      	throw new RuntimeException("make_connect() in fr.irit.smac.may.lib.components.distribution.ivy.IvySend should not return null.");
      }
      assert this.disconnect == null: "This is a bug.";
      this.disconnect = this.implementation.make_disconnect();
      if (this.disconnect == null) {
      	throw new RuntimeException("make_disconnect() in fr.irit.smac.may.lib.components.distribution.ivy.IvySend should not return null.");
      }
      
    }
    
    public ComponentImpl(final IvySend implem, final IvySend.Requires b, final boolean doInits) {
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
    
    private Push<String> send;
    
    public Push<String> send() {
      return this.send;
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
  
  public interface Component extends IvySend.Provides {
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
  
  private IvySend.ComponentImpl selfComponent;
  
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
  protected IvySend.Provides provides() {
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
  protected IvySend.Requires requires() {
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
  protected IvySend.Parts parts() {
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
  public synchronized IvySend.Component _newComponent(final IvySend.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of IvySend has already been used to create a component, use another one.");
    }
    this.init = true;
    IvySend.ComponentImpl comp = new IvySend.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public IvySend.Component newComponent() {
    return this._newComponent(new IvySend.Requires() {}, true);
  }
}
