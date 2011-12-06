package fr.irit.smac.may.lib.components.messaging.callable;

import fr.irit.smac.may.lib.components.messaging.exceptions.CallRefDoesNotExistException;
import fr.irit.smac.may.lib.components.messaging.interfaces.ReliableSynchronousCall;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class CallableImpl<I> extends Callable<I> {

	public class CallRefImpl implements CallRef {
		
		private AgentSide ref;
		
		public CallRefImpl(AgentSide ref) {
			this.ref = ref;
		}

		private void stop() {
			this.ref = null; // enable GC
		}

		public I call() throws CallRefDoesNotExistException {
			if (ref != null) return ref.toCall();
			else throw new CallRefDoesNotExistException();
		}
		
	}
	
	@Override
	protected ReliableSynchronousCall<I, CallRef> call() {
		return new ReliableSynchronousCall<I, CallRef>() {
			public I call(CallRef ref) throws CallRefDoesNotExistException {
				@SuppressWarnings("unchecked")
				CallRefImpl realRef = (CallRefImpl) ref;
				return realRef.call();
			}
		};
	}
	
	
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
