package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

/**
 * Causes the Dork to start moving forward.
 * @author Kevin
 *
 */
public class RightArrowKeyPressedEvent extends LevelEvent {

	public RightArrowKeyPressedEvent() {
		
	}
	
	@Override
	public void execute(LevelEngine engine) {
		engine.getDork().setRightDepressed(true);
	}

}
