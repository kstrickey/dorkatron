package gameEngines.levelEngine.simObjects.accessories.weapons;

import java.util.ArrayList;

public class HandWeaponArsenal {
	
	public HandWeaponArsenal() {
		handWeapons = new ArrayList<HandWeapon>();
		handWeapons.add(new EmptyHandWeapon());
		handWeapons.add(new Slingshot());
		
		currentHandWeaponIndex = 0;
	}
	
	public HandWeapon currentHandWeapon() {
		return handWeapons.get(currentHandWeaponIndex);
	}
	
	public void changeHandWeapon() {
		++currentHandWeaponIndex;
		if (currentHandWeaponIndex == handWeapons.size()) {
			currentHandWeaponIndex = 0;
		}
	}
	
	private ArrayList<HandWeapon> handWeapons;
	private int currentHandWeaponIndex;
	
}
