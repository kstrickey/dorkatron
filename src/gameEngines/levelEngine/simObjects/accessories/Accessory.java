package gameEngines.levelEngine.simObjects.accessories;

import gameEngines.levelEngine.LevelEventQueue;
import javafx.scene.image.Image;

public interface Accessory {

	public void tick(LevelEventQueue levelEventQueue);
	
	// For drawing (in method Dork.drawAccessory(Accessory accessory))
	public Image image();
	public int relativeX();
	public int relativeY();
	public int width();
	public int height();

}
