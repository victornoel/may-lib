package fr.irit.smac.may.lib.classic.namedPublish;

import fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour;
import fr.irit.smac.may.lib.classic.namedpub.NamedPublishMASFactory;
import fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher;
import fr.irit.smac.may.lib.components.interactions.interfaces.Observe;
import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI;
import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;
import java.util.concurrent.Executor;

@SuppressWarnings("all")
public abstract class NamedPublishMAS {
  @SuppressWarnings("all")
  public interface Requires {
  }
  
  
  @SuppressWarnings("all")
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public NamedPublishMASFactory create();
  }
  
  
  @SuppressWarnings("all")
  public interface Component extends NamedPublishMAS.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public MapRefValuePublisher.Component<Integer,String> observeds();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ExecutorServiceWrapper.Component executor();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Scheduler.Component schedule();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public Clock.Component clock();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public SchedulingControllerGUI.Component gui();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements NamedPublishMAS.Component, NamedPublishMAS.Parts {
    private final NamedPublishMAS.Requires bridge;
    
    private final NamedPublishMAS implementation;
    
    public void start() {
      assert this.observeds != null: "This is a bug.";
      ((MapRefValuePublisher.ComponentImpl<Integer,String>) this.observeds).start();
      assert this.executor != null: "This is a bug.";
      ((ExecutorServiceWrapper.ComponentImpl) this.executor).start();
      assert this.schedule != null: "This is a bug.";
      ((Scheduler.ComponentImpl) this.schedule).start();
      assert this.clock != null: "This is a bug.";
      ((Clock.ComponentImpl) this.clock).start();
      assert this.gui != null: "This is a bug.";
      ((SchedulingControllerGUI.ComponentImpl) this.gui).start();
      this.implementation.start();
      this.implementation.started = true;
      
    }
    
    protected void initParts() {
      assert this.observeds == null: "This is a bug.";
      assert this.implem_observeds == null: "This is a bug.";
      this.implem_observeds = this.implementation.make_observeds();
      if (this.implem_observeds == null) {
      	throw new RuntimeException("make_observeds() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS should not return null.");
      }
      this.observeds = this.implem_observeds._newComponent(new BridgeImpl_observeds(), false);
      assert this.executor == null: "This is a bug.";
      assert this.implem_executor == null: "This is a bug.";
      this.implem_executor = this.implementation.make_executor();
      if (this.implem_executor == null) {
      	throw new RuntimeException("make_executor() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS should not return null.");
      }
      this.executor = this.implem_executor._newComponent(new BridgeImpl_executor(), false);
      assert this.schedule == null: "This is a bug.";
      assert this.implem_schedule == null: "This is a bug.";
      this.implem_schedule = this.implementation.make_schedule();
      if (this.implem_schedule == null) {
      	throw new RuntimeException("make_schedule() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS should not return null.");
      }
      this.schedule = this.implem_schedule._newComponent(new BridgeImpl_schedule(), false);
      assert this.clock == null: "This is a bug.";
      assert this.implem_clock == null: "This is a bug.";
      this.implem_clock = this.implementation.make_clock();
      if (this.implem_clock == null) {
      	throw new RuntimeException("make_clock() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS should not return null.");
      }
      this.clock = this.implem_clock._newComponent(new BridgeImpl_clock(), false);
      assert this.gui == null: "This is a bug.";
      assert this.implem_gui == null: "This is a bug.";
      this.implem_gui = this.implementation.make_gui();
      if (this.implem_gui == null) {
      	throw new RuntimeException("make_gui() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS should not return null.");
      }
      this.gui = this.implem_gui._newComponent(new BridgeImpl_gui(), false);
      
    }
    
    protected void initProvidedPorts() {
      assert this.create == null: "This is a bug.";
      this.create = this.implementation.make_create();
      if (this.create == null) {
      	throw new RuntimeException("make_create() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS should not return null.");
      }
      
    }
    
    public ComponentImpl(final NamedPublishMAS implem, final NamedPublishMAS.Requires b, final boolean doInits) {
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
    
    private NamedPublishMASFactory create;
    
    public final NamedPublishMASFactory create() {
      return this.create;
    }
    
    private MapRefValuePublisher.Component<Integer,String> observeds;
    
    private MapRefValuePublisher<Integer,String> implem_observeds;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_observeds implements MapRefValuePublisher.Requires<Integer,String> {
    }
    
    
    public final MapRefValuePublisher.Component<Integer,String> observeds() {
      return this.observeds;
    }
    
    private ExecutorServiceWrapper.Component executor;
    
    private ExecutorServiceWrapper implem_executor;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_executor implements ExecutorServiceWrapper.Requires {
    }
    
    
    public final ExecutorServiceWrapper.Component executor() {
      return this.executor;
    }
    
    private Scheduler.Component schedule;
    
    private Scheduler implem_schedule;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_schedule implements Scheduler.Requires {
      public final AdvancedExecutor executor() {
        return NamedPublishMAS.ComponentImpl.this.executor.executor();
      }
    }
    
    
    public final Scheduler.Component schedule() {
      return this.schedule;
    }
    
    private Clock.Component clock;
    
    private Clock implem_clock;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_clock implements Clock.Requires {
      public final Executor sched() {
        return NamedPublishMAS.ComponentImpl.this.executor.executor();
      }
      
      public final Do tick() {
        return NamedPublishMAS.ComponentImpl.this.schedule.tick();
      }
    }
    
    
    public final Clock.Component clock() {
      return this.clock;
    }
    
    private SchedulingControllerGUI.Component gui;
    
    private SchedulingControllerGUI implem_gui;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_gui implements SchedulingControllerGUI.Requires {
      public final SchedulingControl control() {
        return NamedPublishMAS.ComponentImpl.this.clock.control();
      }
    }
    
    
    public final SchedulingControllerGUI.Component gui() {
      return this.gui;
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Observed {
    @SuppressWarnings("all")
    public interface Requires {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends NamedPublishMAS.Observed.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Scheduler.Scheduled.Component sched();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ObservedBehaviour.Component beh();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapRefValuePublisher.PublisherPush.Component<Integer,String> observed();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements NamedPublishMAS.Observed.Component, NamedPublishMAS.Observed.Parts {
      private final NamedPublishMAS.Observed.Requires bridge;
      
      private final NamedPublishMAS.Observed implementation;
      
      public void start() {
        assert this.sched != null: "This is a bug.";
        ((Scheduler.Scheduled.ComponentImpl) this.sched).start();
        assert this.beh != null: "This is a bug.";
        ((ObservedBehaviour.ComponentImpl) this.beh).start();
        assert this.observed != null: "This is a bug.";
        ((MapRefValuePublisher.PublisherPush.ComponentImpl<Integer,String>) this.observed).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.sched == null: "This is a bug.";
        assert this.implementation.use_sched != null: "This is a bug.";
        this.sched = this.implementation.use_sched._newComponent(new BridgeImpl_schedule_sched(), false);
        assert this.beh == null: "This is a bug.";
        assert this.implem_beh == null: "This is a bug.";
        this.implem_beh = this.implementation.make_beh();
        if (this.implem_beh == null) {
        	throw new RuntimeException("make_beh() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS$Observed should not return null.");
        }
        this.beh = this.implem_beh._newComponent(new BridgeImpl_beh(), false);
        assert this.observed == null: "This is a bug.";
        assert this.implementation.use_observed != null: "This is a bug.";
        this.observed = this.implementation.use_observed._newComponent(new BridgeImpl_observeds_observed(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final NamedPublishMAS.Observed implem, final NamedPublishMAS.Observed.Requires b, final boolean doInits) {
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
      
      private Scheduler.Scheduled.Component sched;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_schedule_sched implements Scheduler.Scheduled.Requires {
        public final Do cycle() {
          return NamedPublishMAS.Observed.ComponentImpl.this.beh.cycle();
        }
      }
      
      
      public final Scheduler.Scheduled.Component sched() {
        return this.sched;
      }
      
      private ObservedBehaviour.Component beh;
      
      private ObservedBehaviour implem_beh;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_beh implements ObservedBehaviour.Requires {
        public final Push<Integer> changeValue() {
          return NamedPublishMAS.Observed.ComponentImpl.this.observed.set();
        }
      }
      
      
      public final ObservedBehaviour.Component beh() {
        return this.beh;
      }
      
      private MapRefValuePublisher.PublisherPush.Component<Integer,String> observed;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_observeds_observed implements MapRefValuePublisher.PublisherPush.Requires<Integer,String> {
      }
      
      
      public final MapRefValuePublisher.PublisherPush.Component<Integer,String> observed() {
        return this.observed;
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
    
    private NamedPublishMAS.Observed.ComponentImpl selfComponent;
    
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
    protected NamedPublishMAS.Observed.Provides provides() {
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
    protected NamedPublishMAS.Observed.Requires requires() {
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
    protected NamedPublishMAS.Observed.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private Scheduler.Scheduled use_sched;
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract ObservedBehaviour make_beh();
    
    private MapRefValuePublisher.PublisherPush<Integer,String> use_observed;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized NamedPublishMAS.Observed.Component _newComponent(final NamedPublishMAS.Observed.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Observed has already been used to create a component, use another one.");
      }
      this.init = true;
      NamedPublishMAS.Observed.ComponentImpl comp = new NamedPublishMAS.Observed.ComponentImpl(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private NamedPublishMAS.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected NamedPublishMAS.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected NamedPublishMAS.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected NamedPublishMAS.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
  }
  
  
  @SuppressWarnings("all")
  public abstract static class Observer {
    @SuppressWarnings("all")
    public interface Requires {
    }
    
    
    @SuppressWarnings("all")
    public interface Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends NamedPublishMAS.Observer.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public Scheduler.Scheduled.Component sched();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public ObserverBehaviour.Component<String> beh();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public MapRefValuePublisher.Observer.Component<Integer,String> observer();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements NamedPublishMAS.Observer.Component, NamedPublishMAS.Observer.Parts {
      private final NamedPublishMAS.Observer.Requires bridge;
      
      private final NamedPublishMAS.Observer implementation;
      
      public void start() {
        assert this.sched != null: "This is a bug.";
        ((Scheduler.Scheduled.ComponentImpl) this.sched).start();
        assert this.beh != null: "This is a bug.";
        ((ObserverBehaviour.ComponentImpl<String>) this.beh).start();
        assert this.observer != null: "This is a bug.";
        ((MapRefValuePublisher.Observer.ComponentImpl<Integer,String>) this.observer).start();
        this.implementation.start();
        this.implementation.started = true;
        
      }
      
      protected void initParts() {
        assert this.sched == null: "This is a bug.";
        assert this.implementation.use_sched != null: "This is a bug.";
        this.sched = this.implementation.use_sched._newComponent(new BridgeImpl_schedule_sched(), false);
        assert this.beh == null: "This is a bug.";
        assert this.implem_beh == null: "This is a bug.";
        this.implem_beh = this.implementation.make_beh();
        if (this.implem_beh == null) {
        	throw new RuntimeException("make_beh() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS$Observer should not return null.");
        }
        this.beh = this.implem_beh._newComponent(new BridgeImpl_beh(), false);
        assert this.observer == null: "This is a bug.";
        assert this.implementation.use_observer != null: "This is a bug.";
        this.observer = this.implementation.use_observer._newComponent(new BridgeImpl_observeds_observer(), false);
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final NamedPublishMAS.Observer implem, final NamedPublishMAS.Observer.Requires b, final boolean doInits) {
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
      
      private Scheduler.Scheduled.Component sched;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_schedule_sched implements Scheduler.Scheduled.Requires {
        public final Do cycle() {
          return NamedPublishMAS.Observer.ComponentImpl.this.beh.cycle();
        }
      }
      
      
      public final Scheduler.Scheduled.Component sched() {
        return this.sched;
      }
      
      private ObserverBehaviour.Component<String> beh;
      
      private ObserverBehaviour<String> implem_beh;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_beh implements ObserverBehaviour.Requires<String> {
        public final Observe<Integer,String> observe() {
          return NamedPublishMAS.Observer.ComponentImpl.this.observer.observe();
        }
      }
      
      
      public final ObserverBehaviour.Component<String> beh() {
        return this.beh;
      }
      
      private MapRefValuePublisher.Observer.Component<Integer,String> observer;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_observeds_observer implements MapRefValuePublisher.Observer.Requires<Integer,String> {
      }
      
      
      public final MapRefValuePublisher.Observer.Component<Integer,String> observer() {
        return this.observer;
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
    
    private NamedPublishMAS.Observer.ComponentImpl selfComponent;
    
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
    protected NamedPublishMAS.Observer.Provides provides() {
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
    protected NamedPublishMAS.Observer.Requires requires() {
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
    protected NamedPublishMAS.Observer.Parts parts() {
      assert this.selfComponent != null: "This is a bug.";
      if (!this.init) {
      	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
      }
      return this.selfComponent;
      
    }
    
    private Scheduler.Scheduled use_sched;
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract ObserverBehaviour<String> make_beh();
    
    private MapRefValuePublisher.Observer<Integer,String> use_observer;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public synchronized NamedPublishMAS.Observer.Component _newComponent(final NamedPublishMAS.Observer.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of Observer has already been used to create a component, use another one.");
      }
      this.init = true;
      NamedPublishMAS.Observer.ComponentImpl comp = new NamedPublishMAS.Observer.ComponentImpl(this, b, true);
      if (start) {
      	comp.start();
      }
      return comp;
      
    }
    
    private NamedPublishMAS.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected NamedPublishMAS.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected NamedPublishMAS.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected NamedPublishMAS.Parts eco_parts() {
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
  
  private NamedPublishMAS.ComponentImpl selfComponent;
  
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
  protected NamedPublishMAS.Provides provides() {
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
  protected abstract NamedPublishMASFactory make_create();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected NamedPublishMAS.Requires requires() {
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
  protected NamedPublishMAS.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
    
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract MapRefValuePublisher<Integer,String> make_observeds();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ExecutorServiceWrapper make_executor();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Scheduler make_schedule();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Clock make_clock();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract SchedulingControllerGUI make_gui();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized NamedPublishMAS.Component _newComponent(final NamedPublishMAS.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of NamedPublishMAS has already been used to create a component, use another one.");
    }
    this.init = true;
    NamedPublishMAS.ComponentImpl comp = new NamedPublishMAS.ComponentImpl(this, b, true);
    if (start) {
    	comp.start();
    }
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract NamedPublishMAS.Observed make_Observed(final String name, final ObservedBehaviour implem);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public NamedPublishMAS.Observed _createImplementationOfObserved(final String name, final ObservedBehaviour implem) {
    NamedPublishMAS.Observed implem_1 = make_Observed(name,implem);
    if (implem_1 == null) {
    	throw new RuntimeException("make_Observed() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS should not return null.");
    }
    assert implem_1.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem_1.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_schedule != null: "This is a bug.";
    assert implem_1.use_sched == null: "This is a bug.";
    implem_1.use_sched = this.selfComponent.implem_schedule._createImplementationOfScheduled();
    assert this.selfComponent.implem_observeds != null: "This is a bug.";
    assert implem_1.use_observed == null: "This is a bug.";
    implem_1.use_observed = this.selfComponent.implem_observeds._createImplementationOfPublisherPush(name);
    return implem_1;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected NamedPublishMAS.Observed.Component newObserved(final String name, final ObservedBehaviour implem) {
    NamedPublishMAS.Observed implem_1 = _createImplementationOfObserved(name,implem);
    return implem_1._newComponent(new NamedPublishMAS.Observed.Requires() {},true);
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract NamedPublishMAS.Observer make_Observer(final ObserverBehaviour<String> beha);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public NamedPublishMAS.Observer _createImplementationOfObserver(final ObserverBehaviour<String> beha) {
    NamedPublishMAS.Observer implem = make_Observer(beha);
    if (implem == null) {
    	throw new RuntimeException("make_Observer() in fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_schedule != null: "This is a bug.";
    assert implem.use_sched == null: "This is a bug.";
    implem.use_sched = this.selfComponent.implem_schedule._createImplementationOfScheduled();
    assert this.selfComponent.implem_observeds != null: "This is a bug.";
    assert implem.use_observer == null: "This is a bug.";
    implem.use_observer = this.selfComponent.implem_observeds._createImplementationOfObserver();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected NamedPublishMAS.Observer.Component newObserver(final ObserverBehaviour<String> beha) {
    NamedPublishMAS.Observer implem = _createImplementationOfObserver(beha);
    return implem._newComponent(new NamedPublishMAS.Observer.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public NamedPublishMAS.Component newComponent() {
    return this._newComponent(new NamedPublishMAS.Requires() {}, true);
  }
}
