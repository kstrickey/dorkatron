package gameEngines.levelEngine.levelEvents;

import gameEngines.GameEvent;
import gameEngines.levelEngine.LevelEngine;

public abstract class LevelEvent extends GameEvent {
	
	public abstract void execute(LevelEngine levelEngine);
	
}
