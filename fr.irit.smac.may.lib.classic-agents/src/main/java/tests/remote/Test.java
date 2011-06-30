package tests.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.RemoteClassicBehaviour;
import fr.irit.smac.may.lib.classic.remote.impl.RemoteClassicImpl;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.interfaces.Push;

public class Test {

	public static void main(String[] args) {
		RemoteClassic.Component<String> infra = new RemoteClassic.Component<String>(new RemoteClassicImpl<String>(1099), new RemoteClassic.Bridge<String>() {}) ;
		infra.start();
		
		final RemoteAgentRef<String> create = infra.create().create(new RemoteClassicBehaviour<String, RemoteAgentRef<String>>() {
			@Override
			public Push<String> cycle() {
				return new Push<String>() {
					public void push(String msg) {
						System.out.println("test");
						System.out.println(msg);
					}
				};
			}
		});
		
		RemoteAgentRef<String> create2 = infra.create().create(new RemoteClassicBehaviour<String, RemoteAgentRef<String>>() {
			@Override
			public Push<String> cycle() {
				return new Push<String>() {
					public void push(String msg) {
						System.out.println(msg);
						System.out.println("test2");
						send().send("HAHAhA", create);
					}
				};
			}
		});
		
		infra.send().send("start", create2);
	}
}
