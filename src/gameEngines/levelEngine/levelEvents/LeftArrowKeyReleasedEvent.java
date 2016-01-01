package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

public class LeftArrowKeyReleasedEvent extends LevelEvent {

	public LeftArrowKeyReleasedEvent() {

	}

	@Override
	public void execute(LevelEngine engine) {
		engine.getDork().setLeftDepressed(false);
	}

}
