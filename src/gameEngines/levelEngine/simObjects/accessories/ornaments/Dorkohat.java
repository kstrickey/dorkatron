package gameEngines.levelEngine.simObjects.accessories.ornaments;

import gameEngines.levelEngine.LevelEventQueue;
import javafx.scene.image.Image;

public class Dorkohat extends Hat {
	
	public Dorkohat() {
		image = new Image("/resources/images/dorkohat.png");
		relativeX = 4;
		relativeY = 150;
		width = 90;
		height = 40;
	}
	
	@Override
	public Image image() {
		return image;
	}
	
	@Override
	public int relativeX() {
		return relativeX;
	}
	
	@Override
	public int relativeY() {
		return relativeY;
	}
	
	@Override
	public int width() {
		return width;
	}
	
	@Override
	public int height() {
		return height;
	}
	
	@Override
	public void tick(LevelEventQueue levelEventQueue) {
		
	}
	
	private final Image image;
	private final int relativeX;
	private final int relativeY;
	private final int width;
	private final int height;
}
