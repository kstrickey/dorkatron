package gameEngines.levelEngine.simObjects.enemies;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.levelEvents.DorkBounceOnEnemyEvent;
import gameEngines.levelEngine.levelEvents.DorkStompThroughEnemyEvent;
import gameEngines.levelEngine.simObjects.Dork;

import java.util.ArrayList;

import levelData.LevelData;

public class AllEnemies implements Tickable, Drawable {

	public AllEnemies(Dork targetDork, LevelData level) {
		enemySpawners = new ArrayList<EnemySpawner>();
		try {
			for (EnemySpawner spawner : level.enemySpawners()) {
				enemySpawners.add((EnemySpawner) spawner.clone());
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		enemies = new ArrayList<Enemy>();
		this.targetDork = targetDork;
		missionToKillDorkAccomplished = false;
	}
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		for (EnemySpawner spawner : enemySpawners) {
			spawner.setTotalEnemies(currentNumber());
			spawner.tick(timeElapsed, gameEventQueue, level, targetDork.getX(), targetDork.getY());
		}
		
		for (Enemy enemy : enemies) {
			enemy.tick(timeElapsed, gameEventQueue, level);
			if (enemy.killedDork(targetDork)) {
				missionToKillDorkAccomplished = true;
			}
			if (enemy.killedByDork(targetDork)) {
				if (!targetDork.isStomping()) {
					gameEventQueue.addEvent(new DorkBounceOnEnemyEvent(enemy));
				} else {
					gameEventQueue.addEvent(new DorkStompThroughEnemyEvent(enemy));
				}
			}
		}
		
		if (missionToKillDorkAccomplished) {
			System.out.println("Mission accomplished! Dork dead.");
		}
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		for (Enemy enemy : enemies) {
			enemy.draw(drawMachine);
		}
	}
	
	public boolean isMissionToKillDorkAccomplished() {
		return missionToKillDorkAccomplished;
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	/**
	 * Returns the size of the ArrayList of Enemy objects.
	 * @return
	 */
	public int currentNumber() {
		return enemies.size();
	}
	
	public void removeEnemy(Enemy enemy) {
		if (enemies.contains(enemy)) {
			enemies.remove(enemy);
		}
	}
	
	/**
	 * Note: for use by SpawnEnemyEvent.
	 * @param enemy
	 */
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}
	
	private final ArrayList<EnemySpawner> enemySpawners;
	private final ArrayList<Enemy> enemies;
	private final Dork targetDork;
	
	private boolean missionToKillDorkAccomplished;
	
}
