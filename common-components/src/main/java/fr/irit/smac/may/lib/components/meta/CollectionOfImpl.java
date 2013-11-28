package fr.irit.smac.may.lib.components.meta;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class CollectionOfImpl<I> extends CollectionOf<I> {

	private final Map<Element<I>,I> interfaces = new ConcurrentHashMap<Element<I>, I>();
	
	@Override
	protected Pull<Collection<I>> make_get() {
		return new Pull<Collection<I>>() {
			public Collection<I> pull() {
				return interfaces.values();
			}
		};
	}

	@Override
	protected Element<I> make_Element() {
		return new Element<I>() {
			
			@Override
			protected void start() {
				super.start();
				interfaces.put(this, requires().forwardedPort());
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
