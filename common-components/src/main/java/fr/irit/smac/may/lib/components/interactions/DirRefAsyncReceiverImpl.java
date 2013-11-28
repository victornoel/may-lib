package fr.irit.smac.may.lib.components.interactions;

import fr.irit.smac.may.lib.components.interactions.asyncreceiver.AsyncReceiverImpl;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirectReferencesImpl;
import fr.irit.smac.may.lib.interfaces.Push;

public class DirRefAsyncReceiverImpl<M> extends DirRefAsyncReceiver<M> {

	@Override
	protected DirectReferences<Push<M>> make_dr() {
		return new DirectReferencesImpl<Push<M>>();
	}

	@Override
	protected AsyncReceiver<M, DirRef> make_ar() {
		return new AsyncReceiverImpl<M, DirRef>();
	}

	@Override
	protected Receiver<M> make_Receiver(String name) {
		return new Receiver<M>() {};
	}

	@Override
	protected Sender<M> make_Sender() {
		return new Sender<M>() {};
	}

	

}
