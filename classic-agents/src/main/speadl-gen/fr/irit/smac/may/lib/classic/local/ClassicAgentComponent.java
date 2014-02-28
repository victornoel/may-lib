package fr.irit.smac.may.lib.classic.local;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class ClassicAgentComponent<Msg, Ref> {
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
    public Do die();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CreateClassic<Msg,Ref> create();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg, Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Msg> put();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg, Ref> extends ClassicAgentComponent.Provides<Msg,Ref> {
  }
  
  
  @SuppressWarnings("all")
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
    public ClassicBehaviour.Component<Msg,Ref> beh();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg, Ref> implements ClassicAgentComponent.Component<Msg,Ref>, ClassicAgentComponent.Parts<Msg,Ref> {
    private final ClassicAgentComponent.Requires<Msg,Ref> bridge;
    
    private final ClassicAgentComponent<Msg,Ref> implementation;
    
    public void start() {
      assert this.dispatcher != null: "This is a bug.";
      ((SequentialDispatcher.ComponentImpl<Msg>) this.dispatcher).start();
      assert this.beh != null: "This is a bug.";
      ((ClassicBehaviour.ComponentImpl<Msg,Ref>) this.beh).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.dispatcher == null: "This is a bug.";
      assert this.implem_dispatcher == null: "This is a bug.";
      this.implem_dispatcher = this.implementation.make_dispatcher();
      if (this.implem_dispatcher == null) {
      	throw new RuntimeException("make_dispatcher() in fr.irit.smac.may.lib.classic.local.ClassicAgentComponent should not return null.");
      }
      this.dispatcher = this.implem_dispatcher._newComponent(new BridgeImpl_dispatcher(), false);
      assert this.beh == null: "This is a bug.";
      assert this.implem_beh == null: "This is a bug.";
      this.implem_beh = this.implementation.make_beh();
      if (this.implem_beh == null) {
      	throw new RuntimeException("make_beh() in fr.irit.smac.may.lib.classic.local.ClassicAgentComponent should not return null.");
      }
      this.beh = this.implem_beh._newComponent(new BridgeImpl_beh(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final ClassicAgentComponent<Msg,Ref> implem, final ClassicAgentComponent.Requires<Msg,Ref> b, final boolean doInits) {
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
    
    private SequentialDispatcher.Component<Msg> dispatcher;
    
    private SequentialDispatcher<Msg> implem_dispatcher;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_dispatcher implements SequentialDispatcher.Requires<Msg> {
      public final Executor executor() {
        return ClassicAgentComponent.ComponentImpl.this.bridge.executor();
      }
      
      public final Push<Msg> handler() {
        return ClassicAgentComponent.ComponentImpl.this.beh.cycle();
      }
    }
    
    
    public final SequentialDispatcher.Component<Msg> dispatcher() {
      return this.dispatcher;
    }
    
    private ClassicBehaviour.Component<Msg,Ref> beh;
    
    private ClassicBehaviour<Msg,Ref> implem_beh;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_beh implements ClassicBehaviour.Requires<Msg,Ref> {
      public final Send<Msg,Ref> send() {
        return ClassicAgentComponent.ComponentImpl.this.bridge.send();
      }
      
      public final Pull<Ref> me() {
        return ClassicAgentComponent.ComponentImpl.this.bridge.me();
      }
      
      public final Do die() {
        return ClassicAgentComponent.ComponentImpl.this.bridge.die();
      }
      
      public final CreateClassic<Msg,Ref> create() {
        return ClassicAgentComponent.ComponentImpl.this.bridge.create();
      }
    }
    
    
    public final ClassicBehaviour.Component<Msg,Ref> beh() {
      return this.beh;
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
  
  private ClassicAgentComponent.ComponentImpl<Msg,Ref> selfComponent;
  
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
  protected ClassicAgentComponent.Provides<Msg,Ref> provides() {
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
  protected ClassicAgentComponent.Requires<Msg,Ref> requires() {
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
  protected ClassicAgentComponent.Parts<Msg,Ref> parts() {
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
  protected abstract ClassicBehaviour<Msg,Ref> make_beh();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized ClassicAgentComponent.Component<Msg,Ref> _newComponent(final ClassicAgentComponent.Requires<Msg,Ref> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ClassicAgentComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    ClassicAgentComponent.ComponentImpl<Msg,Ref> comp = new ClassicAgentComponent.ComponentImpl<Msg,Ref>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
