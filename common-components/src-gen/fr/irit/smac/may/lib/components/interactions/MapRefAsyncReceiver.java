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
  @SuppressWarnings("all")
  public interface Requires<M, K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<M, K> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableSend<M,K> deposit();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Provides<M,K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<M, K> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Push<M>,K> mr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,K> ar();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Component<M,K>, fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Parts<M,K> {
    private final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<M,K> bridge;
    
    private final MapRefAsyncReceiver<M,K> implementation;
    
    protected void initParts() {
      assert this.implem_mr == null;
      this.implem_mr = this.implementation.make_mr();
      assert this.mr == null;
      this.mr = this.implem_mr.newComponent(new BridgeImpl_mr());
      assert this.implem_ar == null;
      this.implem_ar = this.implementation.make_ar();
      assert this.ar == null;
      this.ar = this.implem_ar.newComponent(new BridgeImpl_ar());
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final MapRefAsyncReceiver<M,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<M,K> b, final boolean initMakes) {
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
    
    public final ReliableSend<M,K> deposit() {
      return this.ar.deposit();
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Push<M>,K> mr;
    
    private MapReferences<Push<M>,K> implem_mr;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_mr implements fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<Push<M>,K> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Push<M>,K> mr() {
      return this.mr;
    }
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,K> ar;
    
    private AsyncReceiver<M,K> implem_ar;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_ar implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> {
      public final Call<Push<M>,K> call() {
        return fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ComponentImpl.this.mr.call();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,K> ar() {
      return this.ar;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Receiver<M, K> {
    @SuppressWarnings("all")
    public interface Requires<M, K> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Push<M> put();
    }
    
    
    @SuppressWarnings("all")
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
    
    
    @SuppressWarnings("all")
    public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Provides<M,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Push<M>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K> ar();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Component<M,K>, fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Parts<M,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Requires<M,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver<M,K> implementation;
      
      protected void initParts() {
        assert this.implementation.use_mr != null;
        assert this.mr == null;
        this.mr = this.implementation.use_mr.newComponent(new BridgeImpl_mr_mr());
        assert this.implementation.use_ar != null;
        assert this.ar == null;
        this.ar = this.implementation.use_ar.newComponent(new BridgeImpl_ar_ar());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver<M,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Requires<M,K> b, final boolean initMakes) {
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
      
      public final Pull<K> me() {
        return this.mr.me();
      }
      
      public final Do stop() {
        return this.mr.stop();
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Push<M>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<Push<M>,K> {
        public final Push<M> toCall() {
          return fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.ComponentImpl.this.ar.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Push<M>,K> mr() {
        return this.mr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K> ar;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_ar_ar implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,K> {
        public final Push<M> put() {
          return fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.ComponentImpl.this.bridge.put();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K> ar() {
        return this.ar;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.ComponentImpl<M,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Provides<M,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Requires<M,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Parts<M,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<Push<M>,K> use_mr;
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Component<M,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.Requires<M,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.ComponentImpl<M,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver.ComponentImpl<M,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ComponentImpl<M,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Provides<M,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<M,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Parts<M,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class ReceiverKeyPort<M, K> {
    @SuppressWarnings("all")
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
    
    
    @SuppressWarnings("all")
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
    
    
    @SuppressWarnings("all")
    public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Provides<M,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Push<M>,K> mr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K> ar();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Component<M,K>, fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Parts<M,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Requires<M,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort<M,K> implementation;
      
      protected void initParts() {
        assert this.implementation.use_mr != null;
        assert this.mr == null;
        this.mr = this.implementation.use_mr.newComponent(new BridgeImpl_mr_mr());
        assert this.implementation.use_ar != null;
        assert this.ar == null;
        this.ar = this.implementation.use_ar.newComponent(new BridgeImpl_ar_ar());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort<M,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Requires<M,K> b, final boolean initMakes) {
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
      
      public final Pull<K> me() {
        return this.mr.me();
      }
      
      public final Do stop() {
        return this.mr.stop();
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Push<M>,K> mr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_mr_mr implements fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Requires<Push<M>,K> {
        public final Pull<K> key() {
          return fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl.this.bridge.key();
        }
        
        public final Push<M> toCall() {
          return fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl.this.ar.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort.Component<Push<M>,K> mr() {
        return this.mr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K> ar;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_ar_ar implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,K> {
        public final Push<M> put() {
          return fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl.this.bridge.put();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K> ar() {
        return this.ar;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl<M,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Provides<M,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Requires<M,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Parts<M,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.CalleeKeyPort<Push<M>,K> use_mr;
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Component<M,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.Requires<M,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl<M,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort.ComponentImpl<M,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ComponentImpl<M,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Provides<M,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<M,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Parts<M,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Sender<M, K> {
    @SuppressWarnings("all")
    public interface Requires<M, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<M, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableSend<M,K> send();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Provides<M,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,K> ar();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Component<M,K>, fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Parts<M,K> {
      private final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Requires<M,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender<M,K> implementation;
      
      protected void initParts() {
        assert this.implementation.use_ar != null;
        assert this.ar == null;
        this.ar = this.implementation.use_ar.newComponent(new BridgeImpl_ar_ar());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender<M,K> implem, final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Requires<M,K> b, final boolean initMakes) {
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
      
      public final ReliableSend<M,K> send() {
        return this.ar.send();
      }
      
      private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,K> ar;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_ar_ar implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,K> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,K> ar() {
        return this.ar;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.ComponentImpl<M,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Provides<M,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Requires<M,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Parts<M,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Component<M,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Requires<M,K> b) {
      fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.ComponentImpl<M,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.ComponentImpl<M,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ComponentImpl<M,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Provides<M,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<M,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Parts<M,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ComponentImpl<M,K> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Provides<M,K> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<M,K> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Parts<M,K> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract MapReferences<Push<M>,K> make_mr();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AsyncReceiver<M,K> make_ar();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Component<M,K> newComponent(final fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<M,K> b) {
    fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ComponentImpl<M,K> comp = new fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ComponentImpl<M,K>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver<M,K> make_Receiver(final K key);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver<M,K> _createImplementationOfReceiver(final K key) {
    fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Receiver<M,K> implem = make_Receiver(key);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null;
    assert implem.use_mr == null;
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCallee(key);
    assert this.selfComponent.implem_ar != null;
    assert implem.use_ar == null;
    implem.use_ar = this.selfComponent.implem_ar._createImplementationOfReceiver();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort<M,K> make_ReceiverKeyPort();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort<M,K> _createImplementationOfReceiverKeyPort() {
    fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.ReceiverKeyPort<M,K> implem = make_ReceiverKeyPort();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_mr != null;
    assert implem.use_mr == null;
    implem.use_mr = this.selfComponent.implem_mr._createImplementationOfCalleeKeyPort();
    assert this.selfComponent.implem_ar != null;
    assert implem.use_ar == null;
    implem.use_ar = this.selfComponent.implem_ar._createImplementationOfReceiver();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender<M,K> make_Sender();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender<M,K> _createImplementationOfSender() {
    fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender<M,K> implem = make_Sender();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_ar != null;
    assert implem.use_ar == null;
    implem.use_ar = this.selfComponent.implem_ar._createImplementationOfSender();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Component<M,K> newSender() {
    fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender<M,K> implem = _createImplementationOfSender();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Sender.Requires<M,K>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Component<M,K> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver.Requires<M,K>() {});
  }
}
