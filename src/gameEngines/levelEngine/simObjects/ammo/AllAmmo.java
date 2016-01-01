package gameEngines.levelEngine.simObjects.ammo;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.levelEvents.RemoveAmmoEvent;
import gameEngines.levelEngine.simObjects.enemies.AllEnemies;

import java.util.ArrayList;

import levelData.LevelData;

public class AllAmmo implements Tickable, Drawable {
	
	public AllAmmo(AllEnemies allEnemies) {
		ammos = new ArrayList<Ammo>();
		this.allEnemies = allEnemies;
	}
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue levelEventQueue, LevelData level) {
		for (Ammo ammo : ammos) {
			ammo.tick(timeElapsed, levelEventQueue, level);
			if (ammo.killedEnemy(allEnemies, levelEventQueue) || ammo.hitGround(level)) {
				levelEventQueue.addEvent(new RemoveAmmoEvent(ammo));
			}
		}
	}

	@Override
	public void draw(DrawMachine drawMachine) {
		for (Ammo ammo : ammos) {						// ?? - will this yield a concurrent modification exception, if ammo is removed or added while drawing?
			ammo.draw(drawMachine);
		}
	}
	
	public void addAmmo(Ammo ammo) {
		ammos.add(ammo);
	}
	
	public void removeAmmo(Ammo ammo) {
		ammos.remove(ammo);
	}
	
	private ArrayList<Ammo> ammos;
	private AllEnemies allEnemies;
	
}
