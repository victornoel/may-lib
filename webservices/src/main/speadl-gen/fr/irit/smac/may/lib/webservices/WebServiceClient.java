package fr.irit.smac.may.lib.webservices;

import fr.irit.smac.may.lib.interfaces.RemoteCall;

@SuppressWarnings("all")
public abstract class WebServiceClient<I> {
  public interface Requires<I> {
  }
  
  public interface Parts<I> {
  }
  
  public static class ComponentImpl<I> implements WebServiceClient.Component<I>, WebServiceClient.Parts<I> {
    private final WebServiceClient.Requires<I> bridge;
    
    private final WebServiceClient<I> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.service == null: "This is a bug.";
      this.service = this.implementation.make_service();
      if (this.service == null) {
      	throw new RuntimeException("make_service() in fr.irit.smac.may.lib.webservices.WebServiceClient should not return null.");
      }
      
    }
    
    public ComponentImpl(final WebServiceClient<I> implem, final WebServiceClient.Requires<I> b, final boolean doInits) {
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
    
    private RemoteCall<I, String> service;
    
    public RemoteCall<I, String> service() {
      return this.service;
    }
  }
  
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public RemoteCall<I, String> service();
  }
  
  public interface Component<I> extends WebServiceClient.Provides<I> {
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
  
  private WebServiceClient.ComponentImpl<I> selfComponent;
  
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
  protected WebServiceClient.Provides<I> provides() {
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
  protected abstract RemoteCall<I, String> make_service();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected WebServiceClient.Requires<I> requires() {
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
  protected WebServiceClient.Parts<I> parts() {
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
  public synchronized WebServiceClient.Component<I> _newComponent(final WebServiceClient.Requires<I> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of WebServiceClient has already been used to create a component, use another one.");
    }
    this.init = true;
    WebServiceClient.ComponentImpl<I> comp = new WebServiceClient.ComponentImpl<I>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public WebServiceClient.Component<I> newComponent() {
    return this._newComponent(new WebServiceClient.Requires<I>() {}, true);
  }
}
