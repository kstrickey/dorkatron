package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import levelData.SoundEffects;

public class PlaySingleSoundEffectEvent extends LevelEvent {

	public PlaySingleSoundEffectEvent(SoundEffects effect) {
		this.effect = effect;
	}

	@Override
	public void execute(LevelEngine levelEngine) {
		levelEngine.playSoundEffect(effect);
	}
	
	private final SoundEffects effect;
	
}
