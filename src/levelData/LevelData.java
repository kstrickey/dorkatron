package levelData;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.enemies.EnemySpawner;
import gameEngines.levelEngine.simObjects.world.Scaffold;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * A class representing everything necessary about a level. Cannot be ticked or drawn. Feeds information to gameEngines.levelEngine.simObjects when needed.
 *
 */
public abstract class LevelData {
	
	/**
	 * Returns the unique String ID to the level, used for matching with progress files.
	 * Generally the ID consists of the level's name, all lowercase, using dashes for spaces, followed by a dash and a six-digit integer (Ex: level-name-338490).
	 * @return
	 */
	public abstract String levelID();
	
	public abstract String name();
	
	/**
	 * Returns the gravity of the level.
	 * @return
	 */
	public abstract double gravity();
	
	/**
	 * Returns a String that will be outputted to the user telling him/her how to complete the level.
	 * @return
	 */
	public abstract String levelObjective();
	
	/**
	 * Returns the background Image used for the level.
	 * @return
	 */
	public abstract Image backgroundImage();
	
	/**
	 * Returns the AWT Dimension indicating the width and height of the level.
	 * @return
	 */
	public abstract Dimension levelBounds();
	
	/**
	 * Returns the list of Scaffold objects contained in the World of this level.
	 * @return
	 */
	public abstract ArrayList<Scaffold> scaffolds();
	
	/**
	 * Returns an AWT Rectangle indicating the initial source rectangle of the background image to be drawn on screen.
	 * @return
	 */
	public abstract Rectangle initialGameWindow();
	
	/**
	 * Returns a String for the background music that will loop continuously, or null if there is none.
	 * @return
	 */
	public abstract String backgroundMusicURL();
	
//	/**
//	 * Each level should spawn different enemies at different rates. Called in every AllEnemies tick cycle.
//	 * @param timeFromStart
//	 * @param timePerTick : note: divide by timePerTick for frequency (higher resolution --> more chances; need to balance)
//	 * @param allEnemies
//	 */
//	public abstract void spawnEnemies(double timeFromStart, double timePerTick, AllEnemies allEnemies, LevelEventQueue gameEventQueue, Random generator);
	
	
	public abstract ArrayList<EnemySpawner> enemySpawners();
	
	/**
	 * Returns true if the level has been completed, false if otherwise.
	 * @param gameEngines.levelEngine
	 * @return
	 */
	public abstract boolean isDone(LevelEngine levelEngine);
	
}
