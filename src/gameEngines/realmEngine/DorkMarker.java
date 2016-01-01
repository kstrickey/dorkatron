package gameEngines.realmEngine;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.realmEngine.realmEvents.AttemptToKeepFollowingRoadEvent;
import javafx.scene.image.Image;

public class DorkMarker implements Drawable {

	public DorkMarker(Block initialBlock) {
		image = new Image("/resources/images/le dork.png", WIDTH, HEIGHT, false, false);
		currentBlock = initialBlock;
		destinationBlock = initialBlock;
		x = Block.toPixels(initialBlock.getBlockX());
		y = Block.toPixels(initialBlock.getBlockY());
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		drawMachine.draw(image, x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * Intended to make the DorkMarker start to move.
	 * @param destinationBlock
	 * @precondition destinationBlock is legally adjacent to currentBlock
	 */
	public void setDestinationBlock(Block destinationBlock) {			
		this.destinationBlock = destinationBlock;
	}
	
	
	public Block getCurrentBlock() {
		return currentBlock;
	}
	
	public void tick(int millisElapsed, RealmEventQueue realmEventQueue) {
		if (!currentBlock.equals(destinationBlock)) {			// if not done moving
			x += (double) millisElapsed / MILLIS_TO_CROSS_BLOCK * (Block.toPixels(destinationBlock.getBlockX()) - Block.toPixels(currentBlock.getBlockX()));
			y += (double) millisElapsed / MILLIS_TO_CROSS_BLOCK * (Block.toPixels(destinationBlock.getBlockY()) - Block.toPixels(currentBlock.getBlockY()));
			
			// Check if reached destination
			if (Math.abs(x - Block.toPixels(destinationBlock.getBlockX())) < .5 && Math.abs(y - Block.toPixels(destinationBlock.getBlockY())) < .5) {
				x = Block.toPixels(destinationBlock.getBlockX());
				y = Block.toPixels(destinationBlock.getBlockY());
				
				SquareSide entrySide = null;
				if (destinationBlock.getBlockX() > currentBlock.getBlockX()) {
					entrySide = SquareSide.LEFT;
				} else if (destinationBlock.getBlockX() < currentBlock.getBlockX()) {
					entrySide = SquareSide.RIGHT;
				} else if (destinationBlock.getBlockY() > currentBlock.getBlockY()) {
					entrySide = SquareSide.BOTTOM;
				} else if (destinationBlock.getBlockY() < currentBlock.getBlockY()) {
					entrySide = SquareSide.TOP;
				}
				currentBlock = destinationBlock;
				
				realmEventQueue.addEvent(new AttemptToKeepFollowingRoadEvent(currentBlock, entrySide));
			}
		}
	}
	
	public boolean doneMoving() {
		return currentBlock.equals(destinationBlock);
	}
	
	private Image image;
	
	private final int WIDTH = 20;
	private final int HEIGHT = 40;
	
	private final int MILLIS_TO_CROSS_BLOCK = 1000;
	
	private double x, y;
	private Block currentBlock;
	private Block destinationBlock;
	
}
