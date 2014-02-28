package fr.irit.smac.may.lib.classic.local;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.ClassicAgentComponent;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
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
  public interface Component<Msg> extends Classic.Provides<Msg> {
  }
  
  
  @SuppressWarnings("all")
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
    public Forward.Component<CreateClassic<Msg,DirRef>> fact();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ExecutorServiceWrapper.Component executor();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg> implements Classic.Component<Msg>, Classic.Parts<Msg> {
    private final Classic.Requires<Msg> bridge;
    
    private final Classic<Msg> implementation;
    
    public void start() {
      assert this.receive != null: "This is a bug.";
      ((DirRefAsyncReceiver.ComponentImpl<Msg>) this.receive).start();
      assert this.fact != null: "This is a bug.";
      ((Forward.ComponentImpl<CreateClassic<Msg,DirRef>>) this.fact).start();
      assert this.executor != null: "This is a bug.";
      ((ExecutorServiceWrapper.ComponentImpl) this.executor).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.receive == null: "This is a bug.";
      assert this.implem_receive == null: "This is a bug.";
      this.implem_receive = this.implementation.make_receive();
      if (this.implem_receive == null) {
      	throw new RuntimeException("make_receive() in fr.irit.smac.may.lib.classic.local.Classic should not return null.");
      }
      this.receive = this.implem_receive._newComponent(new BridgeImpl_receive(), false);
      assert this.fact == null: "This is a bug.";
      assert this.implem_fact == null: "This is a bug.";
      this.implem_fact = this.implementation.make_fact();
      if (this.implem_fact == null) {
      	throw new RuntimeException("make_fact() in fr.irit.smac.may.lib.classic.local.Classic should not return null.");
      }
      this.fact = this.implem_fact._newComponent(new BridgeImpl_fact(), false);
      assert this.executor == null: "This is a bug.";
      assert this.implem_executor == null: "This is a bug.";
      this.implem_executor = this.implementation.make_executor();
      if (this.implem_executor == null) {
      	throw new RuntimeException("make_executor() in fr.irit.smac.may.lib.classic.local.Classic should not return null.");
      }
      this.executor = this.implem_executor._newComponent(new BridgeImpl_executor(), false);
      
    }
    
    protected void initProvidedPorts() {
      assert this.create == null: "This is a bug.";
      this.create = this.implementation.make_create();
      if (this.create == null) {
      	throw new RuntimeException("make_create() in fr.irit.smac.may.lib.classic.local.Classic should not return null.");
      }
      
    }
    
    public ComponentImpl(final Classic<Msg> implem, final Classic.Requires<Msg> b, final boolean doInits) {
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
    
    public final Send<Msg,DirRef> send() {
      return this.receive.send();
    }
    
    private CreateClassic<Msg,DirRef> create;
    
    public final CreateClassic<Msg,DirRef> create() {
      return this.create;
    }
    
    public final Do stop() {
      return this.executor.stop();
    }
    
    private DirRefAsyncReceiver.Component<Msg> receive;
    
    private DirRefAsyncReceiver<Msg> implem_receive;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_receive implements DirRefAsyncReceiver.Requires<Msg> {
    }
    
    
    public final DirRefAsyncReceiver.Component<Msg> receive() {
      return this.receive;
    }
    
    private Forward.Component<CreateClassic<Msg,DirRef>> fact;
    
    private Forward<CreateClassic<Msg,DirRef>> implem_fact;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_fact implements Forward.Requires<CreateClassic<Msg,DirRef>> {
      public final CreateClassic<Msg,DirRef> forwardedPort() {
        return Classic.ComponentImpl.this.create();
      }
    }
    
    
    public final Forward.Component<CreateClassic<Msg,DirRef>> fact() {
      return this.fact;
    }
    
    private ExecutorServiceWrapper.Component executor;
    
    private ExecutorServiceWrapper implem_executor;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_executor implements ExecutorServiceWrapper.Requires {
    }
    
    
    public final ExecutorServiceWrapper.Component executor() {
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
      public Pull<DirRef> me();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<Msg> extends Classic.ClassicAgent.Provides<Msg> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<Msg> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ClassicAgentComponent.Component<Msg,DirRef> arch();
      
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
      public Forward.Caller.Component<CreateClassic<Msg,DirRef>> f();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public DirRefAsyncReceiver.Receiver.Component<Msg> receive();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public DirRefAsyncReceiver.Sender.Component<Msg> ss();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<Msg> implements Classic.ClassicAgent.Component<Msg>, Classic.ClassicAgent.Parts<Msg> {
      private final Classic.ClassicAgent.Requires<Msg> bridge;
      
      private final Classic.ClassicAgent<Msg> implementation;
      
      public void start() {
        assert this.arch != null: "This is a bug.";
        ((ClassicAgentComponent.ComponentImpl<Msg,DirRef>) this.arch).start();
        assert this.s != null: "This is a bug.";
        ((ExecutorServiceWrapper.Executing.ComponentImpl) this.s).start();
        assert this.f != null: "This is a bug.";
        ((Forward.Caller.ComponentImpl<CreateClassic<Msg,DirRef>>) this.f).start();
        assert this.receive != null: "This is a bug.";
        ((DirRefAsyncReceiver.Receiver.ComponentImpl<Msg>) this.receive).start();
        assert this.ss != null: "This is a bug.";
        ((DirRefAsyncReceiver.Sender.ComponentImpl<Msg>) this.ss).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.arch == null: "This is a bug.";
        assert this.implem_arch == null: "This is a bug.";
        this.implem_arch = this.implementation.make_arch();
        if (this.implem_arch == null) {
        	throw new RuntimeException("make_arch() in fr.irit.smac.may.lib.classic.local.Classic$ClassicAgent should not return null.");
        }
        this.arch = this.implem_arch._newComponent(new BridgeImpl_arch(), false);
        assert this.s == null: "This is a bug.";
        assert this.implementation.use_s != null: "This is a bug.";
        this.s = this.implementation.use_s._newComponent(new BridgeImpl_executor_s(), false);
        assert this.f == null: "This is a bug.";
        assert this.implementation.use_f != null: "This is a bug.";
        this.f = this.implementation.use_f._newComponent(new BridgeImpl_fact_f(), false);
        assert this.receive == null: "This is a bug.";
        assert this.implementation.use_receive != null: "This is a bug.";
        this.receive = this.implementation.use_receive._newComponent(new BridgeImpl_receive_receive(), false);
        assert this.ss == null: "This is a bug.";
        assert this.implementation.use_ss != null: "This is a bug.";
        this.ss = this.implementation.use_ss._newComponent(new BridgeImpl_receive_ss(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final Classic.ClassicAgent<Msg> implem, final Classic.ClassicAgent.Requires<Msg> b, final boolean doInits) {
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
      
      public final Pull<DirRef> me() {
        return this.receive.me();
      }
      
      private ClassicAgentComponent.Component<Msg,DirRef> arch;
      
      private ClassicAgentComponent<Msg,DirRef> implem_arch;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_arch implements ClassicAgentComponent.Requires<Msg,DirRef> {
        public final Send<Msg,DirRef> send() {
          return Classic.ClassicAgent.ComponentImpl.this.ss.send();
        }
        
        public final Pull<DirRef> me() {
          return Classic.ClassicAgent.ComponentImpl.this.receive.me();
        }
        
        public final Executor executor() {
          return Classic.ClassicAgent.ComponentImpl.this.s.executor();
        }
        
        public final Do die() {
          return Classic.ClassicAgent.ComponentImpl.this.s.stop();
        }
        
        public final CreateClassic<Msg,DirRef> create() {
          return Classic.ClassicAgent.ComponentImpl.this.f.forwardedPort();
        }
      }
      
      
      public final ClassicAgentComponent.Component<Msg,DirRef> arch() {
        return this.arch;
      }
      
      private ExecutorServiceWrapper.Executing.Component s;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_executor_s implements ExecutorServiceWrapper.Executing.Requires {
      }
      
      
      public final ExecutorServiceWrapper.Executing.Component s() {
        return this.s;
      }
      
      private Forward.Caller.Component<CreateClassic<Msg,DirRef>> f;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_fact_f implements Forward.Caller.Requires<CreateClassic<Msg,DirRef>> {
      }
      
      
      public final Forward.Caller.Component<CreateClassic<Msg,DirRef>> f() {
        return this.f;
      }
      
      private DirRefAsyncReceiver.Receiver.Component<Msg> receive;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_receive_receive implements DirRefAsyncReceiver.Receiver.Requires<Msg> {
        public final Push<Msg> put() {
          return Classic.ClassicAgent.ComponentImpl.this.arch.put();
        }
      }
      
      
      public final DirRefAsyncReceiver.Receiver.Component<Msg> receive() {
        return this.receive;
      }
      
      private DirRefAsyncReceiver.Sender.Component<Msg> ss;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_receive_ss implements DirRefAsyncReceiver.Sender.Requires<Msg> {
      }
      
      
      public final DirRefAsyncReceiver.Sender.Component<Msg> ss() {
        return this.ss;
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
     */
    private boolean started = false;;
    
    private Classic.ClassicAgent.ComponentImpl<Msg> selfComponent;
    
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
    protected Classic.ClassicAgent.Provides<Msg> provides() {
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
    protected Classic.ClassicAgent.Requires<Msg> requires() {
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
    protected Classic.ClassicAgent.Parts<Msg> parts() {
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
    protected abstract ClassicAgentComponent<Msg,DirRef> make_arch();
    
    private ExecutorServiceWrapper.Executing use_s;
    
    private Forward.Caller<CreateClassic<Msg,DirRef>> use_f;
    
    private DirRefAsyncReceiver.Receiver<Msg> use_receive;
    
    private DirRefAsyncReceiver.Sender<Msg> use_ss;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized Classic.ClassicAgent.Component<Msg> _newComponent(final Classic.ClassicAgent.Requires<Msg> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of ClassicAgent has already been used to create a component, use another one.");
      }
      this.init = true;
      Classic.ClassicAgent.ComponentImpl<Msg> comp = new Classic.ClassicAgent.ComponentImpl<Msg>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private Classic.ComponentImpl<Msg> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Classic.Provides<Msg> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Classic.Requires<Msg> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Classic.Parts<Msg> eco_parts() {
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
   */
  private boolean started = false;;
  
  private Classic.ComponentImpl<Msg> selfComponent;
  
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
  protected Classic.Provides<Msg> provides() {
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
  protected abstract CreateClassic<Msg,DirRef> make_create();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Classic.Requires<Msg> requires() {
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
  protected Classic.Parts<Msg> parts() {
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
  protected abstract Forward<CreateClassic<Msg,DirRef>> make_fact();
  
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
  public synchronized Classic.Component<Msg> _newComponent(final Classic.Requires<Msg> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Classic has already been used to create a component, use another one.");
    }
    this.init = true;
    Classic.ComponentImpl<Msg> comp = new Classic.ComponentImpl<Msg>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Classic.ClassicAgent<Msg> make_ClassicAgent(final ClassicBehaviour<Msg,DirRef> beh, final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Classic.ClassicAgent<Msg> _createImplementationOfClassicAgent(final ClassicBehaviour<Msg,DirRef> beh, final String name) {
    Classic.ClassicAgent<Msg> implem = make_ClassicAgent(beh,name);
    if (implem == null) {
    	throw new RuntimeException("make_ClassicAgent() in fr.irit.smac.may.lib.classic.local.Classic should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_executor != null: "This is a bug.";
    assert implem.use_s == null: "This is a bug.";
    implem.use_s = this.selfComponent.implem_executor._createImplementationOfExecuting();
    assert this.selfComponent.implem_fact != null: "This is a bug.";
    assert implem.use_f == null: "This is a bug.";
    implem.use_f = this.selfComponent.implem_fact._createImplementationOfCaller();
    assert this.selfComponent.implem_receive != null: "This is a bug.";
    assert implem.use_receive == null: "This is a bug.";
    implem.use_receive = this.selfComponent.implem_receive._createImplementationOfReceiver(name);
    assert this.selfComponent.implem_receive != null: "This is a bug.";
    assert implem.use_ss == null: "This is a bug.";
    implem.use_ss = this.selfComponent.implem_receive._createImplementationOfSender();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected Classic.ClassicAgent.Component<Msg> newClassicAgent(final ClassicBehaviour<Msg,DirRef> beh, final String name) {
    Classic.ClassicAgent<Msg> implem = _createImplementationOfClassicAgent(beh,name);
    return implem._newComponent(new Classic.ClassicAgent.Requires<Msg>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Classic.Component<Msg> newComponent() {
    return this._newComponent(new Classic.Requires<Msg>() {}, true);
  }
}
