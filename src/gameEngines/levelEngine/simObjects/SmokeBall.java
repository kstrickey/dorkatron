package gameEngines.levelEngine.simObjects;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;

import java.util.Random;

import javafx.scene.image.Image;
import levelData.LevelData;

/**
 * An individual component of a SmokePuff. Random influence in placement.
 *
 */
public class SmokeBall implements Tickable, Drawable {

	/**
	 * Constructs a new SmokeBall object by randomly sizing and placing it within the bounds of the deceased enemy.
	 * @param x : center
	 * @param y : bottom
	 * @param width
	 * @param height
	 * @param generator : Random object
	 */
	public SmokeBall(double x, double y, double width, double height, Random generator) {
		image = new Image("resources/images/smoke ball " + generator.nextInt(2) + ".png");
		
		this.x = x + generator.nextInt((int) width);
		this.y = y - height/2 + generator.nextInt((int) height);
		this.width = 35 + generator.nextInt(60);
		this.height = 35 + generator.nextInt(60);
		
		timeSinceConception = 0;
		timeToDie = 4 + generator.nextDouble() * 2;
		flaggedForDeath = false;
	}
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		timeSinceConception += timeElapsed;
		flaggedForDeath = timeSinceConception > timeToDie;
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
//		gc.drawImage(image, (x - width/2) * dM, (y - height) * dM, width * dM, height * dM);
		drawMachine.draw(image, x, y, width, height);
	}
	
	public boolean isFlaggedForDeath() {
		return flaggedForDeath;
	}
	
	private Image image;
	
	private double width;
	private double height;
	private double x;
	private double y;
	
	private double timeSinceConception;
	private double timeToDie;
	
	private boolean flaggedForDeath;
}
