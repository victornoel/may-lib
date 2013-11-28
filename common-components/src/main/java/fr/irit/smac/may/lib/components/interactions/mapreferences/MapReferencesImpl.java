package fr.irit.smac.may.lib.components.interactions.mapreferences;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.may.lib.components.interactions.MapReferences;
import fr.irit.smac.may.lib.components.interactions.exceptions.RefDoesNotExistsException;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class MapReferencesImpl<I, K> extends MapReferences<I, K> {

	private final Map<K, Agent<I>> interfaces = new HashMap<K, Agent<I>>();
	
	@Override
	protected Call<I, K> make_call() {
		return new Call<I, K>() {
			public I call(K ref) throws RefDoesNotExistsException {
				if (!interfaces.containsKey(ref)) throw new RefDoesNotExistsException();
				else return interfaces.get(ref).getIf();
			};
		};
	}

	private interface Agent<I> {
		public I getIf();
	}
	
	public class CalleePullKeyImpl extends CalleeKeyPort<I, K> implements Agent<I> {

		private K key;

		public I getIf() {
			return requires().toCall();
		}
		
		@Override
		protected void start() {
			super.start();
			key = requires().key().pull();
			if (interfaces.containsKey(key)) throw new RuntimeException("key already in the map");
			interfaces.put(key, this);
		}

		@Override
		protected Do make_stop() {
			return new Do() {
				public void doIt() {
					interfaces.remove(key);
				}
			};
		}

		@Override
		protected Pull<K> make_me() {
			return new Pull<K>() {
				public K pull() {
					return key;
				}
			};
		}
	}
	
	public class CalleeImpl extends Callee<I, K> implements Agent<I> {

		private final K key;

		public I getIf() {
			return requires().toCall();
		}
		
		public CalleeImpl(K key) {
			this.key = key;
		}
		
		@Override
		protected void start() {
			super.start();
			if (interfaces.containsKey(key)) throw new RuntimeException("key already in the map");
			interfaces.put(key, this);
		}

		@Override
		protected Do make_stop() {
			return new Do() {
				public void doIt() {
					interfaces.remove(key);
				}
			};
		}

		@Override
		protected Pull<K> make_me() {
			return new Pull<K>() {
				public K pull() {
					return key;
				}
			};
		}
	}

	@Override
	protected Callee<I, K> make_Callee(K key) {
		return new CalleeImpl(key);
	}

	@Override
	protected CalleeKeyPort<I, K> make_CalleeKeyPort() {
		return new CalleePullKeyImpl();
	}

	@Override
	protected MapReferences.Caller<I, K> make_Caller() {
		return new Caller<I, K>() {
			@Override
			protected Call<I, K> make_call() {
				return new Call<I, K>() {
					public I call(K ref) throws RefDoesNotExistsException {
						return eco_provides().call().call(ref);
					};
				};
			}
		};
	}
	
	
}
