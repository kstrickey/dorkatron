package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

public class UpArrowKeyReleasedEvent extends LevelEvent {

	public UpArrowKeyReleasedEvent() {
		
	}

	@Override
	public void execute(LevelEngine engine) {
		engine.getDork().setUpDepressed(false);
	}

}
