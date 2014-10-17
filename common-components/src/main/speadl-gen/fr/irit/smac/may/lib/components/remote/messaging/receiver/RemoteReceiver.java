package fr.irit.smac.may.lib.components.remote.messaging.receiver;

import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class RemoteReceiver<Msg, LocalRef> {
  public interface Requires<Msg, LocalRef> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<Msg, LocalRef> localSend();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Pull<Place> myPlace();
  }
  
  public interface Component<Msg, LocalRef> extends RemoteReceiver.Provides<Msg, LocalRef> {
  }
  
  public interface Provides<Msg, LocalRef> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg, RemoteAgentRef> send();
  }
  
  public interface Parts<Msg, LocalRef> {
  }
  
  public static class ComponentImpl<Msg, LocalRef> implements RemoteReceiver.Component<Msg, LocalRef>, RemoteReceiver.Parts<Msg, LocalRef> {
    private final RemoteReceiver.Requires<Msg, LocalRef> bridge;
    
    private final RemoteReceiver<Msg, LocalRef> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_send() {
      assert this.send == null: "This is a bug.";
      this.send = this.implementation.make_send();
      if (this.send == null) {
      	throw new RuntimeException("make_send() in fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver<Msg, LocalRef> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_send();
    }
    
    public ComponentImpl(final RemoteReceiver<Msg, LocalRef> implem, final RemoteReceiver.Requires<Msg, LocalRef> b, final boolean doInits) {
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
    
    private Send<Msg, RemoteAgentRef> send;
    
    public Send<Msg, RemoteAgentRef> send() {
      return this.send;
    }
  }
  
  public static abstract class Agent<Msg, LocalRef> {
    public interface Requires<Msg, LocalRef> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<LocalRef> localMe();
    }
    
    public interface Component<Msg, LocalRef> extends RemoteReceiver.Agent.Provides<Msg, LocalRef> {
    }
    
    public interface Provides<Msg, LocalRef> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<RemoteAgentRef> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do disconnect();
    }
    
    public interface Parts<Msg, LocalRef> {
    }
    
    public static class ComponentImpl<Msg, LocalRef> implements RemoteReceiver.Agent.Component<Msg, LocalRef>, RemoteReceiver.Agent.Parts<Msg, LocalRef> {
      private final RemoteReceiver.Agent.Requires<Msg, LocalRef> bridge;
      
      private final RemoteReceiver.Agent<Msg, LocalRef> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_me() {
        assert this.me == null: "This is a bug.";
        this.me = this.implementation.make_me();
        if (this.me == null) {
        	throw new RuntimeException("make_me() in fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver$Agent<Msg, LocalRef> should not return null.");
        }
      }
      
      private void init_disconnect() {
        assert this.disconnect == null: "This is a bug.";
        this.disconnect = this.implementation.make_disconnect();
        if (this.disconnect == null) {
        	throw new RuntimeException("make_disconnect() in fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver$Agent<Msg, LocalRef> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_me();
        init_disconnect();
      }
      
      public ComponentImpl(final RemoteReceiver.Agent<Msg, LocalRef> implem, final RemoteReceiver.Agent.Requires<Msg, LocalRef> b, final boolean doInits) {
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
      
      private Pull<RemoteAgentRef> me;
      
      public Pull<RemoteAgentRef> me() {
        return this.me;
      }
      
      private Do disconnect;
      
      public Do disconnect() {
        return this.disconnect;
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
    
    private RemoteReceiver.Agent.ComponentImpl<Msg, LocalRef> selfComponent;
    
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
    protected RemoteReceiver.Agent.Provides<Msg, LocalRef> provides() {
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
    protected abstract Pull<RemoteAgentRef> make_me();
    
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
    protected RemoteReceiver.Agent.Requires<Msg, LocalRef> requires() {
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
    protected RemoteReceiver.Agent.Parts<Msg, LocalRef> parts() {
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
    public synchronized RemoteReceiver.Agent.Component<Msg, LocalRef> _newComponent(final RemoteReceiver.Agent.Requires<Msg, LocalRef> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Agent has already been used to create a component, use another one.");
      }
      this.init = true;
      RemoteReceiver.Agent.ComponentImpl<Msg, LocalRef>  _comp = new RemoteReceiver.Agent.ComponentImpl<Msg, LocalRef>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private RemoteReceiver.ComponentImpl<Msg, LocalRef> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected RemoteReceiver.Provides<Msg, LocalRef> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected RemoteReceiver.Requires<Msg, LocalRef> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected RemoteReceiver.Parts<Msg, LocalRef> eco_parts() {
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
  
  private RemoteReceiver.ComponentImpl<Msg, LocalRef> selfComponent;
  
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
  protected RemoteReceiver.Provides<Msg, LocalRef> provides() {
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
  protected abstract Send<Msg, RemoteAgentRef> make_send();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected RemoteReceiver.Requires<Msg, LocalRef> requires() {
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
  protected RemoteReceiver.Parts<Msg, LocalRef> parts() {
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
  public synchronized RemoteReceiver.Component<Msg, LocalRef> _newComponent(final RemoteReceiver.Requires<Msg, LocalRef> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RemoteReceiver has already been used to create a component, use another one.");
    }
    this.init = true;
    RemoteReceiver.ComponentImpl<Msg, LocalRef>  _comp = new RemoteReceiver.ComponentImpl<Msg, LocalRef>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract RemoteReceiver.Agent<Msg, LocalRef> make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public RemoteReceiver.Agent<Msg, LocalRef> _createImplementationOfAgent() {
    RemoteReceiver.Agent<Msg, LocalRef> implem = make_Agent();
    if (implem == null) {
    	throw new RuntimeException("make_Agent() in fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
}
