package gameWindow;

import gameEngines.levelEngine.LevelEngine;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OptionsPanel {
	
	public OptionsPanel(double dM, GameWindow gameWindow) {
		SoundPlayer soundPlayer = gameWindow.getSoundPlayer();
		
		mainGrid = new GridPane();
		mainGrid.setGridLinesVisible(false);				// true while testing
		mainGrid.setPrefSize(1000*dM, 625*dM);
		Insets insets = new Insets(50*dM, 100*dM, 50*dM, 100*dM);
		mainGrid.setPadding(insets);
		mainGrid.setVgap(10*dM);
		mainGrid.setHgap(15*dM);
		Color backgroundColor = new Color(Color.MOCCASIN.getRed(), Color.MOCCASIN.getGreen(), Color.MOCCASIN.getBlue(), .85);
		mainGrid.setBackground(new Background(new BackgroundFill(backgroundColor, new CornerRadii(.1, true), insets)));
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setPercentWidth(25);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(25);
		mainGrid.getColumnConstraints().addAll(column0, column1, column2);
		mainGrid.setAlignment(Pos.TOP_CENTER);
		
		int row = 0;	// the current row while adding things to the grid: use this to keep track of which row you're on, and increment after every one is added
		
		Label title = new Label("OPTIONS");
		title.setFont(Font.font("monospace", FontWeight.BOLD, 36*dM));
		GridPane.setHalignment(title, HPos.CENTER);
		mainGrid.add(title, 1, row);
		++row;
		
		Font stdFont = Font.font("monospace", 20*dM);
		Font stdFont2 = Font.font("monospace", 16*dM);
		
		row = addVolumeControls(soundPlayer, stdFont, stdFont2, row, dM);
		
		row = addGameSpeedControl(gameWindow, stdFont, row);
		
		
		
		Button closeButton = new Button("CLOSE");
		closeButton.setFont(stdFont);
		closeButton.setFocusTraversable(false);
		GridPane.setHalignment(closeButton, HPos.CENTER);
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				close();
			}
		});
		mainGrid.add(closeButton, 1, row);
		
	}

	public void start(StackPane root) {
		this.root = root;
	}
	
	public void open() {
		root.getChildren().add(mainGrid);
	}
	
	public void close() {
		root.getChildren().remove(mainGrid);
		root.getChildren().get(0).requestFocus();
	}
	
	
	/**
	 * Adds the volume controls.
	 * @param soundPlayer
	 * @param stdFont
	 * @param stdFont2
	 * @param row : int, the row number where the next control should be added
	 * @param dM
	 * @return
	 */
	private int addVolumeControls(SoundPlayer soundPlayer, Font stdFont, Font stdFont2, int row, double dM) {
		++row;			// One row blank
		
		final Image MUTED = new Image("/resources/images/muted.png");
		final Image UNMUTED = new Image("/resources/images/unmuted.png");
		
		// Total volume controls
		ImageView totalMutedIcon = new ImageView(UNMUTED);
		totalMutedIcon.setFitHeight(15*dM);
		totalMutedIcon.setFitWidth(15*dM);
		Label volume = new Label("VOLUME", totalMutedIcon);
		volume.setFont(stdFont);
		mainGrid.add(volume, 0, row);
		Slider volumeSlider = new Slider(0, 1, .5);
		volumeSlider.setShowTickMarks(true);
		volumeSlider.setShowTickLabels(false);
		volumeSlider.setMajorTickUnit(.1);
		volumeSlider.setMinorTickCount(1);
		volumeSlider.setBlockIncrement(.1);
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				soundPlayer.setTotalVolume(newValue.doubleValue());
				totalVolumeMuted.set(false);			// any action on the slider unmutes it
			}
		});
		mainGrid.add(volumeSlider, 1, row);
		Button muteAllButton = new Button("MUTE NOISE");
		muteAllButton.setFont(stdFont2);
		muteAllButton.setFocusTraversable(false);
		GridPane.setHalignment(muteAllButton, HPos.CENTER);
		totalVolumeMuted = new SimpleBooleanProperty(false);
		muteAllButton.setOnAction(new EventHandler<ActionEvent>() {		// handle if clicked-->set totalVolumeMuted Property
			@Override
			public void handle(ActionEvent e) {
				totalVolumeMuted.set(!totalVolumeMuted.get());
			}
		});
		totalVolumeMuted.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {		// handle if totalVolumeMuted Property changes
				if (newValue) {			// newly muted
					muteAllButton.setText("UNMUTE NOISE");
					totalMutedIcon.setImage(MUTED);
					soundPlayer.setTotalVolume(0);
				} else {				// freshly unmuted
					muteAllButton.setText("MUTE NOISE");
					totalMutedIcon.setImage(UNMUTED);
					soundPlayer.setTotalVolume(volumeSlider.getValue());
				}
			}
		});
		mainGrid.add(muteAllButton, 2, row);
		++row;
		
		// Background music volume controls
		ImageView musicMutedIcon = new ImageView(UNMUTED);
		musicMutedIcon.setFitHeight(15*dM);
		musicMutedIcon.setFitWidth(15*dM);
		Label music = new Label("   MUSIC", musicMutedIcon);
		music.setFont(stdFont2);
		mainGrid.add(music, 0, row);
		Slider musicSlider = new Slider(0, 1, 1);
		musicSlider.setShowTickMarks(true);
		musicSlider.setShowTickLabels(false);
		musicSlider.setMajorTickUnit(.1);
		musicSlider.setMinorTickCount(1);
		musicSlider.setBlockIncrement(.1);
		backgroundMusicVolumeMuted = new SimpleBooleanProperty(false);
		musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				soundPlayer.setBackgroundMusicVolume(newValue.doubleValue());
				backgroundMusicVolumeMuted.set(false);
			}
		});
		mainGrid.add(musicSlider, 1, row);
		Button muteMusicButton = new Button("MUTE");
		muteMusicButton.setFont(stdFont2);
		muteMusicButton.setFocusTraversable(false);
		GridPane.setHalignment(muteMusicButton, HPos.CENTER);
		muteMusicButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				backgroundMusicVolumeMuted.set(!backgroundMusicVolumeMuted.get());
			}
		});
		backgroundMusicVolumeMuted.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					muteMusicButton.setText("UNMUTE");
					musicMutedIcon.setImage(MUTED);
					soundPlayer.setBackgroundMusicVolume(0);
				} else {
					muteMusicButton.setText("MUTE");
					musicMutedIcon.setImage(UNMUTED);
					soundPlayer.setBackgroundMusicVolume(musicSlider.getValue());
				}
			}
		});
		mainGrid.add(muteMusicButton, 2, row);
		++row;
		
		// Sound effect volume controls
		ImageView effectsMutedIcon = new ImageView(UNMUTED);
		effectsMutedIcon.setFitHeight(15*dM);
		effectsMutedIcon.setFitWidth(15*dM);
		Label soundEffects = new Label("   SOUND EFFECTS", effectsMutedIcon);
		soundEffects.setFont(stdFont2);
		mainGrid.add(soundEffects, 0, row);
		Slider soundEffectSlider = new Slider(0, 1, 1);
		soundEffectSlider.setShowTickMarks(true);
		soundEffectSlider.setShowTickLabels(false);
		soundEffectSlider.setMajorTickUnit(.1);
		soundEffectSlider.setMinorTickCount(1);
		soundEffectSlider.setBlockIncrement(.1);
		soundEffectsVolumeMuted = new SimpleBooleanProperty(false);
		soundEffectSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				soundPlayer.setSoundEffectVolume(newValue.doubleValue());
				soundEffectsVolumeMuted.set(false);
			}
		});
		mainGrid.add(soundEffectSlider, 1, row);
		Button muteSoundEffectsButton = new Button("MUTE");
		muteSoundEffectsButton.setFont(stdFont2);
		muteSoundEffectsButton.setFocusTraversable(false);
		GridPane.setHalignment(muteSoundEffectsButton, HPos.CENTER);
		muteSoundEffectsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				soundEffectsVolumeMuted.set(!soundEffectsVolumeMuted.get());
			}
		});
		soundEffectsVolumeMuted.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					muteSoundEffectsButton.setText("UNMUTE");
					effectsMutedIcon.setImage(MUTED);
					soundPlayer.setSoundEffectVolume(0);
				} else {
					muteSoundEffectsButton.setText("MUTE");
					effectsMutedIcon.setImage(UNMUTED);
					soundPlayer.setSoundEffectVolume(soundEffectSlider.getValue());
				}
			}
		});
		mainGrid.add(muteSoundEffectsButton, 2, row);
		++row;
		
		return row;
	}
	

	private int addGameSpeedControl(GameWindow gameWindow, Font stdFont, int row) {
		Label gameSpeed = new Label("GAME SPEED");
		gameSpeed.setFont(stdFont);
		mainGrid.add(gameSpeed, 0, row);
		Slider gameSpeedSlider = new Slider(.01, .36, LevelEngine.DEFAULT_TIME_PER_TICK);
		gameSpeedSlider.setShowTickMarks(true);
		gameSpeedSlider.setShowTickLabels(false);
		gameSpeedSlider.setMajorTickUnit(.01);
		gameSpeedSlider.setMinorTickCount(1);
		gameSpeedSlider.setBlockIncrement(.01);
		gameSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				gameWindow.setTimePerTick(newValue.doubleValue());
			}
		});
		mainGrid.add(gameSpeedSlider, 1, row);
		++row;
		return row;
	}

	private StackPane root;
	
//	private final SoundPlayer soundPlayer;
	
	private GridPane mainGrid;
	
	
	private BooleanProperty totalVolumeMuted;
	private BooleanProperty backgroundMusicVolumeMuted;
	private BooleanProperty soundEffectsVolumeMuted;

}
