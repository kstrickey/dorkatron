package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import levelData.SoundEffects;

public class DownArrowKeyPressedEvent extends LevelEvent {

	public DownArrowKeyPressedEvent() {
		
	}

	@Override
	public void execute(LevelEngine levelEngine) {
		if (levelEngine.getDork().eligibleToStomp()) {
			levelEngine.getDork().startStomping();
			levelEngine.playSoundEffect(SoundEffects.STOMP_HOVERING);
		}
	}

}
