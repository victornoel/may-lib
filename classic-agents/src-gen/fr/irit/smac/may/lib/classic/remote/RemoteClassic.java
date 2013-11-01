package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.classic.remote.impl.AbstractRemoteClassicBehaviour;
import fr.irit.smac.may.lib.components.messaging.receiver.AgentRef;
import fr.irit.smac.may.lib.components.messaging.receiver.Receiver;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver;
import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.components.remote.place.Placed;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
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
  public interface Parts<Msg> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.Scheduler.Component scheduler();
    
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
    public fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Component<Msg> receive();
    
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
    public fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Component<Msg,AgentRef> remReceive();
    
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
    public fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component executor();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg> extends fr.irit.smac.may.lib.classic.remote.RemoteClassic.Provides<Msg> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg> implements fr.irit.smac.may.lib.classic.remote.RemoteClassic.Parts<Msg>, fr.irit.smac.may.lib.classic.remote.RemoteClassic.Component<Msg> {
    private final fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg> bridge;
    
    private final RemoteClassic<Msg> implementation;
    
    public ComponentImpl(final RemoteClassic<Msg> implem, final fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.create = implem.make_create();
      assert this.implem_scheduler == null;
      this.implem_scheduler = implem.make_scheduler();
      this.scheduler = this.implem_scheduler.newComponent(new BridgeImpl_scheduler());
      assert this.implem_sender == null;
      this.implem_sender = implem.make_sender();
      this.sender = this.implem_sender.newComponent(new BridgeImpl_sender());
      assert this.implem_receive == null;
      this.implem_receive = implem.make_receive();
      this.receive = this.implem_receive.newComponent(new BridgeImpl_receive());
      assert this.implem_placed == null;
      this.implem_placed = implem.make_placed();
      this.placed = this.implem_placed.newComponent(new BridgeImpl_placed());
      assert this.implem_remReceive == null;
      this.implem_remReceive = implem.make_remReceive();
      this.remReceive = this.implem_remReceive.newComponent(new BridgeImpl_remReceive());
      assert this.implem_fact == null;
      this.implem_fact = implem.make_fact();
      this.fact = this.implem_fact.newComponent(new BridgeImpl_fact());
      assert this.implem_executor == null;
      this.implem_executor = implem.make_executor();
      this.executor = this.implem_executor.newComponent(new BridgeImpl_executor());
      
    }
    
    public final Send<Msg,RemoteAgentRef> send() {
      return this.remReceive.deposit();
    }
    
    public final Pull<Place> thisPlace() {
      return this.placed.thisPlace();
    }
    
    private final CreateRemoteClassic<Msg,RemoteAgentRef> create;
    
    public final CreateRemoteClassic<Msg,RemoteAgentRef> create() {
      return this.create;
    }
    
    private final fr.irit.smac.may.lib.components.scheduling.Scheduler.Component scheduler;
    
    private final Scheduler implem_scheduler;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_scheduler implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Requires {
      public final AdvancedExecutor executor() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.executor.exec();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.Scheduler.Component scheduler() {
      return this.scheduler;
    }
    
    private final fr.irit.smac.may.lib.components.meta.Forward.Component<Send<Msg,RemoteAgentRef>> sender;
    
    private final Forward<Send<Msg,RemoteAgentRef>> implem_sender;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_sender implements fr.irit.smac.may.lib.components.meta.Forward.Requires<Send<Msg,RemoteAgentRef>> {
      public final Send<Msg,RemoteAgentRef> i() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.remReceive.deposit();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.meta.Forward.Component<Send<Msg,RemoteAgentRef>> sender() {
      return this.sender;
    }
    
    private final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Component<Msg> receive;
    
    private final Receiver<Msg> implem_receive;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_receive implements fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Requires<Msg> {
    }
    
    
    public final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Component<Msg> receive() {
      return this.receive;
    }
    
    private final fr.irit.smac.may.lib.components.remote.place.Placed.Component placed;
    
    private final Placed implem_placed;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_placed implements fr.irit.smac.may.lib.components.remote.place.Placed.Requires {
    }
    
    
    public final fr.irit.smac.may.lib.components.remote.place.Placed.Component placed() {
      return this.placed;
    }
    
    private final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Component<Msg,AgentRef> remReceive;
    
    private final RemoteReceiver<Msg,AgentRef> implem_remReceive;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_remReceive implements fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Requires<Msg,AgentRef> {
      public final Send<Msg,AgentRef> localDeposit() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.receive.deposit();
      }
      
      public final Pull<Place> myPlace() {
        return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl.this.placed.thisPlace();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Component<Msg,AgentRef> remReceive() {
      return this.remReceive;
    }
    
    private final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Component<Msg,RemoteAgentRef> fact;
    
    private final RemoteFactory<Msg,RemoteAgentRef> implem_fact;
    
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
    
    private final fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component executor;
    
    private final ExecutorService implem_executor;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_executor implements fr.irit.smac.may.lib.components.scheduling.ExecutorService.Requires {
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component executor() {
      return this.executor;
    }
    
    public void start() {
      this.scheduler.start();
      this.sender.start();
      this.receive.start();
      this.placed.start();
      this.remReceive.start();
      this.fact.start();
      this.executor.start();
      this.implementation.start();
      
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
      public fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Component s();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Component<Msg> r();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<Send<Msg,RemoteAgentRef>> ss();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Component<Msg,AgentRef> rr();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<Msg> extends fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Provides<Msg> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<Msg> implements fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Parts<Msg>, fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Component<Msg> {
      private final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Requires<Msg> bridge;
      
      private final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent<Msg> implem, final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Requires<Msg> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        assert this.implem_arch == null;
        this.implem_arch = implem.make_arch();
        this.arch = this.implem_arch.newComponent(new BridgeImpl_arch());
        assert this.implementation.use_p != null;
        this.p = this.implementation.use_p.newComponent(new BridgeImpl_placed_p());
        assert this.implementation.use_f != null;
        this.f = this.implementation.use_f.newComponent(new BridgeImpl_fact_f());
        assert this.implementation.use_s != null;
        this.s = this.implementation.use_s.newComponent(new BridgeImpl_scheduler_s());
        assert this.implementation.use_r != null;
        this.r = this.implementation.use_r.newComponent(new BridgeImpl_receive_r());
        assert this.implementation.use_ss != null;
        this.ss = this.implementation.use_ss.newComponent(new BridgeImpl_sender_ss());
        assert this.implementation.use_rr != null;
        this.rr = this.implementation.use_rr.newComponent(new BridgeImpl_remReceive_rr());
        
      }
      
      public final Pull<RemoteAgentRef> ref() {
        return this.rr.me();
      }
      
      private final fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Component<Msg,RemoteAgentRef> arch;
      
      private final RemoteClassicAgentComponent<Msg,RemoteAgentRef> implem_arch;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_arch implements fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Requires<Msg,RemoteAgentRef> {
        public final Send<Msg,RemoteAgentRef> send() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.ss.a();
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
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.s.exec();
        }
        
        public final CreateRemoteClassic<Msg,RemoteAgentRef> create() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.f.create();
        }
      }
      
      
      public final fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent.Component<Msg,RemoteAgentRef> arch() {
        return this.arch;
      }
      
      private final fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Component p;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_placed_p implements fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Requires {
      }
      
      
      public final fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Component p() {
        return this.p;
      }
      
      private final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Component<Msg,RemoteAgentRef> f;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_fact_f implements fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Requires<Msg,RemoteAgentRef> {
      }
      
      
      public final fr.irit.smac.may.lib.classic.remote.RemoteFactory.Agent.Component<Msg,RemoteAgentRef> f() {
        return this.f;
      }
      
      private final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Component s;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_scheduler_s implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Requires {
      }
      
      
      public final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Component s() {
        return this.s;
      }
      
      private final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Component<Msg> r;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_receive_r implements fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Requires<Msg> {
        public final Push<Msg> put() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.arch.put();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent.Component<Msg> r() {
        return this.r;
      }
      
      private final fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<Send<Msg,RemoteAgentRef>> ss;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_sender_ss implements fr.irit.smac.may.lib.components.meta.Forward.Agent.Requires<Send<Msg,RemoteAgentRef>> {
      }
      
      
      public final fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<Send<Msg,RemoteAgentRef>> ss() {
        return this.ss;
      }
      
      private final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Component<Msg,AgentRef> rr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_remReceive_rr implements fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Requires<Msg,AgentRef> {
        public final Pull<AgentRef> localMe() {
          return fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl.this.r.me();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent.Component<Msg,AgentRef> rr() {
        return this.rr;
      }
      
      public void start() {
        this.arch.start();
        this.p.start();
        this.f.start();
        this.s.start();
        this.r.start();
        this.ss.start();
        this.rr.start();
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl<Msg> selfComponent;
    
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
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent use_s;
    
    private fr.irit.smac.may.lib.components.messaging.receiver.Receiver.Agent<Msg> use_r;
    
    private fr.irit.smac.may.lib.components.meta.Forward.Agent<Send<Msg,RemoteAgentRef>> use_ss;
    
    private fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteReceiver.Agent<Msg,AgentRef> use_rr;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Component<Msg> newComponent(final fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.Requires<Msg> b) {
      return new fr.irit.smac.may.lib.classic.remote.RemoteClassic.ClassicAgent.ComponentImpl<Msg>(this, b);
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
  protected abstract Scheduler make_scheduler();
  
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
  protected abstract Receiver<Msg> make_receive();
  
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
  protected abstract RemoteReceiver<Msg,AgentRef> make_remReceive();
  
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
  protected abstract ExecutorService make_executor();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.classic.remote.RemoteClassic.Component<Msg> newComponent(final fr.irit.smac.may.lib.classic.remote.RemoteClassic.Requires<Msg> b) {
    return new fr.irit.smac.may.lib.classic.remote.RemoteClassic.ComponentImpl<Msg>(this, b);
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
    assert this.selfComponent.implem_scheduler != null;
    assert implem.use_s == null;
    implem.use_s = this.selfComponent.implem_scheduler._createImplementationOfAgent();
    assert this.selfComponent.implem_receive != null;
    assert implem.use_r == null;
    implem.use_r = this.selfComponent.implem_receive._createImplementationOfAgent(name);
    assert this.selfComponent.implem_sender != null;
    assert implem.use_ss == null;
    implem.use_ss = this.selfComponent.implem_sender._createImplementationOfAgent();
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
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <Msg> fr.irit.smac.may.lib.classic.remote.RemoteClassic.Component<Msg> newComponent(final RemoteClassic<Msg> _compo) {
    return _compo.newComponent();
  }
}
