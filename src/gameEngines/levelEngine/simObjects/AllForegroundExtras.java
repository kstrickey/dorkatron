package gameEngines.levelEngine.simObjects;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;

import java.util.ArrayList;

import levelData.LevelData;

public class AllForegroundExtras implements Tickable, Drawable {

	public AllForegroundExtras() {
		smokePuffs = new ArrayList<SmokePuff>();
	}
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		for (SmokePuff puff : smokePuffs) {
			puff.tick(timeElapsed, gameEventQueue, level);
		}
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		for (SmokePuff puff : smokePuffs) {
			puff.draw(drawMachine);
		}
	}
	
	/**
	 * Removes a given SmokePuff object from the ArrayList.
	 * @param puff
	 */
	public void removeSmokePuff(SmokePuff puff) {
		if (smokePuffs.contains(puff)) {
			smokePuffs.remove(puff);
		}
	}
	
	/**
	 * Adds a given SmokePuff object to the ArrayList.
	 * @param puff
	 */
	public void addSmokePuff(SmokePuff puff) {
		smokePuffs.add(puff);
//		System.out.println("NEW SMOKEPUFF ADDED!");
	}
	
	private ArrayList<SmokePuff> smokePuffs;

}
