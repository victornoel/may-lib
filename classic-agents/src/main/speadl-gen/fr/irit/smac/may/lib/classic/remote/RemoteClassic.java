package fr.irit.smac.may.lib.classic.remote;

import fr.irit.smac.may.lib.classic.interfaces.CreateRemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicAgentComponent;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.classic.remote.RemoteFactory;
import fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
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
  public interface Requires<Msg> {
  }
  
  public interface Component<Msg> extends RemoteClassic.Provides<Msg> {
  }
  
  public interface Provides<Msg> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg, RemoteAgentRef> send();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Place> thisPlace();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public CreateRemoteClassic<Msg, RemoteAgentRef> create();
  }
  
  public interface Parts<Msg> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public DirRefAsyncReceiver.Component<Msg> receive();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Placed.Component placed();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public RemoteReceiver.Component<Msg, DirRef> remReceive();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public RemoteFactory.Component<Msg, RemoteAgentRef> fact();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ExecutorServiceWrapper.Component executor();
  }
  
  public static class ComponentImpl<Msg> implements RemoteClassic.Component<Msg>, RemoteClassic.Parts<Msg> {
    private final RemoteClassic.Requires<Msg> bridge;
    
    private final RemoteClassic<Msg> implementation;
    
    public void start() {
      assert this.receive != null: "This is a bug.";
      ((DirRefAsyncReceiver.ComponentImpl<Msg>) this.receive).start();
      assert this.placed != null: "This is a bug.";
      ((Placed.ComponentImpl) this.placed).start();
      assert this.remReceive != null: "This is a bug.";
      ((RemoteReceiver.ComponentImpl<Msg, DirRef>) this.remReceive).start();
      assert this.fact != null: "This is a bug.";
      ((RemoteFactory.ComponentImpl<Msg, RemoteAgentRef>) this.fact).start();
      assert this.executor != null: "This is a bug.";
      ((ExecutorServiceWrapper.ComponentImpl) this.executor).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_receive() {
      assert this.receive == null: "This is a bug.";
      assert this.implem_receive == null: "This is a bug.";
      this.implem_receive = this.implementation.make_receive();
      if (this.implem_receive == null) {
      	throw new RuntimeException("make_receive() in fr.irit.smac.may.lib.classic.remote.RemoteClassic<Msg> should not return null.");
      }
      this.receive = this.implem_receive._newComponent(new BridgeImpl_receive(), false);
      
    }
    
    private void init_placed() {
      assert this.placed == null: "This is a bug.";
      assert this.implem_placed == null: "This is a bug.";
      this.implem_placed = this.implementation.make_placed();
      if (this.implem_placed == null) {
      	throw new RuntimeException("make_placed() in fr.irit.smac.may.lib.classic.remote.RemoteClassic<Msg> should not return null.");
      }
      this.placed = this.implem_placed._newComponent(new BridgeImpl_placed(), false);
      
    }
    
    private void init_remReceive() {
      assert this.remReceive == null: "This is a bug.";
      assert this.implem_remReceive == null: "This is a bug.";
      this.implem_remReceive = this.implementation.make_remReceive();
      if (this.implem_remReceive == null) {
      	throw new RuntimeException("make_remReceive() in fr.irit.smac.may.lib.classic.remote.RemoteClassic<Msg> should not return null.");
      }
      this.remReceive = this.implem_remReceive._newComponent(new BridgeImpl_remReceive(), false);
      
    }
    
    private void init_fact() {
      assert this.fact == null: "This is a bug.";
      assert this.implem_fact == null: "This is a bug.";
      this.implem_fact = this.implementation.make_fact();
      if (this.implem_fact == null) {
      	throw new RuntimeException("make_fact() in fr.irit.smac.may.lib.classic.remote.RemoteClassic<Msg> should not return null.");
      }
      this.fact = this.implem_fact._newComponent(new BridgeImpl_fact(), false);
      
    }
    
    private void init_executor() {
      assert this.executor == null: "This is a bug.";
      assert this.implem_executor == null: "This is a bug.";
      this.implem_executor = this.implementation.make_executor();
      if (this.implem_executor == null) {
      	throw new RuntimeException("make_executor() in fr.irit.smac.may.lib.classic.remote.RemoteClassic<Msg> should not return null.");
      }
      this.executor = this.implem_executor._newComponent(new BridgeImpl_executor(), false);
      
    }
    
    protected void initParts() {
      init_receive();
      init_placed();
      init_remReceive();
      init_fact();
      init_executor();
    }
    
    private void init_create() {
      assert this.create == null: "This is a bug.";
      this.create = this.implementation.make_create();
      if (this.create == null) {
      	throw new RuntimeException("make_create() in fr.irit.smac.may.lib.classic.remote.RemoteClassic<Msg> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_create();
    }
    
    public ComponentImpl(final RemoteClassic<Msg> implem, final RemoteClassic.Requires<Msg> b, final boolean doInits) {
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
    
    public Send<Msg, RemoteAgentRef> send() {
      return this.remReceive().send();
    }
    
    public Pull<Place> thisPlace() {
      return this.placed().thisPlace();
    }
    
    private CreateRemoteClassic<Msg, RemoteAgentRef> create;
    
    public CreateRemoteClassic<Msg, RemoteAgentRef> create() {
      return this.create;
    }
    
    private DirRefAsyncReceiver.Component<Msg> receive;
    
    private DirRefAsyncReceiver<Msg> implem_receive;
    
    private final class BridgeImpl_receive implements DirRefAsyncReceiver.Requires<Msg> {
    }
    
    public final DirRefAsyncReceiver.Component<Msg> receive() {
      return this.receive;
    }
    
    private Placed.Component placed;
    
    private Placed implem_placed;
    
    private final class BridgeImpl_placed implements Placed.Requires {
    }
    
    public final Placed.Component placed() {
      return this.placed;
    }
    
    private RemoteReceiver.Component<Msg, DirRef> remReceive;
    
    private RemoteReceiver<Msg, DirRef> implem_remReceive;
    
    private final class BridgeImpl_remReceive implements RemoteReceiver.Requires<Msg, DirRef> {
      public final Send<Msg, DirRef> localSend() {
        return RemoteClassic.ComponentImpl.this.receive().send();
      }
      
      public final Pull<Place> myPlace() {
        return RemoteClassic.ComponentImpl.this.placed().thisPlace();
      }
    }
    
    public final RemoteReceiver.Component<Msg, DirRef> remReceive() {
      return this.remReceive;
    }
    
    private RemoteFactory.Component<Msg, RemoteAgentRef> fact;
    
    private RemoteFactory<Msg, RemoteAgentRef> implem_fact;
    
    private final class BridgeImpl_fact implements RemoteFactory.Requires<Msg, RemoteAgentRef> {
      public final CreateRemoteClassic<Msg, RemoteAgentRef> infraCreate() {
        return RemoteClassic.ComponentImpl.this.create();
      }
      
      public final Pull<Place> thisPlace() {
        return RemoteClassic.ComponentImpl.this.placed().thisPlace();
      }
    }
    
    public final RemoteFactory.Component<Msg, RemoteAgentRef> fact() {
      return this.fact;
    }
    
    private ExecutorServiceWrapper.Component executor;
    
    private ExecutorServiceWrapper implem_executor;
    
    private final class BridgeImpl_executor implements ExecutorServiceWrapper.Requires {
    }
    
    public final ExecutorServiceWrapper.Component executor() {
      return this.executor;
    }
  }
  
  public static abstract class ClassicAgent<Msg> {
    public interface Requires<Msg> {
    }
    
    public interface Component<Msg> extends RemoteClassic.ClassicAgent.Provides<Msg> {
    }
    
    public interface Provides<Msg> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<RemoteAgentRef> ref();
    }
    
    public interface Parts<Msg> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public RemoteClassicAgentComponent.Component<Msg, RemoteAgentRef> arch();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Placed.Agent.Component p();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public RemoteFactory.Agent.Component<Msg, RemoteAgentRef> f();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ExecutorServiceWrapper.Executing.Component s();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public DirRefAsyncReceiver.Receiver.Component<Msg> r();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public RemoteReceiver.Agent.Component<Msg, DirRef> rr();
    }
    
    public static class ComponentImpl<Msg> implements RemoteClassic.ClassicAgent.Component<Msg>, RemoteClassic.ClassicAgent.Parts<Msg> {
      private final RemoteClassic.ClassicAgent.Requires<Msg> bridge;
      
      private final RemoteClassic.ClassicAgent<Msg> implementation;
      
      public void start() {
        assert this.arch != null: "This is a bug.";
        ((RemoteClassicAgentComponent.ComponentImpl<Msg, RemoteAgentRef>) this.arch).start();
        assert this.p != null: "This is a bug.";
        ((Placed.Agent.ComponentImpl) this.p).start();
        assert this.f != null: "This is a bug.";
        ((RemoteFactory.Agent.ComponentImpl<Msg, RemoteAgentRef>) this.f).start();
        assert this.s != null: "This is a bug.";
        ((ExecutorServiceWrapper.Executing.ComponentImpl) this.s).start();
        assert this.r != null: "This is a bug.";
        ((DirRefAsyncReceiver.Receiver.ComponentImpl<Msg>) this.r).start();
        assert this.rr != null: "This is a bug.";
        ((RemoteReceiver.Agent.ComponentImpl<Msg, DirRef>) this.rr).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_arch() {
        assert this.arch == null: "This is a bug.";
        assert this.implem_arch == null: "This is a bug.";
        this.implem_arch = this.implementation.make_arch();
        if (this.implem_arch == null) {
        	throw new RuntimeException("make_arch() in fr.irit.smac.may.lib.classic.remote.RemoteClassic$ClassicAgent<Msg> should not return null.");
        }
        this.arch = this.implem_arch._newComponent(new BridgeImpl_arch(), false);
        
      }
      
      private void init_p() {
        assert this.p == null: "This is a bug.";
        assert this.implementation.use_p != null: "This is a bug.";
        this.p = this.implementation.use_p._newComponent(new BridgeImpl_placed_p(), false);
        
      }
      
      private void init_f() {
        assert this.f == null: "This is a bug.";
        assert this.implementation.use_f != null: "This is a bug.";
        this.f = this.implementation.use_f._newComponent(new BridgeImpl_fact_f(), false);
        
      }
      
      private void init_s() {
        assert this.s == null: "This is a bug.";
        assert this.implementation.use_s != null: "This is a bug.";
        this.s = this.implementation.use_s._newComponent(new BridgeImpl_executor_s(), false);
        
      }
      
      private void init_r() {
        assert this.r == null: "This is a bug.";
        assert this.implementation.use_r != null: "This is a bug.";
        this.r = this.implementation.use_r._newComponent(new BridgeImpl_receive_r(), false);
        
      }
      
      private void init_rr() {
        assert this.rr == null: "This is a bug.";
        assert this.implementation.use_rr != null: "This is a bug.";
        this.rr = this.implementation.use_rr._newComponent(new BridgeImpl_remReceive_rr(), false);
        
      }
      
      protected void initParts() {
        init_arch();
        init_p();
        init_f();
        init_s();
        init_r();
        init_rr();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final RemoteClassic.ClassicAgent<Msg> implem, final RemoteClassic.ClassicAgent.Requires<Msg> b, final boolean doInits) {
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
      
      public Pull<RemoteAgentRef> ref() {
        return this.rr().me();
      }
      
      private RemoteClassicAgentComponent.Component<Msg, RemoteAgentRef> arch;
      
      private RemoteClassicAgentComponent<Msg, RemoteAgentRef> implem_arch;
      
      private final class BridgeImpl_arch implements RemoteClassicAgentComponent.Requires<Msg, RemoteAgentRef> {
        public final Send<Msg, RemoteAgentRef> send() {
          return RemoteClassic.ClassicAgent.ComponentImpl.this.implementation.ecosystemComponent.remReceive().send();
        }
        
        public final Pull<RemoteAgentRef> me() {
          return RemoteClassic.ClassicAgent.ComponentImpl.this.rr().me();
        }
        
        public final Do stopExec() {
          return RemoteClassic.ClassicAgent.ComponentImpl.this.s().stop();
        }
        
        public final Do stopReceive() {
          return RemoteClassic.ClassicAgent.ComponentImpl.this.rr().disconnect();
        }
        
        public final Executor executor() {
          return RemoteClassic.ClassicAgent.ComponentImpl.this.s().executor();
        }
        
        public final CreateRemoteClassic<Msg, RemoteAgentRef> create() {
          return RemoteClassic.ClassicAgent.ComponentImpl.this.f().create();
        }
      }
      
      public final RemoteClassicAgentComponent.Component<Msg, RemoteAgentRef> arch() {
        return this.arch;
      }
      
      private Placed.Agent.Component p;
      
      private final class BridgeImpl_placed_p implements Placed.Agent.Requires {
      }
      
      public final Placed.Agent.Component p() {
        return this.p;
      }
      
      private RemoteFactory.Agent.Component<Msg, RemoteAgentRef> f;
      
      private final class BridgeImpl_fact_f implements RemoteFactory.Agent.Requires<Msg, RemoteAgentRef> {
      }
      
      public final RemoteFactory.Agent.Component<Msg, RemoteAgentRef> f() {
        return this.f;
      }
      
      private ExecutorServiceWrapper.Executing.Component s;
      
      private final class BridgeImpl_executor_s implements ExecutorServiceWrapper.Executing.Requires {
      }
      
      public final ExecutorServiceWrapper.Executing.Component s() {
        return this.s;
      }
      
      private DirRefAsyncReceiver.Receiver.Component<Msg> r;
      
      private final class BridgeImpl_receive_r implements DirRefAsyncReceiver.Receiver.Requires<Msg> {
        public final Push<Msg> put() {
          return RemoteClassic.ClassicAgent.ComponentImpl.this.arch().put();
        }
      }
      
      public final DirRefAsyncReceiver.Receiver.Component<Msg> r() {
        return this.r;
      }
      
      private RemoteReceiver.Agent.Component<Msg, DirRef> rr;
      
      private final class BridgeImpl_remReceive_rr implements RemoteReceiver.Agent.Requires<Msg, DirRef> {
        public final Pull<DirRef> localMe() {
          return RemoteClassic.ClassicAgent.ComponentImpl.this.r().me();
        }
      }
      
      public final RemoteReceiver.Agent.Component<Msg, DirRef> rr() {
        return this.rr;
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
    
    private RemoteClassic.ClassicAgent.ComponentImpl<Msg> selfComponent;
    
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
    protected RemoteClassic.ClassicAgent.Provides<Msg> provides() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected RemoteClassic.ClassicAgent.Requires<Msg> requires() {
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
    protected RemoteClassic.ClassicAgent.Parts<Msg> parts() {
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
    protected abstract RemoteClassicAgentComponent<Msg, RemoteAgentRef> make_arch();
    
    private Placed.Agent use_p;
    
    private RemoteFactory.Agent<Msg, RemoteAgentRef> use_f;
    
    private ExecutorServiceWrapper.Executing use_s;
    
    private DirRefAsyncReceiver.Receiver<Msg> use_r;
    
    private RemoteReceiver.Agent<Msg, DirRef> use_rr;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized RemoteClassic.ClassicAgent.Component<Msg> _newComponent(final RemoteClassic.ClassicAgent.Requires<Msg> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of ClassicAgent has already been used to create a component, use another one.");
      }
      this.init = true;
      RemoteClassic.ClassicAgent.ComponentImpl<Msg>  _comp = new RemoteClassic.ClassicAgent.ComponentImpl<Msg>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private RemoteClassic.ComponentImpl<Msg> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected RemoteClassic.Provides<Msg> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected RemoteClassic.Requires<Msg> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected RemoteClassic.Parts<Msg> eco_parts() {
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
  
  private RemoteClassic.ComponentImpl<Msg> selfComponent;
  
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
  protected RemoteClassic.Provides<Msg> provides() {
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
  protected abstract CreateRemoteClassic<Msg, RemoteAgentRef> make_create();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected RemoteClassic.Requires<Msg> requires() {
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
  protected RemoteClassic.Parts<Msg> parts() {
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
  protected abstract RemoteReceiver<Msg, DirRef> make_remReceive();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract RemoteFactory<Msg, RemoteAgentRef> make_fact();
  
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
  public synchronized RemoteClassic.Component<Msg> _newComponent(final RemoteClassic.Requires<Msg> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RemoteClassic has already been used to create a component, use another one.");
    }
    this.init = true;
    RemoteClassic.ComponentImpl<Msg>  _comp = new RemoteClassic.ComponentImpl<Msg>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract RemoteClassic.ClassicAgent<Msg> make_ClassicAgent(final RemoteClassicBehaviour<Msg, RemoteAgentRef> beh, final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public RemoteClassic.ClassicAgent<Msg> _createImplementationOfClassicAgent(final RemoteClassicBehaviour<Msg, RemoteAgentRef> beh, final String name) {
    RemoteClassic.ClassicAgent<Msg> implem = make_ClassicAgent(beh,name);
    if (implem == null) {
    	throw new RuntimeException("make_ClassicAgent() in fr.irit.smac.may.lib.classic.remote.RemoteClassic should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_placed != null: "This is a bug.";
    assert implem.use_p == null: "This is a bug.";
    implem.use_p = this.selfComponent.implem_placed._createImplementationOfAgent();
    assert this.selfComponent.implem_fact != null: "This is a bug.";
    assert implem.use_f == null: "This is a bug.";
    implem.use_f = this.selfComponent.implem_fact._createImplementationOfAgent();
    assert this.selfComponent.implem_executor != null: "This is a bug.";
    assert implem.use_s == null: "This is a bug.";
    implem.use_s = this.selfComponent.implem_executor._createImplementationOfExecuting();
    assert this.selfComponent.implem_receive != null: "This is a bug.";
    assert implem.use_r == null: "This is a bug.";
    implem.use_r = this.selfComponent.implem_receive._createImplementationOfReceiver(name);
    assert this.selfComponent.implem_remReceive != null: "This is a bug.";
    assert implem.use_rr == null: "This is a bug.";
    implem.use_rr = this.selfComponent.implem_remReceive._createImplementationOfAgent();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected RemoteClassic.ClassicAgent.Component<Msg> newClassicAgent(final RemoteClassicBehaviour<Msg, RemoteAgentRef> beh, final String name) {
    RemoteClassic.ClassicAgent<Msg> _implem = _createImplementationOfClassicAgent(beh,name);
    return _implem._newComponent(new RemoteClassic.ClassicAgent.Requires<Msg>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public RemoteClassic.Component<Msg> newComponent() {
    return this._newComponent(new RemoteClassic.Requires<Msg>() {}, true);
  }
}
