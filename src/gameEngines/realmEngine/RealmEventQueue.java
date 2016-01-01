package gameEngines.realmEngine;

import gameEngines.GameEvent;
import gameEngines.GameEventQueue;
import gameEngines.realmEngine.realmEvents.RealmEvent;

import java.util.ArrayList;

public class RealmEventQueue extends GameEventQueue {

	public RealmEventQueue(RealmEngine realmEngine) {
		realmEvents = new ArrayList<RealmEvent>();
		this.realmEngine = realmEngine;
	}
	
	/**
	 * Adds a LevelEvent object to the queue to be executed after the current tick cycle.
	 * @param e : LevelEvent object
	 */
	@Override
	public void addEvent(GameEvent e) {
		if (!(e instanceof RealmEvent)) {
			return;
		}
		synchronized (synchronizer) {
			realmEvents.add((RealmEvent) e);
		}
	}
	
	/**
	 * Calls the execute method on all LevelEvent objects in the queue. Clears the queue afterward.
	 */
	@Override
	public void executeAllEvents() {
		ArrayList<RealmEvent> realmEvents;
		synchronized (synchronizer) {
			realmEvents = this.realmEvents;
			this.realmEvents = new ArrayList<>();
		}
		
		for (RealmEvent e : realmEvents) {
			e.execute(realmEngine);
		}
		
	}
	
	private volatile ArrayList<RealmEvent> realmEvents;
	
	private final RealmEngine realmEngine;
	
	private final Object synchronizer = new Object();
}
