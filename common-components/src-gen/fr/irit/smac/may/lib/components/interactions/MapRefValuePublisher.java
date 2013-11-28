package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.MapReferences;
import fr.irit.smac.may.lib.components.interactions.ValuePublisher;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class MapRefValuePublisher<T, K> {
  @SuppressWarnings("all")
  public interface Requires<T, K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<T, K> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableObserve<T,K> observe();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Provides<T,K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T, K> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Pull<T>,K> mr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<T,K> vp();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Component<T,K>, fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Parts<T,K> {
    private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> bridge;
    
    private final MapRefValuePublisher<T,K> implementation;
    
    protected void initParts() {
      assert this.implem_mr == null;
      this.implem_mr = this.implementation.make_mr();
      assert this.mr == null;
      this.mr = this.implem_mr.newComponent(new BridgeImpl_mr());
      assert this.implem_vp == null;
      this.implem_vp = this.implementation.make_vp();
      assert this.vp == null;
      this.vp = this.implem_vp.newComponent(new BridgeImpl_vp());
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final MapRefValuePublisher<T,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> b, final boolean initMakes) {
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
    
    public final ReliableObserve<T,K> observe() {
      return this.vp.observe();
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Pull<T>,K> mr;
    
    private MapReferences<Pull<T>,K> implem_mr;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_mr implements fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<Pull<T>,K> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Pull<T>,K> mr() {
      return this.mr;
    }
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<T,K> vp;
    
    private ValuePublisher<T,K> implem_vp;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,K> {
      public final Call<Pull<T>,K> call() {
        return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl.this.mr.call();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<T,K> vp() {
      return this.vp;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class PublisherPush<T, K> {
    @SuppressWarnings("all")
    public interface Requires<T, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Push<T> set();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> get();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<T>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Component<T,K>, fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Parts<T,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Requires<T,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush<T,K> implementation;
      
      protected void initParts() {
        assert this.implementation.use_mr != null;
        assert this.mr == null;
        this.mr = this.implementation.use_mr.newComponent(new BridgeImpl_mr_mr());
        assert this.implementation.use_vp != null;
        assert this.vp == null;
        this.vp = this.implementation.use_vp.newComponent(new BridgeImpl_vp_vp());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush<T,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Requires<T,K> b, final boolean initMakes) {
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
      
      public final Push<T> set() {
        return this.vp.set();
      }
      
      public final Pull<T> get() {
        return this.vp.get();
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<T>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<Pull<T>,K> {
        public final Pull<T> toCall() {
          return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.ComponentImpl.this.vp.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<T>,K> mr() {
        return this.mr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<T,K> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> vp() {
        return this.vp;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.ComponentImpl<T,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Provides<T,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Requires<T,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Parts<T,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<Pull<T>,K> use_mr;
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Requires<T,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.ComponentImpl<T,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.ComponentImpl<T,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class PublisherPull<T, K> {
    @SuppressWarnings("all")
    public interface Requires<T, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<T> getValue();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> get();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<T>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Component<T,K>, fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Parts<T,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Requires<T,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull<T,K> implementation;
      
      protected void initParts() {
        assert this.implementation.use_mr != null;
        assert this.mr == null;
        this.mr = this.implementation.use_mr.newComponent(new BridgeImpl_mr_mr());
        assert this.implementation.use_vp != null;
        assert this.vp == null;
        this.vp = this.implementation.use_vp.newComponent(new BridgeImpl_vp_vp());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull<T,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Requires<T,K> b, final boolean initMakes) {
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
      
      public final Pull<T> get() {
        return this.vp.get();
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<T>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<Pull<T>,K> {
        public final Pull<T> toCall() {
          return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.ComponentImpl.this.vp.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<T>,K> mr() {
        return this.mr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Requires<T,K> {
        public final Pull<T> getValue() {
          return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.ComponentImpl.this.bridge.getValue();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,K> vp() {
        return this.vp;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.ComponentImpl<T,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Provides<T,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Requires<T,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Parts<T,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<Pull<T>,K> use_mr;
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.Requires<T,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.ComponentImpl<T,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull.ComponentImpl<T,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class PublisherPushKeyPort<T, K> {
    @SuppressWarnings("all")
    public interface Requires<T, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<K> key();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Push<T> set();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> get();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Component<T,K>, fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Parts<T,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Requires<T,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort<T,K> implementation;
      
      protected void initParts() {
        assert this.implementation.use_mr != null;
        assert this.mr == null;
        this.mr = this.implementation.use_mr.newComponent(new BridgeImpl_mr_mr());
        assert this.implementation.use_vp != null;
        assert this.vp == null;
        this.vp = this.implementation.use_vp.newComponent(new BridgeImpl_vp_vp());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort<T,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Requires<T,K> b, final boolean initMakes) {
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
      
      public final Push<T> set() {
        return this.vp.set();
      }
      
      public final Pull<T> get() {
        return this.vp.get();
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Requires<Pull<T>,K> {
        public final Pull<T> toCall() {
          return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl.this.vp.toCall();
        }
        
        public final Pull<K> key() {
          return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl.this.bridge.key();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr() {
        return this.mr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<T,K> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,K> vp() {
        return this.vp;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl<T,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Provides<T,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Requires<T,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Parts<T,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort<Pull<T>,K> use_mr;
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.Requires<T,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl<T,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl<T,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class PublisherPullKeyPort<T, K> {
    @SuppressWarnings("all")
    public interface Requires<T, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<T> getValue();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<K> key();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> get();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Component<T,K>, fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Parts<T,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Requires<T,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort<T,K> implementation;
      
      protected void initParts() {
        assert this.implementation.use_mr != null;
        assert this.mr == null;
        this.mr = this.implementation.use_mr.newComponent(new BridgeImpl_mr_mr());
        assert this.implementation.use_vp != null;
        assert this.vp == null;
        this.vp = this.implementation.use_vp.newComponent(new BridgeImpl_vp_vp());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort<T,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Requires<T,K> b, final boolean initMakes) {
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
      
      public final Pull<T> get() {
        return this.vp.get();
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Requires<Pull<T>,K> {
        public final Pull<T> toCall() {
          return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl.this.vp.toCall();
        }
        
        public final Pull<K> key() {
          return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl.this.bridge.key();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr() {
        return this.mr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Requires<T,K> {
        public final Pull<T> getValue() {
          return fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl.this.bridge.getValue();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,K> vp() {
        return this.vp;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl<T,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Provides<T,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Requires<T,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Parts<T,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort<Pull<T>,K> use_mr;
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.Requires<T,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl<T,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl<T,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Observer<T, K> {
    @SuppressWarnings("all")
    public interface Requires<T, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableObserve<T,K> observe();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T, K> extends fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Component<T,K>, fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Parts<T,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Requires<T,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer<T,K> implementation;
      
      protected void initParts() {
        assert this.implementation.use_vp != null;
        assert this.vp == null;
        this.vp = this.implementation.use_vp.newComponent(new BridgeImpl_vp_vp());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer<T,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Requires<T,K> b, final boolean initMakes) {
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
      
      public final ReliableObserve<T,K> observe() {
        return this.vp.observe();
      }
      
      private fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Requires<T,K> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,K> vp() {
        return this.vp;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.ComponentImpl<T,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Provides<T,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Requires<T,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Parts<T,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Requires<T,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.ComponentImpl<T,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.ComponentImpl<T,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl<T,K> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Provides<T,K> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Parts<T,K> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract MapReferences<Pull<T>,K> make_mr();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ValuePublisher<T,K> make_vp();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Component<T,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K> b) {
    fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl<T,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.ComponentImpl<T,K>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush<T,K> make_PublisherPush(final K key);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush<T,K> _createImplementationOfPublisherPush(final K key) {
    fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush<T,K> implem = make_PublisherPush(key);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null;
    assert implem.use_mr == null;
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCallee(key);
    assert this.selfComponent.implem_vp != null;
    assert implem.use_vp == null;
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPush();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Component<T,K> newPublisherPush(final K key) {
    fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush<T,K> implem = _createImplementationOfPublisherPush(key);
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Requires<T,K>() {});
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull<T,K> make_PublisherPull(final K key);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull<T,K> _createImplementationOfPublisherPull(final K key) {
    fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPull<T,K> implem = make_PublisherPull(key);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null;
    assert implem.use_mr == null;
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCallee(key);
    assert this.selfComponent.implem_vp != null;
    assert implem.use_vp == null;
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPull();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort<T,K> make_PublisherPushKeyPort();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort<T,K> _createImplementationOfPublisherPushKeyPort() {
    fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPushKeyPort<T,K> implem = make_PublisherPushKeyPort();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null;
    assert implem.use_mr == null;
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCalleeKeyPort();
    assert this.selfComponent.implem_vp != null;
    assert implem.use_vp == null;
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPush();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort<T,K> make_PublisherPullKeyPort();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort<T,K> _createImplementationOfPublisherPullKeyPort() {
    fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPullKeyPort<T,K> implem = make_PublisherPullKeyPort();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null;
    assert implem.use_mr == null;
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCalleeKeyPort();
    assert this.selfComponent.implem_vp != null;
    assert implem.use_vp == null;
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPull();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer<T,K> make_Observer();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer<T,K> _createImplementationOfObserver() {
    fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer<T,K> implem = make_Observer();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_vp != null;
    assert implem.use_vp == null;
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfObserver();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Component<T,K> newObserver() {
    fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer<T,K> implem = _createImplementationOfObserver();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Requires<T,K>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Component<T,K> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<T,K>() {});
  }
}
