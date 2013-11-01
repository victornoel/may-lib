package fr.irit.smac.may.lib.classic.namedPublish;

import fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour;
import fr.irit.smac.may.lib.classic.namedpub.AbstractObservedBehaviour;
import fr.irit.smac.may.lib.classic.namedpub.AbstractObserverBehaviour;
import fr.irit.smac.may.lib.classic.namedpub.NamedPublishMASFactory;
import fr.irit.smac.may.lib.components.interactions.MapReferences;
import fr.irit.smac.may.lib.components.interactions.ValuePublisher;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.components.interactions.interfaces.Observe;
import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.ExecutorService;
import fr.irit.smac.may.lib.components.scheduling.Scheduled;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI;
import fr.irit.smac.may.lib.components.scheduling.interfaces.AdvancedExecutor;
import fr.irit.smac.may.lib.components.scheduling.interfaces.SchedulingControl;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;
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
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Pull<Integer>,String> refs();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<Integer,String> observeds();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component executor();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public fr.irit.smac.may.lib.components.scheduling.Scheduled.Component schedule();
    
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
  public interface Component extends fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Provides {
    /**
     * This should be called to start the component.
     * This must be called before any provided port can be called.
     * 
     */
    public void start();
  }
  
  
  @SuppressWarnings("all")
  public static class ComponentImpl implements fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Parts, fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Component {
    private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires bridge;
    
    private final NamedPublishMAS implementation;
    
    public ComponentImpl(final NamedPublishMAS implem, final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Requires b) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null;
      implem.selfComponent = this;
      
      this.create = implem.make_create();
      assert this.implem_refs == null;
      this.implem_refs = implem.make_refs();
      this.refs = this.implem_refs.newComponent(new BridgeImpl_refs());
      assert this.implem_observeds == null;
      this.implem_observeds = implem.make_observeds();
      this.observeds = this.implem_observeds.newComponent(new BridgeImpl_observeds());
      assert this.implem_executor == null;
      this.implem_executor = implem.make_executor();
      this.executor = this.implem_executor.newComponent(new BridgeImpl_executor());
      assert this.implem_schedule == null;
      this.implem_schedule = implem.make_schedule();
      this.schedule = this.implem_schedule.newComponent(new BridgeImpl_schedule());
      assert this.implem_clock == null;
      this.implem_clock = implem.make_clock();
      this.clock = this.implem_clock.newComponent(new BridgeImpl_clock());
      assert this.implem_gui == null;
      this.implem_gui = implem.make_gui();
      this.gui = this.implem_gui.newComponent(new BridgeImpl_gui());
      
    }
    
    private final NamedPublishMASFactory create;
    
    public final NamedPublishMASFactory create() {
      return this.create;
    }
    
    private final fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Pull<Integer>,String> refs;
    
    private final MapReferences<Pull<Integer>,String> implem_refs;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_refs implements fr.irit.smac.may.lib.components.interactions.MapReferences.Requires<Pull<Integer>,String> {
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.MapReferences.Component<Pull<Integer>,String> refs() {
      return this.refs;
    }
    
    private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<Integer,String> observeds;
    
    private final ValuePublisher<Integer,String> implem_observeds;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_observeds implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.Requires<Integer,String> {
      public final Call<Pull<Integer>,String> call() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.refs.call();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Component<Integer,String> observeds() {
      return this.observeds;
    }
    
    private final fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component executor;
    
    private final ExecutorService implem_executor;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_executor implements fr.irit.smac.may.lib.components.scheduling.ExecutorService.Requires {
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.ExecutorService.Component executor() {
      return this.executor;
    }
    
    private final fr.irit.smac.may.lib.components.scheduling.Scheduled.Component schedule;
    
    private final Scheduled implem_schedule;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_schedule implements fr.irit.smac.may.lib.components.scheduling.Scheduled.Requires {
      public final AdvancedExecutor sched() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.executor.exec();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.Scheduled.Component schedule() {
      return this.schedule;
    }
    
    private final fr.irit.smac.may.lib.components.scheduling.Clock.Component clock;
    
    private final Clock implem_clock;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_clock implements fr.irit.smac.may.lib.components.scheduling.Clock.Requires {
      public final Executor sched() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.executor.exec();
      }
      
      public final Do tick() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.schedule.tick();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.Clock.Component clock() {
      return this.clock;
    }
    
    private final fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Component gui;
    
    private final SchedulingControllerGUI implem_gui;
    
    @SuppressWarnings("all")
    private final class BridgeImpl_gui implements fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Requires {
      public final SchedulingControl control() {
        return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl.this.clock.control();
      }
    }
    
    
    public final fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI.Component gui() {
      return this.gui;
    }
    
    public void start() {
      this.refs.start();
      this.observeds.start();
      this.executor.start();
      this.schedule.start();
      this.clock.start();
      this.gui.start();
      this.implementation.start();
      
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
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Component sched();
      
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
      public fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<Integer>,String> ref();
      
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<Integer,String> observed();
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Provides {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Parts, fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Component {
      private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Requires bridge;
      
      private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed implem, final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Requires b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        assert this.implementation.use_sched != null;
        this.sched = this.implementation.use_sched.newComponent(new BridgeImpl_schedule_sched());
        assert this.implem_beh == null;
        this.implem_beh = implem.make_beh();
        this.beh = this.implem_beh.newComponent(new BridgeImpl_beh());
        assert this.implementation.use_ref != null;
        this.ref = this.implementation.use_ref.newComponent(new BridgeImpl_refs_ref());
        assert this.implementation.use_observed != null;
        this.observed = this.implementation.use_observed.newComponent(new BridgeImpl_observeds_observed());
        
      }
      
      private final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Component sched;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_schedule_sched implements fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Requires {
        public final Do cycle() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl.this.beh.cycle();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Component sched() {
        return this.sched;
      }
      
      private final fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Component beh;
      
      private final ObservedBehaviour implem_beh;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_beh implements fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Requires {
        public final Push<Integer> changeValue() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl.this.observed.set();
        }
      }
      
      
      public final fr.irit.smac.may.lib.classic.namedPublish.ObservedBehaviour.Component beh() {
        return this.beh;
      }
      
      private final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<Integer>,String> ref;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_refs_ref implements fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Requires<Pull<Integer>,String> {
        public final Pull<Integer> toCall() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl.this.observed.toCall();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.MapReferences.Callee.Component<Pull<Integer>,String> ref() {
        return this.ref;
      }
      
      private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<Integer,String> observed;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_observeds_observed implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Requires<Integer,String> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush.Component<Integer,String> observed() {
        return this.observed;
      }
      
      public void start() {
        this.sched.start();
        this.beh.start();
        this.ref.start();
        this.observed.start();
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl selfComponent;
    
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
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent use_sched;
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract ObservedBehaviour make_beh();
    
    private fr.irit.smac.may.lib.components.interactions.MapReferences.Callee<Pull<Integer>,String> use_ref;
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.PublisherPush<Integer,String> use_observed;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Component newComponent(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.Requires b) {
      return new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observed.ComponentImpl(this, b);
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
    public interface Parts {
      /**
       * This can be called by the implementation to access the part and its provided ports.
       * It will be initialized after the required ports are initialized and before the provided ports are initialized.
       * 
       */
      public fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Component sched();
      
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
      public fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<Integer,String> observer();
    }
    
    
    @SuppressWarnings("all")
    public interface Component extends fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Provides {
      /**
       * This should be called to start the component.
       * This must be called before any provided port can be called.
       * 
       */
      public void start();
    }
    
    
    @SuppressWarnings("all")
    public static class ComponentImpl implements fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Parts, fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Component {
      private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Requires bridge;
      
      private final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer implementation;
      
      public ComponentImpl(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer implem, final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Requires b) {
        this.bridge = b;
        this.implementation = implem;
        
        assert implem.selfComponent == null;
        implem.selfComponent = this;
        
        assert this.implementation.use_sched != null;
        this.sched = this.implementation.use_sched.newComponent(new BridgeImpl_schedule_sched());
        assert this.implem_beh == null;
        this.implem_beh = implem.make_beh();
        this.beh = this.implem_beh.newComponent(new BridgeImpl_beh());
        assert this.implementation.use_observer != null;
        this.observer = this.implementation.use_observer.newComponent(new BridgeImpl_observeds_observer());
        
      }
      
      private final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Component sched;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_schedule_sched implements fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Requires {
        public final Do cycle() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl.this.beh.cycle();
        }
      }
      
      
      public final fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent.Component sched() {
        return this.sched;
      }
      
      private final fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour.Component<String> beh;
      
      private final ObserverBehaviour<String> implem_beh;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_beh implements fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour.Requires<String> {
        public final Observe<Integer,String> observe() {
          return fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl.this.observer.observe();
        }
      }
      
      
      public final fr.irit.smac.may.lib.classic.namedPublish.ObserverBehaviour.Component<String> beh() {
        return this.beh;
      }
      
      private final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<Integer,String> observer;
      
      @SuppressWarnings("all")
      private final class BridgeImpl_observeds_observer implements fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Requires<Integer,String> {
      }
      
      
      public final fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer.Component<Integer,String> observer() {
        return this.observer;
      }
      
      public void start() {
        this.sched.start();
        this.beh.start();
        this.observer.start();
        this.implementation.start();
        
      }
    }
    
    
    private fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl selfComponent;
    
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
    
    private fr.irit.smac.may.lib.components.scheduling.Scheduled.Agent use_sched;
    
    /**
     * This should be overridden by the implementation to define how to create this sub-component.
     * This will be called once during the construction of the component to initialize this sub-component.
     * 
     */
    protected abstract ObserverBehaviour<String> make_beh();
    
    private fr.irit.smac.may.lib.components.interactions.ValuePublisher.Observer<Integer,String> use_observer;
    
    /**
     * Not meant to be used to manually instantiate components (except for testing).
     * 
     */
    public fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Component newComponent(final fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.Requires b) {
      return new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Observer.ComponentImpl(this, b);
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
  protected abstract MapReferences<Pull<Integer>,String> make_refs();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ValuePublisher<Integer,String> make_observeds();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ExecutorService make_executor();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract Scheduled make_schedule();
  
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
    return new fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.ComponentImpl(this, b);
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
    implem.use_sched = this.selfComponent.implem_schedule._createImplementationOfAgent();
    assert this.selfComponent.implem_refs != null;
    assert implem.use_ref == null;
    implem.use_ref = this.selfComponent.implem_refs._createImplementationOfCallee(name);
    assert this.selfComponent.implem_observeds != null;
    assert implem.use_observed == null;
    implem.use_observed = this.selfComponent.implem_observeds._createImplementationOfPublisherPush();
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
    implem.use_sched = this.selfComponent.implem_schedule._createImplementationOfAgent();
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
  
  /**
   * Use to instantiate a component with this implementation.
   * 
   */
  public static fr.irit.smac.may.lib.classic.namedPublish.NamedPublishMAS.Component newComponent(final NamedPublishMAS _compo) {
    return _compo.newComponent();
  }
}
