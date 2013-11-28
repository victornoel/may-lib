package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.AsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.DirectReferences;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class DirRefAsyncReceiver<M> {
  @SuppressWarnings("all")
  public interface Requires<M> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<M> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableSend<M,DirRef> deposit();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<M> extends fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Provides<M> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<M> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Push<M>> dr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,DirRef> ar();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<M> implements fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Component<M>, fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Parts<M> {
    private final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Requires<M> bridge;
    
    private final DirRefAsyncReceiver<M> implementation;
    
    protected void initParts() {
      assert this.implem_dr == null;
      this.implem_dr = this.implementation.make_dr();
      assert this.dr == null;
      this.dr = this.implem_dr.newComponent(new BridgeImpl_dr());
      assert this.implem_ar == null;
      this.implem_ar = this.implementation.make_ar();
      assert this.ar == null;
      this.ar = this.implem_ar.newComponent(new BridgeImpl_ar());
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final DirRefAsyncReceiver<M> implem, final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Requires<M> b, final boolean initMakes) {
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
    
    public final ReliableSend<M,DirRef> deposit() {
      return this.ar.deposit();
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Push<M>> dr;
    
    private DirectReferences<Push<M>> implem_dr;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_dr implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<Push<M>> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Push<M>> dr() {
      return this.dr;
    }
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,DirRef> ar;
    
    private AsyncReceiver<M,DirRef> implem_ar;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_ar implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,DirRef> {
      public final Call<Push<M>,DirRef> call() {
        return fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.ComponentImpl.this.dr.call();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,DirRef> ar() {
      return this.ar;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Receiver<M> {
    @SuppressWarnings("all")
    public interface Requires<M> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Push<M> put();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<M> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<DirRef> me();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<M> extends fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Provides<M> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Push<M>> dr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,DirRef> ar();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M> implements fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Component<M>, fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Parts<M> {
      private final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Requires<M> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver<M> implementation;
      
      protected void initParts() {
        assert this.implementation.use_dr != null;
        assert this.dr == null;
        this.dr = this.implementation.use_dr.newComponent(new BridgeImpl_dr_dr());
        assert this.implementation.use_ar != null;
        assert this.ar == null;
        this.ar = this.implementation.use_ar.newComponent(new BridgeImpl_ar_ar());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver<M> implem, final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Requires<M> b, final boolean initMakes) {
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
      
      public final Pull<DirRef> me() {
        return this.dr.me();
      }
      
      public final Do stop() {
        return this.dr.stop();
      }
      
      private fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Push<M>> dr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_dr_dr implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Requires<Push<M>> {
        public final Push<M> toCall() {
          return fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.ComponentImpl.this.ar.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Push<M>> dr() {
        return this.dr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,DirRef> ar;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_ar_ar implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,DirRef> {
        public final Push<M> put() {
          return fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.ComponentImpl.this.bridge.put();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,DirRef> ar() {
        return this.ar;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.ComponentImpl<M> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Provides<M> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Requires<M> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Parts<M> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<Push<M>> use_dr;
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,DirRef> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Component<M> newComponent(final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.Requires<M> b) {
      fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.ComponentImpl<M> comp = new fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver.ComponentImpl<M>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.ComponentImpl<M> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Provides<M> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Requires<M> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Parts<M> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Sender<M> {
    @SuppressWarnings("all")
    public interface Requires<M> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<M> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableSend<M,DirRef> send();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<M> extends fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Provides<M> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,DirRef> ar();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M> implements fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Component<M>, fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Parts<M> {
      private final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Requires<M> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender<M> implementation;
      
      protected void initParts() {
        assert this.implementation.use_ar != null;
        assert this.ar == null;
        this.ar = this.implementation.use_ar.newComponent(new BridgeImpl_ar_ar());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender<M> implem, final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Requires<M> b, final boolean initMakes) {
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
      
      public final ReliableSend<M,DirRef> send() {
        return this.ar.send();
      }
      
      private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,DirRef> ar;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_ar_ar implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,DirRef> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,DirRef> ar() {
        return this.ar;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.ComponentImpl<M> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Provides<M> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Requires<M> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Parts<M> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,DirRef> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Component<M> newComponent(final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Requires<M> b) {
      fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.ComponentImpl<M> comp = new fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.ComponentImpl<M>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.ComponentImpl<M> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Provides<M> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Requires<M> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Parts<M> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.ComponentImpl<M> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Provides<M> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Requires<M> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Parts<M> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract DirectReferences<Push<M>> make_dr();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AsyncReceiver<M,DirRef> make_ar();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Component<M> newComponent(final fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Requires<M> b) {
    fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.ComponentImpl<M> comp = new fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.ComponentImpl<M>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver<M> make_Receiver(final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver<M> _createImplementationOfReceiver(final String name) {
    fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Receiver<M> implem = make_Receiver(name);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_dr != null;
    assert implem.use_dr == null;
    implem.use_dr = this.selfComponent.implem_dr._createImplementationOfCallee(name);
    assert this.selfComponent.implem_ar != null;
    assert implem.use_ar == null;
    implem.use_ar = this.selfComponent.implem_ar._createImplementationOfReceiver();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender<M> make_Sender();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender<M> _createImplementationOfSender() {
    fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender<M> implem = make_Sender();
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
  protected fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Component<M> newSender() {
    fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender<M> implem = _createImplementationOfSender();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Sender.Requires<M>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Component<M> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver.Requires<M>() {});
  }
}
