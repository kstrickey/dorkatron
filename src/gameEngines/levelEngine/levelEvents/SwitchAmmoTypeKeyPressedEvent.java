package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

// Default key: i
public class SwitchAmmoTypeKeyPressedEvent extends LevelEvent {

	public SwitchAmmoTypeKeyPressedEvent() {
		
	}

	@Override
	public void execute(LevelEngine levelEngine) {
		levelEngine.getDork().getAmmoArsenal().changeFactory();
		levelEngine.writeMessage("Switched Ammo to " + levelEngine.getDork().getAmmoArsenal().getCurrentFactory().ammoType(), 10);
	}

}
