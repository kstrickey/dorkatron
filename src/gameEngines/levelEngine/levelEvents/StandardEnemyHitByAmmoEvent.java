package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.SmokePuff;
import gameEngines.levelEngine.simObjects.enemies.Enemy;
import levelData.SoundEffects;

public class StandardEnemyHitByAmmoEvent extends LevelEvent {

	public StandardEnemyHitByAmmoEvent(Enemy enemy) {
		this.enemy = enemy;
	}

	@Override
	public void execute(LevelEngine levelEngine) {
		// Kill enemy (into SmokePuff)
		enemy.die();
		levelEngine.getAllEnemies().removeEnemy(enemy);
		levelEngine.getAllForegroundExtras().addSmokePuff(new SmokePuff(enemy.boundsRectangle()));
		levelEngine.addEnemyKilled();
		
		// Play sound effect
		levelEngine.playSoundEffect(SoundEffects.HIT_ENEMY);
	}
	
	private Enemy enemy;

}
