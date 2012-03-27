package fr.irit.smac.may.lib.components.meta;

public class ForwardImpl<I> extends Forward<I> {

	public class AgentSide extends Agent<I> {
		
		@Override
		protected I make_a() {
			return i();
		}
		
	}
	
	@Override
	protected Agent<I> make_Agent() {
		return new AgentSide();
	}
}
