package gameEngines.realmEngine.realmEvents;

import gameEngines.GameEvent;
import gameEngines.realmEngine.RealmEngine;

public abstract class RealmEvent extends GameEvent {

	public abstract void execute(RealmEngine realmEngine);
	
}
