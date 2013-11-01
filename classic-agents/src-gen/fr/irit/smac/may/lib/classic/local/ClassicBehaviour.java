package fr.irit.smac.may.lib.classic.local;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;
import fr.irit.smac.may.lib.interfaces.Send;

@SuppressWarnings("all")
public abstract class ClassicBehaviour<Msg, Ref> {
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
    public CreateClassic<Msg,Ref> create();
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
  public interface Parts<Msg, Ref> {
  }
  
  
  @SuppressWarnings("all")
  public interface Component<Msg, Ref> extends fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Provides<Msg,Ref> {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl<Msg, Ref> implements fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Parts<Msg,Ref>, fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Component<Msg,Ref> {
    private final fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Requires<Msg,Ref> bridge;
    
    private final ClassicBehaviour<Msg,Ref> implementation;
    
    public ComponentImpl(final ClassicBehaviour<Msg,Ref> implem, final fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Requires<Msg,Ref> b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.cycle = implem.make_cycle();
      
    }
    
    private final Push<Msg> cycle;
    
    public final Push<Msg> cycle() {
      return this.cycle;
    }
    
    public void start() {
      this.implementation.start();
      
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.local.ClassicBehaviour.ComponentImpl<Msg,Ref> selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Provides<Msg,Ref> provides() {
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
  protected fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Requires<Msg,Ref> requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Parts<Msg,Ref> parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Component<Msg,Ref> newComponent(final fr.irit.smac.may.lib.classic.local.ClassicBehaviour.Requires<Msg,Ref> b) {
    return new fr.irit.smac.may.lib.classic.local.ClassicBehaviour.ComponentImpl<Msg,Ref>(this, b);
  }
}
