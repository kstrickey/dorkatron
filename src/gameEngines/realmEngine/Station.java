package gameEngines.realmEngine;

import gameEngines.DrawMachine;
import javafx.scene.image.Image;
import levelData.LevelData;

public class Station implements Mappable {
	
	public Station(LevelData level, int blockX, int blockY) {
		this.level = level;
		this.blockX = blockX;
		this.blockY = blockY;
		
		if (level == null) {
			image = new Image("/resources/images/roads/station empty.png");
		} else {
			image = new Image("/resources/images/roads/station full.png");
		}
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		drawMachine.draw(image, blockX * 25, blockY * 25, 25, 25);
	}
	
	@Override
	public int blockX() {
		return blockX;
	}
	
	@Override
	public int blockY() {
		return blockY;
	}
	
	public LevelData getLevel() {
		return level;
	}
	
	private final LevelData level;		// can be null if no level (e.g. for a crossroads)
	
	private final int blockX;			// each block represents 25 pixel-dots. The screen is 40x25 blocks.
	private final int blockY;
	
	private final Image image;
	
}
