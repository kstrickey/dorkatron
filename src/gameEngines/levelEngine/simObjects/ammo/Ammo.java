package gameEngines.levelEngine.simObjects.ammo;

import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.simObjects.enemies.AllEnemies;
import levelData.LevelData;

public abstract class Ammo implements Tickable, Drawable {
	
	public Ammo(double x, double y, double vX, double vY) {
		
	}
	
	public abstract String name();
	public abstract String description();
	
	/**
	 * Fires a LevelEvent for each Enemy that it kills (usually only one, if any).
	 * @return true if it killed at least one Enemy (thus marking this Ammo object for removal), or false if there are no hits.
	 */
	public abstract boolean killedEnemy(AllEnemies allEnemies, LevelEventQueue levelEventQueue);
	/**
	 * 
	 * @param level
	 * @return true if it reached the ground, or platform (thus marking the Ammo object for removal), or false if otherwise.
	 */
	public abstract boolean hitGround(LevelData level);
	
}
