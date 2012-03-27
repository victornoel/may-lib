package fr.irit.smac.may.lib.components.meta;

import java.util.ArrayList;
import java.util.List;

import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;

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
	}
	
	@Override
	protected Agent<I> make_Agent() {
		return new AgentSide();
	}

}
