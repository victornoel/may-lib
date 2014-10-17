package fr.irit.smac.may.lib.components.remote.place;

import fr.irit.smac.may.lib.components.remote.place.Place;
import fr.irit.smac.may.lib.interfaces.Pull;

@SuppressWarnings("all")
public abstract class Placed {
  public interface Requires {
  }
  
  public interface Component extends Placed.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Pull<Place> thisPlace();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements Placed.Component, Placed.Parts {
    private final Placed.Requires bridge;
    
    private final Placed implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_thisPlace() {
      assert this.thisPlace == null: "This is a bug.";
      this.thisPlace = this.implementation.make_thisPlace();
      if (this.thisPlace == null) {
      	throw new RuntimeException("make_thisPlace() in fr.irit.smac.may.lib.components.remote.place.Placed should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_thisPlace();
    }
    
    public ComponentImpl(final Placed implem, final Placed.Requires b, final boolean doInits) {
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
    
    private Pull<Place> thisPlace;
    
    public Pull<Place> thisPlace() {
      return this.thisPlace;
    }
  }
  
  public static abstract class Agent {
    public interface Requires {
    }
    
    public interface Component extends Placed.Agent.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public Pull<Place> myPlace();
    }
    
    public interface Parts {
    }
    
    public static class ComponentImpl implements Placed.Agent.Component, Placed.Agent.Parts {
      private final Placed.Agent.Requires bridge;
      
      private final Placed.Agent implementation;
      
      public void start() {
        this.implementation.start();
        this.implementation.started = true;
      }
      
      protected void initParts() {
        
      }
      
      private void init_myPlace() {
        assert this.myPlace == null: "This is a bug.";
        this.myPlace = this.implementation.make_myPlace();
        if (this.myPlace == null) {
        	throw new RuntimeException("make_myPlace() in fr.irit.smac.may.lib.components.remote.place.Placed$Agent should not return null.");
        }
      }
      
      protected void initProvidedPorts() {
        init_myPlace();
      }
      
      public ComponentImpl(final Placed.Agent implem, final Placed.Agent.Requires b, final boolean doInits) {
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
      
      private Pull<Place> myPlace;
      
      public Pull<Place> myPlace() {
        return this.myPlace;
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
    
    private Placed.Agent.ComponentImpl selfComponent;
    
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
    protected Placed.Agent.Provides provides() {
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
    protected abstract Pull<Place> make_myPlace();
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected Placed.Agent.Requires requires() {
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
    protected Placed.Agent.Parts parts() {
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
    public synchronized Placed.Agent.Component _newComponent(final Placed.Agent.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Agent has already been used to create a component, use another one.");
      }
      this.init = true;
      Placed.Agent.ComponentImpl  _comp = new Placed.Agent.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private Placed.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected Placed.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected Placed.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected Placed.Parts eco_parts() {
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
  
  private Placed.ComponentImpl selfComponent;
  
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
  protected Placed.Provides provides() {
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
  protected abstract Pull<Place> make_thisPlace();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected Placed.Requires requires() {
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
  protected Placed.Parts parts() {
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
  public synchronized Placed.Component _newComponent(final Placed.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of Placed has already been used to create a component, use another one.");
    }
    this.init = true;
    Placed.ComponentImpl  _comp = new Placed.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract Placed.Agent make_Agent();
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public Placed.Agent _createImplementationOfAgent() {
    Placed.Agent implem = make_Agent();
    if (implem == null) {
    	throw new RuntimeException("make_Agent() in fr.irit.smac.may.lib.components.remote.place.Placed should not return null.");
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
  protected Placed.Agent.Component newAgent() {
    Placed.Agent _implem = _createImplementationOfAgent();
    return _implem._newComponent(new Placed.Agent.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public Placed.Component newComponent() {
    return this._newComponent(new Placed.Requires() {}, true);
  }
}
