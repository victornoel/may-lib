package tests.local;

import fr.irit.smac.may.lib.classic.impl.ClassicImpl;
import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.refreceive.impl.AgentRef;
import fr.irit.smac.may.lib.interfaces.Push;

public class Test {

	public static void main(String[] args) {
		Classic.Component<String> infra = new Classic.Component<String>(new ClassicImpl<String>(), new Classic.Bridge<String>() {}) ;
		infra.start();
		
		final AgentRef<String> create = infra.create().create(new ClassicBehaviour<String, AgentRef<String>>() {
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
		
		AgentRef<String> create2 = infra.create().create(new ClassicBehaviour<String, AgentRef<String>>() {
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
