package gameEngines.levelEngine.simObjects.accessories.weapons;

public abstract class HandWeapon extends Weapon {
	
	@Override
	public String instructions() {
		return "Press J to fire the hand weapon.\nPress H to reload the hand weapon.";
	}
	
}
