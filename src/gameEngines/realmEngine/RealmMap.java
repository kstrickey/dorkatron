package gameEngines.realmEngine;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import levelData.LevelData;

/**
 * Organizes and maintains the Station and Road objects in a realm in a grid of blocks.
 * Handles connections requests to move, etc.
 *
 */
public class RealmMap implements Drawable {
	
	public RealmMap(int blockWidth, int blockHeight) {
		layout = new Mappable[blockHeight][blockWidth];
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		for (int y = 0; y < layout.length; ++y) {
			for (int x = 0; x < layout[y].length; ++x) {
				if (layout[y][x] != null) {
					layout[y][x].draw(drawMachine);
				}
			}
		}
		dorkMarker.draw(drawMachine);
	}
	
	/**
	 * Returns the current Station's level ONLY if dorkMarker is not moving and if the Station contains a level (otherwise returns null).
	 * @return
	 */
	public LevelData currentLevel() {
		if (dorkMarker.doneMoving()) {
			Mappable currentMappable = layout[dorkMarker.getCurrentBlock().getBlockY()][dorkMarker.getCurrentBlock().getBlockX()];
			if (currentMappable instanceof Station) {
				return ((Station) currentMappable).getLevel();
			}
		}
		return null;
	}
	
	public void tick(int millisElapsed, RealmEventQueue realmEventQueue) {
		dorkMarker.tick(millisElapsed, realmEventQueue);
	}
	
	/**
	 * Only used in constructing the RealmMap, not in gameplay.
	 * @param station
	 */
	public void addStation(Station station) {
		layout[station.blockY()][station.blockX()] = station;
		
		if (dorkMarker == null) {					// if it hasn't been set yet (aka it is the first Station)
			Block firstBlock = new Block();
			firstBlock.setBlockX(station.blockX());
			firstBlock.setBlockY(station.blockY());
			dorkMarker = new DorkMarker(firstBlock);	// initialize dorkMarker
		}
	}
	
	/**
	 * Only used in constructing the RealmMap, not in gameplay.
	 * @param road
	 */
	public void addRoad(Road road) {
		layout[road.blockY()][road.blockX()] = road;
	}
	
	/**
	 * Moves dorkMarker in the specified direction from the currentBlock (if legal).
	 * @param direction
	 */
	public void moveMarker(SquareSide direction) {
		if (dorkMarker.doneMoving()) {
			Block desiredBlock = blockInDirection(direction);
			if (canMove(direction, desiredBlock)) {
				dorkMarker.setDestinationBlock(desiredBlock);
			}
		}
	}
	
	/**
	 * Returns true if the passed block contains a Road object in its location in layout.
	 * @param block
	 * @return
	 * @precondition block's coordinates are within the layout boundaries
	 */
	public boolean containsRoad(Block block) {
		return layout[block.getBlockY()][block.getBlockX()] != null && layout[block.getBlockY()][block.getBlockX()] instanceof Road;
	}
	
	/**
	 * 
	 * @param block
	 * @return the Mappable object contained in this block location
	 */
	public Mappable retrieveMappable(Block block) {
		return layout[block.getBlockY()][block.getBlockX()];
	}
	
	
	/**
	 * Determines whether or not there is an applicable Road in the desired block.
	 * @param directionMoving : The side of the current block to which the desired block connects
	 * @param desiredBlock : The Block object specifying the location of the desired block
	 * @return true if there is a Road in the desiredBlock that has a side connecting to the currentBlock's directionMoving face; false if there is not
	 * @precondition desiredBlock is directly adjacent to currentBlock's directionMoving SquareSide
	 */
	private boolean canMove(SquareSide directionMoving, Block desiredBlock) {
		// Check that desiredBlock is in bounds of Realm
		if (!desiredBlock.withinBounds(layout[0].length, layout.length)) {
			return false;
		}
		
		// Check for presence of Road in desiredBlock
		if (layout[desiredBlock.getBlockY()][desiredBlock.getBlockX()] == null) {
			return false;
		}
		
		// Ensure that the Road connects to the correct side (if it is a Road)
		if (layout[desiredBlock.getBlockY()][desiredBlock.getBlockX()] instanceof Road) {
			if (((Road) layout[desiredBlock.getBlockY()][desiredBlock.getBlockX()]).containsSide(directionMoving.getOppositeSide())) {
				return true;
			}
		} else if (layout[desiredBlock.getBlockY()][desiredBlock.getBlockX()] instanceof Station) {
			return true;
		}
		
		return false;		// could be unreachable code
	}
	
	/**
	 * Returns a new Block in the specified Direction from the currentBlock.
	 * @param direction
	 * @return
	 */
	private Block blockInDirection(SquareSide direction) {
		Block desiredBlock = new Block(dorkMarker.getCurrentBlock());
		switch (direction) {
			case RIGHT:
				desiredBlock.setBlockX(desiredBlock.getBlockX() + 1);
				break;
			case BOTTOM:
				desiredBlock.setBlockY(desiredBlock.getBlockY() - 1);
				break;
			case LEFT:
				desiredBlock.setBlockX(desiredBlock.getBlockX() - 1);
				break;
			case TOP:
				desiredBlock.setBlockY(desiredBlock.getBlockY() + 1);
				break;
		}
		return desiredBlock;
	}
	
	private final Mappable[][] layout;
	
	private DorkMarker dorkMarker;
//	private LevelData currentLevel;
	
}
