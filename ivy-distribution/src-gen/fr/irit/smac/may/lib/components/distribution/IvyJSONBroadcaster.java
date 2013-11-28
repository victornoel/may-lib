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
  @SuppressWarnings("all")
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
  
  
  @SuppressWarnings("all")
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<T> send();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T> extends fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Provides<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Component ivy();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.distribution.JSONTransformer.Component<T> json();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Component binder();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Component<T> bc();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Component<T>, fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Parts<T> {
    private final fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Requires<T> bridge;
    
    private final IvyJSONBroadcaster<T> implementation;
    
    protected void initParts() {
      assert this.implem_ivy == null;
      this.implem_ivy = this.implementation.make_ivy();
      assert this.ivy == null;
      this.ivy = this.implem_ivy.newComponent(new BridgeImpl_ivy());
      assert this.implem_json == null;
      this.implem_json = this.implementation.make_json();
      assert this.json == null;
      this.json = this.implem_json.newComponent(new BridgeImpl_json());
      assert this.implem_binder == null;
      this.implem_binder = this.implementation.make_binder();
      assert this.binder == null;
      this.binder = this.implem_binder.newComponent(new BridgeImpl_binder());
      assert this.implem_bc == null;
      this.implem_bc = this.implementation.make_bc();
      assert this.bc == null;
      this.bc = this.implem_bc.newComponent(new BridgeImpl_bc());
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final IvyJSONBroadcaster<T> implem, final fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Requires<T> b, final boolean initMakes) {
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
    
    public final Push<T> send() {
      return this.bc.send();
    }
    
    private fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Component ivy;
    
    private IvyBus implem_ivy;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_ivy implements fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Requires {
      public final Executor exec() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.bridge.exec();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.distribution.ivy.IvyBus.Component ivy() {
      return this.ivy;
    }
    
    private fr.irit.smac.may.lib.components.distribution.JSONTransformer.Component<T> json;
    
    private JSONTransformer<T> implem_json;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_json implements fr.irit.smac.may.lib.components.distribution.JSONTransformer.Requires<T> {
    }
    
    
    public final fr.irit.smac.may.lib.components.distribution.JSONTransformer.Component<T> json() {
      return this.json;
    }
    
    private fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Component binder;
    
    private IvyBinder implem_binder;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_binder implements fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Requires {
      public final Bind bindMsg() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.ivy.bindMsg();
      }
      
      public final Push<List<String>> receive() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.bc.ivyReceive();
      }
      
      public final Push<Integer> unBindMsg() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.ivy.unBindMsg();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.distribution.ivy.IvyBinder.Component binder() {
      return this.binder;
    }
    
    private fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Component<T> bc;
    
    private IvyBroadcaster<T> implem_bc;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_bc implements fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Requires<T> {
      public final Transform<String,T> deserializer() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.json.deserializer();
      }
      
      public final Transform<T,String> serializer() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.json.serializer();
      }
      
      public final Push<T> handle() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.bridge.handle();
      }
      
      public final Push<String> ivyBindMsg() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.binder.reBindMsg();
      }
      
      public final Push<String> ivySend() {
        return fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl.this.ivy.send();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.distribution.IvyBroadcaster.Component<T> bc() {
      return this.bc;
    }
  }
  
  
  private fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl<T> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Provides<T> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Requires<T> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Parts<T> parts() {
    assert this.selfComponent != null;
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
  public fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Component<T> newComponent(final fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.Requires<T> b) {
    fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl<T> comp = new fr.irit.smac.may.lib.components.distribution.IvyJSONBroadcaster.ComponentImpl<T>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
