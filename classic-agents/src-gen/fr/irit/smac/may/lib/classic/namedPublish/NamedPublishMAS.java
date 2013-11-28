package fr.irit.smac.may.lib.classic.namedPublish;

import fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour;
import fr.irit.smac.may.lib.classic.namedpub.AbstractObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedpub.AbstractObserverBehaviour;
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
  public interface Component extends fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Provides {
  }
  
  
  @SuppressWarnings("all")
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Component<Integer,String> observeds();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component executor();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.Scheduler.Component schedule();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.Clock.Component clock();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Component gui();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Component, fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Parts {
    private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires bridge;
    
    private final NamedPublishMAS implementation;
    
    protected void initParts() {
      assert this.implem_observeds == null;
      this.implem_observeds = this.implementation.make_observeds();
      assert this.observeds == null;
      this.observeds = this.implem_observeds.newComponent(new BridgeImpl_observeds());
      assert this.implem_executor == null;
      this.implem_executor = this.implementation.make_executor();
      assert this.executor == null;
      this.executor = this.implem_executor.newComponent(new BridgeImpl_executor());
      assert this.implem_schedule == null;
      this.implem_schedule = this.implementation.make_schedule();
      assert this.schedule == null;
      this.schedule = this.implem_schedule.newComponent(new BridgeImpl_schedule());
      assert this.implem_clock == null;
      this.implem_clock = this.implementation.make_clock();
      assert this.clock == null;
      this.clock = this.implem_clock.newComponent(new BridgeImpl_clock());
      assert this.implem_gui == null;
      this.implem_gui = this.implementation.make_gui();
      assert this.gui == null;
      this.gui = this.implem_gui.newComponent(new BridgeImpl_gui());
      
    }
    
    protected void initProvidedPorts() {
      assert this.create == null;
      this.create = this.implementation.make_create();
      
    }
    
    public ComponentImpl(final NamedPublishMAS implem, final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires b, final boolean initMakes) {
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
    
    private NamedPublishMASFactory create;
    
    public final NamedPublishMASFactory create() {
      return this.create;
    }
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Component<Integer,String> observeds;
    
    private MapRefValuePublisher<Integer,String> implem_observeds;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_observeds implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Requires<Integer,String> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Component<Integer,String> observeds() {
      return this.observeds;
    }
    
    private fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component executor;
    
    private ExecutorServiceWrapper implem_executor;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_executor implements fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Requires {
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper.Component executor() {
      return this.executor;
    }
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduler.Component schedule;
    
    private Scheduler implem_schedule;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_schedule implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Requires {
      public final AdvancedExecutor executor() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.executor.executor();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.Scheduler.Component schedule() {
      return this.schedule;
    }
    
    private fr.irit.smac.may.lib.components.scheduling.Clock.Component clock;
    
    private Clock implem_clock;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_clock implements fr.irit.smac.may.lib.components.scheduling.Clock.Requires {
      public final Executor sched() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.executor.executor();
      }
      
      public final Do tick() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.schedule.tick();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.Clock.Component clock() {
      return this.clock;
    }
    
    private fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Component gui;
    
    private SchedulingControllerGUI implem_gui;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_gui implements fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Requires {
      public final SchedulingControl control() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.clock.control();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Component gui() {
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
    public interface Component extends fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled.Component sched();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Component beh();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Component<Integer,String> observed();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Component, fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Parts {
      private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Requires bridge;
      
      private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed implementation;
      
      protected void initParts() {
        assert this.implementation.use_sched != null;
        assert this.sched == null;
        this.sched = this.implementation.use_sched.newComponent(new BridgeImpl_schedule_sched());
        assert this.implem_beh == null;
        this.implem_beh = this.implementation.make_beh();
        assert this.beh == null;
        this.beh = this.implem_beh.newComponent(new BridgeImpl_beh());
        assert this.implementation.use_observed != null;
        assert this.observed == null;
        this.observed = this.implementation.use_observed.newComponent(new BridgeImpl_observeds_observed());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed implem, final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Requires b, final boolean initMakes) {
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
      
      private fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled.Component sched;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_schedule_sched implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled.Requires {
        public final Do cycle() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl.this.beh.cycle();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled.Component sched() {
        return this.sched;
      }
      
      private fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Component beh;
      
      private ObservedBehaviour implem_beh;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_beh implements fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Requires {
        public final Push<Integer> changeValue() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl.this.observed.set();
        }
      }
      
      
      public final fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Component beh() {
        return this.beh;
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Component<Integer,String> observed;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_observeds_observed implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Requires<Integer,String> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush.Component<Integer,String> observed() {
        return this.observed;
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl selfComponent;
    
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
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Provides provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Requires requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Parts parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled use_sched;
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract ObservedBehaviour make_beh();
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.PublisherPush<Integer,String> use_observed;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Component newComponent(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Requires b) {
      fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl comp = new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Provides eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Parts eco_parts() {
      assert this.ecosystemComponent != null;
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
    public interface Component extends fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Provides {
    }
    
    
    @SuppressWarnings("all")
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled.Component sched();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour.Component<String> beh();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Component<Integer,String> observer();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Component, fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Parts {
      private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Requires bridge;
      
      private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer implementation;
      
      protected void initParts() {
        assert this.implementation.use_sched != null;
        assert this.sched == null;
        this.sched = this.implementation.use_sched.newComponent(new BridgeImpl_schedule_sched());
        assert this.implem_beh == null;
        this.implem_beh = this.implementation.make_beh();
        assert this.beh == null;
        this.beh = this.implem_beh.newComponent(new BridgeImpl_beh());
        assert this.implementation.use_observer != null;
        assert this.observer == null;
        this.observer = this.implementation.use_observer.newComponent(new BridgeImpl_observeds_observer());
        
      }
      
      protected void initProvidedPorts() {
        
      }
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer implem, final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Requires b, final boolean initMakes) {
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
      
      private fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled.Component sched;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_schedule_sched implements fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled.Requires {
        public final Do cycle() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl.this.beh.cycle();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled.Component sched() {
        return this.sched;
      }
      
      private fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour.Component<String> beh;
      
      private ObserverBehaviour<String> implem_beh;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_beh implements fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour.Requires<String> {
        public final Observe<Integer,String> observe() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl.this.observer.observe();
        }
      }
      
      
      public final fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour.Component<String> beh() {
        return this.beh;
      }
      
      private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Component<Integer,String> observer;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_observeds_observer implements fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Requires<Integer,String> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer.Component<Integer,String> observer() {
        return this.observer;
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl selfComponent;
    
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
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Provides provides() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    /**
     * This can be called by the implementation to access the required ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Requires requires() {
      assert this.selfComponent != null;
      return this.selfComponent.bridge;
      
    }
    
    /**
     * This can be called by the implementation to access the parts and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Parts parts() {
      assert this.selfComponent != null;
      return this.selfComponent;
      
    }
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduler.Scheduled use_sched;
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract ObserverBehaviour<String> make_beh();
    
    private fr.irit.smac.may.lib.components.interactions.MapRefValuePublisher.Observer<Integer,String> use_observer;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Component newComponent(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Requires b) {
      fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl comp = new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl(this, b, true);
      comp.implementation.start();
      return comp;
      
    }
    
    private fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Provides eco_provides() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires eco_requires() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent.bridge;
      
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Parts eco_parts() {
      assert this.ecosystemComponent != null;
      return this.ecosystemComponent;
      
    }
  }
  
  
  private fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl selfComponent;
  
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
  protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Provides provides() {
    assert this.selfComponent != null;
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
  protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires requires() {
    assert this.selfComponent != null;
    return this.selfComponent.bridge;
    
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Parts parts() {
    assert this.selfComponent != null;
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
  public fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Component newComponent(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires b) {
    fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl comp = new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl(this, b, true);
    comp.implementation.start();
    return comp;
    
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed make_Observed(final String name, final AbstractObservedBehaviour beha);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed _createImplementationOfObserved(final String name, final AbstractObservedBehaviour beha) {
    fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed implem = make_Observed(name,beha);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_schedule != null;
    assert implem.use_sched == null;
    implem.use_sched = this.selfComponent.implem_schedule._createImplementationOfScheduled();
    assert this.selfComponent.implem_observeds != null;
    assert implem.use_observed == null;
    implem.use_observed = this.selfComponent.implem_observeds._createImplementationOfPublisherPush(name);
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Component newObserved(final String name, final AbstractObservedBehaviour beha) {
    fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed implem = _createImplementationOfObserved(name,beha);
    return implem.newComponent(new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Requires() {});
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer make_Observer(final AbstractObserverBehaviour<String> beha);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer _createImplementationOfObserver(final AbstractObserverBehaviour<String> beha) {
    fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer implem = make_Observer(beha);
    assert implem.ecosystemComponent == null;
    assert this.selfComponent != null;
    implem.ecosystemComponent = this.selfComponent;
    assert this.selfComponent.implem_schedule != null;
    assert implem.use_sched == null;
    implem.use_sched = this.selfComponent.implem_schedule._createImplementationOfScheduled();
    assert this.selfComponent.implem_observeds != null;
    assert implem.use_observer == null;
    implem.use_observer = this.selfComponent.implem_observeds._createImplementationOfObserver();
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Component newObserver(final AbstractObserverBehaviour<String> beha) {
    fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer implem = _createImplementationOfObserver(beha);
    return implem.newComponent(new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Requires() {});
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Component newComponent() {
    return this.newComponent(new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires() {});
  }
}
