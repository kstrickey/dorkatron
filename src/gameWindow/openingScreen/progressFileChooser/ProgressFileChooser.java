package gameWindow.openingScreen.progressFileChooser;

import javafx.scene.layout.StackPane;
import gameWindow.SoundPlayer;
import gameWindow.openingScreen.progressFileChooser.gameManager.GameManager;

public class ProgressFileChooser {
	
	public ProgressFileChooser(double dM, SoundPlayer soundPlayer, double timePerTick) {
		this.dM = dM;
		this.soundPlayer = soundPlayer;
		this.timePerTick = timePerTick;
	}
	
	public void start(StackPane root) {
		gameManager = new GameManager(dM, soundPlayer, timePerTick);
		gameManager.start(root);
	}
	
	/**
	 * Sets the field timePerTick as well as calling gameManager.setTimePerTick.
	 * @param timePerTick
	 */
	public void setTimePerTick(double timePerTick) {
		this.timePerTick = timePerTick;
		if (gameManager != null) {
			gameManager.setTimePerTick(timePerTick);
		}
	}
	
	private final double dM;
	private final SoundPlayer soundPlayer;
	private double timePerTick;
	
	private GameManager gameManager;
	
}
