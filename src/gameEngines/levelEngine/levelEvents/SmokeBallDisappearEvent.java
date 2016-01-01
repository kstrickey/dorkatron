package gameEngines.levelEngine.levelEvents;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.SmokeBall;
import gameEngines.levelEngine.simObjects.SmokePuff;

public class SmokeBallDisappearEvent extends LevelEvent {

	public SmokeBallDisappearEvent(SmokePuff puff, SmokeBall ball) {
		this.puff = puff;
		this.ball = ball;
	}

	@Override
	public void execute(LevelEngine engine) {
		puff.removeSmokeBall(ball);
	}
	
	private final SmokePuff puff;
	private final SmokeBall ball;

}
