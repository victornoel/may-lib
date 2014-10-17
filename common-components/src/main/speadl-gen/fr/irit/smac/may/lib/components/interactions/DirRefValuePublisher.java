package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.DirectReferences;
import fr.irit.smac.may.lib.components.interactions.ValuePublisher;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class DirRefValuePublisher<T> {
  public interface Requires<T> {
  }
  
  public interface Component<T> extends DirRefValuePublisher.Provides<T> {
  }
  
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableObserve<T, DirRef> observe();
  }
  
  public interface Parts<T> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public DirectReferences.Component<Pull<T>> dr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ValuePublisher.Component<T, DirRef> vp();
  }
  
  public static class ComponentImpl<T> implements DirRefValuePublisher.Component<T>, DirRefValuePublisher.Parts<T> {
    private final DirRefValuePublisher.Requires<T> bridge;
    
    private final DirRefValuePublisher<T> implementation;
    
    public void start() {
      assert this.dr != null: "This is a bug.";
      ((DirectReferences.ComponentImpl<Pull<T>>) this.dr).start();
      assert this.vp != null: "This is a bug.";
      ((ValuePublisher.ComponentImpl<T, DirRef>) this.vp).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_dr() {
      assert this.dr == null: "This is a bug.";
      assert this.implem_dr == null: "This is a bug.";
      this.implem_dr = this.implementation.make_dr();
      if (this.implem_dr == null) {
      	throw new RuntimeException("make_dr() in fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher<T> should not return null.");
      }
      this.dr = this.implem_dr._newComponent(new BridgeImpl_dr(), false);
      
    }
    
    private void init_vp() {
      assert this.vp == null: "This is a bug.";
      assert this.implem_vp == null: "This is a bug.";
      this.implem_vp = this.implementation.make_vp();
      if (this.implem_vp == null) {
      	throw new RuntimeException("make_vp() in fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher<T> should not return null.");
      }
      this.vp = this.implem_vp._newComponent(new BridgeImpl_vp(), false);
      
    }
    
    protected void initParts() {
      init_dr();
      init_vp();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final DirRefValuePublisher<T> implem, final DirRefValuePublisher.Requires<T> b, final boolean doInits) {
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
    
    public ReliableObserve<T, DirRef> observe() {
      return this.vp().observe();
    }
    
    private DirectReferences.Component<Pull<T>> dr;
    
    private DirectReferences<Pull<T>> implem_dr;
    
    private final class BridgeImpl_dr implements DirectReferences.Requires<Pull<T>> {
    }
    
    public final DirectReferences.Component<Pull<T>> dr() {
      return this.dr;
    }
    
    private ValuePublisher.Component<T, DirRef> vp;
    
    private ValuePublisher<T, DirRef> implem_vp;
    
    private final class BridgeImpl_vp implements ValuePublisher.Requires<T, DirRef> {
      public final Call<Pull<T>, DirRef> call() {
        return DirRefValuePublisher.ComponentImpl.this.dr().call();
      }
    }
    
    public final ValuePublisher.Component<T, DirRef> vp() {
      return this.vp;
    }
  }
  
  public static class PublisherPush<T> {
    public interface Requires<T> {
    }
    
    public interface Component<T> extends DirRefValuePublisher.PublisherPush.Provides<T> {
    }
    
    public interface Provides<T> {
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
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    public interface Parts<T> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public DirectReferences.Callee.Component<Pull<T>> dr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ValuePublisher.PublisherPush.Component<T, DirRef> vp();
    }
    
    public static class ComponentImpl<T> implements DirRefValuePublisher.PublisherPush.Component<T>, DirRefValuePublisher.PublisherPush.Parts<T> {
      private final DirRefValuePublisher.PublisherPush.Requires<T> bridge;
      
      private final DirRefValuePublisher.PublisherPush<T> implementation;
      
      public void start() {
        assert this.dr != null: "This is a bug.";
        ((DirectReferences.Callee.ComponentImpl<Pull<T>>) this.dr).start();
        assert this.vp != null: "This is a bug.";
        ((ValuePublisher.PublisherPush.ComponentImpl<T, DirRef>) this.vp).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_dr() {
        assert this.dr == null: "This is a bug.";
        assert this.implementation.use_dr != null: "This is a bug.";
        this.dr = this.implementation.use_dr._newComponent(new BridgeImpl_dr_dr(), false);
        
      }
      
      private void init_vp() {
        assert this.vp == null: "This is a bug.";
        assert this.implementation.use_vp != null: "This is a bug.";
        this.vp = this.implementation.use_vp._newComponent(new BridgeImpl_vp_vp(), false);
        
      }
      
      protected void initParts() {
        init_dr();
        init_vp();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final DirRefValuePublisher.PublisherPush<T> implem, final DirRefValuePublisher.PublisherPush.Requires<T> b, final boolean doInits) {
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
      
      public Push<T> set() {
        return this.vp().set();
      }
      
      public Pull<T> get() {
        return this.vp().get();
      }
      
      public Do stop() {
        return this.dr().stop();
      }
      
      private DirectReferences.Callee.Component<Pull<T>> dr;
      
      private final class BridgeImpl_dr_dr implements DirectReferences.Callee.Requires<Pull<T>> {
        public final Pull<T> toCall() {
          return DirRefValuePublisher.PublisherPush.ComponentImpl.this.vp().toCall();
        }
      }
      
      public final DirectReferences.Callee.Component<Pull<T>> dr() {
        return this.dr;
      }
      
      private ValuePublisher.PublisherPush.Component<T, DirRef> vp;
      
      private final class BridgeImpl_vp_vp implements ValuePublisher.PublisherPush.Requires<T, DirRef> {
      }
      
      public final ValuePublisher.PublisherPush.Component<T, DirRef> vp() {
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
     * 
     */
    private boolean started = false;;
    
    private DirRefValuePublisher.PublisherPush.ComponentImpl<T> selfComponent;
    
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
    protected DirRefValuePublisher.PublisherPush.Provides<T> provides() {
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
    protected DirRefValuePublisher.PublisherPush.Requires<T> requires() {
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
    protected DirRefValuePublisher.PublisherPush.Parts<T> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private DirectReferences.Callee<Pull<T>> use_dr;
    
    private ValuePublisher.PublisherPush<T, DirRef> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized DirRefValuePublisher.PublisherPush.Component<T> _newComponent(final DirRefValuePublisher.PublisherPush.Requires<T> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PublisherPush has already been used to create a component, use another one.");
      }
      this.init = true;
      DirRefValuePublisher.PublisherPush.ComponentImpl<T>  _comp = new DirRefValuePublisher.PublisherPush.ComponentImpl<T>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private DirRefValuePublisher.ComponentImpl<T> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected DirRefValuePublisher.Provides<T> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected DirRefValuePublisher.Requires<T> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected DirRefValuePublisher.Parts<T> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static class PublisherPull<T> {
    public interface Requires<T> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<T> getValue();
    }
    
    public interface Component<T> extends DirRefValuePublisher.PublisherPull.Provides<T> {
    }
    
    public interface Provides<T> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> get();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    public interface Parts<T> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public DirectReferences.Callee.Component<Pull<T>> dr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ValuePublisher.PublisherPull.Component<T, DirRef> vp();
    }
    
    public static class ComponentImpl<T> implements DirRefValuePublisher.PublisherPull.Component<T>, DirRefValuePublisher.PublisherPull.Parts<T> {
      private final DirRefValuePublisher.PublisherPull.Requires<T> bridge;
      
      private final DirRefValuePublisher.PublisherPull<T> implementation;
      
      public void start() {
        assert this.dr != null: "This is a bug.";
        ((DirectReferences.Callee.ComponentImpl<Pull<T>>) this.dr).start();
        assert this.vp != null: "This is a bug.";
        ((ValuePublisher.PublisherPull.ComponentImpl<T, DirRef>) this.vp).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_dr() {
        assert this.dr == null: "This is a bug.";
        assert this.implementation.use_dr != null: "This is a bug.";
        this.dr = this.implementation.use_dr._newComponent(new BridgeImpl_dr_dr(), false);
        
      }
      
      private void init_vp() {
        assert this.vp == null: "This is a bug.";
        assert this.implementation.use_vp != null: "This is a bug.";
        this.vp = this.implementation.use_vp._newComponent(new BridgeImpl_vp_vp(), false);
        
      }
      
      protected void initParts() {
        init_dr();
        init_vp();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final DirRefValuePublisher.PublisherPull<T> implem, final DirRefValuePublisher.PublisherPull.Requires<T> b, final boolean doInits) {
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
      
      public Pull<T> get() {
        return this.vp().get();
      }
      
      public Do stop() {
        return this.dr().stop();
      }
      
      private DirectReferences.Callee.Component<Pull<T>> dr;
      
      private final class BridgeImpl_dr_dr implements DirectReferences.Callee.Requires<Pull<T>> {
        public final Pull<T> toCall() {
          return DirRefValuePublisher.PublisherPull.ComponentImpl.this.vp().toCall();
        }
      }
      
      public final DirectReferences.Callee.Component<Pull<T>> dr() {
        return this.dr;
      }
      
      private ValuePublisher.PublisherPull.Component<T, DirRef> vp;
      
      private final class BridgeImpl_vp_vp implements ValuePublisher.PublisherPull.Requires<T, DirRef> {
        public final Pull<T> getValue() {
          return DirRefValuePublisher.PublisherPull.ComponentImpl.this.bridge.getValue();
        }
      }
      
      public final ValuePublisher.PublisherPull.Component<T, DirRef> vp() {
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
     * 
     */
    private boolean started = false;;
    
    private DirRefValuePublisher.PublisherPull.ComponentImpl<T> selfComponent;
    
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
    protected DirRefValuePublisher.PublisherPull.Provides<T> provides() {
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
    protected DirRefValuePublisher.PublisherPull.Requires<T> requires() {
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
    protected DirRefValuePublisher.PublisherPull.Parts<T> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private DirectReferences.Callee<Pull<T>> use_dr;
    
    private ValuePublisher.PublisherPull<T, DirRef> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized DirRefValuePublisher.PublisherPull.Component<T> _newComponent(final DirRefValuePublisher.PublisherPull.Requires<T> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of PublisherPull has already been used to create a component, use another one.");
      }
      this.init = true;
      DirRefValuePublisher.PublisherPull.ComponentImpl<T>  _comp = new DirRefValuePublisher.PublisherPull.ComponentImpl<T>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private DirRefValuePublisher.ComponentImpl<T> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected DirRefValuePublisher.Provides<T> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected DirRefValuePublisher.Requires<T> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected DirRefValuePublisher.Parts<T> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public static class Observer<T> {
    public interface Requires<T> {
    }
    
    public interface Component<T> extends DirRefValuePublisher.Observer.Provides<T> {
    }
    
    public interface Provides<T> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableObserve<T, DirRef> observe();
    }
    
    public interface Parts<T> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ValuePublisher.Observer.Component<T, DirRef> vp();
    }
    
    public static class ComponentImpl<T> implements DirRefValuePublisher.Observer.Component<T>, DirRefValuePublisher.Observer.Parts<T> {
      private final DirRefValuePublisher.Observer.Requires<T> bridge;
      
      private final DirRefValuePublisher.Observer<T> implementation;
      
      public void start() {
        assert this.vp != null: "This is a bug.";
        ((ValuePublisher.Observer.ComponentImpl<T, DirRef>) this.vp).start();
        this.implementation.start();
        this.implementation.started = true;
      }
      
      private void init_vp() {
        assert this.vp == null: "This is a bug.";
        assert this.implementation.use_vp != null: "This is a bug.";
        this.vp = this.implementation.use_vp._newComponent(new BridgeImpl_vp_vp(), false);
        
      }
      
      protected void initParts() {
        init_vp();
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final DirRefValuePublisher.Observer<T> implem, final DirRefValuePublisher.Observer.Requires<T> b, final boolean doInits) {
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
      
      public ReliableObserve<T, DirRef> observe() {
        return this.vp().observe();
      }
      
      private ValuePublisher.Observer.Component<T, DirRef> vp;
      
      private final class BridgeImpl_vp_vp implements ValuePublisher.Observer.Requires<T, DirRef> {
      }
      
      public final ValuePublisher.Observer.Component<T, DirRef> vp() {
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
     * 
     */
    private boolean started = false;;
    
    private DirRefValuePublisher.Observer.ComponentImpl<T> selfComponent;
    
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
    protected DirRefValuePublisher.Observer.Provides<T> provides() {
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
    protected DirRefValuePublisher.Observer.Requires<T> requires() {
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
    protected DirRefValuePublisher.Observer.Parts<T> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    private ValuePublisher.Observer<T, DirRef> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized DirRefValuePublisher.Observer.Component<T> _newComponent(final DirRefValuePublisher.Observer.Requires<T> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Observer has already been used to create a component, use another one.");
      }
      this.init = true;
      DirRefValuePublisher.Observer.ComponentImpl<T>  _comp = new DirRefValuePublisher.Observer.ComponentImpl<T>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private DirRefValuePublisher.ComponentImpl<T> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected DirRefValuePublisher.Provides<T> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected DirRefValuePublisher.Requires<T> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected DirRefValuePublisher.Parts<T> eco_parts() {
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
  
  private DirRefValuePublisher.ComponentImpl<T> selfComponent;
  
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
  protected DirRefValuePublisher.Provides<T> provides() {
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
  protected DirRefValuePublisher.Requires<T> requires() {
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
  protected DirRefValuePublisher.Parts<T> parts() {
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
  protected abstract DirectReferences<Pull<T>> make_dr();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ValuePublisher<T, DirRef> make_vp();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized DirRefValuePublisher.Component<T> _newComponent(final DirRefValuePublisher.Requires<T> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of DirRefValuePublisher has already been used to create a component, use another one.");
    }
    this.init = true;
    DirRefValuePublisher.ComponentImpl<T>  _comp = new DirRefValuePublisher.ComponentImpl<T>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected DirRefValuePublisher.PublisherPush<T> make_PublisherPush(final String name) {
    return new DirRefValuePublisher.PublisherPush<T>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public DirRefValuePublisher.PublisherPush<T> _createImplementationOfPublisherPush(final String name) {
    DirRefValuePublisher.PublisherPush<T> implem = make_PublisherPush(name);
    if (implem == null) {
    	throw new RuntimeException("make_PublisherPush() in fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_dr != null: "This is a bug.";
    assert implem.use_dr == null: "This is a bug.";
    implem.use_dr = this.selfComponent.implem_dr._createImplementationOfCallee(name);
    assert this.selfComponent.implem_vp != null: "This is a bug.";
    assert implem.use_vp == null: "This is a bug.";
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPush();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected DirRefValuePublisher.PublisherPush.Component<T> newPublisherPush(final String name) {
    DirRefValuePublisher.PublisherPush<T> _implem = _createImplementationOfPublisherPush(name);
    return _implem._newComponent(new DirRefValuePublisher.PublisherPush.Requires<T>() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected DirRefValuePublisher.PublisherPull<T> make_PublisherPull(final String name) {
    return new DirRefValuePublisher.PublisherPull<T>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public DirRefValuePublisher.PublisherPull<T> _createImplementationOfPublisherPull(final String name) {
    DirRefValuePublisher.PublisherPull<T> implem = make_PublisherPull(name);
    if (implem == null) {
    	throw new RuntimeException("make_PublisherPull() in fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_dr != null: "This is a bug.";
    assert implem.use_dr == null: "This is a bug.";
    implem.use_dr = this.selfComponent.implem_dr._createImplementationOfCallee(name);
    assert this.selfComponent.implem_vp != null: "This is a bug.";
    assert implem.use_vp == null: "This is a bug.";
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPull();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected DirRefValuePublisher.Observer<T> make_Observer() {
    return new DirRefValuePublisher.Observer<T>();
  }
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public DirRefValuePublisher.Observer<T> _createImplementationOfObserver() {
    DirRefValuePublisher.Observer<T> implem = make_Observer();
    if (implem == null) {
    	throw new RuntimeException("make_Observer() in fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher should not return null.");
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
  protected DirRefValuePublisher.Observer.Component<T> newObserver() {
    DirRefValuePublisher.Observer<T> _implem = _createImplementationOfObserver();
    return _implem._newComponent(new DirRefValuePublisher.Observer.Requires<T>() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public DirRefValuePublisher.Component<T> newComponent() {
    return this._newComponent(new DirRefValuePublisher.Requires<T>() {}, true);
  }
}
