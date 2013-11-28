package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.classic.interfaces.CreateNamed;
import fr.irit.smac.may.lib.classic.named.AbstractClassicNamedBehaviour;
import fr.irit.smac.may.lib.classic.named.ClassicNamedAgentComponent;
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
  @SuppressWarnings("all")
  public interface Requires<Msg> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Send<Msg,String> send();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public CreateNamed<Msg,String> create();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg> extends fr.irit.smac.may.lib.classic.named.ClassicNamed.Provides<Msg> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Msg> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.meta.Forward.Component<CreateNamed<Msg,String>> fact();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Component<Msg,String> receive();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component executor();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg> implements fr.irit.smac.may.lib.classic.named.ClassicNamed.Component<Msg>, fr.irit.smac.may.lib.classic.named.ClassicNamed.Parts<Msg> {
    private final fr.irit.smac.may.lib.classic.named.ClassicNamed.Requires<Msg> bridge;
    
    private final ClassicNamed<Msg> implementation;
    
    protected void initParts() {
      assert this.implem_fact == null;
      this.implem_fact = this.implementation.make_fact();
      assert this.fact == null;
      this.fact = this.implem_fact.newComponent(new BridgeImpl_fact());
      assert this.implem_receive == null;
      this.implem_receive = this.implementation.make_receive();
      assert this.receive == null;
      this.receive = this.implem_receive.newComponent(new BridgeImpl_receive());
      assert this.implem_executor == null;
      this.implem_executor = this.implementation.make_executor();
      assert this.executor == null;
      this.executor = this.implem_executor.newComponent(new BridgeImpl_executor());
      
    }
    
    protected void initProvidedPorts() {
      assert this.create == null;
      this.create = this.implementation.make_create();
      
    }
    
    public ComponentImpl(final ClassicNamed<Msg> implem, final fr.irit.smac.may.lib.classic.named.ClassicNamed.Requires<Msg> b, final boolean initMakes) {
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
    
    public final Send<Msg,String> send() {
      return this.receive.deposit();
    }
    
    private CreateNamed<Msg,String> create;
    
    public final CreateNamed<Msg,String> create() {
      return this.create;
    }
    
    private fr.irit.smac.may.lib.components.meta.Forward.Component<CreateNamed<Msg,String>> fact;
    
    private Forward<CreateNamed<Msg,String>> implem_fact;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_fact implements fr.irit.smac.may.lib.components.meta.Forward.Requires<CreateNamed<Msg,String>> {
      public final CreateNamed<Msg,String> forwardedPort() {
        return fr.irit.smac.may.lib.classic.named.ClassicNamed.ComponentImpl.this.create();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.meta.Forward.Component<CreateNamed<Msg,String>> fact() {
      return this.fact;
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Component<Msg,String> receive;
    
    private MapRefAsyncReceiver<Msg,String> implem_receive;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_receive implements fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<Msg,String> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Component<Msg,String> receive() {
      return this.receive;
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
  public abstract static class ClassicNamedAgent<Msg> {
    @SuppressWarnings("all")
    public interface Requires<Msg> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<Msg> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<Msg> extends fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Provides<Msg> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<Msg> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.classic.named.ClassicNamedAgentComponent.Component<Msg,String> arch();
      
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
      public fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<CreateNamed<Msg,String>> f();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Component<Msg,String> receive();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Component<Msg,String> ss();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<Msg> implements fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Component<Msg>, fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Parts<Msg> {
      private final fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Requires<Msg> bridge;
      
      private final fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent<Msg> implementation;
      
      protected void initParts() {
        assert this.implem_arch == null;
        this.implem_arch = this.implementation.make_arch();
        assert this.arch == null;
        this.arch = this.implem_arch.newComponent(new BridgeImpl_arch());
        assert this.implementation.use_s != null;
        assert this.s == null;
        this.s = this.implementation.use_s.newComponent(new BridgeImpl_executor_s());
        assert this.implementation.use_f != null;
        assert this.f == null;
        this.f = this.implementation.use_f.newComponent(new BridgeImpl_fact_f());
        assert this.implementation.use_receive != null;
        assert this.receive == null;
        this.receive = this.implementation.use_receive.newComponent(new BridgeImpl_receive_receive());
        assert this.implementation.use_ss != null;
        assert this.ss == null;
        this.ss = this.implementation.use_ss.newComponent(new BridgeImpl_receive_ss());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent<Msg> implem, final fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Requires<Msg> b, final boolean initMakes) {
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
      
      private fr.irit.smac.may.lib.classic.named.ClassicNamedAgentComponent.Component<Msg,String> arch;
      
      private ClassicNamedAgentComponent<Msg,String> implem_arch;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_arch implements fr.irit.smac.may.lib.classic.named.ClassicNamedAgentComponent.Requires<Msg,String> {
        public final Send<Msg,String> send() {
          return fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl.this.ss.send();
        }
        
        public final Executor executor() {
          return fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl.this.s.executor();
        }
        
        public final Do die() {
          return fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl.this.s.stop();
        }
        
        public final CreateNamed<Msg,String> create() {
          return fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl.this.f.forwardedPort();
        }
        
        public final Pull<String> me() {
          return fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl.this.receive.me();
        }
      }
      
      
      public final fr.irit.smac.may.lib.classic.named.ClassicNamedAgentComponent.Component<Msg,String> arch() {
        return this.arch;
      }
      
      private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Component s;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_executor_s implements fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Requires {
      }
      
      
      public final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing.Component s() {
        return this.s;
      }
      
      private fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<CreateNamed<Msg,String>> f;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_fact_f implements fr.irit.smac.may.lib.components.meta.Forward.Caller.Requires<CreateNamed<Msg,String>> {
      }
      
      
      public final fr.irit.smac.may.lib.components.meta.Forward.Caller.Component<CreateNamed<Msg,String>> f() {
        return this.f;
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Component<Msg,String> receive;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_receive_receive implements fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Requires<Msg,String> {
        public final Push<Msg> put() {
          return fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl.this.arch.put();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Component<Msg,String> receive() {
        return this.receive;
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Component<Msg,String> ss;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_receive_ss implements fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Requires<Msg,String> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Component<Msg,String> ss() {
        return this.ss;
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl<Msg> selfComponent;
    
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
    protected fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Provides<Msg> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Requires<Msg> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Parts<Msg> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract ClassicNamedAgentComponent<Msg,String> make_arch();
    
    private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Executing use_s;
    
    private fr.irit.smac.may.lib.components.meta.Forward.Caller<CreateNamed<Msg,String>> use_f;
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver<Msg,String> use_receive;
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender<Msg,String> use_ss;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Component<Msg> newComponent(final fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Requires<Msg> b) {
      fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl<Msg> comp = new fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.ComponentImpl<Msg>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.classic.named.ClassicNamed.ComponentImpl<Msg> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.named.ClassicNamed.Provides<Msg> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.named.ClassicNamed.Requires<Msg> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.named.ClassicNamed.Parts<Msg> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.named.ClassicNamed.ComponentImpl<Msg> selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.named.ClassicNamed.Provides<Msg> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract CreateNamed<Msg,String> make_create();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.named.ClassicNamed.Requires<Msg> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.named.ClassicNamed.Parts<Msg> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Forward<CreateNamed<Msg,String>> make_fact();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract MapRefAsyncReceiver<Msg,String> make_receive();
  
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
  public fr.irit.smac.may.lib.classic.named.ClassicNamed.Component<Msg> newComponent(final fr.irit.smac.may.lib.classic.named.ClassicNamed.Requires<Msg> b) {
    fr.irit.smac.may.lib.classic.named.ClassicNamed.ComponentImpl<Msg> comp = new fr.irit.smac.may.lib.classic.named.ClassicNamed.ComponentImpl<Msg>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent<Msg> make_ClassicNamedAgent(final AbstractClassicNamedBehaviour<Msg,String> beh, final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent<Msg> _createImplementationOfClassicNamedAgent(final AbstractClassicNamedBehaviour<Msg,String> beh, final String name) {
    fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent<Msg> implem = make_ClassicNamedAgent(beh,name);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_executor != null;
    assert implem.use_s == null;
    implem.use_s = this.selfComponent.implem_executor._createImplementationOfExecuting();
    assert this.selfComponent.implem_fact != null;
    assert implem.use_f == null;
    implem.use_f = this.selfComponent.implem_fact._createImplementationOfCaller();
    assert this.selfComponent.implem_receive != null;
    assert implem.use_receive == null;
    implem.use_receive = this.selfComponent.implem_receive._createImplementationOfReceiver(name);
    assert this.selfComponent.implem_receive != null;
    assert implem.use_ss == null;
    implem.use_ss = this.selfComponent.implem_receive._createImplementationOfSender();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Component<Msg> newClassicNamedAgent(final AbstractClassicNamedBehaviour<Msg,String> beh, final String name) {
    fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent<Msg> implem = _createImplementationOfClassicNamedAgent(beh,name);
    return implem.newComponent(new fr.irit.smac.may.lib.classic.named.ClassicNamed.ClassicNamedAgent.Requires<Msg>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.classic.named.ClassicNamed.Component<Msg> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.classic.named.ClassicNamed.Requires<Msg>() {});
  }
}
