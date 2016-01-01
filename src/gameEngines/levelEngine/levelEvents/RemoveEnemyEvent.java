package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.enemies.Enemy;

public class RemoveEnemyEvent extends LevelEvent {

	public RemoveEnemyEvent(Enemy enemy) {
		this.enemy = enemy;
	}

	@Override
	public void execute(LevelEngine engine) {
		enemy.die();
		engine.getAllEnemies().removeEnemy(enemy);
	}
	
	Enemy enemy;

}
