package gameEngines.levelEngine.simObjects.ammo;

import gameEngines.levelEngine.simObjects.Dork;

public class EmptyAmmoFactory extends AmmoFactory {

	public EmptyAmmoFactory() {
		
	}
	
	@Override
	public String ammoType() {
		return "NONE";
	}

	@Override
	public Ammo createOne(Dork dork, int relativeX, int relativeY) {
		return null;		// Creates nothing
	}
	
	@Override
	public int stockRemaining() {
		return 0;
	}

}
