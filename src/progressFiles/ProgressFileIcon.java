package progressFiles;

import javafx.scene.image.Image;

public class ProgressFileIcon {
	
	public ProgressFileIcon(String name, String imagePath) {
		this.name = name;
		image = new Image(imagePath);
	}
	
	private String name;
	private Image image;
	
}
