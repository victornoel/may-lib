package fr.irit.smac.may.lib.components.messaging.interfaces;

import fr.irit.smac.may.lib.components.messaging.exceptions.AgentDoesNotExistException;
import fr.irit.smac.may.lib.interfaces.Send;

public interface ReliableSend<T, R> extends Send<T, R> {

	public void reliableSend(T message, R receiver) throws AgentDoesNotExistException;
}
