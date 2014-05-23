package fr.irit.smac.may.lib.components.controlflow;

import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class SequentialDispatcher<T> {
  public interface Requires<T> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Executor executor();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<T> handler();
  }
  
  public interface Parts<T> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Queue.Component<T> queue();
  }
  
  public static class ComponentImpl<T> implements SequentialDispatcher.Component<T>, SequentialDispatcher.Parts<T> {
    private final SequentialDispatcher.Requires<T> bridge;
    
    private final SequentialDispatcher<T> implementation;
    
    public void start() {
      assert this.queue != null: "This is a bug.";
      ((Queue.ComponentImpl<T>) this.queue).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.queue == null: "This is a bug.";
      assert this.implem_queue == null: "This is a bug.";
      this.implem_queue = this.implementation.make_queue();
      if (this.implem_queue == null) {
      	throw new RuntimeException("make_queue() in fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher should not return null.");
      }
      this.queue = this.implem_queue._newComponent(new BridgeImpl_queue(), false);
      
    }
    
    protected void initProvidedPorts() {
      assert this.dispatch == null: "This is a bug.";
      this.dispatch = this.implementation.make_dispatch();
      if (this.dispatch == null) {
      	throw new RuntimeException("make_dispatch() in fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher should not return null.");
      }
      
    }
    
    public ComponentImpl(final SequentialDispatcher<T> implem, final SequentialDispatcher.Requires<T> b, final boolean doInits) {
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
    
    private Push<T> dispatch;
    
    public final Push<T> dispatch() {
      return this.dispatch;
    }
    
    private Queue.Component<T> queue;
    
    private Queue<T> implem_queue;
    
    private final class BridgeImpl_queue implements Queue.Requires<T> {
    }
    
    public final Queue.Component<T> queue() {
      return this.queue;
    }
  }
  
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<T> dispatch();
  }
  
  public interface Component<T> extends SequentialDispatcher.Provides<T> {
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
  
  private SequentialDispatcher.ComponentImpl<T> selfComponent;
  
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
  protected SequentialDispatcher.Provides<T> provides() {
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
  protected abstract Push<T> make_dispatch();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected SequentialDispatcher.Requires<T> requires() {
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
  protected SequentialDispatcher.Parts<T> parts() {
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
  protected abstract Queue<T> make_queue();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized SequentialDispatcher.Component<T> _newComponent(final SequentialDispatcher.Requires<T> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of SequentialDispatcher has already been used to create a component, use another one.");
    }
    this.init = true;
    SequentialDispatcher.ComponentImpl<T> comp = new SequentialDispatcher.ComponentImpl<T>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
