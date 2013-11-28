package fr.irit.smac.may.lib.classic.named;

import fr.irit.smac.may.lib.classic.interfaces.CreateNamed;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class ClassicNamedBehaviour<Msg, Ref> {
  @SuppressWarnings("all")
  public interface Requires<Msg, Ref> {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public Send<Msg,Ref> send();
    
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
    public CreateNamed<Msg,Ref> create();
  }
  
  
  @SuppressWarnings("all")
  public interface Provides<Msg, Ref> {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Push<Msg> cycle();
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg, Ref> extends fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Provides<Msg,Ref> {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts<Msg, Ref> {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg, Ref> implements fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Component<Msg,Ref>, fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Parts<Msg,Ref> {
    private final fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Requires<Msg,Ref> bridge;
    
    private final ClassicNamedBehaviour<Msg,Ref> implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.cycle == null;
      this.cycle = this.implementation.make_cycle();
      
    }
    
    public ComponentImpl(final ClassicNamedBehaviour<Msg,Ref> implem, final fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Requires<Msg,Ref> b, final boolean initMakes) {
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
    
    private Push<Msg> cycle;
    
    public final Push<Msg> cycle() {
      return this.cycle;
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.ComponentImpl<Msg,Ref> selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Provides<Msg,Ref> provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Requires<Msg,Ref> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Parts<Msg,Ref> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Component<Msg,Ref> newComponent(final fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.Requires<Msg,Ref> b) {
    fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.ComponentImpl<Msg,Ref> comp = new fr.irit.smac.may.lib.classic.named.ClassicNamedBehaviour.ComponentImpl<Msg,Ref>(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
}
