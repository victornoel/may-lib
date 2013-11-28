package fr.irit.smac.may.lib.components.remote.place;

import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class Placed {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Place> thisPlace();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends fr.irit.smac.may.lib.components.remote.place.Placed.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.components.remote.place.Placed.Component, fr.irit.smac.may.lib.components.remote.place.Placed.Parts {
    private final fr.irit.smac.may.lib.components.remote.place.Placed.Requires bridge;
    
    private final Placed implementation;
    
    protected void initParts() {
      
    }
    
    protected void initProvidedPorts() {
      assert this.thisPlace == null;
      this.thisPlace = this.implementation.make_thisPlace();
      
    }
    
    public ComponentImpl(final Placed implem, final fr.irit.smac.may.lib.components.remote.place.Placed.Requires b, final boolean initMakes) {
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
    
    private Pull<Place> thisPlace;
    
    public final Pull<Place> thisPlace() {
      return this.thisPlace;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Agent {
    @SuppressWarnings("all")
    public interface Requires {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<Place> myPlace();
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Component, fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Parts {
      private final fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Requires bridge;
      
      private final fr.irit.smac.may.lib.components.remote.place.Placed.Agent implementation;
      
      protected void initParts() {
        
      }
      
      protected void initProvidedPorts() {
        assert this.myPlace == null;
        this.myPlace = this.implementation.make_myPlace();
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.components.remote.place.Placed.Agent implem, final fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Requires b, final boolean initMakes) {
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
      
      private Pull<Place> myPlace;
      
      public final Pull<Place> myPlace() {
        return this.myPlace;
      }
    }
    
    
    private fr.irit.smac.may.lib.components.remote.place.Placed.Agent.ComponentImpl selfComponent;
    
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
    protected fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Provides provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This should be overridden by the implementation to define the provided port.
     * This will be called once during the construction of the component to initialize the port.
     * 
     */
    protected abstract Pull<Place> make_myPlace();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Requires requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Parts parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Component newComponent(final fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Requires b) {
      fr.irit.smac.may.lib.components.remote.place.Placed.Agent.ComponentImpl comp = new fr.irit.smac.may.lib.components.remote.place.Placed.Agent.ComponentImpl(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.components.remote.place.Placed.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.place.Placed.Provides eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.place.Placed.Requires eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.components.remote.place.Placed.Parts eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.components.remote.place.Placed.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.components.remote.place.Placed.Provides provides() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract Pull<Place> make_thisPlace();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.remote.place.Placed.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.components.remote.place.Placed.Parts parts() {
    assert this.selfComponent != null;
    return this.selfComponent;
    
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public fr.irit.smac.may.lib.components.remote.place.Placed.Component newComponent(final fr.irit.smac.may.lib.components.remote.place.Placed.Requires b) {
    fr.irit.smac.may.lib.components.remote.place.Placed.ComponentImpl comp = new fr.irit.smac.may.lib.components.remote.place.Placed.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.components.remote.place.Placed.Agent make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.components.remote.place.Placed.Agent _createImplementationOfAgent() {
    fr.irit.smac.may.lib.components.remote.place.Placed.Agent implem = make_Agent();
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Component newAgent() {
    fr.irit.smac.may.lib.components.remote.place.Placed.Agent implem = _createImplementationOfAgent();
    return implem.newComponent(new fr.irit.smac.may.lib.components.remote.place.Placed.Agent.Requires() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.components.remote.place.Placed.Component newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.components.remote.place.Placed.Requires() {});
  }
}
