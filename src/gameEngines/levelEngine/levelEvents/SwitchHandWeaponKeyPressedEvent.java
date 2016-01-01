package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;

//Default key: U
public class SwitchHandWeaponKeyPressedEvent extends LevelEvent {

	public SwitchHandWeaponKeyPressedEvent() {
		
	}
	
	@Override
	public void execute(LevelEngine levelEngine) {
		levelEngine.getDork().getHandWeaponArsenal().changeHandWeapon();
		levelEngine.writeMessage("Switched hand weapon to " + levelEngine.getDork().getHandWeaponArsenal().currentHandWeapon().name() + ".", 5);
	}

}
