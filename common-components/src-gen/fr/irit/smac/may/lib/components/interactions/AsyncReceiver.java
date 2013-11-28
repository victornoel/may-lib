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
    public ReliableSend<M,K> deposit();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Provides<M,K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<M, K> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,K>, fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Parts<M,K> {
    private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> bridge;
    
    private final AsyncReceiver<M,K> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.deposit == null;
      this.deposit = this.implementation.make_deposit();
      
    }
    
    public ComponentImpl(final AsyncReceiver<M,K> implem, final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> b, final boolean initMakes) {
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
    
    private ReliableSend<M,K> deposit;
    
    public final ReliableSend<M,K> deposit() {
      return this.deposit;
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
    public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Provides<M,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M, K> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K>, fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Parts<M,K> {
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> implem, final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,K> b, final boolean initMakes) {
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
      
      public final Push<M> toCall() {
        return this.bridge.put();
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.ComponentImpl<M,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Provides<M,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Parts<M,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K> newComponent(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,K> b) {
      fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.ComponentImpl<M,K> comp = new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.ComponentImpl<M,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ComponentImpl<M,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Provides<M,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Parts<M,K> eco_parts() {
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
    public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Provides<M,K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M, K> {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,K>, fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Parts<M,K> {
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.send == null;
        this.send = this.implementation.make_send();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> implem, final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,K> b, final boolean initMakes) {
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
      
      private ReliableSend<M,K> send;
      
      public final ReliableSend<M,K> send() {
        return this.send;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.ComponentImpl<M,K> selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Provides<M,K> provides() {
      assert this.selfComponent != null;
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
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Parts<M,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,K> newComponent(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,K> b) {
      fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.ComponentImpl<M,K> comp = new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.ComponentImpl<M,K>(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ComponentImpl<M,K> ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Provides<M,K> eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Parts<M,K> eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ComponentImpl<M,K> selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Provides<M,K> provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract ReliableSend<M,K> make_deposit();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Parts<M,K> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,K> newComponent(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> b) {
    fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ComponentImpl<M,K> comp = new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ComponentImpl<M,K>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> make_Receiver();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> _createImplementationOfReceiver() {
    fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> implem = make_Receiver();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> make_Sender();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> _createImplementationOfSender() {
    fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> implem = make_Sender();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,K> newSender() {
    fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> implem = _createImplementationOfSender();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,K>() {});
  }
}
