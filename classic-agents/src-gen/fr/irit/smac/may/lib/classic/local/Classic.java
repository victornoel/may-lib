package fr.irit.smac.may.lib.classic.local;

import fr.irit.smac.may.lib.classic.impl.AbstractClassicBehaviour;
import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.components.interactions.AsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.DirectReferences;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class Classic<Msg> {
  @SuppressWarnings("all")
  public interface Requires<Msg> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg,DirRef> send();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public CreateClassic<Msg,DirRef> create();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Do stop();
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
    public fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Push<Msg>> refs();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<Msg,DirRef> receive();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.meta.Forward.Component<CreateClassic<Msg,DirRef>> fact();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component executor();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg> extends fr.irit.smac.may.lib.classic.local.Classic.Provides<Msg> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg> implements fr.irit.smac.may.lib.classic.local.Classic.Parts<Msg>, fr.irit.smac.may.lib.classic.local.Classic.Component<Msg> {
    private final fr.irit.smac.may.lib.classic.local.Classic.Requires<Msg> bridge;
    
    private final Classic<Msg> implementation;
    
    public ComponentImpl(final Classic<Msg> implem, final fr.irit.smac.may.lib.classic.local.Classic.Requires<Msg> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.create = implem.make_create();
      assert this.implem_scheduler == null;
      this.implem_scheduler = implem.make_scheduler();
      this.scheduler = this.implem_scheduler.newComponent(new BridgeImpl_scheduler());
      assert this.implem_refs == null;
      this.implem_refs = implem.make_refs();
      this.refs = this.implem_refs.newComponent(new BridgeImpl_refs());
      assert this.implem_receive == null;
      this.implem_receive = implem.make_receive();
      this.receive = this.implem_receive.newComponent(new BridgeImpl_receive());
      assert this.implem_fact == null;
      this.implem_fact = implem.make_fact();
      this.fact = this.implem_fact.newComponent(new BridgeImpl_fact());
      assert this.implem_executor == null;
      this.implem_executor = implem.make_executor();
      this.executor = this.implem_executor.newComponent(new BridgeImpl_executor());
      
    }
    
    public final Send<Msg,DirRef> send() {
      return this.receive.deposit();
    }
    
    private final CreateClassic<Msg,DirRef> create;
    
    public final CreateClassic<Msg,DirRef> create() {
      return this.create;
    }
    
    public final Do stop() {
      return this.executor.stop();
    }
    
    private final fr.irit.smac.may.lib.components.scheduling.Scheduler.Component scheduler;
    
    private final Scheduler implem_scheduler;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_scheduler implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Requires {
      public final AdvancedExecutor executor() {
        return fr.irit.smac.may.lib.classic.local.Classic.ComponentImpl.this.executor.exec();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.Scheduler.Component scheduler() {
      return this.scheduler;
    }
    
    private final fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Push<Msg>> refs;
    
    private final DirectReferences<Push<Msg>> implem_refs;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_refs implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<Push<Msg>> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Push<Msg>> refs() {
      return this.refs;
    }
    
    private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<Msg,DirRef> receive;
    
    private final AsyncReceiver<Msg,DirRef> implem_receive;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_receive implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<Msg,DirRef> {
      public final Call<Push<Msg>,DirRef> call() {
        return fr.irit.smac.may.lib.classic.local.Classic.ComponentImpl.this.refs.call();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<Msg,DirRef> receive() {
      return this.receive;
    }
    
    private final fr.irit.smac.may.lib.components.meta.Forward.Component<CreateClassic<Msg,DirRef>> fact;
    
    private final Forward<CreateClassic<Msg,DirRef>> implem_fact;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_fact implements fr.irit.smac.may.lib.components.meta.Forward.Requires<CreateClassic<Msg,DirRef>> {
      public final CreateClassic<Msg,DirRef> i() {
        return fr.irit.smac.may.lib.classic.local.Classic.ComponentImpl.this.create();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.meta.Forward.Component<CreateClassic<Msg,DirRef>> fact() {
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
      this.refs.start();
      this.receive.start();
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
      public Pull<DirRef> me();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<Msg> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.classic.local.ClassicAgentComponent.Component<Msg,DirRef> arch();
      
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
      public fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<CreateClassic<Msg,DirRef>> f();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<Msg,DirRef> receive();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Push<Msg>> ref();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<Msg,DirRef> ss();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<Msg> extends fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Provides<Msg> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<Msg> implements fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Parts<Msg>, fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Component<Msg> {
      private final fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Requires<Msg> bridge;
      
      private final fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent<Msg> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent<Msg> implem, final fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Requires<Msg> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        assert this.implem_arch == null;
        this.implem_arch = implem.make_arch();
        this.arch = this.implem_arch.newComponent(new BridgeImpl_arch());
        assert this.implementation.use_s != null;
        this.s = this.implementation.use_s.newComponent(new BridgeImpl_scheduler_s());
        assert this.implementation.use_f != null;
        this.f = this.implementation.use_f.newComponent(new BridgeImpl_fact_f());
        assert this.implementation.use_receive != null;
        this.receive = this.implementation.use_receive.newComponent(new BridgeImpl_receive_receive());
        assert this.implementation.use_ref != null;
        this.ref = this.implementation.use_ref.newComponent(new BridgeImpl_refs_ref());
        assert this.implementation.use_ss != null;
        this.ss = this.implementation.use_ss.newComponent(new BridgeImpl_receive_ss());
        
      }
      
      public final Pull<DirRef> me() {
        return this.ref.me();
      }
      
      private final fr.irit.smac.may.lib.classic.local.ClassicAgentComponent.Component<Msg,DirRef> arch;
      
      private final ClassicAgentComponent<Msg,DirRef> implem_arch;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_arch implements fr.irit.smac.may.lib.classic.local.ClassicAgentComponent.Requires<Msg,DirRef> {
        public final Send<Msg,DirRef> send() {
          return fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl.this.ss.send();
        }
        
        public final Pull<DirRef> me() {
          return fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl.this.ref.me();
        }
        
        public final Executor executor() {
          return fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl.this.s.exec();
        }
        
        public final Do die() {
          return fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl.this.s.stop();
        }
        
        public final CreateClassic<Msg,DirRef> create() {
          return fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl.this.f.a();
        }
      }
      
      
      public final fr.irit.smac.may.lib.classic.local.ClassicAgentComponent.Component<Msg,DirRef> arch() {
        return this.arch;
      }
      
      private final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Component s;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_scheduler_s implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Requires {
      }
      
      
      public final fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent.Component s() {
        return this.s;
      }
      
      private final fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<CreateClassic<Msg,DirRef>> f;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_fact_f implements fr.irit.smac.may.lib.components.meta.Forward.Agent.Requires<CreateClassic<Msg,DirRef>> {
      }
      
      
      public final fr.irit.smac.may.lib.components.meta.Forward.Agent.Component<CreateClassic<Msg,DirRef>> f() {
        return this.f;
      }
      
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<Msg,DirRef> receive;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_receive_receive implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<Msg,DirRef> {
        public final Push<Msg> put() {
          return fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl.this.arch.put();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<Msg,DirRef> receive() {
        return this.receive;
      }
      
      private final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Push<Msg>> ref;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_refs_ref implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Requires<Push<Msg>> {
        public final Push<Msg> toCall() {
          return fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl.this.receive.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Push<Msg>> ref() {
        return this.ref;
      }
      
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<Msg,DirRef> ss;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_receive_ss implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<Msg,DirRef> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<Msg,DirRef> ss() {
        return this.ss;
      }
      
      public void start() {
        this.arch.start();
        this.s.start();
        this.f.start();
        this.receive.start();
        this.ref.start();
        this.ss.start();
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl<Msg> selfComponent;
    
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
    protected fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Provides<Msg> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Requires<Msg> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Parts<Msg> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract ClassicAgentComponent<Msg,DirRef> make_arch();
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduler.Agent use_s;
    
    private fr.irit.smac.may.lib.components.meta.Forward.Agent<CreateClassic<Msg,DirRef>> use_f;
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<Msg,DirRef> use_receive;
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<Push<Msg>> use_ref;
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<Msg,DirRef> use_ss;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Component<Msg> newComponent(final fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Requires<Msg> b) {
      return new fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.ComponentImpl<Msg>(this, b);
    }
    
    private fr.irit.smac.may.lib.classic.local.Classic.ComponentImpl<Msg> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.local.Classic.Provides<Msg> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.local.Classic.Requires<Msg> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.local.Classic.Parts<Msg> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.local.Classic.ComponentImpl<Msg> selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.local.Classic.Provides<Msg> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract CreateClassic<Msg,DirRef> make_create();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.local.Classic.Requires<Msg> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.local.Classic.Parts<Msg> parts() {
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
  protected abstract DirectReferences<Push<Msg>> make_refs();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AsyncReceiver<Msg,DirRef> make_receive();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Forward<CreateClassic<Msg,DirRef>> make_fact();
  
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
  public fr.irit.smac.may.lib.classic.local.Classic.Component<Msg> newComponent(final fr.irit.smac.may.lib.classic.local.Classic.Requires<Msg> b) {
    return new fr.irit.smac.may.lib.classic.local.Classic.ComponentImpl<Msg>(this, b);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent<Msg> make_ClassicAgent(final AbstractClassicBehaviour<Msg,DirRef> beh, final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent<Msg> _createImplementationOfClassicAgent(final AbstractClassicBehaviour<Msg,DirRef> beh, final String name) {
    fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent<Msg> implem = make_ClassicAgent(beh,name);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_scheduler != null;
    assert implem.use_s == null;
    implem.use_s = this.selfComponent.implem_scheduler._createImplementationOfAgent();
    assert this.selfComponent.implem_fact != null;
    assert implem.use_f == null;
    implem.use_f = this.selfComponent.implem_fact._createImplementationOfAgent();
    assert this.selfComponent.implem_receive != null;
    assert implem.use_receive == null;
    implem.use_receive = this.selfComponent.implem_receive._createImplementationOfReceiver();
    assert this.selfComponent.implem_refs != null;
    assert implem.use_ref == null;
    implem.use_ref = this.selfComponent.implem_refs._createImplementationOfCallee(name);
    assert this.selfComponent.implem_receive != null;
    assert implem.use_ss == null;
    implem.use_ss = this.selfComponent.implem_receive._createImplementationOfSender();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Component<Msg> newClassicAgent(final AbstractClassicBehaviour<Msg,DirRef> beh, final String name) {
    fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent<Msg> implem = _createImplementationOfClassicAgent(beh,name);
    return implem.newComponent(new fr.irit.smac.may.lib.classic.local.Classic.ClassicAgent.Requires<Msg>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.classic.local.Classic.Component<Msg> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.classic.local.Classic.Requires<Msg>() {});
  }
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static <Msg> fr.irit.smac.may.lib.classic.local.Classic.Component<Msg> newComponent(final Classic<Msg> _compo) {
    return _compo.newComponent();
  }
}
