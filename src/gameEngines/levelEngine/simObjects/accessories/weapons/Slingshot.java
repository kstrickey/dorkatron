package gameEngines.levelEngine.simObjects.accessories.weapons;

import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.levelEvents.CreateAmmoEvent;
import gameEngines.levelEngine.levelEvents.PlaySingleSoundEffectEvent;
import javafx.scene.image.Image;
import levelData.SoundEffects;

public class Slingshot extends HandWeapon {
	
	public final int WIDTH = 57;
	public final int HEIGHT = 51;
	
	public Slingshot() {
		IMAGE_UNLOADED = new Image("/resources/images/slingshot unloaded.png");
		IMAGE_LOADED = new Image("/resources/images/slingshot loaded.png");
		currentImage = IMAGE_UNLOADED;
		loaded = false;
		loadNow = false;
		fireNow = false;
		
		RELATIVE_X = 10;
		RELATIVE_Y = 40;
	}
	
	@Override
	public String name() {
		return "Slingshot";
	}
	
	@Override
	public String description() {
		return "Fires a projectile in a graceful parabola, hopefully onto an unsuspecting enemy.";
	}
	
	@Override
	public boolean isLoaded() {
		return loaded;
	}
	
	@Override
	public void load() {
		loadNow = true;
	}
	
	@Override
	public void fire() {
		loaded = false;
		fireNow = true;
	}
	
	@Override
	public void tick(LevelEventQueue levelEventQueue) {
		if (loadNow) {
			loaded = true;
			loadNow = false;
			currentImage = IMAGE_LOADED;
		}
		if (fireNow) {
			levelEventQueue.addEvent(new CreateAmmoEvent(RELATIVE_X + 5, RELATIVE_Y + 37));
			levelEventQueue.addEvent(new PlaySingleSoundEffectEvent(SoundEffects.FIRE_SLINGSHOT));
			fireNow = false;
			currentImage = IMAGE_UNLOADED;
		}
	}
	
	@Override
	public Image image() {
		return currentImage;
	}
	
	@Override
	public int relativeX() {
		return RELATIVE_X;
	}
	
	@Override
	public int relativeY() {
		return RELATIVE_Y;
	}
	
	@Override
	public int width() {
		return WIDTH;
	}
	
	@Override
	public int height() {
		return HEIGHT;
	}
	
	private Image currentImage;
	private final Image IMAGE_UNLOADED;
	private final Image IMAGE_LOADED;
	
	private boolean loaded;
	private boolean loadNow;		// if loadNow == true in a tick cycle, it will load and then set loadNow back to false.
	private boolean fireNow;		// if fireNow == true in a tick cycle, it will fire and then set fireNow back to false.
	
	private final int RELATIVE_X;
	private final int RELATIVE_Y;
	
}
