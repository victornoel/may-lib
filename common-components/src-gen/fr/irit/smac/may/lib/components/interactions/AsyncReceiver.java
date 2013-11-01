package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.Collection;

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
  public interface Parts<M, K> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Provides<M,K> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Parts<M,K>, fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Component<M,K> {
    private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> bridge;
    
    private final AsyncReceiver<M,K> implementation;
    
    public ComponentImpl(final AsyncReceiver<M,K> implem, final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Requires<M,K> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.deposit = implem.make_deposit();
      
    }
    
    private final ReliableSend<M,K> deposit;
    
    public final ReliableSend<M,K> deposit() {
      return this.deposit;
    }
    
    public void start() {
      this.implementation.start();
      
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
    public interface Parts<M, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Provides<M,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Parts<M,K>, fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Component<M,K> {
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver<M,K> implem, final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.Requires<M,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        
      }
      
      public final Push<M> toCall() {
        return this.bridge.put();
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.ComponentImpl<M,K> selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called after the component has been instantiated, after the components have been instantiated
     * and during the containing component start() method is called.
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
      return new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Receiver.ComponentImpl<M,K>(this, b);
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
  public abstract static class ReceiverBuf<M, K> {
    @SuppressWarnings("all")
    public interface Requires<M, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides<M, K> {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<M> getNext();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<Collection<M>> getAll();
      
      /**
       * This can be called to access the provided port.
       * 
       */
      public Push<M> toCall();
    }
    
    
    @SuppressWarnings("all")
    public interface Parts<M, K> {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.collections.Queue.Component<M> q();
    }
    
    
    @SuppressWarnings("all")
    public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Provides<M,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Parts<M,K>, fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Component<M,K> {
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Requires<M,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf<M,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf<M,K> implem, final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Requires<M,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        assert this.implem_q == null;
        this.implem_q = implem.make_q();
        this.q = this.implem_q.newComponent(new BridgeImpl_q());
        
      }
      
      public final Pull<M> getNext() {
        return this.q.get();
      }
      
      public final Pull<Collection<M>> getAll() {
        return this.q.getAll();
      }
      
      public final Push<M> toCall() {
        return this.q.put();
      }
      
      private final fr.irit.smac.may.lib.components.collections.Queue.Component<M> q;
      
      private final Queue<M> implem_q;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_q implements fr.irit.smac.may.lib.components.collections.Queue.Requires<M> {
      }
      
      
      public final fr.irit.smac.may.lib.components.collections.Queue.Component<M> q() {
        return this.q;
      }
      
      public void start() {
        this.q.start();
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.ComponentImpl<M,K> selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called after the component has been instantiated, after the components have been instantiated
     * and during the containing component start() method is called.
     * 
     */
    protected void start() {
      
    }
    
    /**
     * This can be called by the implementation to access the provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Provides<M,K> provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Requires<M,K> requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Parts<M,K> parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract Queue<M> make_q();
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Component<M,K> newComponent(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Requires<M,K> b) {
      return new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.ComponentImpl<M,K>(this, b);
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
    public interface Parts<M, K> {
    }
    
    
    @SuppressWarnings("all")
    public interface Component<M, K> extends fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Provides<M,K> {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl<M, K> implements fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Parts<M,K>, fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Component<M,K> {
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,K> bridge;
      
      private final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender<M,K> implem, final fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.Requires<M,K> b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        this.send = implem.make_send();
        
      }
      
      private final ReliableSend<M,K> send;
      
      public final ReliableSend<M,K> send() {
        return this.send;
      }
      
      public void start() {
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.ComponentImpl<M,K> selfComponent;
    
    /**
     * Can be overridden by the implementation.
     * It will be called after the component has been instantiated, after the components have been instantiated
     * and during the containing component start() method is called.
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
      return new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.Sender.ComponentImpl<M,K>(this, b);
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
   * It will be called after the component has been instantiated, after the components have been instantiated
   * and during the containing component start() method is called.
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
    return new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ComponentImpl<M,K>(this, b);
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
  protected abstract fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf<M,K> make_ReceiverBuf();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf<M,K> _createImplementationOfReceiverBuf() {
    fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf<M,K> implem = make_ReceiverBuf();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Component<M,K> newReceiverBuf() {
    fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf<M,K> implem = _createImplementationOfReceiverBuf();
    return implem.newComponent(new fr.irit.smac.may.lib.components.interactions.AsyncReceiver.ReceiverBuf.Requires<M,K>() {});
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
