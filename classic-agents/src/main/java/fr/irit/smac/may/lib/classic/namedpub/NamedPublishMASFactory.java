package fr.irit.smac.may.lib.classic.namedpub;

public interface NamedPublishMASFactory {

	public void createObserver(AbstractObserverBehaviour<String> beh);

	public void createObserved(String name, AbstractObservedBehaviour beh);
}
