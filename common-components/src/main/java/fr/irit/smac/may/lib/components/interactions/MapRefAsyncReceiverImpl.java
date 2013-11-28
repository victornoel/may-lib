package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.asyncreceiver.AsyncReceiverImpl;
import fr.irit.smac.may.lib.components.interactions.mapreferences.MapReferencesImpl;
import fr.irit.smac.may.lib.interfaces.Push;

public class MapRefAsyncReceiverImpl<M,K> extends MapRefAsyncReceiver<M,K> {

	@Override
	protected MapReferences<Push<M>, K> make_mr() {
		return new MapReferencesImpl<Push<M>, K>();
	}

	@Override
	protected AsyncReceiver<M, K> make_ar() {
		return new AsyncReceiverImpl<M, K>();
	}

	@Override
	protected Receiver<M, K> make_Receiver(K key) {
		return new Receiver<M,K>() {};
	}

	@Override
	protected ReceiverKeyPort<M, K> make_ReceiverKeyPort() {
		return new ReceiverKeyPort<M,K>() {};
	}

	@Override
	protected Sender<M, K> make_Sender() {
		return new Sender<M,K>() {};
	}


}
