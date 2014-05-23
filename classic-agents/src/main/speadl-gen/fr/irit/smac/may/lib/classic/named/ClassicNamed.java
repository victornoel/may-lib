package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.classic.interfaces.CreateNamed;
import fr.irit.smac.may.lib.classic.named.ClassicNamedAgentComponent;
import fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour;
import fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver;
import fr.irit.smac.may.lib.components.meta.Forward;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class ClassicNamed<Msg> {
  public interface Requires<Msg> {
  }
  
  public interface Parts<Msg> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Forward.Component<CreateNamed<Msg, String>> fact();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public MapRefAsyncReceiver.Component<Msg, String> receive();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ExecutorServiceWrapper.Component executor();
  }
  
  public static class ComponentImpl<Msg> implements ClassicNamed.Component<Msg>, ClassicNamed.Parts<Msg> {
    private final ClassicNamed.Requires<Msg> bridge;
    
    private final ClassicNamed<Msg> implementation;
    
    public void start() {
      assert this.fact != null: "This is a bug.";
      ((Forward.ComponentImpl<CreateNamed<Msg, String>>) this.fact).start();
      assert this.receive != null: "This is a bug.";
      ((MapRefAsyncReceiver.ComponentImpl<Msg, String>) this.receive).start();
      assert this.executor != null: "This is a bug.";
      ((ExecutorServiceWrapper.ComponentImpl) this.executor).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.fact == null: "This is a bug.";
      assert this.implem_fact == null: "This is a bug.";
      this.implem_fact = this.implementation.make_fact();
      if (this.implem_fact == null) {
      	throw new RuntimeException("make_fact() in fr.irit.smac.may.lib.classic.named.ClassicNamed should not return null.");
      }
      this.fact = this.implem_fact._newComponent(new BridgeImpl_fact(), false);
      assert this.receive == null: "This is a bug.";
      assert this.implem_receive == null: "This is a bug.";
      this.implem_receive = this.implementation.make_receive();
      if (this.implem_receive == null) {
      	throw new RuntimeException("make_receive() in fr.irit.smac.may.lib.classic.named.ClassicNamed should not return null.");
      }
      this.receive = this.implem_receive._newComponent(new BridgeImpl_receive(), false);
      assert this.executor == null: "This is a bug.";
      assert this.implem_executor == null: "This is a bug.";
      this.implem_executor = this.implementation.make_executor();
      if (this.implem_executor == null) {
      	throw new RuntimeException("make_executor() in fr.irit.smac.may.lib.classic.named.ClassicNamed should not return null.");
      }
      this.executor = this.implem_executor._newComponent(new BridgeImpl_executor(), false);
      
    }
    
    protected void initProvidedPorts() {
      assert this.create == null: "This is a bug.";
      this.create = this.implementation.make_create();
      if (this.create == null) {
      	throw new RuntimeException("make_create() in fr.irit.smac.may.lib.classic.named.ClassicNamed should not return null.");
      }
      
    }
    
    public ComponentImpl(final ClassicNamed<Msg> implem, final ClassicNamed.Requires<Msg> b, final boolean doInits) {
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
    
    public Send<Msg, String> send() {
      return this.receive.send();
    }
    
    private CreateNamed<Msg, String> create;
    
    public CreateNamed<Msg, String> create() {
      return this.create;
    }
    
    private Forward.Component<CreateNamed<Msg, String>> fact;
    
    private Forward<CreateNamed<Msg, String>> implem_fact;
    
    private final class BridgeImpl_fact implements Forward.Requires<CreateNamed<Msg, String>> {
      public final CreateNamed<Msg, String> forwardedPort() {
        return ClassicNamed.ComponentImpl.this.create();
      }
    }
    
    public final Forward.Component<CreateNamed<Msg, String>> fact() {
      return this.fact;
    }
    
    private MapRefAsyncReceiver.Component<Msg, String> receive;
    
    private MapRefAsyncReceiver<Msg, String> implem_receive;
    
    private final class BridgeImpl_receive implements MapRefAsyncReceiver.Requires<Msg, String> {
    }
    
    public final MapRefAsyncReceiver.Component<Msg, String> receive() {
      return this.receive;
    }
    
    private ExecutorServiceWrapper.Component executor;
    
    private ExecutorServiceWrapper implem_executor;
    
    private final class BridgeImpl_executor implements ExecutorServiceWrapper.Requires {
    }
    
    public final ExecutorServiceWrapper.Component executor() {
      return this.executor;
    }
  }
  
  public interface Provides<Msg> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg, String> send();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public CreateNamed<Msg, String> create();
  }
  
  public interface Component<Msg> extends ClassicNamed.Provides<Msg> {
  }
  
  public abstract static class ClassicNamedAgent<Msg> {
    public interface Requires<Msg> {
    }
    
    public interface Parts<Msg> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ClassicNamedAgentComponent.Component<Msg, String> arch();
      
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
      public Forward.Caller.Component<CreateNamed<Msg, String>> f();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapRefAsyncReceiver.Receiver.Component<Msg, String> receive();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapRefAsyncReceiver.Sender.Component<Msg, String> ss();
    }
    
    public static class ComponentImpl<Msg> implements ClassicNamed.ClassicNamedAgent.Component<Msg>, ClassicNamed.ClassicNamedAgent.Parts<Msg> {
      private final ClassicNamed.ClassicNamedAgent.Requires<Msg> bridge;
      
      private final ClassicNamed.ClassicNamedAgent<Msg> implementation;
      
      public void start() {
        assert this.arch != null: "This is a bug.";
        ((ClassicNamedAgentComponent.ComponentImpl<Msg, String>) this.arch).start();
        assert this.s != null: "This is a bug.";
        ((ExecutorServiceWrapper.Executing.ComponentImpl) this.s).start();
        assert this.f != null: "This is a bug.";
        ((Forward.Caller.ComponentImpl<CreateNamed<Msg, String>>) this.f).start();
        assert this.receive != null: "This is a bug.";
        ((MapRefAsyncReceiver.Receiver.ComponentImpl<Msg, String>) this.receive).start();
        assert this.ss != null: "This is a bug.";
        ((MapRefAsyncReceiver.Sender.ComponentImpl<Msg, String>) this.ss).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.arch == null: "This is a bug.";
        assert this.implem_arch == null: "This is a bug.";
        this.implem_arch = this.implementation.make_arch();
        if (this.implem_arch == null) {
        	throw new RuntimeException("make_arch() in fr.irit.smac.may.lib.classic.named.ClassicNamed$ClassicNamedAgent should not return null.");
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
      
      public ComponentImpl(final ClassicNamed.ClassicNamedAgent<Msg> implem, final ClassicNamed.ClassicNamedAgent.Requires<Msg> b, final boolean doInits) {
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
      
      private ClassicNamedAgentComponent.Component<Msg, String> arch;
      
      private ClassicNamedAgentComponent<Msg, String> implem_arch;
      
      private final class BridgeImpl_arch implements ClassicNamedAgentComponent.Requires<Msg, String> {
        public final Send<Msg, String> send() {
          return ClassicNamed.ClassicNamedAgent.ComponentImpl.this.ss.send();
        }
        
        public final Executor executor() {
          return ClassicNamed.ClassicNamedAgent.ComponentImpl.this.s.executor();
        }
        
        public final Do die() {
          return ClassicNamed.ClassicNamedAgent.ComponentImpl.this.s.stop();
        }
        
        public final CreateNamed<Msg, String> create() {
          return ClassicNamed.ClassicNamedAgent.ComponentImpl.this.f.forwardedPort();
        }
        
        public final Pull<String> me() {
          return ClassicNamed.ClassicNamedAgent.ComponentImpl.this.receive.me();
        }
      }
      
      public final ClassicNamedAgentComponent.Component<Msg, String> arch() {
        return this.arch;
      }
      
      private ExecutorServiceWrapper.Executing.Component s;
      
      private final class BridgeImpl_executor_s implements ExecutorServiceWrapper.Executing.Requires {
      }
      
      public final ExecutorServiceWrapper.Executing.Component s() {
        return this.s;
      }
      
      private Forward.Caller.Component<CreateNamed<Msg, String>> f;
      
      private final class BridgeImpl_fact_f implements Forward.Caller.Requires<CreateNamed<Msg, String>> {
      }
      
      public final Forward.Caller.Component<CreateNamed<Msg, String>> f() {
        return this.f;
      }
      
      private MapRefAsyncReceiver.Receiver.Component<Msg, String> receive;
      
      private final class BridgeImpl_receive_receive implements MapRefAsyncReceiver.Receiver.Requires<Msg, String> {
        public final Push<Msg> put() {
          return ClassicNamed.ClassicNamedAgent.ComponentImpl.this.arch.put();
        }
      }
      
      public final MapRefAsyncReceiver.Receiver.Component<Msg, String> receive() {
        return this.receive;
      }
      
      private MapRefAsyncReceiver.Sender.Component<Msg, String> ss;
      
      private final class BridgeImpl_receive_ss implements MapRefAsyncReceiver.Sender.Requires<Msg, String> {
      }
      
      public final MapRefAsyncReceiver.Sender.Component<Msg, String> ss() {
        return this.ss;
      }
    }
    
    public interface Provides<Msg> {
    }
    
    public interface Component<Msg> extends ClassicNamed.ClassicNamedAgent.Provides<Msg> {
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
    
    private ClassicNamed.ClassicNamedAgent.ComponentImpl<Msg> selfComponent;
    
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
    protected ClassicNamed.ClassicNamedAgent.Provides<Msg> provides() {
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
    protected ClassicNamed.ClassicNamedAgent.Requires<Msg> requires() {
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
    protected ClassicNamed.ClassicNamedAgent.Parts<Msg> parts() {
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
    protected abstract ClassicNamedAgentComponent<Msg, String> make_arch();
    
    private ExecutorServiceWrapper.Executing use_s;
    
    private Forward.Caller<CreateNamed<Msg, String>> use_f;
    
    private MapRefAsyncReceiver.Receiver<Msg, String> use_receive;
    
    private MapRefAsyncReceiver.Sender<Msg, String> use_ss;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized ClassicNamed.ClassicNamedAgent.Component<Msg> _newComponent(final ClassicNamed.ClassicNamedAgent.Requires<Msg> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of ClassicNamedAgent has already been used to create a component, use another one.");
      }
      this.init = true;
      ClassicNamed.ClassicNamedAgent.ComponentImpl<Msg> comp = new ClassicNamed.ClassicNamedAgent.ComponentImpl<Msg>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private ClassicNamed.ComponentImpl<Msg> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected ClassicNamed.Provides<Msg> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected ClassicNamed.Requires<Msg> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected ClassicNamed.Parts<Msg> eco_parts() {
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
  
  private ClassicNamed.ComponentImpl<Msg> selfComponent;
  
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
  protected ClassicNamed.Provides<Msg> provides() {
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
  protected abstract CreateNamed<Msg, String> make_create();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ClassicNamed.Requires<Msg> requires() {
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
  protected ClassicNamed.Parts<Msg> parts() {
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
  protected abstract Forward<CreateNamed<Msg, String>> make_fact();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract MapRefAsyncReceiver<Msg, String> make_receive();
  
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
  public synchronized ClassicNamed.Component<Msg> _newComponent(final ClassicNamed.Requires<Msg> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ClassicNamed has already been used to create a component, use another one.");
    }
    this.init = true;
    ClassicNamed.ComponentImpl<Msg> comp = new ClassicNamed.ComponentImpl<Msg>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract ClassicNamed.ClassicNamedAgent<Msg> make_ClassicNamedAgent(final ClassicNamedBehaviour<Msg, String> beh, final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public ClassicNamed.ClassicNamedAgent<Msg> _createImplementationOfClassicNamedAgent(final ClassicNamedBehaviour<Msg, String> beh, final String name) {
    ClassicNamed.ClassicNamedAgent<Msg> implem = make_ClassicNamedAgent(beh,name);
    if (implem == null) {
    	throw new RuntimeException("make_ClassicNamedAgent() in fr.irit.smac.may.lib.classic.named.ClassicNamed should not return null.");
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
  protected ClassicNamed.ClassicNamedAgent.Component<Msg> newClassicNamedAgent(final ClassicNamedBehaviour<Msg, String> beh, final String name) {
    ClassicNamed.ClassicNamedAgent<Msg> implem = _createImplementationOfClassicNamedAgent(beh,name);
    return implem._newComponent(new ClassicNamed.ClassicNamedAgent.Requires<Msg>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public ClassicNamed.Component<Msg> newComponent() {
    return this._newComponent(new ClassicNamed.Requires<Msg>() {}, true);
  }
}
