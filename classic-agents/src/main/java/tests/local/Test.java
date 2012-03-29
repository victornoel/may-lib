package tests.local;

import fr.irit.smac.may.lib.classic.impl.ClassicImpl;
import fr.irit.smac.may.lib.classic.local.Classic;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.components.interactions.directreferences.DirRef;
import fr.irit.smac.may.lib.interfaces.Push;

public class Test {

	public static void main(String[] args) {
		Classic.Component<String> infra = Classic.createComponent(new ClassicImpl<String>()) ;
		infra.start();
		
		final DirRef create = infra.create().create(new ClassicBehaviour<String, DirRef>() {
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
		
		DirRef create2 = infra.create().create(new ClassicBehaviour<String, DirRef>() {
			@Override
			public Push<String> make_cycle() {
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
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		infra.stop().doIt();
	}
}
