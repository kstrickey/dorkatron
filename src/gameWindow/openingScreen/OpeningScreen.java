package gameWindow.openingScreen;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import threading.ThreadFactories;
import gameEngines.levelEngine.LevelEngine;
import gameWindow.SoundPlayer;
import gameWindow.openingScreen.progressFileChooser.ProgressFileChooser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * In charge of the first screen that the user sees when opens.
 *
 */
public class OpeningScreen {
	
	public OpeningScreen(double dM, SoundPlayer soundPlayer) {
		this.dM = dM;
		this.soundPlayer = soundPlayer;
		progressFileChooser = null;
		timePerTick = LevelEngine.DEFAULT_TIME_PER_TICK;
	}
	
	/**
	 * Calls progressFileChooser.setTimePerTick as well as setting its own field.
	 * @param timePerTick : double
	 */
	public void setTimePerTick(double timePerTick) {
		this.timePerTick = timePerTick;
		if (progressFileChooser != null) {
			progressFileChooser.setTimePerTick(timePerTick);
		}
	}
	
	// TODO Level Editor
	
	public void start(StackPane root) {
		backgroundPane = new Pane();
		backgroundPane.setPrefSize(1000*dM, 625*dM);
		
		backgroundImages = new ArrayList<OpeningScreenBackgroundImage>();
		String[] imageLocations = {
				"/resources/images/tall bunny rabbit.png",
				"/resources/images/le dork.png",
				"/resources/images/slingshot loaded.png",
				"/resources/images/dorkohat.png",
				"/resources/images/enhanced dorkohat.png",
		};
		for (String str : imageLocations) {
			ImageView iv = new ImageView(new Image(str));
			backgroundPane.getChildren().add(iv);
			backgroundImages.add(new OpeningScreenBackgroundImage(iv, dM));
		}
		bkgdScheduler = Executors.newSingleThreadScheduledExecutor(ThreadFactories.daemonThreadFactory);
		bkgdScheduler.scheduleAtFixedRate(new BackgroundTicker(), 0, 10, TimeUnit.MILLISECONDS);
		
		root.getChildren().add(backgroundPane);
		
		
		mainGrid = new GridPane();
		mainGrid.setPrefSize(1000*dM, 625*dM);
		mainGrid.setAlignment(Pos.CENTER);
		// 1 row, 3 columns
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setPercentWidth(25);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(25);
		mainGrid.getColumnConstraints().addAll(column0, column1, column2);
		
		VBox middleColumn = new VBox();
		middleColumn.setPadding(new Insets(100*dM, 0, 50*dM, 0));
		middleColumn.setSpacing(0);
		middleColumn.setFillWidth(true);
		
		Label title = new Label("DORKATRON 2");
		title.setFont(Font.font("monospace", FontWeight.BOLD, 75));
		
		VBox buttonList = new VBox();
		buttonList.setPadding(new Insets(50*dM, 75*dM, 50*dM, 75*dM));
		buttonList.setSpacing(25*dM);
		
		Font buttonFont = Font.font("monospace", FontWeight.BOLD, 24);
		ArrayList<Button> buttons = new ArrayList<Button>();
		Button playButton = new Button("PLAY");
		buttons.add(playButton);
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				play(root);
			}
		});
		Button levelEditorButton = new Button("LEVEL EDITOR");
		buttons.add(levelEditorButton);
		Button quitButton = new Button("QUIT");
		buttons.add(quitButton);
		for (Button b : buttons) {
			b.setFont(buttonFont);
			b.setPrefWidth(350*dM);
			b.setOpacity(50);
			b.setFocusTraversable(false);
		}
		buttonList.getChildren().addAll(buttons);
		
		middleColumn.getChildren().addAll(title, buttonList);
		
		mainGrid.add(middleColumn, 1, 0);
		
		root.getChildren().add(mainGrid);
	}
	
	
	private class BackgroundTicker implements Runnable {
		@Override
		public void run() {
			Platform.runLater(new SingleTicker());
		}
	}
	
	private class SingleTicker implements Runnable {
		@Override
		public void run() {
			for (OpeningScreenBackgroundImage osbi : backgroundImages) {
				osbi.tick(.01);
			}
		}
	}
	
	private void play(StackPane root) {
		bkgdScheduler.shutdown();
		root.getChildren().removeAll(backgroundPane, mainGrid);
		progressFileChooser = new ProgressFileChooser(dM, soundPlayer, timePerTick);
		progressFileChooser.start(root);
	}
	
	private ProgressFileChooser progressFileChooser;
	
	private Pane backgroundPane;
	private GridPane mainGrid;
	
	private double dM;
	
	private double timePerTick;
	
	private ArrayList<OpeningScreenBackgroundImage> backgroundImages;
	
	private ScheduledExecutorService bkgdScheduler;
	
	private SoundPlayer soundPlayer;
	
}
