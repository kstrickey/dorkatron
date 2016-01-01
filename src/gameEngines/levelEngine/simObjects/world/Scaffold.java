package gameEngines.levelEngine.simObjects.world;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;

import java.awt.Rectangle;

import javafx.scene.image.Image;
import levelData.LevelData;

/**
 * Represents a standard platform in a World that objects can stand on.
 *
 */
public class Scaffold implements Tickable, Drawable {
	
	public Scaffold(int x, int y, int width, int height, boolean impassable) {
		BLACK_RECTANGLE = new Image("/resources/images/black rectangle.png");
		area = new Rectangle(x, y, width, height);
		this.impassable = impassable;
	}
	
	/**
	 * Copy constructor
	 * @param other : Scaffold object
	 */
	public Scaffold(Scaffold other) {
		BLACK_RECTANGLE = new Image("/resources/images/black rectangle.png");
		area = new Rectangle(other.area);
		impassable = other.impassable;
	}
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		drawMachine.draw(BLACK_RECTANGLE, area.x + area.width/2.0, area.y, area.width, area.height);
	}
	
	public Rectangle getArea() {
		return area;
	}
	
	public boolean isImpassible() {
		return impassable;
	}
	
	public int surface() {
		return area.y + area.height;
	}
	
	private final Image BLACK_RECTANGLE;
	private Rectangle area;			// x, y are at left, bottom
	private boolean impassable;		// if the Scaffold is impassable, it means that the Dork cannot jump through it, even from underneath or the side.
	
}
