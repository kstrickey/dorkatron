package gameEngines.realmEngine;

import gameEngines.DrawMachine;
import javafx.scene.image.Image;

public class Road implements Mappable {
	
	public Road(int blockX, int blockY, SquareSide side1, SquareSide side2) {
		// Define location
		this.blockX = blockX;
		this.blockY = blockY;
		
		// Define sides (direction)
		if (side1.compareTo(side2) < 0) {			// want side1 to be before side2
			this.side1 = side1;
			this.side2 = side2;
		} else {
			this.side1 = side2;
			this.side2 = side1;
		} 
		
		// Define image
		image = new Image("/resources/images/roads/road " + this.side1.getName() + "-" + this.side2.getName() + ".png");		// how good is this!
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		drawMachine.draw(image, blockX * 25, blockY * 25, 25, 25);
	}
	
	@Override
	public String toString() {		//TODO for testing only
		return "side1: " + side1.getName() + "  side2: " + side2.getName();
	}
	
	@Override
	public int blockX() {
		return blockX;
	}
	
	@Override
	public int blockY() {
		return blockY;
	}
	
	public boolean containsSide(SquareSide side) {
		return side1 == side || side2 == side;
	}
	
	/**
	 * Returns the side of the Road NOT given as the parameter.
	 * If the parameter passed in is not a side at all, returns null.
	 * @param side
	 * @return
	 */
	public SquareSide otherSide(SquareSide side) {
		if (side == side1) {
			return side2;
		}
		if (side == side2) {
			return side1;
		}
		return null;
	}
	
	private final int blockX;
	private final int blockY;
	private final SquareSide side1;
	private final SquareSide side2;
	
	private final Image image;
	
}
