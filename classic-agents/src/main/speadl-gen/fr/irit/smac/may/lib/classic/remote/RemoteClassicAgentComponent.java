package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class RemoteClassicAgentComponent<Msg, Ref> {
  public interface Requires<Msg, Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<Msg, Ref> send();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Pull<Ref> me();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Executor executor();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Do stopExec();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Do stopReceive();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CreateRemoteClassic<Msg, Ref> create();
  }
  
  public interface Parts<Msg, Ref> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public SequentialDispatcher.Component<Msg> dispatcher();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public RemoteClassicBehaviour.Component<Msg, Ref> beh();
  }
  
  public static class ComponentImpl<Msg, Ref> implements RemoteClassicAgentComponent.Component<Msg, Ref>, RemoteClassicAgentComponent.Parts<Msg, Ref> {
    private final RemoteClassicAgentComponent.Requires<Msg, Ref> bridge;
    
    private final RemoteClassicAgentComponent<Msg, Ref> implementation;
    
    public void start() {
      assert this.dispatcher != null: "This is a bug.";
      ((SequentialDispatcher.ComponentImpl<Msg>) this.dispatcher).start();
      assert this.beh != null: "This is a bug.";
      ((RemoteClassicBehaviour.ComponentImpl<Msg, Ref>) this.beh).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.dispatcher == null: "This is a bug.";
      assert this.implem_dispatcher == null: "This is a bug.";
      this.implem_dispatcher = this.implementation.make_dispatcher();
      if (this.implem_dispatcher == null) {
      	throw new RuntimeException("make_dispatcher() in fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent should not return null.");
      }
      this.dispatcher = this.implem_dispatcher._newComponent(new BridgeImpl_dispatcher(), false);
      assert this.beh == null: "This is a bug.";
      assert this.implem_beh == null: "This is a bug.";
      this.implem_beh = this.implementation.make_beh();
      if (this.implem_beh == null) {
      	throw new RuntimeException("make_beh() in fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent should not return null.");
      }
      this.beh = this.implem_beh._newComponent(new BridgeImpl_beh(), false);
      
    }
    
    protected void initProvidedPorts() {
      assert this.die == null: "This is a bug.";
      this.die = this.implementation.make_die();
      if (this.die == null) {
      	throw new RuntimeException("make_die() in fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent should not return null.");
      }
      
    }
    
    public ComponentImpl(final RemoteClassicAgentComponent<Msg, Ref> implem, final RemoteClassicAgentComponent.Requires<Msg, Ref> b, final boolean doInits) {
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
    
    public final Push<Msg> put() {
      return this.dispatcher.dispatch();
    }
    
    private Do die;
    
    public final Do die() {
      return this.die;
    }
    
    private SequentialDispatcher.Component<Msg> dispatcher;
    
    private SequentialDispatcher<Msg> implem_dispatcher;
    
    private final class BridgeImpl_dispatcher implements SequentialDispatcher.Requires<Msg> {
      public final Executor executor() {
        return RemoteClassicAgentComponent.ComponentImpl.this.bridge.executor();
      }
      
      public final Push<Msg> handler() {
        return RemoteClassicAgentComponent.ComponentImpl.this.beh.cycle();
      }
    }
    
    public final SequentialDispatcher.Component<Msg> dispatcher() {
      return this.dispatcher;
    }
    
    private RemoteClassicBehaviour.Component<Msg, Ref> beh;
    
    private RemoteClassicBehaviour<Msg, Ref> implem_beh;
    
    private final class BridgeImpl_beh implements RemoteClassicBehaviour.Requires<Msg, Ref> {
      public final Send<Msg, Ref> send() {
        return RemoteClassicAgentComponent.ComponentImpl.this.bridge.send();
      }
      
      public final Pull<Ref> me() {
        return RemoteClassicAgentComponent.ComponentImpl.this.bridge.me();
      }
      
      public final Do die() {
        return RemoteClassicAgentComponent.ComponentImpl.this.die();
      }
      
      public final CreateRemoteClassic<Msg, Ref> create() {
        return RemoteClassicAgentComponent.ComponentImpl.this.bridge.create();
      }
    }
    
    public final RemoteClassicBehaviour.Component<Msg, Ref> beh() {
      return this.beh;
    }
  }
  
  public interface Provides<Msg, Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Msg> put();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do die();
  }
  
  public interface Component<Msg, Ref> extends RemoteClassicAgentComponent.Provides<Msg, Ref> {
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
  
  private RemoteClassicAgentComponent.ComponentImpl<Msg, Ref> selfComponent;
  
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
  protected RemoteClassicAgentComponent.Provides<Msg, Ref> provides() {
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
  protected abstract Do make_die();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected RemoteClassicAgentComponent.Requires<Msg, Ref> requires() {
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
  protected RemoteClassicAgentComponent.Parts<Msg, Ref> parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract SequentialDispatcher<Msg> make_dispatcher();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract RemoteClassicBehaviour<Msg, Ref> make_beh();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized RemoteClassicAgentComponent.Component<Msg, Ref> _newComponent(final RemoteClassicAgentComponent.Requires<Msg, Ref> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RemoteClassicAgentComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    RemoteClassicAgentComponent.ComponentImpl<Msg, Ref> comp = new RemoteClassicAgentComponent.ComponentImpl<Msg, Ref>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
