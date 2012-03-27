package fr.irit.smac.may.lib.components.distribution.ivy;

import java.util.List;

import fr.irit.smac.may.lib.interfaces.Push;

public class IvyBinderImpl extends IvyBinder {

	private int bindId = -1;
	
	@Override
	protected Push<String> make_reBindMsg() {
		return new Push<String>() {
			public void push(String thing) {
				if (bindId != -1) {
					unBindMsg().push(bindId);
				}
				Push<List<String>> callback = new Push<List<String>>() {
					public void push(List<String> thing) {
						IvyBinderImpl.this.receive().push(thing);
					}
				};
				bindId = bindMsg().bind(thing, callback);
			}
		};
	}
	
	

}
