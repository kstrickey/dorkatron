package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.SmokePuff;

/**
 * LevelEvent to remove a SmokePuff object from the ArrayList contained in the AllForegroundExtras object once every SmokeBall has disappeared.
 *
 */
public class SmokePuffHasDisappearedEvent extends LevelEvent {

	public SmokePuffHasDisappearedEvent(SmokePuff puff) {
		this.puff = puff;
	}

	@Override
	public void execute(LevelEngine engine) {
		engine.getAllForegroundExtras().removeSmokePuff(puff);
	}
	
	private final SmokePuff puff;

}
