package fr.irit.smac.may.lib.components.meta;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.may.lib.interfaces.MapGet;

public class CollectionMapImpl<K, I> extends CollectionMap<K, I> {

	private final Map<K, AgentSide> interfaces = new HashMap<K, AgentSide>();
	
	@Override
	protected MapGet<K, I> get() {
		return new MapGet<K, I>() {
			public I get(K key) {
				return interfaces.get(key).getIf();
			}
		};
	}

	public class AgentSide extends Agent<K, I> {

		private I getIf() {
			return this.p();
		}
		
		@Override
		protected void start() {
			super.start();
			interfaces.put(this.key().pull(), this);
		}
	}
}
