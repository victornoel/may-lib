package tests.named;

import fr.irit.smac.may.lib.classic.named.AbstractClassicNamedBehaviour;
import fr.irit.smac.may.lib.classic.named.ClassicNamed;
import fr.irit.smac.may.lib.classic.named.ClassicNamedImpl;
import fr.irit.smac.may.lib.interfaces.Push;

public class Test {

	public static void main(String[] args) {
		ClassicNamed.Component<String> infra = ClassicNamed.newComponent(new ClassicNamedImpl<String>()) ;
		infra.start();
		
		infra.create().create(new AbstractClassicNamedBehaviour<String, String>() {
			@Override
			public Push<String> make_cycle() {
				return new Push<String>() {
					public void push(String msg) {
						System.out.println("test");
						System.out.println(msg);
					}
				};
			}
		},"a1");
		
		infra.create().create(new AbstractClassicNamedBehaviour<String, String>() {
			@Override
			public Push<String> make_cycle() {
				return new Push<String>() {
					public void push(String msg) {
						System.out.println(msg);
						System.out.println("test2");
						requires().send().send("HAHAhA", "a1");
					}
				};
			}
		}, "a2");
		
		infra.send().send("start", "a2");
	}
}
