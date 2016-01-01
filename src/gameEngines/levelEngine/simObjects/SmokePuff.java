package gameEngines.levelEngine.simObjects;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.levelEvents.SmokeBallDisappearEvent;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import levelData.LevelData;

/**
 * The smoky remains of a late Enemy. Made up of ArrayList of SmokeBall objects. Has random influence. Part of AllForegroundExtras.
 *
 */
public class SmokePuff implements Tickable, Drawable {
	
	/**
	 * Constructs a new SmokePuff object with the given boundaries.
	 * @param x : center
	 * @param y : bottom
	 * @param width
	 * @param height
	 */
	public SmokePuff(double x, double y, double width, double height) {
		smokeBalls = new ArrayList<SmokeBall>();
		Random generator = new Random();
		for (int i = 0, limit = 4 + generator.nextInt(4); i < limit; ++i) {
			smokeBalls.add(new SmokeBall(x, y, width, height, generator));
		}
	}
	
	/**
	 * Overloaded constructor to take AWT Rectangle object marking bounds.
	 * @param bounds
	 */
	public SmokePuff(Rectangle2D.Double bounds) {
		this(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		if (smokeBalls.isEmpty()) {											// ??- what is this for.
			
		}
		for (SmokeBall ball : smokeBalls) {
			ball.tick(timeElapsed, gameEventQueue, level);
			if (ball.isFlaggedForDeath()) {
				gameEventQueue.addEvent(new SmokeBallDisappearEvent(this, ball));
			}
		}
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		for (SmokeBall ball : smokeBalls) {				// ??-got a concurrent modification exception.
			ball.draw(drawMachine);
		}
	}
	
	/**
	 * For use by SmokeBallDisappearEvent class.
	 * @param ball
	 */
	public void removeSmokeBall(SmokeBall ball) {
		if (smokeBalls.contains(ball)) {
			smokeBalls.remove(ball);
		}
	}
	
	private ArrayList<SmokeBall> smokeBalls;
	
}
