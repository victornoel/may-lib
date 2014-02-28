package fr.irit.smac.may.lib.components.interactions.asyncreceiver;

import fr.irit.smac.may.lib.components.interactions.AsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.exceptions.RefDoesNotExistsException;
import fr.irit.smac.may.lib.components.interactions.interfaces.ReliableSend;

public class AsyncReceiverImpl<M,K> extends AsyncReceiver<M,K> {

	@Override
	protected ReliableSend<M, K> make_send() {
		return new ReliableSend<M, K>() {
			public void reliableSend(M message, K receiver) throws RefDoesNotExistsException {
				requires().call().call(receiver).push(message);
			}

			public void send(M message, K receiver) {
				try {
					provides().send().reliableSend(message, receiver);
				} catch (RefDoesNotExistsException e) {
					// do nothing, on purpose!
				}
			};
		};
	}

	@Override
	protected Receiver<M, K> make_Receiver() {
		return new Receiver<M, K>() {};
	}

	@Override
	protected Sender<M, K> make_Sender() {
		return new Sender<M, K>() {
			@Override
			protected ReliableSend<M, K> make_send() {
				return new ReliableSend<M, K>() {
					public void reliableSend(M message, K receiver) throws RefDoesNotExistsException {
						eco_provides().send().reliableSend(message, receiver);
					};
					public void send(M message, K receiver) {
						eco_provides().send().send(message, receiver);
					};
				};
			}
		};
	}

}
