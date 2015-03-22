package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.AsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.MapReferences;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class MapRefAsyncReceiver<M, K> {
  public interface Requires<M, K> {
  }
  
  public interface Component<M, K> extends MapRefAsyncReceiver.Provides<M, K> {
  }
  
  public interface Provides<M, K> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableSend<M, K> send();
  }
  
  public interface Parts<M, K> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public MapReferences.Component<Push<M>, K> mr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AsyncReceiver.Component<M, K> ar();
  }
  
  public static class ComponentImpl<M, K> implements MapRefAsyncReceiver.Component<M, K>, MapRefAsyncReceiver.Parts<M, K> {
    private final MapRefAsyncReceiver.Requires<M, K> bridge;
    
    private final MapRefAsyncReceiver<M, K> implementation;
    
    public void start() {
      assert this.mr != null: "This is a bug.";
      ((MapReferences.ComponentImpl<Push<M>, K>) this.mr).start();
      assert this.ar != null: "This is a bug.";
      ((AsyncReceiver.ComponentImpl<M, K>) this.ar).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_mr() {
      assert this.mr == null: "This is a bug.";
      assert this.implem_mr == null: "This is a bug.";
      this.implem_mr = this.implementation.make_mr();
      if (this.implem_mr == null) {
      	throw new RuntimeException("make_mr() in fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver<M, K> should not return null.");
      }
      this.mr = this.implem_mr._newComponent(new BridgeImpl_mr(), false);
    }
    
    private void init_ar() {
      assert this.ar == null: "This is a bug.";
      assert this.implem_ar == null: "This is a bug.";
      this.implem_ar = this.implementation.make_ar();
      if (this.implem_ar == null) {
      	throw new RuntimeException("make_ar() in fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver<M, K> should not return null.");
      }
      this.ar = this.implem_ar._newComponent(new BridgeImpl_ar(), false);
    }
    
    protected void initParts() {
      init_mr();
      init_ar();
    }
    
    protected void init_send() {
      // nothing to do here
    }
    
    protected void initProvidedPorts() {
      init_send();
    }
    
    public ComponentImpl(final MapRefAsyncReceiver<M, K> implem, final MapRefAsyncReceiver.Requires<M, K> b, final boolean doInits) {
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
    
    public ReliableSend<M, K> send() {
      return this.ar().
      send()
      ;
    }
    
    private MapReferences.Component<Push<M>, K> mr;
    
    private MapReferences<Push<M>, K> implem_mr;
    
    private final class BridgeImpl_mr implements MapReferences.Requires<Push<M>, K> {
    }
    
    public final MapReferences.Component<Push<M>, K> mr() {
      return this.mr;
    }
    
    private AsyncReceiver.Component<M, K> ar;
    
    private AsyncReceiver<M, K> implem_ar;
    
    private final class BridgeImpl_ar implements AsyncReceiver.Requires<M, K> {
      public final Call<Push<M>, K> call() {
        return MapRefAsyncReceiver.ComponentImpl.this.mr().
        call()
        ;
      }
    }
    
    public final AsyncReceiver.Component<M, K> ar() {
      return this.ar;
    }
  }
  
  public static class Receiver<M, K> {
    public interface Requires<M, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Push<M> put();
    }
    
    public interface Component<M, K> extends MapRefAsyncReceiver.Receiver.Provides<M, K> {
    }
    
    public interface Provides<M, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<K> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    public interface Parts<M, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapReferences.Callee.Component<Push<M>, K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AsyncReceiver.Receiver.Component<M, K> ar();
    }
    
    public static class ComponentImpl<M, K> implements MapRefAsyncReceiver.Receiver.Component<M, K>, MapRefAsyncReceiver.Receiver.Parts<M, K> {
      private final MapRefAsyncReceiver.Receiver.Requires<M, K> bridge;
      
      private final MapRefAsyncReceiver.Receiver<M, K> implementation;
      
      public void start() {
        assert this.mr != null: "This is a bug.";
        ((MapReferences.Callee.ComponentImpl<Push<M>, K>) this.mr).start();
        assert this.ar != null: "This is a bug.";
        ((AsyncReceiver.Receiver.ComponentImpl<M, K>) this.ar).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_mr() {
        assert this.mr == null: "This is a bug.";
        assert this.implementation.use_mr != null: "This is a bug.";
        this.mr = this.implementation.use_mr._newComponent(new BridgeImpl_mr_mr(), false);
      }
      
      private void init_ar() {
        assert this.ar == null: "This is a bug.";
        assert this.implementation.use_ar != null: "This is a bug.";
        this.ar = this.implementation.use_ar._newComponent(new BridgeImpl_ar_ar(), false);
      }
      
      protected void initParts() {
        init_mr();
        init_ar();
      }
      
      protected void init_me() {
        // nothing to do here
      }
      
      protected void init_stop() {
        // nothing to do here
      }
      
      protected void initProvidedPorts() {
        init_me();
        init_stop();
      }
      
      public ComponentImpl(final MapRefAsyncReceiver.Receiver<M, K> implem, final MapRefAsyncReceiver.Receiver.Requires<M, K> b, final boolean doInits) {
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
      
      public Pull<K> me() {
        return this.mr().
        me()
        ;
      }
      
      public Do stop() {
        return this.mr().
        stop()
        ;
      }
      
      private MapReferences.Callee.Component<Push<M>, K> mr;
      
      private final class BridgeImpl_mr_mr implements MapReferences.Callee.Requires<Push<M>, K> {
        public final Push<M> toCall() {
          return MapRefAsyncReceiver.Receiver.ComponentImpl.this.ar().
          toCall()
          ;
        }
      }
      
      public final MapReferences.Callee.Component<Push<M>, K> mr() {
        return this.mr;
      }
      
      private AsyncReceiver.Receiver.Component<M, K> ar;
      
      private final class BridgeImpl_ar_ar implements AsyncReceiver.Receiver.Requires<M, K> {
        public final Push<M> put() {
          return MapRefAsyncReceiver.Receiver.ComponentImpl.this.bridge.
          put()
          ;
        }
      }
      
      public final AsyncReceiver.Receiver.Component<M, K> ar() {
        return this.ar;
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
     * 
     */
    private boolean started = false;;
    
    private MapRefAsyncReceiver.Receiver.ComponentImpl<M, K> selfComponent;
    
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
    protected MapRefAsyncReceiver.Receiver.Provides<M, K> provides() {
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
    protected MapRefAsyncReceiver.Receiver.Requires<M, K> requires() {
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
    protected MapRefAsyncReceiver.Receiver.Parts<M, K> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private MapReferences.Callee<Push<M>, K> use_mr;
    
    private AsyncReceiver.Receiver<M, K> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized MapRefAsyncReceiver.Receiver.Component<M, K> _newComponent(final MapRefAsyncReceiver.Receiver.Requires<M, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Receiver has already been used to create a component, use another one.");
      }
      this.init = true;
      MapRefAsyncReceiver.Receiver.ComponentImpl<M, K>  _comp = new MapRefAsyncReceiver.Receiver.ComponentImpl<M, K>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private MapRefAsyncReceiver.ComponentImpl<M, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapRefAsyncReceiver.Provides<M, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapRefAsyncReceiver.Requires<M, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapRefAsyncReceiver.Parts<M, K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static class ReceiverKeyPort<M, K> {
    public interface Requires<M, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Push<M> put();
      
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<K> key();
    }
    
    public interface Component<M, K> extends MapRefAsyncReceiver.ReceiverKeyPort.Provides<M, K> {
    }
    
    public interface Provides<M, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<K> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    public interface Parts<M, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapReferences.CalleeKeyPort.Component<Push<M>, K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AsyncReceiver.Receiver.Component<M, K> ar();
    }
    
    public static class ComponentImpl<M, K> implements MapRefAsyncReceiver.ReceiverKeyPort.Component<M, K>, MapRefAsyncReceiver.ReceiverKeyPort.Parts<M, K> {
      private final MapRefAsyncReceiver.ReceiverKeyPort.Requires<M, K> bridge;
      
      private final MapRefAsyncReceiver.ReceiverKeyPort<M, K> implementation;
      
      public void start() {
        assert this.mr != null: "This is a bug.";
        ((MapReferences.CalleeKeyPort.ComponentImpl<Push<M>, K>) this.mr).start();
        assert this.ar != null: "This is a bug.";
        ((AsyncReceiver.Receiver.ComponentImpl<M, K>) this.ar).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_mr() {
        assert this.mr == null: "This is a bug.";
        assert this.implementation.use_mr != null: "This is a bug.";
        this.mr = this.implementation.use_mr._newComponent(new BridgeImpl_mr_mr(), false);
      }
      
      private void init_ar() {
        assert this.ar == null: "This is a bug.";
        assert this.implementation.use_ar != null: "This is a bug.";
        this.ar = this.implementation.use_ar._newComponent(new BridgeImpl_ar_ar(), false);
      }
      
      protected void initParts() {
        init_mr();
        init_ar();
      }
      
      protected void init_me() {
        // nothing to do here
      }
      
      protected void init_stop() {
        // nothing to do here
      }
      
      protected void initProvidedPorts() {
        init_me();
        init_stop();
      }
      
      public ComponentImpl(final MapRefAsyncReceiver.ReceiverKeyPort<M, K> implem, final MapRefAsyncReceiver.ReceiverKeyPort.Requires<M, K> b, final boolean doInits) {
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
      
      public Pull<K> me() {
        return this.mr().
        me()
        ;
      }
      
      public Do stop() {
        return this.mr().
        stop()
        ;
      }
      
      private MapReferences.CalleeKeyPort.Component<Push<M>, K> mr;
      
      private final class BridgeImpl_mr_mr implements MapReferences.CalleeKeyPort.Requires<Push<M>, K> {
        public final Pull<K> key() {
          return MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl.this.bridge.
          key()
          ;
        }
        
        public final Push<M> toCall() {
          return MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl.this.ar().
          toCall()
          ;
        }
      }
      
      public final MapReferences.CalleeKeyPort.Component<Push<M>, K> mr() {
        return this.mr;
      }
      
      private AsyncReceiver.Receiver.Component<M, K> ar;
      
      private final class BridgeImpl_ar_ar implements AsyncReceiver.Receiver.Requires<M, K> {
        public final Push<M> put() {
          return MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl.this.bridge.
          put()
          ;
        }
      }
      
      public final AsyncReceiver.Receiver.Component<M, K> ar() {
        return this.ar;
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
     * 
     */
    private boolean started = false;;
    
    private MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl<M, K> selfComponent;
    
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
    protected MapRefAsyncReceiver.ReceiverKeyPort.Provides<M, K> provides() {
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
    protected MapRefAsyncReceiver.ReceiverKeyPort.Requires<M, K> requires() {
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
    protected MapRefAsyncReceiver.ReceiverKeyPort.Parts<M, K> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private MapReferences.CalleeKeyPort<Push<M>, K> use_mr;
    
    private AsyncReceiver.Receiver<M, K> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized MapRefAsyncReceiver.ReceiverKeyPort.Component<M, K> _newComponent(final MapRefAsyncReceiver.ReceiverKeyPort.Requires<M, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of ReceiverKeyPort has already been used to create a component, use another one.");
      }
      this.init = true;
      MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl<M, K>  _comp = new MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl<M, K>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private MapRefAsyncReceiver.ComponentImpl<M, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapRefAsyncReceiver.Provides<M, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapRefAsyncReceiver.Requires<M, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapRefAsyncReceiver.Parts<M, K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static class Sender<M, K> {
    public interface Requires<M, K> {
    }
    
    public interface Component<M, K> extends MapRefAsyncReceiver.Sender.Provides<M, K> {
    }
    
    public interface Provides<M, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableSend<M, K> send();
    }
    
    public interface Parts<M, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AsyncReceiver.Sender.Component<M, K> ar();
    }
    
    public static class ComponentImpl<M, K> implements MapRefAsyncReceiver.Sender.Component<M, K>, MapRefAsyncReceiver.Sender.Parts<M, K> {
      private final MapRefAsyncReceiver.Sender.Requires<M, K> bridge;
      
      private final MapRefAsyncReceiver.Sender<M, K> implementation;
      
      public void start() {
        assert this.ar != null: "This is a bug.";
        ((AsyncReceiver.Sender.ComponentImpl<M, K>) this.ar).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_ar() {
        assert this.ar == null: "This is a bug.";
        assert this.implementation.use_ar != null: "This is a bug.";
        this.ar = this.implementation.use_ar._newComponent(new BridgeImpl_ar_ar(), false);
      }
      
      protected void initParts() {
        init_ar();
      }
      
      protected void init_send() {
        // nothing to do here
      }
      
      protected void initProvidedPorts() {
        init_send();
      }
      
      public ComponentImpl(final MapRefAsyncReceiver.Sender<M, K> implem, final MapRefAsyncReceiver.Sender.Requires<M, K> b, final boolean doInits) {
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
      
      public ReliableSend<M, K> send() {
        return this.ar().
        send()
        ;
      }
      
      private AsyncReceiver.Sender.Component<M, K> ar;
      
      private final class BridgeImpl_ar_ar implements AsyncReceiver.Sender.Requires<M, K> {
      }
      
      public final AsyncReceiver.Sender.Component<M, K> ar() {
        return this.ar;
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
     * 
     */
    private boolean started = false;;
    
    private MapRefAsyncReceiver.Sender.ComponentImpl<M, K> selfComponent;
    
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
    protected MapRefAsyncReceiver.Sender.Provides<M, K> provides() {
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
    protected MapRefAsyncReceiver.Sender.Requires<M, K> requires() {
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
    protected MapRefAsyncReceiver.Sender.Parts<M, K> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private AsyncReceiver.Sender<M, K> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized MapRefAsyncReceiver.Sender.Component<M, K> _newComponent(final MapRefAsyncReceiver.Sender.Requires<M, K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Sender has already been used to create a component, use another one.");
      }
      this.init = true;
      MapRefAsyncReceiver.Sender.ComponentImpl<M, K>  _comp = new MapRefAsyncReceiver.Sender.ComponentImpl<M, K>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private MapRefAsyncReceiver.ComponentImpl<M, K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected MapRefAsyncReceiver.Provides<M, K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected MapRefAsyncReceiver.Requires<M, K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected MapRefAsyncReceiver.Parts<M, K> eco_parts() {
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
   * 
   */
  private boolean started = false;;
  
  private MapRefAsyncReceiver.ComponentImpl<M, K> selfComponent;
  
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
  protected MapRefAsyncReceiver.Provides<M, K> provides() {
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
  protected MapRefAsyncReceiver.Requires<M, K> requires() {
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
  protected MapRefAsyncReceiver.Parts<M, K> parts() {
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
  protected abstract MapReferences<Push<M>, K> make_mr();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AsyncReceiver<M, K> make_ar();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized MapRefAsyncReceiver.Component<M, K> _newComponent(final MapRefAsyncReceiver.Requires<M, K> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of MapRefAsyncReceiver has already been used to create a component, use another one.");
    }
    this.init = true;
    MapRefAsyncReceiver.ComponentImpl<M, K>  _comp = new MapRefAsyncReceiver.ComponentImpl<M, K>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected MapRefAsyncReceiver.Receiver<M, K> make_Receiver(final K key) {
    return new MapRefAsyncReceiver.Receiver<M, K>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapRefAsyncReceiver.Receiver<M, K> _createImplementationOfReceiver(final K key) {
    MapRefAsyncReceiver.Receiver<M, K> implem = make_Receiver(key);
    if (implem == null) {
    	throw new RuntimeException("make_Receiver() in fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null: "This is a bug.";
    assert implem.use_mr == null: "This is a bug.";
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCallee(key);
    assert this.selfComponent.implem_ar != null: "This is a bug.";
    assert implem.use_ar == null: "This is a bug.";
    implem.use_ar = this.selfComponent.implem_ar._createImplementationOfReceiver();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected MapRefAsyncReceiver.ReceiverKeyPort<M, K> make_ReceiverKeyPort() {
    return new MapRefAsyncReceiver.ReceiverKeyPort<M, K>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapRefAsyncReceiver.ReceiverKeyPort<M, K> _createImplementationOfReceiverKeyPort() {
    MapRefAsyncReceiver.ReceiverKeyPort<M, K> implem = make_ReceiverKeyPort();
    if (implem == null) {
    	throw new RuntimeException("make_ReceiverKeyPort() in fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null: "This is a bug.";
    assert implem.use_mr == null: "This is a bug.";
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCalleeKeyPort();
    assert this.selfComponent.implem_ar != null: "This is a bug.";
    assert implem.use_ar == null: "This is a bug.";
    implem.use_ar = this.selfComponent.implem_ar._createImplementationOfReceiver();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected MapRefAsyncReceiver.Sender<M, K> make_Sender() {
    return new MapRefAsyncReceiver.Sender<M, K>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public MapRefAsyncReceiver.Sender<M, K> _createImplementationOfSender() {
    MapRefAsyncReceiver.Sender<M, K> implem = make_Sender();
    if (implem == null) {
    	throw new RuntimeException("make_Sender() in fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_ar != null: "This is a bug.";
    assert implem.use_ar == null: "This is a bug.";
    implem.use_ar = this.selfComponent.implem_ar._createImplementationOfSender();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected MapRefAsyncReceiver.Sender.Component<M, K> newSender() {
    MapRefAsyncReceiver.Sender<M, K> _implem = _createImplementationOfSender();
    return _implem._newComponent(new MapRefAsyncReceiver.Sender.Requires<M, K>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public MapRefAsyncReceiver.Component<M, K> newComponent() {
    return this._newComponent(new MapRefAsyncReceiver.Requires<M, K>() {}, true);
  }
}
