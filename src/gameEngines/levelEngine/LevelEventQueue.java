package gameEngines.levelEngine;

import gameEngines.GameEvent;
import gameEngines.GameEventQueue;
import gameEngines.levelEngine.levelEvents.LevelEvent;

import java.util.ArrayList;

public class LevelEventQueue extends GameEventQueue {
	
	public LevelEventQueue(LevelEngine engine) {
		levelEvents = new ArrayList<LevelEvent>();
		this.engine = engine;
	}
	
	/**
	 * Adds a LevelEvent object to the queue to be executed after the current tick cycle.
	 * @param e : LevelEvent object
	 */
	@Override
	public void addEvent(GameEvent e) {
		if (!(e instanceof LevelEvent)) {
			return;
		}
		synchronized (synchronizer) {
			levelEvents.add((LevelEvent) e);
		}
	}
	
	/**
	 * Calls the execute method on all LevelEvent objects in the queue. Clears the queue afterward.
	 */
	@Override
	public void executeAllEvents() {
		ArrayList<LevelEvent> gameEvents;
		synchronized (synchronizer) {
			gameEvents = this.levelEvents;
			this.levelEvents = new ArrayList<>();
		}
		
		for (LevelEvent e : gameEvents) {
			e.execute(engine);
		}
		
	}
	
	private volatile ArrayList<LevelEvent> levelEvents;
	
	private final LevelEngine engine;
	
	private final Object synchronizer = new Object();
	
}
