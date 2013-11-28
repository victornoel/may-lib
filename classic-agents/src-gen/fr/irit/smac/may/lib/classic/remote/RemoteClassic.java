package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.classic.remote.impl.AbstractRemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.components.remote.place.Placed;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class RemoteClassic<Msg> {
  @SuppressWarnings("all")
  public interface Requires<Msg> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg,RemoteAgentRef> send();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Place> thisPlace();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public CreateRemoteClassic<Msg,RemoteAgentRef> create();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg> extends fr.irit.smac.may.lib.classic.remote.RemoteClassic.Provides<Msg> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Msg> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.meta.Forward.Component<Send<Msg,RemoteAgentRef>> sender();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Component<Msg> receive();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.remote.place.Placed.Component placed();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Component<Msg,DirRef> remReceive();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.classic.remote.RemoteFactory.Component<Msg,RemoteAgentRef> fact();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component executor();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg> implements fr.irit.smac.may.lib.classic.remote.RemoteClassic.Component<Msg>, fr.irit.smac.may.lib.classic.remote.RemoteClassic.Parts<Msg> {
    private final fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg> bridge;
    
    private final RemoteClassic<Msg> implementation;
    
    protected void initParts() {
      assert this.implem_sender == null;
      this.implem_sender = this.implementation.make_sender();
      assert this.sender == null;
      this.sender = this.implem_sender.newComponent(new BridgeImpl_sender());
      assert this.implem_receive == null;
      this.implem_receive = this.implementation.make_receive();
      assert this.receive == null;
      this.receive = this.implem_receive.newComponent(new BridgeImpl_receive());
      assert this.implem_placed == null;
      this.implem_placed = this.implementation.make_placed();
      assert this.placed == null;
      this.placed = this.implem_placed.newComponent(new BridgeImpl_placed());
      assert this.implem_remReceive == null;
      this.implem_remReceive = this.implementation.make_remReceive();
      assert this.remReceive == null;
      this.remReceive = this.implem_remReceive.newComponent(new BridgeImpl_remReceive());
      assert this.implem_fact == null;
      this.implem_fact = this.implementation.make_fact();
      assert this.fact == null;
      this.fact = this.implem_fact.newComponent(new BridgeImpl_fact());
      assert this.implem_executor == null;
      this.implem_executor = this.implementation.make_executor();
      assert this.executor == null;
      this.executor = this.implem_executor.newComponent(new BridgeImpl_executor());
      
    }
    
    protected void initProvidedPorts() {
      assert this.create == null;
      this.create = this.implementation.make_create();
      
    }
    
    public ComponentImpl(final RemoteClassic<Msg> implem, final fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg> b, final boolean initMakes) {
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
    
    public final Send<Msg,RemoteAgentRef> send() {
      return this.remReceive.deposit();
    }
    
    public final Pull<Place> thisPlace() {
      return this.placed.thisPlace();
    }
    
    private CreateRemoteClassic<Msg,RemoteAgentRef> create;
    
    public final CreateRemoteClassic<Msg,RemoteAgentRef> create() {
      return this.create;
    }
    
    private fr.irit.smac.may.lib.components.meta.Forward.Component<Send<Msg,RemoteAgentRef>> sender;
    
    private Forward<Send<Msg,RemoteAgentRef>> implem_sender;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_sender implements fr.irit.smac.may.lib.components.meta.Forward.Requires<Send<Msg,RemoteAgentRef>> {
      public final Send<Msg,RemoteAgentRef> forwardedPort() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.remReceive.deposit();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.meta.Forward.Component<Send<Msg,RemoteAgentRef>> sender() {
      return this.sender;
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Component<Msg> receive;
    
    private DirRefAsyncReceiver<Msg> implem_receive;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_receive implements fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Requires<Msg> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Component<Msg> receive() {
      return this.receive;
    }
    
    private fr.irit.smac.may.lib.components.remote.place.Placed.Component placed;
    
    private Placed implem_placed;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_placed implements fr.irit.smac.may.lib.components.remote.place.Placed.Requires {
    }
    
    
    public final fr.irit.smac.may.lib.components.remote.place.Placed.Component placed() {
      return this.placed;
    }
    
    private fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Component<Msg,DirRef> remReceive;
    
    private RemoteReceiver<Msg,DirRef> implem_remReceive;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_remReceive implements fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Requires<Msg,DirRef> {
      public final Send<Msg,DirRef> localDeposit() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.receive.deposit();
      }
      
      public final Pull<Place> myPlace() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.placed.thisPlace();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Component<Msg,DirRef> remReceive() {
      return this.remReceive;
    }
    
    private fr.irit.smac.may.lib.classic.remote.RemoteFactory.Component<Msg,RemoteAgentRef> fact;
    
    private RemoteFactory<Msg,RemoteAgentRef> implem_fact;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_fact implements fr.irit.smac.may.lib.classic.remote.RemoteFactory.Requires<Msg,RemoteAgentRef> {
      public final CreateRemoteClassic<Msg,RemoteAgentRef> infraCreate() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.create();
      }
      
      public final Pull<Place> thisPlace() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.placed.thisPlace();
      }
    }
    
    
    public final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Component<Msg,RemoteAgentRef> fact() {
      return this.fact;
    }
    
    private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component executor;
    
    private ExecutorServiceWrapper implem_executor;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_executor implements fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Requires {
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component executor() {
      return this.executor;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class ClassicAgent<Msg> {
    @SuppressWarnings("all")
    public interface Requires<Msg> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<Msg> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<RemoteAgentRef> ref();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<Msg> extends fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Provides<Msg> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<Msg> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Component<Msg,RemoteAgentRef> arch();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Component p();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Component<Msg,RemoteAgentRef> f();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Component s();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Component<Msg> r();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<Send<Msg,RemoteAgentRef>> ss();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Component<Msg,DirRef> rr();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<Msg> implements fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Component<Msg>, fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Parts<Msg> {
      private final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Requires<Msg> bridge;
      
      private final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> implementation;
      
      protected void initParts() {
        assert this.implem_arch == null;
        this.implem_arch = this.implementation.make_arch();
        assert this.arch == null;
        this.arch = this.implem_arch.newComponent(new BridgeImpl_arch());
        assert this.implementation.use_p != null;
        assert this.p == null;
        this.p = this.implementation.use_p.newComponent(new BridgeImpl_placed_p());
        assert this.implementation.use_f != null;
        assert this.f == null;
        this.f = this.implementation.use_f.newComponent(new BridgeImpl_fact_f());
        assert this.implementation.use_s != null;
        assert this.s == null;
        this.s = this.implementation.use_s.newComponent(new BridgeImpl_executor_s());
        assert this.implementation.use_r != null;
        assert this.r == null;
        this.r = this.implementation.use_r.newComponent(new BridgeImpl_receive_r());
        assert this.implementation.use_ss != null;
        assert this.ss == null;
        this.ss = this.implementation.use_ss.newComponent(new BridgeImpl_sender_ss());
        assert this.implementation.use_rr != null;
        assert this.rr == null;
        this.rr = this.implementation.use_rr.newComponent(new BridgeImpl_remReceive_rr());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> implem, final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Requires<Msg> b, final boolean initMakes) {
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
      
      public final Pull<RemoteAgentRef> ref() {
        return this.rr.me();
      }
      
      private fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Component<Msg,RemoteAgentRef> arch;
      
      private RemoteClassicAgentComponent<Msg,RemoteAgentRef> implem_arch;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_arch implements fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Requires<Msg,RemoteAgentRef> {
        public final Send<Msg,RemoteAgentRef> send() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.ss.forwardedPort();
        }
        
        public final Pull<RemoteAgentRef> me() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.rr.me();
        }
        
        public final Do stopExec() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.s.stop();
        }
        
        public final Do stopReceive() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.rr.disconnect();
        }
        
        public final Executor executor() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.s.executor();
        }
        
        public final CreateRemoteClassic<Msg,RemoteAgentRef> create() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.f.create();
        }
      }
      
      
      public final fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Component<Msg,RemoteAgentRef> arch() {
        return this.arch;
      }
      
      private fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Component p;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_placed_p implements fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Requires {
      }
      
      
      public final fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Component p() {
        return this.p;
      }
      
      private fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Component<Msg,RemoteAgentRef> f;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_fact_f implements fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Requires<Msg,RemoteAgentRef> {
      }
      
      
      public final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Component<Msg,RemoteAgentRef> f() {
        return this.f;
      }
      
      private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Component s;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_executor_s implements fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Requires {
      }
      
      
      public final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Component s() {
        return this.s;
      }
      
      private fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Component<Msg> r;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_receive_r implements fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Requires<Msg> {
        public final Push<Msg> put() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.arch.put();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Component<Msg> r() {
        return this.r;
      }
      
      private fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<Send<Msg,RemoteAgentRef>> ss;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_sender_ss implements fr.irit.smac.may.lib.components.meta.Forward.Caller.Requires<Send<Msg,RemoteAgentRef>> {
      }
      
      
      public final fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<Send<Msg,RemoteAgentRef>> ss() {
        return this.ss;
      }
      
      private fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Component<Msg,DirRef> rr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_remReceive_rr implements fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Requires<Msg,DirRef> {
        public final Pull<DirRef> localMe() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.r.me();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Component<Msg,DirRef> rr() {
        return this.rr;
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl<Msg> selfComponent;
    
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
    protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Provides<Msg> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Requires<Msg> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Parts<Msg> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract RemoteClassicAgentComponent<Msg,RemoteAgentRef> make_arch();
    
    private fr.irit.smac.may.lib.components.remote.place.Placed.Agent use_p;
    
    private fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent<Msg,RemoteAgentRef> use_f;
    
    private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing use_s;
    
    private fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver<Msg> use_r;
    
    private fr.irit.smac.may.lib.components.meta.Forward.Caller<Send<Msg,RemoteAgentRef>> use_ss;
    
    private fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent<Msg,DirRef> use_rr;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Component<Msg> newComponent(final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Requires<Msg> b) {
      fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl<Msg> comp = new fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl<Msg>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl<Msg> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.Provides<Msg> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.Parts<Msg> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl<Msg> selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.Provides<Msg> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract CreateRemoteClassic<Msg,RemoteAgentRef> make_create();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.Parts<Msg> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Forward<Send<Msg,RemoteAgentRef>> make_sender();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract DirRefAsyncReceiver<Msg> make_receive();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Placed make_placed();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract RemoteReceiver<Msg,DirRef> make_remReceive();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract RemoteFactory<Msg,RemoteAgentRef> make_fact();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ExecutorServiceWrapper make_executor();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.classic.remote.RemoteClassic.Component<Msg> newComponent(final fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg> b) {
    fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl<Msg> comp = new fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl<Msg>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> make_ClassicAgent(final AbstractRemoteClassicBehaviour<Msg,RemoteAgentRef> beh, final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> _createImplementationOfClassicAgent(final AbstractRemoteClassicBehaviour<Msg,RemoteAgentRef> beh, final String name) {
    fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> implem = make_ClassicAgent(beh,name);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_placed != null;
    assert implem.use_p == null;
    implem.use_p = this.selfComponent.implem_placed._createImplementationOfAgent();
    assert this.selfComponent.implem_fact != null;
    assert implem.use_f == null;
    implem.use_f = this.selfComponent.implem_fact._createImplementationOfAgent();
    assert this.selfComponent.implem_executor != null;
    assert implem.use_s == null;
    implem.use_s = this.selfComponent.implem_executor._createImplementationOfExecuting();
    assert this.selfComponent.implem_receive != null;
    assert implem.use_r == null;
    implem.use_r = this.selfComponent.implem_receive._createImplementationOfReceiver(name);
    assert this.selfComponent.implem_sender != null;
    assert implem.use_ss == null;
    implem.use_ss = this.selfComponent.implem_sender._createImplementationOfCaller();
    assert this.selfComponent.implem_remReceive != null;
    assert implem.use_rr == null;
    implem.use_rr = this.selfComponent.implem_remReceive._createImplementationOfAgent();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Component<Msg> newClassicAgent(final AbstractRemoteClassicBehaviour<Msg,RemoteAgentRef> beh, final String name) {
    fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> implem = _createImplementationOfClassicAgent(beh,name);
    return implem.newComponent(new fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Requires<Msg>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.classic.remote.RemoteClassic.Component<Msg> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg>() {});
  }
}
