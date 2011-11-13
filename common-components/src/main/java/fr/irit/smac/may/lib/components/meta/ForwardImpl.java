package fr.irit.smac.may.lib.components.meta;

public class ForwardImpl<I> extends Forward<I> {

	public class AgentSide extends Agent<I> {
		
		@Override
		protected I a() {
			return ForwardImpl.this.i();
		}
		
	}
}
