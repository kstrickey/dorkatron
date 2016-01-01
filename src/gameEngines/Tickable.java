package gameEngines;

import gameEngines.levelEngine.LevelEventQueue;
import levelData.LevelData;

public interface Tickable {
	
	public abstract void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level);
	
}
