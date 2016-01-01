package gameEngines.levelEngine.simObjects.enemies;

import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.levelEvents.RemoveEnemyEvent;
import gameEngines.levelEngine.simObjects.Dork;

import java.awt.geom.Rectangle2D;

import levelData.LevelData;

public abstract class Enemy implements Tickable, Drawable {
	
	public Enemy(EnemySpawner spawner) {
		this.spawner = spawner;
	}
	
	public abstract boolean killedDork(Dork dork);
	public abstract boolean killedByDork(Dork dork);
	
	public abstract double getVY();
	public abstract int width();
	public abstract int height();
	public abstract double x();
	public abstract double y();
	
	/**
	 * Returns the AWT Rectangle2D.Double object depicting where the SmokePuff should be drawn when the Enemy dies. Coordinates with respect to the Level graph.
	 * Note: Because of the nature of the y-coordinate system, the bounds rectangle will actually be upside-down if it is simply drawn.
	 * In the system used here, the origin is at the bottom-left of the screen.
	 * @return
	 */
	public abstract Rectangle2D.Double boundsRectangle();
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		// Check if out of level boundaries (and remove)
		if (x() > level.levelBounds().width + 2 * width() || x() < -2 * width()) {		// note: should provide margin of 2 * WIDTH
			gameEventQueue.addEvent(new RemoveEnemyEvent(this));
		}
	}
	
	/**
	 * Called before the Enemy is removed from the ArrayList. Removes one from the EnemySpawner's count.
	 */
	public void die() {
		spawner.loseEnemyFromMe();
	}
	
	private final EnemySpawner spawner;
	
}
