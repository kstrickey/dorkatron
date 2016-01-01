package gameEngines.realmEngine;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import javafx.scene.image.Image;

/**
 * Similar to the World class in a Level, but for a Realm. Includes background image, etc.
 *
 */
public class RealmBackgroundStuff implements Drawable {
	
	public RealmBackgroundStuff(Image backgroundImage) {			// TODO add random animated things and stuff through arraylist
		this.backgroundImage = backgroundImage;
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		drawMachine.drawBackground(backgroundImage);
	}
	
	private Image backgroundImage;
	
}
