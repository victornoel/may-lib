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
  @SuppressWarnings("all")
  public interface Requires<Msg, Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<Msg,Ref> send();
    
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
    public CreateRemoteClassic<Msg,Ref> create();
  }
  
  
  @SuppressWarnings("all")
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
  
  
  @SuppressWarnings("all")
  public interface Component<Msg, Ref> extends fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Provides<Msg,Ref> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Msg, Ref> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Component<Msg> dispatcher();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour.Component<Msg,Ref> beh();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg, Ref> implements fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Component<Msg,Ref>, fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Parts<Msg,Ref> {
    private final fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Requires<Msg,Ref> bridge;
    
    private final RemoteClassicAgentComponent<Msg,Ref> implementation;
    
    protected void initParts() {
      assert this.implem_dispatcher == null;
      this.implem_dispatcher = this.implementation.make_dispatcher();
      assert this.dispatcher == null;
      this.dispatcher = this.implem_dispatcher.newComponent(new BridgeImpl_dispatcher());
      assert this.implem_beh == null;
      this.implem_beh = this.implementation.make_beh();
      assert this.beh == null;
      this.beh = this.implem_beh.newComponent(new BridgeImpl_beh());
      
    }
    
    protected void initProvidedPorts() {
      assert this.die == null;
      this.die = this.implementation.make_die();
      
    }
    
    public ComponentImpl(final RemoteClassicAgentComponent<Msg,Ref> implem, final fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Requires<Msg,Ref> b, final boolean initMakes) {
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
    
    public final Push<Msg> put() {
      return this.dispatcher.dispatch();
    }
    
    private Do die;
    
    public final Do die() {
      return this.die;
    }
    
    private fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Component<Msg> dispatcher;
    
    private SequentialDispatcher<Msg> implem_dispatcher;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_dispatcher implements fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Requires<Msg> {
      public final Executor executor() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl.this.bridge.executor();
      }
      
      public final Push<Msg> handler() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl.this.beh.cycle();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Component<Msg> dispatcher() {
      return this.dispatcher;
    }
    
    private fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour.Component<Msg,Ref> beh;
    
    private RemoteClassicBehaviour<Msg,Ref> implem_beh;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_beh implements fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour.Requires<Msg,Ref> {
      public final Send<Msg,Ref> send() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl.this.bridge.send();
      }
      
      public final Pull<Ref> me() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl.this.bridge.me();
      }
      
      public final Do die() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl.this.die();
      }
      
      public final CreateRemoteClassic<Msg,Ref> create() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl.this.bridge.create();
      }
    }
    
    
    public final fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour.Component<Msg,Ref> beh() {
      return this.beh;
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl<Msg,Ref> selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Provides<Msg,Ref> provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Requires<Msg,Ref> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Parts<Msg,Ref> parts() {
    assert this.selfComponent != null;
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
  protected abstract RemoteClassicBehaviour<Msg,Ref> make_beh();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Component<Msg,Ref> newComponent(final fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Requires<Msg,Ref> b) {
    fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl<Msg,Ref> comp = new fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.ComponentImpl<Msg,Ref>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
