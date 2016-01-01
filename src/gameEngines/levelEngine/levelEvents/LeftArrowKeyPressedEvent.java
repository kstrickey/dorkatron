package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

public class LeftArrowKeyPressedEvent extends LevelEvent {
	
	public LeftArrowKeyPressedEvent() {
		
	}
	
	@Override
	public void execute(LevelEngine engine) {
		engine.getDork().setLeftDepressed(true);
	}

}
