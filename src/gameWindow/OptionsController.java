package gameWindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class OptionsController {
	
	public OptionsController(double dM, GameWindow gameWindow) {
		
		optionsButton = new Button();
		optionsButton.setFont(Font.font("monospace", 12));
		optionsButton.setText("OPTIONS");
		StackPane.setAlignment(optionsButton, Pos.TOP_RIGHT);
		StackPane.setMargin(optionsButton, new Insets(5));
		optionsButton.setOpacity(.25);
		optionsButton.setFocusTraversable(false);
		optionsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				optionsButton.setOpacity(1);
			}
		});
		optionsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				optionsButton.setOpacity(.25);
			}
		});
		optionsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				optionsPanel.open();
			}
		});
		
		optionsPanel = new OptionsPanel(dM, gameWindow);
		
	}
	
	public void start(StackPane root) {
		root.getChildren().add(optionsButton);
		
		optionsPanel.start(root);
		
		
	}
	
	private Button optionsButton;
	
	private OptionsPanel optionsPanel;
	
}
