package gameEngines.levelEngine.simObjects.accessories.weapons;

import gameEngines.levelEngine.LevelEventQueue;
import javafx.scene.image.Image;



public class EmptyHandWeapon extends HandWeapon {
	
	@Override
	public String description() {
		return "No hand weapon selected";
	}
	
	@Override
	public void fire() {
		
	}
	
	@Override
	public int height() {
		return 0;
	}
	
	@Override
	public Image image() {
		return null;
	}
	
	@Override
	public boolean isLoaded() {
		return false;
	}
	
	@Override
	public void load() {
		
	}
	
	@Override
	public String name() {
		return "NONE";
	}
	
	@Override
	public int relativeX() {
		return 0;
	}
	
	@Override
	public int relativeY() {
		return 0;
	}
	
	@Override
	public void tick(LevelEventQueue levelEventQueue) {

	}
	
	@Override
	public int width() {
		return 0;
	}
	
}
