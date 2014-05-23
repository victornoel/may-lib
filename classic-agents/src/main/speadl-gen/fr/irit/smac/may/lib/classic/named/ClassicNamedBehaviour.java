package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.classic.interfaces.CreateNamed;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class ClassicNamedBehaviour<Msg, Ref> {
  public interface Requires<Msg, Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<Msg, Ref> send();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Pull<Ref> me();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Do die();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public CreateNamed<Msg, Ref> create();
  }
  
  public interface Parts<Msg, Ref> {
  }
  
  public static class ComponentImpl<Msg, Ref> implements ClassicNamedBehaviour.Component<Msg, Ref>, ClassicNamedBehaviour.Parts<Msg, Ref> {
    private final ClassicNamedBehaviour.Requires<Msg, Ref> bridge;
    
    private final ClassicNamedBehaviour<Msg, Ref> implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.cycle == null: "This is a bug.";
      this.cycle = this.implementation.make_cycle();
      if (this.cycle == null) {
      	throw new RuntimeException("make_cycle() in fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour should not return null.");
      }
      
    }
    
    public ComponentImpl(final ClassicNamedBehaviour<Msg, Ref> implem, final ClassicNamedBehaviour.Requires<Msg, Ref> b, final boolean doInits) {
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
    
    private Push<Msg> cycle;
    
    public final Push<Msg> cycle() {
      return this.cycle;
    }
  }
  
  public interface Provides<Msg, Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Msg> cycle();
  }
  
  public interface Component<Msg, Ref> extends ClassicNamedBehaviour.Provides<Msg, Ref> {
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
  
  private ClassicNamedBehaviour.ComponentImpl<Msg, Ref> selfComponent;
  
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
  protected ClassicNamedBehaviour.Provides<Msg, Ref> provides() {
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
  protected abstract Push<Msg> make_cycle();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected ClassicNamedBehaviour.Requires<Msg, Ref> requires() {
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
  protected ClassicNamedBehaviour.Parts<Msg, Ref> parts() {
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
  public synchronized ClassicNamedBehaviour.Component<Msg, Ref> _newComponent(final ClassicNamedBehaviour.Requires<Msg, Ref> b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of ClassicNamedBehaviour has already been used to create a component, use another one.");
    }
    this.init = true;
    ClassicNamedBehaviour.ComponentImpl<Msg, Ref> comp = new ClassicNamedBehaviour.ComponentImpl<Msg, Ref>(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
}
