package fr.irit.smac.may.lib.components.meta;

import java.util.ArrayList;
import java.util.List;

import fj.F;
import fj.Unit;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;
import fr.irit.smac.may.lib.interfaces.Push;

public class CollectionIntegerImpl<I> extends CollectionInteger<I> {

	private final List<AgentSide> interfaces = new ArrayList<AgentSide>();
	
	@Override
	protected MapGet<Integer,I> make_get() {
		return new MapGet<Integer,I>() {
			public I get(Integer i) {
				return interfaces.get(i).getIf();
			}
		};
	}
	
	@Override
	protected Pull<Integer> make_size() {
		return new Pull<Integer>() {
			public Integer pull() {
				return interfaces.size();
			}
		};
	}
	
	@Override
	protected Push<F<I, Unit>> make_forAll() {
		return new Push<F<I, Unit>>() {
			public void push(F<I, Unit> thing) {
				for(AgentSide a: interfaces) {
					if (a != null) thing.f(a.p());
				}
			}
		};
	}
	
	public class AgentSide extends Agent<I> {
		
		private final int k;
		
		public AgentSide() {
			interfaces.add(this);
			this.k = interfaces.indexOf(this);
		}
		
		private I getIf() {
			return this.p();
		}
		
		@Override
		protected Pull<Integer> make_idx() {
			return new Pull<Integer>() {
				public Integer pull() {
					return k;
				}
			};
		}

		@Override
		protected Do make_stop() {
			return new Do() {
				public void doIt() {
					interfaces.set(k, null);
				}
			};
		}
	}
	
	@Override
	protected Agent<I> make_Agent() {
		return new AgentSide();
	}

}
