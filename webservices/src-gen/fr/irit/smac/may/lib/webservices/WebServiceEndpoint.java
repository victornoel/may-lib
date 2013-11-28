package fr.irit.smac.may.lib.webservices;

@SuppressWarnings("all")
public abstract class WebServiceEndpoint<I> {
  @SuppressWarnings("all")
  public interface Requires<I> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public I service();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<I> extends fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Provides<I> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<I> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<I> implements fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Component<I>, fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Parts<I> {
    private final fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Requires<I> bridge;
    
    private final WebServiceEndpoint<I> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final WebServiceEndpoint<I> implem, final fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Requires<I> b, final boolean initMakes) {
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
  }
  
  
  private fr.irit.smac.may.lib.webservices.WebServiceEndpoint.ComponentImpl<I> selfComponent;
  
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
  protected fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Provides<I> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Requires<I> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Parts<I> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Component<I> newComponent(final fr.irit.smac.may.lib.webservices.WebServiceEndpoint.Requires<I> b) {
    fr.irit.smac.may.lib.webservices.WebServiceEndpoint.ComponentImpl<I> comp = new fr.irit.smac.may.lib.webservices.WebServiceEndpoint.ComponentImpl<I>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
