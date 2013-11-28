package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class RemoteFactory<Msg, Ref> {
  @SuppressWarnings("all")
  public interface Requires<Msg, Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CreateRemoteClassic<Msg,Ref> infraCreate();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Pull<Place> thisPlace();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg, Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public CreateRemoteClassic<Msg,Ref> factCreate();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg, Ref> extends fr.irit.smac.may.lib.classic.remote.RemoteFactory.Provides<Msg,Ref> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Msg, Ref> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg, Ref> implements fr.irit.smac.may.lib.classic.remote.RemoteFactory.Component<Msg,Ref>, fr.irit.smac.may.lib.classic.remote.RemoteFactory.Parts<Msg,Ref> {
    private final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Requires<Msg,Ref> bridge;
    
    private final RemoteFactory<Msg,Ref> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.factCreate == null;
      this.factCreate = this.implementation.make_factCreate();
      
    }
    
    public ComponentImpl(final RemoteFactory<Msg,Ref> implem, final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Requires<Msg,Ref> b, final boolean initMakes) {
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
    
    private CreateRemoteClassic<Msg,Ref> factCreate;
    
    public final CreateRemoteClassic<Msg,Ref> factCreate() {
      return this.factCreate;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent<Msg, Ref> {
    @SuppressWarnings("all")
    public interface Requires<Msg, Ref> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<Msg, Ref> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public CreateRemoteClassic<Msg,Ref> create();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<Msg, Ref> extends fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Provides<Msg,Ref> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<Msg, Ref> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<Msg, Ref> implements fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Component<Msg,Ref>, fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Parts<Msg,Ref> {
      private final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Requires<Msg,Ref> bridge;
      
      private final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent<Msg,Ref> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.create == null;
        this.create = this.implementation.make_create();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent<Msg,Ref> implem, final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Requires<Msg,Ref> b, final boolean initMakes) {
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
      
      private CreateRemoteClassic<Msg,Ref> create;
      
      public final CreateRemoteClassic<Msg,Ref> create() {
        return this.create;
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.ComponentImpl<Msg,Ref> selfComponent;
    
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
    protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Provides<Msg,Ref> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract CreateRemoteClassic<Msg,Ref> make_create();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Requires<Msg,Ref> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Parts<Msg,Ref> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Component<Msg,Ref> newComponent(final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Requires<Msg,Ref> b) {
      fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.ComponentImpl<Msg,Ref> comp = new fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.ComponentImpl<Msg,Ref>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.classic.remote.RemoteFactory.ComponentImpl<Msg,Ref> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Provides<Msg,Ref> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Requires<Msg,Ref> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Parts<Msg,Ref> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.remote.RemoteFactory.ComponentImpl<Msg,Ref> selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Provides<Msg,Ref> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract CreateRemoteClassic<Msg,Ref> make_factCreate();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Requires<Msg,Ref> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Parts<Msg,Ref> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.classic.remote.RemoteFactory.Component<Msg,Ref> newComponent(final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Requires<Msg,Ref> b) {
    fr.irit.smac.may.lib.classic.remote.RemoteFactory.ComponentImpl<Msg,Ref> comp = new fr.irit.smac.may.lib.classic.remote.RemoteFactory.ComponentImpl<Msg,Ref>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent<Msg,Ref> make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent<Msg,Ref> _createImplementationOfAgent() {
    fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent<Msg,Ref> implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Component<Msg,Ref> newAgent() {
    fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent<Msg,Ref> implem = _createImplementationOfAgent();
    return implem.newComponent(new fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Requires<Msg,Ref>() {});
  }
}
