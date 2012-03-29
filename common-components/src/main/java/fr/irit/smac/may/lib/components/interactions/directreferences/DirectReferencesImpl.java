package fr.irit.smac.may.lib.components.interactions.directreferences;

import fr.irit.smac.may.lib.components.interactions.DirectReferences;
import fr.irit.smac.may.lib.components.interactions.exceptions.RefDoesNotExistsException;
import fr.irit.smac.may.lib.components.interactions.interfaces.Call;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class DirectReferencesImpl<I> extends DirectReferences<I> {

	@Override
	protected Call<I,DirRef> make_call() {
		return new Call<I,DirRef>() {
			public I call(DirRef ref) throws RefDoesNotExistsException {
				if (ref instanceof DirectReferencesImpl.RefImpl) {
					@SuppressWarnings({ "rawtypes", "unchecked" })
					RefImpl realRef = (DirectReferencesImpl.RefImpl) ref;
					return realRef.call();
				} else {
					throw new RefDoesNotExistsException();
				}
			}
		};
	}

	@Override
	protected Callee<I> make_Callee(String name) {
		return new CalleeImpl(name);
	}
	
	private class CalleeImpl extends Callee<I> {

		private final RefImpl me;
		
		private CalleeImpl(String name) {
			this.me = new RefImpl(this, name);
		}
		
		
		
		@Override
		protected Pull<DirRef> make_me() {
			return new Pull<DirRef>() {
				public DirRef pull() {
					return me;
				}
			};
		}

		@Override
		protected Do make_stop() {
			return new Do() {
				public void doIt() {
					me.stop();
				}
			};
		}
		
		public I p_toCall() {
			return toCall();
		}
		
	}
	
	private class RefImpl implements DirRef {
		
		private CalleeImpl ref;
		private final String name;
		
		private RefImpl(CalleeImpl ref, String name) {
			this.ref = ref;
			this.name = name;
		}

		private void stop() {
			// allow for garbage collection and reliability checks
			this.ref = null;
		}

		public I call() throws RefDoesNotExistsException {
			if (ref != null) return ref.p_toCall();
			else throw new RefDoesNotExistsException();
		}
		
		@Override
		public String toString() {
			return this.name + (this.ref == null ? "(stopped)" : "");
		}
	}
	

}
