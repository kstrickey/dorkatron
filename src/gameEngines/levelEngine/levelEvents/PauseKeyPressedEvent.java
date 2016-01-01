package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

// Default key: P
public class PauseKeyPressedEvent extends LevelEvent {

	public PauseKeyPressedEvent() {
		
	}

	@Override
	public void execute(LevelEngine levelEngine) {
		levelEngine.togglePaused();
	}

}
