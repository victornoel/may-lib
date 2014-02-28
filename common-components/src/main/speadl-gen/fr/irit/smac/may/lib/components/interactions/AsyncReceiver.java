package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend;
import fr.irit.smac.may.lib.interfaces.Push;

@SuppressWarnings("all")
public abstract class AsyncReceiver<M, K> {
  @SuppressWarnings("all")
  public interface Requires<M, K> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Call<Push<M>,K> call();
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
  public interface Component<M, K> extends AsyncReceiver.Provides<M,K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<M, K> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<M, K> implements AsyncReceiver.Component<M,K>, AsyncReceiver.Parts<M,K> {
    private final AsyncReceiver.Requires<M,K> bridge;
    
    private final AsyncReceiver<M,K> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.send == null: "This is a bug.";
      this.send = this.implementation.make_send();
      if (this.send == null) {
      	throw new RuntimeException("make_send() in fr.irit.smac.may.lib.components.interactions.AsyncReceiver should not return null.");
      }
      
    }
    
    public ComponentImpl(final AsyncReceiver<M,K> implem, final AsyncReceiver.Requires<M,K> b, final boolean doInits) {
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
    
    private ReliableSend<M,K> send;
    
    public final ReliableSend<M,K> send() {
      return this.send;
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
      public Push<M> toCall();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<M, K> extends AsyncReceiver.Receiver.Provides<M,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M, K> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements AsyncReceiver.Receiver.Component<M,K>, AsyncReceiver.Receiver.Parts<M,K> {
      private final AsyncReceiver.Receiver.Requires<M,K> bridge;
      
      private final AsyncReceiver.Receiver<M,K> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final AsyncReceiver.Receiver<M,K> implem, final AsyncReceiver.Receiver.Requires<M,K> b, final boolean doInits) {
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
      
      public final Push<M> toCall() {
        return this.bridge.put();
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
    
    private AsyncReceiver.Receiver.ComponentImpl<M,K> selfComponent;
    
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
    protected AsyncReceiver.Receiver.Provides<M,K> provides() {
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
    protected AsyncReceiver.Receiver.Requires<M,K> requires() {
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
    protected AsyncReceiver.Receiver.Parts<M,K> parts() {
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
    public synchronized AsyncReceiver.Receiver.Component<M,K> _newComponent(final AsyncReceiver.Receiver.Requires<M,K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Receiver has already been used to create a component, use another one.");
      }
      this.init = true;
      AsyncReceiver.Receiver.ComponentImpl<M,K> comp = new AsyncReceiver.Receiver.ComponentImpl<M,K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private AsyncReceiver.ComponentImpl<M,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected AsyncReceiver.Provides<M,K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected AsyncReceiver.Requires<M,K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected AsyncReceiver.Parts<M,K> eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
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
    public interface Component<M, K> extends AsyncReceiver.Sender.Provides<M,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M, K> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements AsyncReceiver.Sender.Component<M,K>, AsyncReceiver.Sender.Parts<M,K> {
      private final AsyncReceiver.Sender.Requires<M,K> bridge;
      
      private final AsyncReceiver.Sender<M,K> implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.send == null: "This is a bug.";
        this.send = this.implementation.make_send();
        if (this.send == null) {
        	throw new RuntimeException("make_send() in fr.irit.smac.may.lib.components.interactions.AsyncReceiver$Sender should not return null.");
        }
        
      }
      
      public ComponentImpl(final AsyncReceiver.Sender<M,K> implem, final AsyncReceiver.Sender.Requires<M,K> b, final boolean doInits) {
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
      
      private ReliableSend<M,K> send;
      
      public final ReliableSend<M,K> send() {
        return this.send;
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
    
    private AsyncReceiver.Sender.ComponentImpl<M,K> selfComponent;
    
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
    protected AsyncReceiver.Sender.Provides<M,K> provides() {
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
    protected abstract ReliableSend<M,K> make_send();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected AsyncReceiver.Sender.Requires<M,K> requires() {
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
    protected AsyncReceiver.Sender.Parts<M,K> parts() {
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
    public synchronized AsyncReceiver.Sender.Component<M,K> _newComponent(final AsyncReceiver.Sender.Requires<M,K> b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Sender has already been used to create a component, use another one.");
      }
      this.init = true;
      AsyncReceiver.Sender.ComponentImpl<M,K> comp = new AsyncReceiver.Sender.ComponentImpl<M,K>(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private AsyncReceiver.ComponentImpl<M,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected AsyncReceiver.Provides<M,K> eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected AsyncReceiver.Requires<M,K> eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected AsyncReceiver.Parts<M,K> eco_parts() {
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
  
  private AsyncReceiver.ComponentImpl<M,K> selfComponent;
  
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
  protected AsyncReceiver.Provides<M,K> provides() {
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
  protected abstract ReliableSend<M,K> make_send();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected AsyncReceiver.Requires<M,K> requires() {
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
  protected AsyncReceiver.Parts<M,K> parts() {
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
  public synchronized AsyncReceiver.Component<M,K> _newComponent(final AsyncReceiver.Requires<M,K> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of AsyncReceiver has already been used to create a component, use another one.");
    }
    this.init = true;
    AsyncReceiver.ComponentImpl<M,K> comp = new AsyncReceiver.ComponentImpl<M,K>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract AsyncReceiver.Receiver<M,K> make_Receiver();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public AsyncReceiver.Receiver<M,K> _createImplementationOfReceiver() {
    AsyncReceiver.Receiver<M,K> implem = make_Receiver();
    if (implem == null) {
    	throw new RuntimeException("make_Receiver() in fr.irit.smac.may.lib.components.interactions.AsyncReceiver should not return null.");
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
  protected abstract AsyncReceiver.Sender<M,K> make_Sender();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public AsyncReceiver.Sender<M,K> _createImplementationOfSender() {
    AsyncReceiver.Sender<M,K> implem = make_Sender();
    if (implem == null) {
    	throw new RuntimeException("make_Sender() in fr.irit.smac.may.lib.components.interactions.AsyncReceiver should not return null.");
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
  protected AsyncReceiver.Sender.Component<M,K> newSender() {
    AsyncReceiver.Sender<M,K> implem = _createImplementationOfSender();
    return implem._newComponent(new AsyncReceiver.Sender.Requires<M,K>() {},true);
  }
}
