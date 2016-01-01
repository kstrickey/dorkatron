package gameEngines.levelEngine.simObjects.accessories.weapons;

import gameEngines.levelEngine.simObjects.accessories.Accessory;

public abstract class Weapon implements Accessory {
	
	public abstract String name();
	public abstract String description();
	public abstract String instructions();
	
	public abstract boolean isLoaded();
	public abstract void load();
	/**
	 * Precondition: is loaded.
	 */
	public abstract void fire();
}
