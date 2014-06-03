package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class RemoteFactory<Msg, Ref> {
  public interface Requires<Msg, Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CreateRemoteClassic<Msg, Ref> infraCreate();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Pull<Place> thisPlace();
  }
  
  public interface Component<Msg, Ref> extends RemoteFactory.Provides<Msg, Ref> {
  }
  
  public interface Provides<Msg, Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public CreateRemoteClassic<Msg, Ref> factCreate();
  }
  
  public interface Parts<Msg, Ref> {
  }
  
  public static class ComponentImpl<Msg, Ref> implements RemoteFactory.Component<Msg, Ref>, RemoteFactory.Parts<Msg, Ref> {
    private final RemoteFactory.Requires<Msg, Ref> bridge;
    
    private final RemoteFactory<Msg, Ref> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_factCreate() {
      assert this.factCreate == null: "This is a bug.";
      this.factCreate = this.implementation.make_factCreate();
      if (this.factCreate == null) {
      	throw new RuntimeException("make_factCreate() in fr.irit.smac.may.lib.classic.remote.RemoteFactory<Msg, Ref> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_factCreate();
    }
    
    public ComponentImpl(final RemoteFactory<Msg, Ref> implem, final RemoteFactory.Requires<Msg, Ref> b, final boolean doInits) {
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
    
    private CreateRemoteClassic<Msg, Ref> factCreate;
    
    public CreateRemoteClassic<Msg, Ref> factCreate() {
      return this.factCreate;
    }
  }
  
  public abstract static class Agent<Msg, Ref> {
    public interface Requires<Msg, Ref> {
    }
    
    public interface Component<Msg, Ref> extends RemoteFactory.Agent.Provides<Msg, Ref> {
    }
    
    public interface Provides<Msg, Ref> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public CreateRemoteClassic<Msg, Ref> create();
    }
    
    public interface Parts<Msg, Ref> {
    }
    
    public static class ComponentImpl<Msg, Ref> implements RemoteFactory.Agent.Component<Msg, Ref>, RemoteFactory.Agent.Parts<Msg, Ref> {
      private final RemoteFactory.Agent.Requires<Msg, Ref> bridge;
      
      private final RemoteFactory.Agent<Msg, Ref> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_create() {
        assert this.create == null: "This is a bug.";
        this.create = this.implementation.make_create();
        if (this.create == null) {
        	throw new RuntimeException("make_create() in fr.irit.smac.may.lib.classic.remote.RemoteFactory$Agent<Msg, Ref> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_create();
      }
      
      public ComponentImpl(final RemoteFactory.Agent<Msg, Ref> implem, final RemoteFactory.Agent.Requires<Msg, Ref> b, final boolean doInits) {
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
      
      private CreateRemoteClassic<Msg, Ref> create;
      
      public CreateRemoteClassic<Msg, Ref> create() {
        return this.create;
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
    
    private RemoteFactory.Agent.ComponentImpl<Msg, Ref> selfComponent;
    
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
    protected RemoteFactory.Agent.Provides<Msg, Ref> provides() {
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
    protected abstract CreateRemoteClassic<Msg, Ref> make_create();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected RemoteFactory.Agent.Requires<Msg, Ref> requires() {
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
    protected RemoteFactory.Agent.Parts<Msg, Ref> parts() {
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
    public synchronized RemoteFactory.Agent.Component<Msg, Ref> _newComponent(final RemoteFactory.Agent.Requires<Msg, Ref> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Agent has already been used to create a component, use another one.");
      }
      this.init = true;
      RemoteFactory.Agent.ComponentImpl<Msg, Ref>  _comp = new RemoteFactory.Agent.ComponentImpl<Msg, Ref>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private RemoteFactory.ComponentImpl<Msg, Ref> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected RemoteFactory.Provides<Msg, Ref> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected RemoteFactory.Requires<Msg, Ref> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected RemoteFactory.Parts<Msg, Ref> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
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
  
  private RemoteFactory.ComponentImpl<Msg, Ref> selfComponent;
  
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
  protected RemoteFactory.Provides<Msg, Ref> provides() {
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
  protected abstract CreateRemoteClassic<Msg, Ref> make_factCreate();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected RemoteFactory.Requires<Msg, Ref> requires() {
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
  protected RemoteFactory.Parts<Msg, Ref> parts() {
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
  public synchronized RemoteFactory.Component<Msg, Ref> _newComponent(final RemoteFactory.Requires<Msg, Ref> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RemoteFactory has already been used to create a component, use another one.");
    }
    this.init = true;
    RemoteFactory.ComponentImpl<Msg, Ref>  _comp = new RemoteFactory.ComponentImpl<Msg, Ref>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract RemoteFactory.Agent<Msg, Ref> make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public RemoteFactory.Agent<Msg, Ref> _createImplementationOfAgent() {
    RemoteFactory.Agent<Msg, Ref> implem = make_Agent();
    if (implem == null) {
    	throw new RuntimeException("make_Agent() in fr.irit.smac.may.lib.classic.remote.RemoteFactory should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected RemoteFactory.Agent.Component<Msg, Ref> newAgent() {
    RemoteFactory.Agent<Msg, Ref> _implem = _createImplementationOfAgent();
    return _implem._newComponent(new RemoteFactory.Agent.Requires<Msg, Ref>() {},true);
  }
}
