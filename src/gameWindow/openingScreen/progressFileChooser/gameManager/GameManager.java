package gameWindow.openingScreen.progressFileChooser.gameManager;

import gameEngines.realmEngine.RealmEngine;
import gameEngines.realmEngine.StandardRealm;
import gameWindow.SoundPlayer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

/**
 * Manages the different Engines for the game.
 *
 */
public class GameManager {

	public GameManager(double dM, SoundPlayer soundPlayer, double timePerTick) {
		this.dM = dM;
		this.soundPlayer = soundPlayer;
		this.timePerTick = timePerTick;
	}
	
	public void setTimePerTick(double timePerTick) {
		this.timePerTick = timePerTick;
		currentRealmEngine.setTimePerTick(timePerTick);
	}
	
	/**
	 * Called by the top-level GameWindow to start the thread that will draw the graphics and such.
	 * Does not clear root: simply adds children.
	 */
	public void start(StackPane root) {
		Canvas canvas = new Canvas(1000 * dM, 625 * dM);
		root.getChildren().add(0, canvas);						// Adds at 0th index so that options button is still on top of Canvas
		gc = canvas.getGraphicsContext2D();
		
		currentRealmEngine = new RealmEngine(dM, soundPlayer, new StandardRealm("src/levelData/files/waterdownShip/_REALM_DATA.xml"), timePerTick);
		currentRealmEngine.start(gc);
		canvas.requestFocus();
		
		KeyboardInputEventHandler handler = new KeyboardInputEventHandler(currentRealmEngine);			//TODO IF CHANGE REALMS OOPS
		gc.getCanvas().addEventHandler(KeyEvent.KEY_PRESSED, handler);
		gc.getCanvas().addEventHandler(KeyEvent.KEY_RELEASED, handler);
	}
	
	
	private RealmEngine currentRealmEngine;
	
	private double timePerTick;
	
	private GraphicsContext gc;
	private double dM;
	private SoundPlayer soundPlayer;
	
}
