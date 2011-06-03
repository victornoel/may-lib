package fr.irit.smac.may.lib.classic.impl;

import fr.irit.smac.may.lib.classic.interfaces.CreateClassic;
import fr.irit.smac.may.lib.classic.local.ClassicBehaviour;
import fr.irit.smac.may.lib.classic.local.Factory;

public class FactoryImpl<Msg, Ref> extends Factory<Msg, Ref> {

	public class AgentSide extends Factory.Agent<Msg, Ref> {
		@Override
		protected CreateClassic<Msg, Ref> create() {
			return new CreateClassic<Msg, Ref>() {
				public Ref create(ClassicBehaviour<Msg, Ref> beh) {
					return FactoryImpl.this.infraCreate().create(beh);
				}
			};
		}
	}
}
