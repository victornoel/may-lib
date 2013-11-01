package tests.remote;

import fr.irit.smac.may.lib.classic.remote.RemoteClassic;
import fr.irit.smac.may.lib.classic.remote.impl.AbstractRemoteClassicBehaviour;
import fr.irit.smac.may.lib.classic.remote.impl.RemoteClassicImpl;
import fr.irit.smac.may.lib.components.remote.messaging.receiver.RemoteAgentRef;
import fr.irit.smac.may.lib.interfaces.Push;

public class Test {

	public static void main(String[] args) {
		RemoteClassic.Component<String> infra = RemoteClassic.newComponent(new RemoteClassicImpl<String>(1099));
		infra.start();
		
		final RemoteAgentRef create = infra.create().create(new AbstractRemoteClassicBehaviour<String, RemoteAgentRef>() {
			@Override
			public Push<String> make_cycle() {
				return new Push<String>() {
					public void push(String msg) {
						System.out.println("test");
						System.out.println(msg);
					}
				};
			}
		});
		
		RemoteAgentRef create2 = infra.create().create(new AbstractRemoteClassicBehaviour<String, RemoteAgentRef>() {
			@Override
			public Push<String> make_cycle() {
				return new Push<String>() {
					public void push(String msg) {
						System.out.println(msg);
						System.out.println("test2");
						requires().send().send("HAHAhA", create);
					}
				};
			}
		});
		
		infra.send().send("start", create2);
	}
}
