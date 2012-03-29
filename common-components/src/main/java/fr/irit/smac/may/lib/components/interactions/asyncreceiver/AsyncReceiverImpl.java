package fr.irit.smac.may.lib.components.interactions.asyncreceiver;

import fr.irit.smac.may.lib.components.collections.ConcurrentQueueImpl;
import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.components.interactions.AsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.exceptions.RefDoesNotExistsException;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend;

public class AsyncReceiverImpl<M,K> extends AsyncReceiver<M,K> {

	@Override
	protected ReliableSend<M, K> make_deposit() {
		return new ReliableSend<M, K>() {
			public void reliableSend(M message, K receiver) throws RefDoesNotExistsException {
				call().call(receiver).push(message);
			}

			public void send(M message, K receiver) {
				try {
					self().deposit().reliableSend(message, receiver);
				} catch (RefDoesNotExistsException e) {
					// do nothing, on purpose!
				}
			};
		};
	}
	
	@Override
	protected ReceiverBuf<M,K> make_ReceiverBuf() {
		return new ReceiverBufImpl();
	}
	
	private class ReceiverBufImpl extends ReceiverBuf<M,K> {

		@Override
		protected Queue<M> make_q() {
			return new ConcurrentQueueImpl<M>();
		}
		
	}

}
