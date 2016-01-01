package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.accessories.weapons.EmptyHandWeapon;
import gameEngines.levelEngine.simObjects.ammo.EmptyAmmoFactory;
import levelData.SoundEffects;

// Default key: h
public class LoadHandWeaponKeyPressedEvent extends LevelEvent {

	public LoadHandWeaponKeyPressedEvent() {

	}

	@Override
	public void execute(LevelEngine levelEngine) {
		if (levelEngine.getDork().getAmmoArsenal().getCurrentFactory() instanceof EmptyAmmoFactory) {
			levelEngine.writeMessage("No ammo selected.", 10);
		} else if (levelEngine.getDork().getHandWeaponArsenal().currentHandWeapon() instanceof EmptyHandWeapon) {
			levelEngine.writeMessage("No weapon selected.", 10);
		} else if (levelEngine.getDork().getAmmoArsenal().getCurrentFactory().stockRemaining() == 0) {
			levelEngine.writeMessage("No ammo remaining!", 10);
			levelEngine.playSoundEffect(SoundEffects.NO_AMMO_LEFT);
		} else {
			levelEngine.getDork().getHandWeaponArsenal().currentHandWeapon().load();
		}
	}

}
