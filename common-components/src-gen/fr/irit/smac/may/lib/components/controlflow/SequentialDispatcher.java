package fr.irit.smac.may.lib.components.controlflow;

import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class SequentialDispatcher<T> {
  @SuppressWarnings("all")
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
  
  
  @SuppressWarnings("all")
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<T> dispatch();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T> extends fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Provides<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.collections.Queue.Component<T> queue();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Component<T>, fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Parts<T> {
    private final fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Requires<T> bridge;
    
    private final SequentialDispatcher<T> implementation;
    
    protected void initParts() {
      assert this.implem_queue == null;
      this.implem_queue = this.implementation.make_queue();
      assert this.queue == null;
      this.queue = this.implem_queue.newComponent(new BridgeImpl_queue());
      
    }
    
    protected void initProvidedPorts() {
      assert this.dispatch == null;
      this.dispatch = this.implementation.make_dispatch();
      
    }
    
    public ComponentImpl(final SequentialDispatcher<T> implem, final fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Requires<T> b, final boolean initMakes) {
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
    
    private Push<T> dispatch;
    
    public final Push<T> dispatch() {
      return this.dispatch;
    }
    
    private fr.irit.smac.may.lib.components.collections.Queue.Component<T> queue;
    
    private Queue<T> implem_queue;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_queue implements fr.irit.smac.may.lib.components.collections.Queue.Requires<T> {
    }
    
    
    public final fr.irit.smac.may.lib.components.collections.Queue.Component<T> queue() {
      return this.queue;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.ComponentImpl<T> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Provides<T> provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Requires<T> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Parts<T> parts() {
    assert this.selfComponent != null;
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
  public fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Component<T> newComponent(final fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.Requires<T> b) {
    fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.ComponentImpl<T> comp = new fr.irit.smac.may.lib.components.controlflow.SequentialDispatcher.ComponentImpl<T>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
