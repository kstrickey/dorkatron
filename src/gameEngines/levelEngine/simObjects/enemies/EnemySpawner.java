package gameEngines.levelEngine.simObjects.enemies;

import gameEngines.levelEngine.LevelEventQueue;

import java.awt.Dimension;
import java.util.Random;

import levelData.LevelData;

/**
 * Abstract class for spawning Enemy objects in a level.
 * Ways to control when an Enemy object is spawned (may be combined):
 * 	* At a specified period (constant/decreasing)
 * 	* When the existing number of enemies reaches a certain number (option: total enemies/just this EnemySpawner)
 * 	Will spawn only when Dork is in certain range (x and y positions): can be entire level
 * 
 */
public abstract class EnemySpawner implements Cloneable {
	
	public EnemySpawner(int x, int y, Dimension levelBounds) {
		timeFromStart = 0;
		enemiesFromMe = 0;
		totalEnemies = 0;
		
		this.x = x;
		this.y = y;
		this.levelBounds = levelBounds;
		
		// Default settings for spawning
		numberPerSpawn = 1;
		periodLength = UNDEFINED;
		minNumberOfEnemies = UNDEFINED;
		setMinX(x - 1000);
		setMaxX(x + 1000);
		setMinY(y - 625);
		setMaxY(y + 625);
	}
	
	public static final Random generator = new Random();
	
	public static final int UNDEFINED = -1;
	
	
	/**
	 * Fires new SpawnEnemyEvent.
	 * @param gameEventQueue
	 */
	public abstract void spawnOne(LevelEventQueue gameEventQueue, int x, int y);
	
	
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level, double dorkX, double dorkY) {
		timeFromStart += timeElapsed;
		
		if (dorkX >= minX && dorkX <= maxX && dorkY >= minY && dorkY <= maxY) {
			if (generator.nextInt((int) (1 + 1 / timeElapsed)) == 0) { // should theoretically occur about once per second, regardless of the timePerTick
				boolean spawn = false;

				if (periodLength != UNDEFINED) {
					if (generator.nextInt(periodLength) == 0) {
						spawn = true;
					}
					if (decreasingPeriod && !spawn) {
						if (generator.nextInt(1 + (int) timeFromStart) > 15 * Math.sqrt(timeFromStart)) { // To make a more gentle curve, increase sqrt coefficient
							spawn = true;
						}
					}
				}

				if (!spawn && minNumberOfEnemies != UNDEFINED) {
					if (onlyMyEnemies) {
						if (enemiesFromMe < minNumberOfEnemies) {
							spawn = true;
						}
					} else {
						if (totalEnemies < minNumberOfEnemies) {
							spawn = true;
						}
					}
				}

				if (spawn) {
					for (int i = 0; i < numberPerSpawn; ++i) {
						spawnOne(gameEventQueue, x, y);
						++enemiesFromMe;
					}
				}
			}
		}
	}
	
	public void setTotalEnemies(int totalEnemies) {
		this.totalEnemies = totalEnemies;
	}
	
	public void loseEnemyFromMe() {
		--enemiesFromMe;
	}
	
	
	public void setNumberPerSpawn(int numberPerSpawn) {
		this.numberPerSpawn = numberPerSpawn;
	}

	public void setPeriodLength(int periodLength) {
		this.periodLength = periodLength;
	}

	public void setDecreasingPeriod(boolean decreasingPeriod) {
		this.decreasingPeriod = decreasingPeriod;
	}

	public void setMinNumberOfEnemies(int minNumberOfEnemies) {
		this.minNumberOfEnemies = minNumberOfEnemies;
	}

	public void setOnlyMyEnemies(boolean onlyMyEnemies) {
		this.onlyMyEnemies = onlyMyEnemies;
	}

	public void setMinX(int minX) {
		if (minX > 0) {
			this.minX = minX;
		} else {
			this.minX = 0;
		}
	}

	public void setMaxX(int maxX) {
		if (maxX < levelBounds.width) {
			this.maxX = maxX;
		} else {
			this.maxX = levelBounds.width;
		}
	}

	public void setMinY(int minY) {
		if (minY > 0) {
			this.minY = minY;
		} else {
			this.minY = 0;
		}
	}

	public void setMaxY(int maxY) {
		if (maxY < levelBounds.height) {
			this.maxY = maxY;
		} else {
			this.maxY = levelBounds.height;
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object clone = super.clone();
		((EnemySpawner) clone).levelBounds = new Dimension(levelBounds);
		return clone;
	}
	
	private double timeFromStart;
	private int enemiesFromMe;
	private int totalEnemies;
	
	private final int x;
	private final int y;
	
	private int numberPerSpawn;
	
	private int periodLength;				// MUST BE GREATER THAN ZERO (unless undefined)
	private boolean decreasingPeriod;		// true if decreasing, false if constant
	
	private int minNumberOfEnemies;			// MUST BE AT LEAST ZERO (unless undefined)
	private boolean onlyMyEnemies;
	
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	
	private Dimension levelBounds;
	
}
