package gameWindow;

import java.awt.Dimension;
import java.awt.Toolkit;

import gameWindow.openingScreen.OpeningScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameWindow extends Application {
	
	public GameWindow() {
		// First set window size
		Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());						// may not be 8:5 ratio.
		if (screenSize.width >= 1000 && screenSize.height >= 625) {
			dM = 1;
		} else {
			// Find largest 8:5 ratio
			if (8 * screenSize.height > 5 * screenSize.width) {						// width is limiting factor
				dM = screenSize.width / 1000.0;
			} else {																// height is limiting factor
				dM = screenSize.height / 625.0;
			}
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Dorkatron 2");
		StackPane root = new StackPane();
		root.setPrefSize(1000 * dM, 625 * dM);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		soundPlayer = new SoundPlayer();
		
		openingScreen = new OpeningScreen(dM, soundPlayer);
		openingScreen.start(root);
		
		optionsController = new OptionsController(dM, this);
		optionsController.start(root);
	}
	
	public SoundPlayer getSoundPlayer() {
		return soundPlayer;
	}
	
	/**
	 * Calls openingScreen.setTimePerTick and starts a cascade of setting time per tick until it reaches the LevelEngine.
	 * @param timePerTick : double
	 */
	public void setTimePerTick(double timePerTick) {
		openingScreen.setTimePerTick(timePerTick);
	}
	
	private OptionsController optionsController;
	
	private OpeningScreen openingScreen;
	
	private double dM;				// drawing multiplier
	
	private SoundPlayer soundPlayer;
	
}
