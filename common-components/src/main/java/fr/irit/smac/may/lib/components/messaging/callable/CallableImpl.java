package fr.irit.smac.may.lib.components.messaging.callable;

import fr.irit.smac.may.lib.exceptions.KeyDoesNotExistException;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.MapGet;
import fr.irit.smac.may.lib.interfaces.Pull;

public class CallableImpl<I> extends Callable<I> {

	private class CallRefImpl implements CallRef {
		
		private CalleeImpl ref;
		
		private CallRefImpl(CalleeImpl ref) {
			this.ref = ref;
		}

		private void stop() {
			this.ref = null; // allow for GC
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
	
	private class CalleeImpl extends Callee<I> {

		private final CallRefImpl me;
		
		private CalleeImpl() {
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
	
	private class CallerImpl extends Caller<I> {

		@Override
		protected MapGet<CallRef, I> call() {
			return infraSelf().call();
		}
		
	}

	@Override
	protected Callee<I> make_Callee() {
		return new CalleeImpl();
	}

	@Override
	protected Caller<I> make_Caller() {
		return new CallerImpl();
	}
	

}
