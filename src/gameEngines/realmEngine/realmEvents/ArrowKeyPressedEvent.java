package gameEngines.realmEngine.realmEvents;

import gameEngines.realmEngine.RealmEngine;
import gameEngines.realmEngine.SquareSide;

public class ArrowKeyPressedEvent extends RealmEvent {

	public ArrowKeyPressedEvent(SquareSide direction) {
		this.direction = direction;
	}
	
	@Override
	public void execute(RealmEngine realmEngine) {
		realmEngine.requestMoveToward(direction);
	}
	
	private final SquareSide direction;

}
