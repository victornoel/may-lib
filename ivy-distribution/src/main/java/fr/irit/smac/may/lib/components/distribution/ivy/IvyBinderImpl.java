package fr.irit.smac.may.lib.components.distribution.ivy;

import java.util.List;

import org.javatuples.Pair;

import fr.irit.smac.may.lib.interfaces.Push;

public class IvyBinderImpl extends IvyBinder {

	private Integer bindId = null;
	
	@Override
	protected Push<String> reBindMsg() {
		return new Push<String>() {
			public void push(String thing) {
				if (bindId != null) {
					unBindMsg().push(bindId);
				}
				Push<List<String>> callback = new Push<List<String>>() {
					public void push(List<String> thing) {
						IvyBinderImpl.this.receive().push(thing);
					}
				};
				bindId = bindMsg().get(Pair.with(thing, callback));
			}
		};
	}

}
