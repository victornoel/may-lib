package fr.irit.smac.may.lib.components.meta;

public class ForwardImpl<I> extends Forward<I> {

	private class AgentSide extends Caller<I> {
		
		@Override
		protected I make_forwardedPort() {
			return eco_requires().forwardedPort();
		}
		
	}
	
	@Override
	protected Caller<I> make_Caller() {
		return new AgentSide();
	}
}
