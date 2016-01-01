package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

// Default key: j
public class FireHandWeaponKeyPressedEvent extends LevelEvent {

	public FireHandWeaponKeyPressedEvent() {
		
	}
	
	@Override
	public void execute(LevelEngine levelEngine) {
		if (levelEngine.getDork().getHandWeaponArsenal().currentHandWeapon() == null) {
			levelEngine.writeMessage("No weapon selected.", 10);
		} else if (!levelEngine.getDork().getHandWeaponArsenal().currentHandWeapon().isLoaded()) {
			levelEngine.writeMessage("Weapon not loaded.", 10);
		} else {
			levelEngine.getDork().getHandWeaponArsenal().currentHandWeapon().fire();
		}
	}

}
