package fr.irit.smac.may.lib.components.messaging.callable;

import fr.irit.smac.may.lib.exceptions.KeyDoesNotExistException;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;

public class CallableImpl<I> extends Callable<I> {

	private class CallRefImpl implements CallRef {
		
		private AgentSide ref;
		
		public CallRefImpl(AgentSide ref) {
			this.ref = ref;
		}

		private void stop() {
			this.ref = null; // enable GC
		}

		public I call() {
			if (ref != null) return ref.toCall();
			else return null;
		}
	}
	
	protected MapGet<CallRef, I> call() {
		return new MapGet<CallRef, I>() {
			public I get(CallRef k) throws KeyDoesNotExistException {
				if (k instanceof CallableImpl.CallRefImpl) {
					@SuppressWarnings({ "rawtypes", "unchecked" })
					CallRefImpl realRef = (CallableImpl.CallRefImpl) k;
					return realRef.call();
				} else {
					throw new KeyDoesNotExistException();
				}
			}
		};
	};
	
	public class AgentSide extends Agent<I> {

		private final CallRefImpl me;
		
		public AgentSide() {
			this.me = new CallRefImpl(this);
		}
		
		@Override
		protected Pull<CallRef> me() {
			return new Pull<CallRef>() {
				public CallRef pull() {
					return me;
				}
			};
		}

		@Override
		protected Do stop() {
			return new Do() {
				public void doIt() {
					me.stop();
				}
			};
		}
		
	}
	

}
