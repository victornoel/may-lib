package fr.irit.smac.may.lib.components.meta;

import java.util.ArrayList;
import java.util.List;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;

public class CollectionIntegerImpl<I> extends CollectionInteger<I> {

	private final List<AgentSide> interfaces = new ArrayList<AgentSide>();
	
	@Override
	protected MapGet<Integer,I> get() {
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
		protected Pull<Integer> idx() {
			return new Pull<Integer>() {
				public Integer pull() {
					return k;
				}
			};
		}
		
		@Override
		protected Do stop() {
			return new Do() {
				public void doIt() {
					interfaces.set(k, null);
				}
			};
		}
	}

}
