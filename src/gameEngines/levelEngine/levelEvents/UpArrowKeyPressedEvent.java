package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

public class UpArrowKeyPressedEvent extends LevelEvent {

	public UpArrowKeyPressedEvent() {

	}

	@Override
	public void execute(LevelEngine engine) {
		engine.getDork().setUpDepressed(true);
	}

}
