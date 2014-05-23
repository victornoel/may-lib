package fr.irit.smac.may.lib.components.distribution;

import fr.irit.smac.may.lib.components.distribution.IvyBroadcaster;
import fr.irit.smac.may.lib.components.distribution.JSONTransformer;
import fr.irit.smac.may.lib.components.distribution.interfaces.Transform;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder;
import fr.irit.smac.may.lib.components.distribution.ivy.IvyBus;
import fr.irit.smac.may.lib.components.distribution.ivy.interfaces.Bind;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.List;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class IvyJSONBroadcaster<T> {
  public interface Requires<T> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Push<T> handle();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Executor exec();
  }
  
  public interface Parts<T> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public IvyBus.Component ivy();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public JSONTransformer.Component<T> json();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public IvyBinder.Component binder();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public IvyBroadcaster.Component<T> bc();
  }
  
  public static class ComponentImpl<T> implements IvyJSONBroadcaster.Component<T>, IvyJSONBroadcaster.Parts<T> {
    private final IvyJSONBroadcaster.Requires<T> bridge;
    
    private final IvyJSONBroadcaster<T> implementation;
    
    public void start() {
      assert this.ivy != null: "This is a bug.";
      ((IvyBus.ComponentImpl) this.ivy).start();
      assert this.json != null: "This is a bug.";
      ((JSONTransformer.ComponentImpl<T>) this.json).start();
      assert this.binder != null: "This is a bug.";
      ((IvyBinder.ComponentImpl) this.binder).start();
      assert this.bc != null: "This is a bug.";
      ((IvyBroadcaster.ComponentImpl<T>) this.bc).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.ivy == null: "This is a bug.";
      assert this.implem_ivy == null: "This is a bug.";
      this.implem_ivy = this.implementation.make_ivy();
      if (this.implem_ivy == null) {
      	throw new RuntimeException("make_ivy() in fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster should not return null.");
      }
      this.ivy = this.implem_ivy._newComponent(new BridgeImpl_ivy(), false);
      assert this.json == null: "This is a bug.";
      assert this.implem_json == null: "This is a bug.";
      this.implem_json = this.implementation.make_json();
      if (this.implem_json == null) {
      	throw new RuntimeException("make_json() in fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster should not return null.");
      }
      this.json = this.implem_json._newComponent(new BridgeImpl_json(), false);
      assert this.binder == null: "This is a bug.";
      assert this.implem_binder == null: "This is a bug.";
      this.implem_binder = this.implementation.make_binder();
      if (this.implem_binder == null) {
      	throw new RuntimeException("make_binder() in fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster should not return null.");
      }
      this.binder = this.implem_binder._newComponent(new BridgeImpl_binder(), false);
      assert this.bc == null: "This is a bug.";
      assert this.implem_bc == null: "This is a bug.";
      this.implem_bc = this.implementation.make_bc();
      if (this.implem_bc == null) {
      	throw new RuntimeException("make_bc() in fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster should not return null.");
      }
      this.bc = this.implem_bc._newComponent(new BridgeImpl_bc(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final IvyJSONBroadcaster<T> implem, final IvyJSONBroadcaster.Requires<T> b, final boolean doInits) {
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
    
    public Push<T> send() {
      return this.bc.send();
    }
    
    private IvyBus.Component ivy;
    
    private IvyBus implem_ivy;
    
    private final class BridgeImpl_ivy implements IvyBus.Requires {
      public final Executor exec() {
        return IvyJSONBroadcaster.ComponentImpl.this.bridge.exec();
      }
    }
    
    public final IvyBus.Component ivy() {
      return this.ivy;
    }
    
    private JSONTransformer.Component<T> json;
    
    private JSONTransformer<T> implem_json;
    
    private final class BridgeImpl_json implements JSONTransformer.Requires<T> {
    }
    
    public final JSONTransformer.Component<T> json() {
      return this.json;
    }
    
    private IvyBinder.Component binder;
    
    private IvyBinder implem_binder;
    
    private final class BridgeImpl_binder implements IvyBinder.Requires {
      public final Bind bindMsg() {
        return IvyJSONBroadcaster.ComponentImpl.this.ivy.bindMsg();
      }
      
      public final Push<List<String>> receive() {
        return IvyJSONBroadcaster.ComponentImpl.this.bc.ivyReceive();
      }
      
      public final Push<Integer> unBindMsg() {
        return IvyJSONBroadcaster.ComponentImpl.this.ivy.unBindMsg();
      }
    }
    
    public final IvyBinder.Component binder() {
      return this.binder;
    }
    
    private IvyBroadcaster.Component<T> bc;
    
    private IvyBroadcaster<T> implem_bc;
    
    private final class BridgeImpl_bc implements IvyBroadcaster.Requires<T> {
      public final Transform<String, T> deserializer() {
        return IvyJSONBroadcaster.ComponentImpl.this.json.deserializer();
      }
      
      public final Transform<T, String> serializer() {
        return IvyJSONBroadcaster.ComponentImpl.this.json.serializer();
      }
      
      public final Push<T> handle() {
        return IvyJSONBroadcaster.ComponentImpl.this.bridge.handle();
      }
      
      public final Push<String> ivyBindMsg() {
        return IvyJSONBroadcaster.ComponentImpl.this.binder.reBindMsg();
      }
      
      public final Push<String> ivySend() {
        return IvyJSONBroadcaster.ComponentImpl.this.ivy.send();
      }
    }
    
    public final IvyBroadcaster.Component<T> bc() {
      return this.bc;
    }
  }
  
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<T> send();
  }
  
  public interface Component<T> extends IvyJSONBroadcaster.Provides<T> {
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
  
  private IvyJSONBroadcaster.ComponentImpl<T> selfComponent;
  
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
  protected IvyJSONBroadcaster.Provides<T> provides() {
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
  protected IvyJSONBroadcaster.Requires<T> requires() {
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
  protected IvyJSONBroadcaster.Parts<T> parts() {
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
  protected abstract IvyBus make_ivy();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract JSONTransformer<T> make_json();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract IvyBinder make_binder();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract IvyBroadcaster<T> make_bc();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized IvyJSONBroadcaster.Component<T> _newComponent(final IvyJSONBroadcaster.Requires<T> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of IvyJSONBroadcaster has already been used to create a component, use another one.");
    }
    this.init = true;
    IvyJSONBroadcaster.ComponentImpl<T> comp = new IvyJSONBroadcaster.ComponentImpl<T>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
