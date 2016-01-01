package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

public class CreateAmmoEvent extends LevelEvent {

	public CreateAmmoEvent(int relativeX, int relativeY) {
		// Relative to Dork
		this.relativeX = relativeX;
		this.relativeY = relativeY;
	}

	@Override
	public void execute(LevelEngine levelEngine) {
		levelEngine.getAllAmmo().addAmmo(levelEngine.getDork().getAmmoArsenal().getCurrentFactory().createOne(levelEngine.getDork(), relativeX, relativeY));
	}
	
	private final int relativeX, relativeY;
	
}
