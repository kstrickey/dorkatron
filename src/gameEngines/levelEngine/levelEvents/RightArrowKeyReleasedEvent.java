package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

public class RightArrowKeyReleasedEvent extends LevelEvent {
	
	public RightArrowKeyReleasedEvent() {
		
	}

	@Override
	public void execute(LevelEngine engine) {
		engine.getDork().setRightDepressed(false);
	}

}
