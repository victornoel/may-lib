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
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.webservices.WebServiceClient.Provides<I> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.webservices.WebServiceClient.Parts<I>, fr.irit.smac.may.lib.webservices.WebServiceClient.Component<I> {
    private final fr.irit.smac.may.lib.webservices.WebServiceClient.Requires<I> bridge;
    
    private final WebServiceClient<I> implementation;
    
    public ComponentImpl(final WebServiceClient<I> implem, final fr.irit.smac.may.lib.webservices.WebServiceClient.Requires<I> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.service = implem.make_service();
      
    }
    
    private final RemoteCall<I,String> service;
    
    public final RemoteCall<I,String> service() {
      return this.service;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.webservices.WebServiceClient.ComponentImpl<I> selfComponent;
  
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
    return new fr.irit.smac.may.lib.webservices.WebServiceClient.ComponentImpl<I>(this, b);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.webservices.WebServiceClient.Component<I> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.webservices.WebServiceClient.Requires<I>() {});
  }
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <I> fr.irit.smac.may.lib.webservices.WebServiceClient.Component<I> newComponent(final WebServiceClient<I> _compo) {
    return _compo.newComponent();
  }
}
