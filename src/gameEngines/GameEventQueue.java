/**
 * 
 */
package gameEngines;

public abstract class GameEventQueue {
	
	public abstract void addEvent(GameEvent e);
	public abstract void executeAllEvents();
	
}
