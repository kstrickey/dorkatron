package gameEngines.levelEngine.simObjects.enemies;

import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.levelEvents.SpawnEnemyEvent;

import java.awt.Dimension;

public class TallBunnyRabbitSpawner extends BunnyRabbitSpawner {
	
	public TallBunnyRabbitSpawner(int x, int y, Dimension levelBounds) {
		super(x, y, levelBounds);
		
		// Default spawn settings
		spawnVX = -7;
		spawnJumpSpeed = 45;
		strict = false;
	}
	
	@Override
	public void spawnOne(LevelEventQueue gameEventQueue, int x, int y) {
		gameEventQueue.addEvent(new SpawnEnemyEvent(new TallBunnyRabbit(generator, this, x, y, spawnVX, spawnJumpSpeed, strict)));
	}
	
	public void setSpawnVX(double spawnVX) {
		this.spawnVX = spawnVX;
	}
	
	public void setSpawnJumpSpeed(double spawnJumpSpeed) {
		this.spawnJumpSpeed = spawnJumpSpeed;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	private double spawnVX;
	private double spawnJumpSpeed;
	private boolean strict;
}