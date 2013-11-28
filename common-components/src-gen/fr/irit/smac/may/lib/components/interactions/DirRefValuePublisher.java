package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.DirectReferences;
import fr.irit.smac.may.lib.components.interactions.ValuePublisher;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableObserve;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class DirRefValuePublisher<T> {
  @SuppressWarnings("all")
  public interface Requires<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<T> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public ReliableObserve<T,DirRef> observe();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<T> extends fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Provides<T> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<T> {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Pull<T>> dr();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<T,DirRef> vp();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Component<T>, fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Parts<T> {
    private final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Requires<T> bridge;
    
    private final DirRefValuePublisher<T> implementation;
    
    protected void initParts() {
      assert this.implem_dr == null;
      this.implem_dr = this.implementation.make_dr();
      assert this.dr == null;
      this.dr = this.implem_dr.newComponent(new BridgeImpl_dr());
      assert this.implem_vp == null;
      this.implem_vp = this.implementation.make_vp();
      assert this.vp == null;
      this.vp = this.implem_vp.newComponent(new BridgeImpl_vp());
      
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final DirRefValuePublisher<T> implem, final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Requires<T> b, final boolean initMakes) {
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
    
    public final ReliableObserve<T,DirRef> observe() {
      return this.vp.observe();
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Pull<T>> dr;
    
    private DirectReferences<Pull<T>> implem_dr;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_dr implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Requires<Pull<T>> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.DirectReferences.Component<Pull<T>> dr() {
      return this.dr;
    }
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<T,DirRef> vp;
    
    private ValuePublisher<T,DirRef> implem_vp;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<T,DirRef> {
      public final Call<Pull<T>,DirRef> call() {
        return fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.ComponentImpl.this.dr.call();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<T,DirRef> vp() {
      return this.vp;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class PublisherPush<T> {
    @SuppressWarnings("all")
    public interface Requires<T> {
    }
    
    
    @SuppressWarnings("all")
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
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T> extends fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Provides<T> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Pull<T>> dr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,DirRef> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Component<T>, fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Parts<T> {
      private final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Requires<T> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush<T> implementation;
      
      protected void initParts() {
        assert this.implementation.use_dr != null;
        assert this.dr == null;
        this.dr = this.implementation.use_dr.newComponent(new BridgeImpl_dr_dr());
        assert this.implementation.use_vp != null;
        assert this.vp == null;
        this.vp = this.implementation.use_vp.newComponent(new BridgeImpl_vp_vp());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush<T> implem, final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Requires<T> b, final boolean initMakes) {
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
      
      private fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Pull<T>> dr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_dr_dr implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Requires<Pull<T>> {
        public final Pull<T> toCall() {
          return fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.ComponentImpl.this.vp.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Pull<T>> dr() {
        return this.dr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,DirRef> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<T,DirRef> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<T,DirRef> vp() {
        return this.vp;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.ComponentImpl<T> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Provides<T> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Requires<T> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Parts<T> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<Pull<T>> use_dr;
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<T,DirRef> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Component<T> newComponent(final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Requires<T> b) {
      fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.ComponentImpl<T> comp = new fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.ComponentImpl<T>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.ComponentImpl<T> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Provides<T> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Requires<T> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Parts<T> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class PublisherPull<T> {
    @SuppressWarnings("all")
    public interface Requires<T> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<T> getValue();
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<T> get();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T> extends fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Provides<T> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Pull<T>> dr();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,DirRef> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Component<T>, fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Parts<T> {
      private final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Requires<T> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull<T> implementation;
      
      protected void initParts() {
        assert this.implementation.use_dr != null;
        assert this.dr == null;
        this.dr = this.implementation.use_dr.newComponent(new BridgeImpl_dr_dr());
        assert this.implementation.use_vp != null;
        assert this.vp == null;
        this.vp = this.implementation.use_vp.newComponent(new BridgeImpl_vp_vp());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull<T> implem, final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Requires<T> b, final boolean initMakes) {
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
      
      private fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Pull<T>> dr;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_dr_dr implements fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Requires<Pull<T>> {
        public final Pull<T> toCall() {
          return fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.ComponentImpl.this.vp.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee.Component<Pull<T>> dr() {
        return this.dr;
      }
      
      private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,DirRef> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Requires<T,DirRef> {
        public final Pull<T> getValue() {
          return fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.ComponentImpl.this.bridge.getValue();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull.Component<T,DirRef> vp() {
        return this.vp;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.ComponentImpl<T> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Provides<T> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Requires<T> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Parts<T> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirectReferences.Callee<Pull<T>> use_dr;
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPull<T,DirRef> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Component<T> newComponent(final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.Requires<T> b) {
      fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.ComponentImpl<T> comp = new fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull.ComponentImpl<T>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.ComponentImpl<T> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Provides<T> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Requires<T> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Parts<T> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Observer<T> {
    @SuppressWarnings("all")
    public interface Requires<T> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<T> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public ReliableObserve<T,DirRef> observe();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<T> extends fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Provides<T> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<T> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,DirRef> vp();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<T> implements fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Component<T>, fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Parts<T> {
      private final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Requires<T> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer<T> implementation;
      
      protected void initParts() {
        assert this.implementation.use_vp != null;
        assert this.vp == null;
        this.vp = this.implementation.use_vp.newComponent(new BridgeImpl_vp_vp());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer<T> implem, final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Requires<T> b, final boolean initMakes) {
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
      
      public final ReliableObserve<T,DirRef> observe() {
        return this.vp.observe();
      }
      
      private fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,DirRef> vp;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_vp_vp implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Requires<T,DirRef> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<T,DirRef> vp() {
        return this.vp;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.ComponentImpl<T> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Provides<T> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Requires<T> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Parts<T> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<T,DirRef> use_vp;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Component<T> newComponent(final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Requires<T> b) {
      fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.ComponentImpl<T> comp = new fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.ComponentImpl<T>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.ComponentImpl<T> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Provides<T> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Requires<T> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Parts<T> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.ComponentImpl<T> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Provides<T> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Requires<T> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Parts<T> parts() {
    assert this.selfComponent != null;
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
  protected abstract ValuePublisher<T,DirRef> make_vp();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Component<T> newComponent(final fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Requires<T> b) {
    fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.ComponentImpl<T> comp = new fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.ComponentImpl<T>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush<T> make_PublisherPush(final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush<T> _createImplementationOfPublisherPush(final String name) {
    fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush<T> implem = make_PublisherPush(name);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_dr != null;
    assert implem.use_dr == null;
    implem.use_dr = this.selfComponent.implem_dr._createImplementationOfCallee(name);
    assert this.selfComponent.implem_vp != null;
    assert implem.use_vp == null;
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPush();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Component<T> newPublisherPush(final String name) {
    fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush<T> implem = _createImplementationOfPublisherPush(name);
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPush.Requires<T>() {});
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull<T> make_PublisherPull(final String name);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull<T> _createImplementationOfPublisherPull(final String name) {
    fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.PublisherPull<T> implem = make_PublisherPull(name);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_dr != null;
    assert implem.use_dr == null;
    implem.use_dr = this.selfComponent.implem_dr._createImplementationOfCallee(name);
    assert this.selfComponent.implem_vp != null;
    assert implem.use_vp == null;
    implem.use_vp = this.selfComponent.implem_vp._createImplementationOfPublisherPull();
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer<T> make_Observer();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer<T> _createImplementationOfObserver() {
    fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer<T> implem = make_Observer();
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
  protected fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Component<T> newObserver() {
    fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer<T> implem = _createImplementationOfObserver();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Observer.Requires<T>() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Component<T> newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.interactions.DirRefValuePublisher.Requires<T>() {});
  }
}
