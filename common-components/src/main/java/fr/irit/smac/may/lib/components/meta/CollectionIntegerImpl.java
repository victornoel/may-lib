package fr.irit.smac.may.lib.components.meta;

import java.util.ArrayList;
import java.util.List;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;

public class CollectionIntegerImpl<I> extends CollectionInteger<I> {

	private final List<AgentSide> interfaces = new ArrayList<AgentSide>();
	
	@Override
	protected MapGet<Integer,I> make_get() {
		return new MapGet<Integer,I>() {
			public I get(Integer i) {
				return interfaces.get(i).requires().forwardedPort();
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
	
	public class AgentSide extends Element<I> {
		
		private final int k;
		
		public AgentSide() {
			interfaces.add(this);
			this.k = interfaces.indexOf(this);
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
	protected Element<I> make_Element() {
		return new AgentSide();
	}

}
