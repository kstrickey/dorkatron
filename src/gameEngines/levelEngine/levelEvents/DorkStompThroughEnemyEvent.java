package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.SmokePuff;
import gameEngines.levelEngine.simObjects.enemies.Enemy;
import levelData.SoundEffects;

public class DorkStompThroughEnemyEvent extends LevelEvent {
	
	public DorkStompThroughEnemyEvent(Enemy enemy) {
		this.enemy = enemy;
	}
	
	@Override
	public void execute(LevelEngine levelEngine) {
		// Kill Enemy
		enemy.die();
		levelEngine.getAllEnemies().removeEnemy(enemy);
		levelEngine.getAllForegroundExtras().addSmokePuff(new SmokePuff(enemy.boundsRectangle()));
		levelEngine.addEnemyKilled();
		
		// Play sound effect
		levelEngine.playSoundEffect(SoundEffects.STOMP_THROUGH_ENEMY);
	}
	
	private Enemy enemy;
	
}
