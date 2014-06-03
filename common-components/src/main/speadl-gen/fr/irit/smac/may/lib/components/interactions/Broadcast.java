package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class Broadcast<M, R> {
  public interface Requires<M, R> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ReliableSend<M, R> send();
  }
  
  public interface Component<M, R> extends Broadcast.Provides<M, R> {
  }
  
  public interface Provides<M, R> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<M> broadcast();
  }
  
  public interface Parts<M, R> {
  }
  
  public static class ComponentImpl<M, R> implements Broadcast.Component<M, R>, Broadcast.Parts<M, R> {
    private final Broadcast.Requires<M, R> bridge;
    
    private final Broadcast<M, R> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_broadcast() {
      assert this.broadcast == null: "This is a bug.";
      this.broadcast = this.implementation.make_broadcast();
      if (this.broadcast == null) {
      	throw new RuntimeException("make_broadcast() in fr.irit.smac.may.lib.components.interactions.Broadcast<M, R> should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_broadcast();
    }
    
    public ComponentImpl(final Broadcast<M, R> implem, final Broadcast.Requires<M, R> b, final boolean doInits) {
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
    
    private Push<M> broadcast;
    
    public Push<M> broadcast() {
      return this.broadcast;
    }
  }
  
  public abstract static class BroadcastTarget<M, R> {
    public interface Requires<M, R> {
      /**
       * This can be called by the implementation to access this required port.
       * 
       */
      public Pull<R> me();
    }
    
    public interface Component<M, R> extends Broadcast.BroadcastTarget.Provides<M, R> {
    }
    
    public interface Provides<M, R> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Do stop();
    }
    
    public interface Parts<M, R> {
    }
    
    public static class ComponentImpl<M, R> implements Broadcast.BroadcastTarget.Component<M, R>, Broadcast.BroadcastTarget.Parts<M, R> {
      private final Broadcast.BroadcastTarget.Requires<M, R> bridge;
      
      private final Broadcast.BroadcastTarget<M, R> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_stop() {
        assert this.stop == null: "This is a bug.";
        this.stop = this.implementation.make_stop();
        if (this.stop == null) {
        	throw new RuntimeException("make_stop() in fr.irit.smac.may.lib.components.interactions.Broadcast$BroadcastTarget<M, R> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_stop();
      }
      
      public ComponentImpl(final Broadcast.BroadcastTarget<M, R> implem, final Broadcast.BroadcastTarget.Requires<M, R> b, final boolean doInits) {
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
      
      private Do stop;
      
      public Do stop() {
        return this.stop;
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
    
    private Broadcast.BroadcastTarget.ComponentImpl<M, R> selfComponent;
    
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
    protected Broadcast.BroadcastTarget.Provides<M, R> provides() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Do make_stop();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Broadcast.BroadcastTarget.Requires<M, R> requires() {
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
    protected Broadcast.BroadcastTarget.Parts<M, R> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized Broadcast.BroadcastTarget.Component<M, R> _newComponent(final Broadcast.BroadcastTarget.Requires<M, R> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of BroadcastTarget has already been used to create a component, use another one.");
      }
      this.init = true;
      Broadcast.BroadcastTarget.ComponentImpl<M, R>  _comp = new Broadcast.BroadcastTarget.ComponentImpl<M, R>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Broadcast.ComponentImpl<M, R> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Broadcast.Provides<M, R> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Broadcast.Requires<M, R> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Broadcast.Parts<M, R> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
  }
  
  public abstract static class Broadcaster<M, R> {
    public interface Requires<M, R> {
    }
    
    public interface Component<M, R> extends Broadcast.Broadcaster.Provides<M, R> {
    }
    
    public interface Provides<M, R> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Push<M> broadcast();
    }
    
    public interface Parts<M, R> {
    }
    
    public static class ComponentImpl<M, R> implements Broadcast.Broadcaster.Component<M, R>, Broadcast.Broadcaster.Parts<M, R> {
      private final Broadcast.Broadcaster.Requires<M, R> bridge;
      
      private final Broadcast.Broadcaster<M, R> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_broadcast() {
        assert this.broadcast == null: "This is a bug.";
        this.broadcast = this.implementation.make_broadcast();
        if (this.broadcast == null) {
        	throw new RuntimeException("make_broadcast() in fr.irit.smac.may.lib.components.interactions.Broadcast$Broadcaster<M, R> should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_broadcast();
      }
      
      public ComponentImpl(final Broadcast.Broadcaster<M, R> implem, final Broadcast.Broadcaster.Requires<M, R> b, final boolean doInits) {
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
      
      private Push<M> broadcast;
      
      public Push<M> broadcast() {
        return this.broadcast;
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
    
    private Broadcast.Broadcaster.ComponentImpl<M, R> selfComponent;
    
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
    protected Broadcast.Broadcaster.Provides<M, R> provides() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Push<M> make_broadcast();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Broadcast.Broadcaster.Requires<M, R> requires() {
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
    protected Broadcast.Broadcaster.Parts<M, R> parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized Broadcast.Broadcaster.Component<M, R> _newComponent(final Broadcast.Broadcaster.Requires<M, R> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Broadcaster has already been used to create a component, use another one.");
      }
      this.init = true;
      Broadcast.Broadcaster.ComponentImpl<M, R>  _comp = new Broadcast.Broadcaster.ComponentImpl<M, R>(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Broadcast.ComponentImpl<M, R> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Broadcast.Provides<M, R> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Broadcast.Requires<M, R> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Broadcast.Parts<M, R> eco_parts() {
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
  
  private Broadcast.ComponentImpl<M, R> selfComponent;
  
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
  protected Broadcast.Provides<M, R> provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Push<M> make_broadcast();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Broadcast.Requires<M, R> requires() {
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
  protected Broadcast.Parts<M, R> parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized Broadcast.Component<M, R> _newComponent(final Broadcast.Requires<M, R> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Broadcast has already been used to create a component, use another one.");
    }
    this.init = true;
    Broadcast.ComponentImpl<M, R>  _comp = new Broadcast.ComponentImpl<M, R>(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Broadcast.BroadcastTarget<M, R> make_BroadcastTarget();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Broadcast.BroadcastTarget<M, R> _createImplementationOfBroadcastTarget() {
    Broadcast.BroadcastTarget<M, R> implem = make_BroadcastTarget();
    if (implem == null) {
    	throw new RuntimeException("make_BroadcastTarget() in fr.irit.smac.may.lib.components.interactions.Broadcast should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Broadcast.Broadcaster<M, R> make_Broadcaster();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Broadcast.Broadcaster<M, R> _createImplementationOfBroadcaster() {
    Broadcast.Broadcaster<M, R> implem = make_Broadcaster();
    if (implem == null) {
    	throw new RuntimeException("make_Broadcaster() in fr.irit.smac.may.lib.components.interactions.Broadcast should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected Broadcast.Broadcaster.Component<M, R> newBroadcaster() {
    Broadcast.Broadcaster<M, R> _implem = _createImplementationOfBroadcaster();
    return _implem._newComponent(new Broadcast.Broadcaster.Requires<M, R>() {},true);
  }
}
