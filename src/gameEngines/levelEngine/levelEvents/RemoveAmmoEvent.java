package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.ammo.Ammo;

public class RemoveAmmoEvent extends LevelEvent {

	public RemoveAmmoEvent(Ammo ammo) {
		this.ammo = ammo;
	}

	@Override
	public void execute(LevelEngine levelEngine) {
		levelEngine.getAllAmmo().removeAmmo(ammo);
	}
	
	private Ammo ammo;

}
