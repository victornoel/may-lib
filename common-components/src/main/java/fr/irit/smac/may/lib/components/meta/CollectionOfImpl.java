package fr.irit.smac.may.lib.components.meta;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class CollectionOfImpl<I> extends CollectionOf<I> {

	private Map<Agent<I>,I> interfaces = new ConcurrentHashMap<Agent<I>, I>();
	
	@Override
	protected Pull<Collection<I>> make_get() {
		return new Pull<Collection<I>>() {
			public Collection<I> pull() {
				return interfaces.values();
			}
		};
	}

	@Override
	protected Agent<I> make_Agent() {
		return new Agent<I>() {
			
			@Override
			protected void start() {
				super.start();
				interfaces.put(this, p());
			}
			
			@Override
			protected Do make_stop() {
				return new Do() {
					public void doIt() {
						interfaces.remove(this);
					}
				};
			}
		};
	}

	
	
}
