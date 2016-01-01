package gameEngines.realmEngine.realmEvents;

import gameEngines.realmEngine.Block;
import gameEngines.realmEngine.RealmEngine;
import gameEngines.realmEngine.Road;
import gameEngines.realmEngine.SquareSide;

public class AttemptToKeepFollowingRoadEvent extends RealmEvent {
	
	/**
	 * 
	 * @param blockJustReached : the Block, either Station or Road, that the DorkMarker has just reached
	 * @param entrySide : the SquareSide of the blockJustReached from which the DorkMarker entered
	 */
	public AttemptToKeepFollowingRoadEvent(Block blockJustReached, SquareSide entrySide) {
		this.blockJustReached = blockJustReached;
		this.entrySide = entrySide;
	}
	
	@Override
	public void execute(RealmEngine realmEngine) {
		if (realmEngine.containsRoad(blockJustReached)) {
			Road road = (Road) realmEngine.retrieveMappable(blockJustReached);
			SquareSide newDirection = road.otherSide(entrySide);
			realmEngine.requestMoveToward(newDirection);
		}
	}
	
	private final Block blockJustReached;
	private final SquareSide entrySide;
	
}
