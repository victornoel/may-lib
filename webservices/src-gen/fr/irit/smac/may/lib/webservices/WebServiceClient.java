package fr.irit.smac.may.lib.webservices;

import fr.irit.smac.may.lib.interfaces.RemoteCall;

@SuppressWarnings("all")
public abstract class WebServiceClient<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public RemoteCall<I,String> service();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.webservices.WebServiceClient.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.webservices.WebServiceClient.Component<I>, fr.irit.smac.may.lib.webservices.WebServiceClient.Parts<I> {
    private final fr.irit.smac.may.lib.webservices.WebServiceClient.Requires<I> bridge;
    
    private final WebServiceClient<I> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.service == null;
      this.service = this.implementation.make_service();
      
    }
    
    public ComponentImpl(final WebServiceClient<I> implem, final fr.irit.smac.may.lib.webservices.WebServiceClient.Requires<I> b, final boolean initMakes) {
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
    
    private RemoteCall<I,String> service;
    
    public final RemoteCall<I,String> service() {
      return this.service;
    }
  }
  
  
  private fr.irit.smac.may.lib.webservices.WebServiceClient.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.webservices.WebServiceClient.Provides<I> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract RemoteCall<I,String> make_service();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.webservices.WebServiceClient.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.webservices.WebServiceClient.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.webservices.WebServiceClient.Component<I> newComponent(final fr.irit.smac.may.lib.webservices.WebServiceClient.Requires<I> b) {
    fr.irit.smac.may.lib.webservices.WebServiceClient.ComponentImpl<I> comp = new fr.irit.smac.may.lib.webservices.WebServiceClient.ComponentImpl<I>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.webservices.WebServiceClient.Component<I> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.webservices.WebServiceClient.Requires<I>() {});
  }
}
