package gameEngines.realmEngine.realmEvents;

import gameEngines.realmEngine.RealmEngine;

public class EnterLevelEvent extends RealmEvent {

	public EnterLevelEvent() {
		
	}
	
	@Override
	public void execute(RealmEngine realmEngine) {
		realmEngine.enterLevel();
	}

}
