package gameEngines.levelEngine.simObjects.ammo;

import gameEngines.levelEngine.simObjects.Dork;

public abstract class AmmoFactory {
	
	public static final int INFINITY = -1;
	
	public abstract String ammoType();
	public abstract Ammo createOne(Dork dork, int relativeX, int relativeY);
	
	public abstract int stockRemaining();
	
}
