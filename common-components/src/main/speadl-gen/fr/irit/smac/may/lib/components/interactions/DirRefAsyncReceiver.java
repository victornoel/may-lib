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
  public interface Requires<M> {
  }
  
  public interface Parts<M> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public DirectReferences.Component<Push<M>> dr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AsyncReceiver.Component<M, DirRef> ar();
  }
  
  public static class ComponentImpl<M> implements DirRefAsyncReceiver.Component<M>, DirRefAsyncReceiver.Parts<M> {
    private final DirRefAsyncReceiver.Requires<M> bridge;
    
    private final DirRefAsyncReceiver<M> implementation;
    
    public void start() {
      assert this.dr != null: "This is a bug.";
      ((DirectReferences.ComponentImpl<Push<M>>) this.dr).start();
      assert this.ar != null: "This is a bug.";
      ((AsyncReceiver.ComponentImpl<M, DirRef>) this.ar).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.dr == null: "This is a bug.";
      assert this.implem_dr == null: "This is a bug.";
      this.implem_dr = this.implementation.make_dr();
      if (this.implem_dr == null) {
      	throw new RuntimeException("make_dr() in fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver should not return null.");
      }
      this.dr = this.implem_dr._newComponent(new BridgeImpl_dr(), false);
      assert this.ar == null: "This is a bug.";
      assert this.implem_ar == null: "This is a bug.";
      this.implem_ar = this.implementation.make_ar();
      if (this.implem_ar == null) {
      	throw new RuntimeException("make_ar() in fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver should not return null.");
      }
      this.ar = this.implem_ar._newComponent(new BridgeImpl_ar(), false);
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final DirRefAsyncReceiver<M> implem, final DirRefAsyncReceiver.Requires<M> b, final boolean doInits) {
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
    
    public ReliableSend<M, DirRef> send() {
      return this.ar.send();
    }
    
    private DirectReferences.Component<Push<M>> dr;
    
    private DirectReferences<Push<M>> implem_dr;
    
    private final class BridgeImpl_dr implements DirectReferences.Requires<Push<M>> {
    }
    
    public final DirectReferences.Component<Push<M>> dr() {
      return this.dr;
    }
    
    private AsyncReceiver.Component<M, DirRef> ar;
    
    private AsyncReceiver<M, DirRef> implem_ar;
    
    private final class BridgeImpl_ar implements AsyncReceiver.Requires<M, DirRef> {
      public final Call<Push<M>, DirRef> call() {
        return DirRefAsyncReceiver.ComponentImpl.this.dr.call();
      }
    }
    
    public final AsyncReceiver.Component<M, DirRef> ar() {
      return this.ar;
    }
  }
  
  public interface Provides<M> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableSend<M, DirRef> send();
  }
  
  public interface Component<M> extends DirRefAsyncReceiver.Provides<M> {
  }
  
  public abstract static class Receiver<M> {
    public interface Requires<M> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Push<M> put();
    }
    
    public interface Parts<M> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public DirectReferences.Callee.Component<Push<M>> dr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AsyncReceiver.Receiver.Component<M, DirRef> ar();
    }
    
    public static class ComponentImpl<M> implements DirRefAsyncReceiver.Receiver.Component<M>, DirRefAsyncReceiver.Receiver.Parts<M> {
      private final DirRefAsyncReceiver.Receiver.Requires<M> bridge;
      
      private final DirRefAsyncReceiver.Receiver<M> implementation;
      
      public void start() {
        assert this.dr != null: "This is a bug.";
        ((DirectReferences.Callee.ComponentImpl<Push<M>>) this.dr).start();
        assert this.ar != null: "This is a bug.";
        ((AsyncReceiver.Receiver.ComponentImpl<M, DirRef>) this.ar).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.dr == null: "This is a bug.";
        assert this.implementation.use_dr != null: "This is a bug.";
        this.dr = this.implementation.use_dr._newComponent(new BridgeImpl_dr_dr(), false);
        assert this.ar == null: "This is a bug.";
        assert this.implementation.use_ar != null: "This is a bug.";
        this.ar = this.implementation.use_ar._newComponent(new BridgeImpl_ar_ar(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final DirRefAsyncReceiver.Receiver<M> implem, final DirRefAsyncReceiver.Receiver.Requires<M> b, final boolean doInits) {
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
      
      public Pull<DirRef> me() {
        return this.dr.me();
      }
      
      public Do stop() {
        return this.dr.stop();
      }
      
      private DirectReferences.Callee.Component<Push<M>> dr;
      
      private final class BridgeImpl_dr_dr implements DirectReferences.Callee.Requires<Push<M>> {
        public final Push<M> toCall() {
          return DirRefAsyncReceiver.Receiver.ComponentImpl.this.ar.toCall();
        }
      }
      
      public final DirectReferences.Callee.Component<Push<M>> dr() {
        return this.dr;
      }
      
      private AsyncReceiver.Receiver.Component<M, DirRef> ar;
      
      private final class BridgeImpl_ar_ar implements AsyncReceiver.Receiver.Requires<M, DirRef> {
        public final Push<M> put() {
          return DirRefAsyncReceiver.Receiver.ComponentImpl.this.bridge.put();
        }
      }
      
      public final AsyncReceiver.Receiver.Component<M, DirRef> ar() {
        return this.ar;
      }
    }
    
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
    
    public interface Component<M> extends DirRefAsyncReceiver.Receiver.Provides<M> {
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
    
    private DirRefAsyncReceiver.Receiver.ComponentImpl<M> selfComponent;
    
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
    protected DirRefAsyncReceiver.Receiver.Provides<M> provides() {
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
    protected DirRefAsyncReceiver.Receiver.Requires<M> requires() {
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
    protected DirRefAsyncReceiver.Receiver.Parts<M> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private DirectReferences.Callee<Push<M>> use_dr;
    
    private AsyncReceiver.Receiver<M, DirRef> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized DirRefAsyncReceiver.Receiver.Component<M> _newComponent(final DirRefAsyncReceiver.Receiver.Requires<M> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Receiver has already been used to create a component, use another one.");
      }
      this.init = true;
      DirRefAsyncReceiver.Receiver.ComponentImpl<M> comp = new DirRefAsyncReceiver.Receiver.ComponentImpl<M>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private DirRefAsyncReceiver.ComponentImpl<M> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected DirRefAsyncReceiver.Provides<M> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected DirRefAsyncReceiver.Requires<M> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected DirRefAsyncReceiver.Parts<M> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  public abstract static class Sender<M> {
    public interface Requires<M> {
    }
    
    public interface Parts<M> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public AsyncReceiver.Sender.Component<M, DirRef> ar();
    }
    
    public static class ComponentImpl<M> implements DirRefAsyncReceiver.Sender.Component<M>, DirRefAsyncReceiver.Sender.Parts<M> {
      private final DirRefAsyncReceiver.Sender.Requires<M> bridge;
      
      private final DirRefAsyncReceiver.Sender<M> implementation;
      
      public void start() {
        assert this.ar != null: "This is a bug.";
        ((AsyncReceiver.Sender.ComponentImpl<M, DirRef>) this.ar).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.ar == null: "This is a bug.";
        assert this.implementation.use_ar != null: "This is a bug.";
        this.ar = this.implementation.use_ar._newComponent(new BridgeImpl_ar_ar(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final DirRefAsyncReceiver.Sender<M> implem, final DirRefAsyncReceiver.Sender.Requires<M> b, final boolean doInits) {
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
      
      public ReliableSend<M, DirRef> send() {
        return this.ar.send();
      }
      
      private AsyncReceiver.Sender.Component<M, DirRef> ar;
      
      private final class BridgeImpl_ar_ar implements AsyncReceiver.Sender.Requires<M, DirRef> {
      }
      
      public final AsyncReceiver.Sender.Component<M, DirRef> ar() {
        return this.ar;
      }
    }
    
    public interface Provides<M> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableSend<M, DirRef> send();
    }
    
    public interface Component<M> extends DirRefAsyncReceiver.Sender.Provides<M> {
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
    
    private DirRefAsyncReceiver.Sender.ComponentImpl<M> selfComponent;
    
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
    protected DirRefAsyncReceiver.Sender.Provides<M> provides() {
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
    protected DirRefAsyncReceiver.Sender.Requires<M> requires() {
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
    protected DirRefAsyncReceiver.Sender.Parts<M> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private AsyncReceiver.Sender<M, DirRef> use_ar;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized DirRefAsyncReceiver.Sender.Component<M> _newComponent(final DirRefAsyncReceiver.Sender.Requires<M> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Sender has already been used to create a component, use another one.");
      }
      this.init = true;
      DirRefAsyncReceiver.Sender.ComponentImpl<M> comp = new DirRefAsyncReceiver.Sender.ComponentImpl<M>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private DirRefAsyncReceiver.ComponentImpl<M> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected DirRefAsyncReceiver.Provides<M> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected DirRefAsyncReceiver.Requires<M> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected DirRefAsyncReceiver.Parts<M> eco_parts() {
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
  
  private DirRefAsyncReceiver.ComponentImpl<M> selfComponent;
  
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
  protected DirRefAsyncReceiver.Provides<M> provides() {
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
  protected DirRefAsyncReceiver.Requires<M> requires() {
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
  protected DirRefAsyncReceiver.Parts<M> parts() {
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
  protected abstract DirectReferences<Push<M>> make_dr();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AsyncReceiver<M, DirRef> make_ar();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized DirRefAsyncReceiver.Component<M> _newComponent(final DirRefAsyncReceiver.Requires<M> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of DirRefAsyncReceiver has already been used to create a component, use another one.");
    }
    this.init = true;
    DirRefAsyncReceiver.ComponentImpl<M> comp = new DirRefAsyncReceiver.ComponentImpl<M>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract DirRefAsyncReceiver.Receiver<M> make_Receiver(final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public DirRefAsyncReceiver.Receiver<M> _createImplementationOfReceiver(final String name) {
    DirRefAsyncReceiver.Receiver<M> implem = make_Receiver(name);
    if (implem == null) {
    	throw new RuntimeException("make_Receiver() in fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_dr != null: "This is a bug.";
    assert implem.use_dr == null: "This is a bug.";
    implem.use_dr = this.selfComponent.implem_dr._createImplementationOfCallee(name);
    assert this.selfComponent.implem_ar != null: "This is a bug.";
    assert implem.use_ar == null: "This is a bug.";
    implem.use_ar = this.selfComponent.implem_ar._createImplementationOfReceiver();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract DirRefAsyncReceiver.Sender<M> make_Sender();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public DirRefAsyncReceiver.Sender<M> _createImplementationOfSender() {
    DirRefAsyncReceiver.Sender<M> implem = make_Sender();
    if (implem == null) {
    	throw new RuntimeException("make_Sender() in fr.irit.smac.may.lib.components.interactions.DirRefAsyncReceiver should not return null.");
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
  protected DirRefAsyncReceiver.Sender.Component<M> newSender() {
    DirRefAsyncReceiver.Sender<M> implem = _createImplementationOfSender();
    return implem._newComponent(new DirRefAsyncReceiver.Sender.Requires<M>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public DirRefAsyncReceiver.Component<M> newComponent() {
    return this._newComponent(new DirRefAsyncReceiver.Requires<M>() {}, true);
  }
}
