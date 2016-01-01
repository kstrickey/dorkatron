package gameEngines.levelEngine.simObjects.world;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;

import java.util.ArrayList;

import javafx.scene.image.Image;
import levelData.LevelData;

public class World implements Tickable, Drawable {
	
	public World(LevelData level) {		// TODO Reduce parameters of World constructor: only need image, scaffolds
		backgroundImage = level.backgroundImage();
		
		scaffolds = new ArrayList<Scaffold>();
		for (Scaffold scaf : level.scaffolds()) {		// copy Scaffold objects
			scaffolds.add(new Scaffold(scaf));
		}
	}
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		
		for (Scaffold scaffold : scaffolds) {
			scaffold.tick(timeElapsed, gameEventQueue, level);
		}
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
//		gc.drawImage(backgroundImage, scrollWindow.x, scrollWindow.y, scrollWindow.width, scrollWindow.height, 0, 0, scrollWindow.width * dM, scrollWindow.height * dM);
		drawMachine.drawBackground(backgroundImage);
		
		for (Scaffold scaffold : scaffolds) {
			scaffold.draw(drawMachine);
		}
	}
	

	
	
	private Image backgroundImage;
	
	private ArrayList<Scaffold> scaffolds;

}
