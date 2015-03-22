package fr.irit.smac.may.lib.components.distribution.ivy;

import fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class IvyBus {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Executor exec();
  }
  
  public interface Component extends IvyBus.Provides {
  }
  
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
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements IvyBus.Component, IvyBus.Parts {
    private final IvyBus.Requires bridge;
    
    private final IvyBus implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    protected void init_disconnect() {
      assert this.disconnect == null: "This is a bug.";
      this.disconnect = this.implementation.make_disconnect();
      if (this.disconnect == null) {
      	throw new RuntimeException("make_disconnect() in fr.irit.smac.may.lib.components.distribution.ivy.IvyBus shall not return null.");
      }
    }
    
    protected void init_bindMsg() {
      assert this.bindMsg == null: "This is a bug.";
      this.bindMsg = this.implementation.make_bindMsg();
      if (this.bindMsg == null) {
      	throw new RuntimeException("make_bindMsg() in fr.irit.smac.may.lib.components.distribution.ivy.IvyBus shall not return null.");
      }
    }
    
    protected void init_unBindMsg() {
      assert this.unBindMsg == null: "This is a bug.";
      this.unBindMsg = this.implementation.make_unBindMsg();
      if (this.unBindMsg == null) {
      	throw new RuntimeException("make_unBindMsg() in fr.irit.smac.may.lib.components.distribution.ivy.IvyBus shall not return null.");
      }
    }
    
    protected void init_send() {
      assert this.send == null: "This is a bug.";
      this.send = this.implementation.make_send();
      if (this.send == null) {
      	throw new RuntimeException("make_send() in fr.irit.smac.may.lib.components.distribution.ivy.IvyBus shall not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_disconnect();
      init_bindMsg();
      init_unBindMsg();
      init_send();
    }
    
    public ComponentImpl(final IvyBus implem, final IvyBus.Requires b, final boolean doInits) {
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
    
    private Do disconnect;
    
    public Do disconnect() {
      return this.disconnect;
    }
    
    private Bind bindMsg;
    
    public Bind bindMsg() {
      return this.bindMsg;
    }
    
    private Push<Integer> unBindMsg;
    
    public Push<Integer> unBindMsg() {
      return this.unBindMsg;
    }
    
    private Push<String> send;
    
    public Push<String> send() {
      return this.send;
    }
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
   * 
   */
  private boolean started = false;;
  
  private IvyBus.ComponentImpl selfComponent;
  
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
  protected IvyBus.Provides provides() {
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
  protected IvyBus.Requires requires() {
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
  protected IvyBus.Parts parts() {
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
  public synchronized IvyBus.Component _newComponent(final IvyBus.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of IvyBus has already been used to create a component, use another one.");
    }
    this.init = true;
    IvyBus.ComponentImpl  _comp = new IvyBus.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
