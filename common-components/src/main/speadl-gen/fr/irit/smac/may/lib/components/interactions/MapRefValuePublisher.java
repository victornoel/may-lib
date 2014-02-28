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
  public interface Component<T, K> extends MapRefValuePublisher.Provides<T,K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T, K> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public MapReferences.Component<Pull<T>,K> mr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ValuePublisher.Component<T,K> vp();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T, K> implements MapRefValuePublisher.Component<T,K>, MapRefValuePublisher.Parts<T,K> {
    private final MapRefValuePublisher.Requires<T,K> bridge;
    
    private final MapRefValuePublisher<T,K> implementation;
    
    public void start() {
      assert this.mr != null: "This is a bug.";
      ((MapReferences.ComponentImpl<Pull<T>,K>) this.mr).start();
      assert this.vp != null: "This is a bug.";
      ((ValuePublisher.ComponentImpl<T,K>) this.vp).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.mr == null: "This is a bug.";
      assert this.implem_mr == null: "This is a bug.";
      this.implem_mr = this.implementation.make_mr();
      if (this.implem_mr == null) {
      	throw new RuntimeException("make_mr() in fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher should not return null.");
      }
      this.mr = this.implem_mr._newComponent(new BridgeImpl_mr(), false);
      assert this.vp == null: "This is a bug.";
      assert this.implem_vp == null: "This is a bug.";
      this.implem_vp = this.implementation.make_vp();
      if (this.implem_vp == null) {
      	throw new RuntimeException("make_vp() in fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher should not return null.");
      }
      this.vp = this.implem_vp._newComponent(new BridgeImpl_vp(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final MapRefValuePublisher<T,K> implem, final MapRefValuePublisher.Requires<T,K> b, final boolean doInits) {
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
    
    public final ReliableObserve<T,K> observe() {
      return this.vp.observe();
    }
    
    private MapReferences.Component<Pull<T>,K> mr;
    
    private MapReferences<Pull<T>,K> implem_mr;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_mr implements MapReferences.Requires<Pull<T>,K> {
    }
    
    
    public final MapReferences.Component<Pull<T>,K> mr() {
      return this.mr;
    }
    
    private ValuePublisher.Component<T,K> vp;
    
    private ValuePublisher<T,K> implem_vp;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_vp implements ValuePublisher.Requires<T,K> {
      public final Call<Pull<T>,K> call() {
        return MapRefValuePublisher.ComponentImpl.this.mr.call();
      }
    }
    
    
    public final ValuePublisher.Component<T,K> vp() {
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
    public interface Component<T, K> extends MapRefValuePublisher.PublisherPush.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapReferences.Callee.Component<Pull<T>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ValuePublisher.PublisherPush.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements MapRefValuePublisher.PublisherPush.Component<T,K>, MapRefValuePublisher.PublisherPush.Parts<T,K> {
      private final MapRefValuePublisher.PublisherPush.Requires<T,K> bridge;
      
      private final MapRefValuePublisher.PublisherPush<T,K> implementation;
      
      public void start() {
        assert this.mr != null: "This is a bug.";
        ((MapReferences.Callee.ComponentImpl<Pull<T>,K>) this.mr).start();
        assert this.vp != null: "This is a bug.";
        ((ValuePublisher.PublisherPush.ComponentImpl<T,K>) this.vp).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.mr == null: "This is a bug.";
        assert this.implementation.use_mr != null: "This is a bug.";
        this.mr = this.implementation.use_mr._newComponent(new BridgeImpl_mr_mr(), false);
        assert this.vp == null: "This is a bug.";
        assert this.implementation.use_vp != null: "This is a bug.";
        this.vp = this.implementation.use_vp._newComponent(new BridgeImpl_vp_vp(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final MapRefValuePublisher.PublisherPush<T,K> implem, final MapRefValuePublisher.PublisherPush.Requires<T,K> b, final boolean doInits) {
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
      
      public final Push<T> set() {
        return this.vp.set();
      }
      
      public final Pull<T> get() {
        return this.vp.get();
      }
      
      private MapReferences.Callee.Component<Pull<T>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements MapReferences.Callee.Requires<Pull<T>,K> {
        public final Pull<T> toCall() {
          return MapRefValuePublisher.PublisherPush.ComponentImpl.this.vp.toCall();
        }
      }
      
      
      public final MapReferences.Callee.Component<Pull<T>,K> mr() {
        return this.mr;
      }
      
      private ValuePublisher.PublisherPush.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements ValuePublisher.PublisherPush.Requires<T,K> {
      }
      
      
      public final ValuePublisher.PublisherPush.Component<T,K> vp() {
        return this.vp;
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
    
    private MapRefValuePublisher.PublisherPush.ComponentImpl<T,K> selfComponent;
    
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
    protected MapRefValuePublisher.PublisherPush.Provides<T,K> provides() {
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
    protected MapRefValuePublisher.PublisherPush.Requires<T,K> requires() {
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
    protected MapRefValuePublisher.PublisherPush.Parts<T,K> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private MapReferences.Callee<Pull<T>,K> use_mr;
    
    private ValuePublisher.PublisherPush<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized MapRefValuePublisher.PublisherPush.Component<T,K> _newComponent(final MapRefValuePublisher.PublisherPush.Requires<T,K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PublisherPush has already been used to create a component, use another one.");
      }
      this.init = true;
      MapRefValuePublisher.PublisherPush.ComponentImpl<T,K> comp = new MapRefValuePublisher.PublisherPush.ComponentImpl<T,K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
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
    public interface Component<T, K> extends MapRefValuePublisher.PublisherPull.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapReferences.Callee.Component<Pull<T>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ValuePublisher.PublisherPull.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements MapRefValuePublisher.PublisherPull.Component<T,K>, MapRefValuePublisher.PublisherPull.Parts<T,K> {
      private final MapRefValuePublisher.PublisherPull.Requires<T,K> bridge;
      
      private final MapRefValuePublisher.PublisherPull<T,K> implementation;
      
      public void start() {
        assert this.mr != null: "This is a bug.";
        ((MapReferences.Callee.ComponentImpl<Pull<T>,K>) this.mr).start();
        assert this.vp != null: "This is a bug.";
        ((ValuePublisher.PublisherPull.ComponentImpl<T,K>) this.vp).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.mr == null: "This is a bug.";
        assert this.implementation.use_mr != null: "This is a bug.";
        this.mr = this.implementation.use_mr._newComponent(new BridgeImpl_mr_mr(), false);
        assert this.vp == null: "This is a bug.";
        assert this.implementation.use_vp != null: "This is a bug.";
        this.vp = this.implementation.use_vp._newComponent(new BridgeImpl_vp_vp(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final MapRefValuePublisher.PublisherPull<T,K> implem, final MapRefValuePublisher.PublisherPull.Requires<T,K> b, final boolean doInits) {
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
      
      public final Pull<T> get() {
        return this.vp.get();
      }
      
      private MapReferences.Callee.Component<Pull<T>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements MapReferences.Callee.Requires<Pull<T>,K> {
        public final Pull<T> toCall() {
          return MapRefValuePublisher.PublisherPull.ComponentImpl.this.vp.toCall();
        }
      }
      
      
      public final MapReferences.Callee.Component<Pull<T>,K> mr() {
        return this.mr;
      }
      
      private ValuePublisher.PublisherPull.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements ValuePublisher.PublisherPull.Requires<T,K> {
        public final Pull<T> getValue() {
          return MapRefValuePublisher.PublisherPull.ComponentImpl.this.bridge.getValue();
        }
      }
      
      
      public final ValuePublisher.PublisherPull.Component<T,K> vp() {
        return this.vp;
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
    
    private MapRefValuePublisher.PublisherPull.ComponentImpl<T,K> selfComponent;
    
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
    protected MapRefValuePublisher.PublisherPull.Provides<T,K> provides() {
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
    protected MapRefValuePublisher.PublisherPull.Requires<T,K> requires() {
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
    protected MapRefValuePublisher.PublisherPull.Parts<T,K> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private MapReferences.Callee<Pull<T>,K> use_mr;
    
    private ValuePublisher.PublisherPull<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized MapRefValuePublisher.PublisherPull.Component<T,K> _newComponent(final MapRefValuePublisher.PublisherPull.Requires<T,K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PublisherPull has already been used to create a component, use another one.");
      }
      this.init = true;
      MapRefValuePublisher.PublisherPull.ComponentImpl<T,K> comp = new MapRefValuePublisher.PublisherPull.ComponentImpl<T,K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
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
    public interface Component<T, K> extends MapRefValuePublisher.PublisherPushKeyPort.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ValuePublisher.PublisherPush.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements MapRefValuePublisher.PublisherPushKeyPort.Component<T,K>, MapRefValuePublisher.PublisherPushKeyPort.Parts<T,K> {
      private final MapRefValuePublisher.PublisherPushKeyPort.Requires<T,K> bridge;
      
      private final MapRefValuePublisher.PublisherPushKeyPort<T,K> implementation;
      
      public void start() {
        assert this.mr != null: "This is a bug.";
        ((MapReferences.CalleeKeyPort.ComponentImpl<Pull<T>,K>) this.mr).start();
        assert this.vp != null: "This is a bug.";
        ((ValuePublisher.PublisherPush.ComponentImpl<T,K>) this.vp).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.mr == null: "This is a bug.";
        assert this.implementation.use_mr != null: "This is a bug.";
        this.mr = this.implementation.use_mr._newComponent(new BridgeImpl_mr_mr(), false);
        assert this.vp == null: "This is a bug.";
        assert this.implementation.use_vp != null: "This is a bug.";
        this.vp = this.implementation.use_vp._newComponent(new BridgeImpl_vp_vp(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final MapRefValuePublisher.PublisherPushKeyPort<T,K> implem, final MapRefValuePublisher.PublisherPushKeyPort.Requires<T,K> b, final boolean doInits) {
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
      
      public final Push<T> set() {
        return this.vp.set();
      }
      
      public final Pull<T> get() {
        return this.vp.get();
      }
      
      private MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements MapReferences.CalleeKeyPort.Requires<Pull<T>,K> {
        public final Pull<T> toCall() {
          return MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl.this.vp.toCall();
        }
        
        public final Pull<K> key() {
          return MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl.this.bridge.key();
        }
      }
      
      
      public final MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr() {
        return this.mr;
      }
      
      private ValuePublisher.PublisherPush.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements ValuePublisher.PublisherPush.Requires<T,K> {
      }
      
      
      public final ValuePublisher.PublisherPush.Component<T,K> vp() {
        return this.vp;
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
    
    private MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl<T,K> selfComponent;
    
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
    protected MapRefValuePublisher.PublisherPushKeyPort.Provides<T,K> provides() {
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
    protected MapRefValuePublisher.PublisherPushKeyPort.Requires<T,K> requires() {
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
    protected MapRefValuePublisher.PublisherPushKeyPort.Parts<T,K> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private MapReferences.CalleeKeyPort<Pull<T>,K> use_mr;
    
    private ValuePublisher.PublisherPush<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized MapRefValuePublisher.PublisherPushKeyPort.Component<T,K> _newComponent(final MapRefValuePublisher.PublisherPushKeyPort.Requires<T,K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PublisherPushKeyPort has already been used to create a component, use another one.");
      }
      this.init = true;
      MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl<T,K> comp = new MapRefValuePublisher.PublisherPushKeyPort.ComponentImpl<T,K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
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
    public interface Component<T, K> extends MapRefValuePublisher.PublisherPullKeyPort.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ValuePublisher.PublisherPull.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements MapRefValuePublisher.PublisherPullKeyPort.Component<T,K>, MapRefValuePublisher.PublisherPullKeyPort.Parts<T,K> {
      private final MapRefValuePublisher.PublisherPullKeyPort.Requires<T,K> bridge;
      
      private final MapRefValuePublisher.PublisherPullKeyPort<T,K> implementation;
      
      public void start() {
        assert this.mr != null: "This is a bug.";
        ((MapReferences.CalleeKeyPort.ComponentImpl<Pull<T>,K>) this.mr).start();
        assert this.vp != null: "This is a bug.";
        ((ValuePublisher.PublisherPull.ComponentImpl<T,K>) this.vp).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.mr == null: "This is a bug.";
        assert this.implementation.use_mr != null: "This is a bug.";
        this.mr = this.implementation.use_mr._newComponent(new BridgeImpl_mr_mr(), false);
        assert this.vp == null: "This is a bug.";
        assert this.implementation.use_vp != null: "This is a bug.";
        this.vp = this.implementation.use_vp._newComponent(new BridgeImpl_vp_vp(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final MapRefValuePublisher.PublisherPullKeyPort<T,K> implem, final MapRefValuePublisher.PublisherPullKeyPort.Requires<T,K> b, final boolean doInits) {
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
      
      public final Pull<T> get() {
        return this.vp.get();
      }
      
      private MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements MapReferences.CalleeKeyPort.Requires<Pull<T>,K> {
        public final Pull<T> toCall() {
          return MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl.this.vp.toCall();
        }
        
        public final Pull<K> key() {
          return MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl.this.bridge.key();
        }
      }
      
      
      public final MapReferences.CalleeKeyPort.Component<Pull<T>,K> mr() {
        return this.mr;
      }
      
      private ValuePublisher.PublisherPull.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements ValuePublisher.PublisherPull.Requires<T,K> {
        public final Pull<T> getValue() {
          return MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl.this.bridge.getValue();
        }
      }
      
      
      public final ValuePublisher.PublisherPull.Component<T,K> vp() {
        return this.vp;
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
    
    private MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl<T,K> selfComponent;
    
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
    protected MapRefValuePublisher.PublisherPullKeyPort.Provides<T,K> provides() {
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
    protected MapRefValuePublisher.PublisherPullKeyPort.Requires<T,K> requires() {
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
    protected MapRefValuePublisher.PublisherPullKeyPort.Parts<T,K> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private MapReferences.CalleeKeyPort<Pull<T>,K> use_mr;
    
    private ValuePublisher.PublisherPull<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized MapRefValuePublisher.PublisherPullKeyPort.Component<T,K> _newComponent(final MapRefValuePublisher.PublisherPullKeyPort.Requires<T,K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PublisherPullKeyPort has already been used to create a component, use another one.");
      }
      this.init = true;
      MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl<T,K> comp = new MapRefValuePublisher.PublisherPullKeyPort.ComponentImpl<T,K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapRefValuePublisher.Parts<T,K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
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
    public interface Component<T, K> extends MapRefValuePublisher.Observer.Provides<T,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ValuePublisher.Observer.Component<T,K> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T, K> implements MapRefValuePublisher.Observer.Component<T,K>, MapRefValuePublisher.Observer.Parts<T,K> {
      private final MapRefValuePublisher.Observer.Requires<T,K> bridge;
      
      private final MapRefValuePublisher.Observer<T,K> implementation;
      
      public void start() {
        assert this.vp != null: "This is a bug.";
        ((ValuePublisher.Observer.ComponentImpl<T,K>) this.vp).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.vp == null: "This is a bug.";
        assert this.implementation.use_vp != null: "This is a bug.";
        this.vp = this.implementation.use_vp._newComponent(new BridgeImpl_vp_vp(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final MapRefValuePublisher.Observer<T,K> implem, final MapRefValuePublisher.Observer.Requires<T,K> b, final boolean doInits) {
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
      
      public final ReliableObserve<T,K> observe() {
        return this.vp.observe();
      }
      
      private ValuePublisher.Observer.Component<T,K> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements ValuePublisher.Observer.Requires<T,K> {
      }
      
      
      public final ValuePublisher.Observer.Component<T,K> vp() {
        return this.vp;
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
    
    private MapRefValuePublisher.Observer.ComponentImpl<T,K> selfComponent;
    
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
    protected MapRefValuePublisher.Observer.Provides<T,K> provides() {
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
    protected MapRefValuePublisher.Observer.Requires<T,K> requires() {
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
    protected MapRefValuePublisher.Observer.Parts<T,K> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private ValuePublisher.Observer<T,K> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized MapRefValuePublisher.Observer.Component<T,K> _newComponent(final MapRefValuePublisher.Observer.Requires<T,K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Observer has already been used to create a component, use another one.");
      }
      this.init = true;
      MapRefValuePublisher.Observer.ComponentImpl<T,K> comp = new MapRefValuePublisher.Observer.ComponentImpl<T,K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private MapRefValuePublisher.ComponentImpl<T,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Provides<T,K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapRefValuePublisher.Requires<T,K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapRefValuePublisher.Parts<T,K> eco_parts() {
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
  
  private MapRefValuePublisher.ComponentImpl<T,K> selfComponent;
  
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
  protected MapRefValuePublisher.Provides<T,K> provides() {
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
  protected MapRefValuePublisher.Requires<T,K> requires() {
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
  protected MapRefValuePublisher.Parts<T,K> parts() {
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
  public synchronized MapRefValuePublisher.Component<T,K> _newComponent(final MapRefValuePublisher.Requires<T,K> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of MapRefValuePublisher has already been used to create a component, use another one.");
    }
    this.init = true;
    MapRefValuePublisher.ComponentImpl<T,K> comp = new MapRefValuePublisher.ComponentImpl<T,K>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract MapRefValuePublisher.PublisherPush<T,K> make_PublisherPush(final K key);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapRefValuePublisher.PublisherPush<T,K> _createImplementationOfPublisherPush(final K key) {
    MapRefValuePublisher.PublisherPush<T,K> implem = make_PublisherPush(key);
    if (implem == null) {
    	throw new RuntimeException("make_PublisherPush() in fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null: "This is a bug.";
    assert implem.use_mr == null: "This is a bug.";
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCallee(key);
    assert this.selfComponent.implem_vp != null: "This is a bug.";
    assert implem.use_vp == null: "This is a bug.";
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPush();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected MapRefValuePublisher.PublisherPush.Component<T,K> newPublisherPush(final K key) {
    MapRefValuePublisher.PublisherPush<T,K> implem = _createImplementationOfPublisherPush(key);
    return implem._newComponent(new MapRefValuePublisher.PublisherPush.Requires<T,K>() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract MapRefValuePublisher.PublisherPull<T,K> make_PublisherPull(final K key);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapRefValuePublisher.PublisherPull<T,K> _createImplementationOfPublisherPull(final K key) {
    MapRefValuePublisher.PublisherPull<T,K> implem = make_PublisherPull(key);
    if (implem == null) {
    	throw new RuntimeException("make_PublisherPull() in fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null: "This is a bug.";
    assert implem.use_mr == null: "This is a bug.";
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCallee(key);
    assert this.selfComponent.implem_vp != null: "This is a bug.";
    assert implem.use_vp == null: "This is a bug.";
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPull();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract MapRefValuePublisher.PublisherPushKeyPort<T,K> make_PublisherPushKeyPort();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapRefValuePublisher.PublisherPushKeyPort<T,K> _createImplementationOfPublisherPushKeyPort() {
    MapRefValuePublisher.PublisherPushKeyPort<T,K> implem = make_PublisherPushKeyPort();
    if (implem == null) {
    	throw new RuntimeException("make_PublisherPushKeyPort() in fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null: "This is a bug.";
    assert implem.use_mr == null: "This is a bug.";
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCalleeKeyPort();
    assert this.selfComponent.implem_vp != null: "This is a bug.";
    assert implem.use_vp == null: "This is a bug.";
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPush();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract MapRefValuePublisher.PublisherPullKeyPort<T,K> make_PublisherPullKeyPort();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapRefValuePublisher.PublisherPullKeyPort<T,K> _createImplementationOfPublisherPullKeyPort() {
    MapRefValuePublisher.PublisherPullKeyPort<T,K> implem = make_PublisherPullKeyPort();
    if (implem == null) {
    	throw new RuntimeException("make_PublisherPullKeyPort() in fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null: "This is a bug.";
    assert implem.use_mr == null: "This is a bug.";
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCalleeKeyPort();
    assert this.selfComponent.implem_vp != null: "This is a bug.";
    assert implem.use_vp == null: "This is a bug.";
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPull();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract MapRefValuePublisher.Observer<T,K> make_Observer();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapRefValuePublisher.Observer<T,K> _createImplementationOfObserver() {
    MapRefValuePublisher.Observer<T,K> implem = make_Observer();
    if (implem == null) {
    	throw new RuntimeException("make_Observer() in fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_vp != null: "This is a bug.";
    assert implem.use_vp == null: "This is a bug.";
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfObserver();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected MapRefValuePublisher.Observer.Component<T,K> newObserver() {
    MapRefValuePublisher.Observer<T,K> implem = _createImplementationOfObserver();
    return implem._newComponent(new MapRefValuePublisher.Observer.Requires<T,K>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public MapRefValuePublisher.Component<T,K> newComponent() {
    return this._newComponent(new MapRefValuePublisher.Requires<T,K>() {}, true);
  }
}
