package gameEngines.realmEngine;

public class Block {
	
	public Block() {
		blockX = 0;
		blockY = 0;
	}
	
	public Block(int blockX, int blockY) {
		this.blockX = blockX;
		this.blockY = blockY;
	}
	
	public Block(Block other) {
		blockX = other.blockX;
		blockY = other.blockY;
	}
	
	@Override
	public String toString() {
		return "blockX: " + blockX + "; blockY: " + blockY;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Block && ((Block) obj).blockX == blockX && ((Block) obj).blockY == blockY;
	}
	
	public int getBlockX() {
		return blockX;
	}
	
	public int getBlockY() {
		return blockY;
	}
	
	public void setBlockX(int blockX) {
		this.blockX = blockX;
	}
	
	public void setBlockY(int blockY) {
		this.blockY = blockY;
	}
	
	/**
	 * Checks if the block lies in range x[0, blockWidth) and y[0, blockHeight).
	 * @param blockWidth
	 * @param blockHeight
	 * @return true if in range; false if not in range
	 */
	public boolean withinBounds(int blockWidth, int blockHeight) {
		return blockX >= 0 && blockY >= 0 && blockX < blockWidth && blockY < blockHeight;
	}
	
	/**
	 * Returns the center pixel of the block given the block's coordinate.
	 * @param blockCoordinate
	 * @return blockCoordinate * 25 + 12
	 */
	public static int toPixels(int blockCoordinate) {
		return blockCoordinate * 25 + 12;
	}
	
	private int blockX;
	private int blockY;
	
}
