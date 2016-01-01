package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.enemies.Enemy;

/**
 * LevelEvent to add an Enemy object (declared & initialized before passing it in as a constructor parameter) to the AllEnemies ArrayList.
 *
 */
public class SpawnEnemyEvent extends LevelEvent {

	public SpawnEnemyEvent(Enemy enemy) {
		this.enemy = enemy;
	}

	@Override
	public void execute(LevelEngine engine) {
		engine.getAllEnemies().addEnemy(enemy);
	}
	
	private Enemy enemy;
	
}
