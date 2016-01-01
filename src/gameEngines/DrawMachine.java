package gameEngines;

import java.awt.geom.Rectangle2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DrawMachine {
	
	// For each entry, takes an image, x, y, height, and width. coordinates in Level system.
	// In draw method, takes GraphicsContext, dM, and scrollWindow.
	
	public DrawMachine(GraphicsContext gc, double dM, Rectangle2D.Double scrollWindow) {
		this.gc = gc;
		this.dM = dM;
		this.scrollWindow = scrollWindow;
	}
	
	public void draw(Image image, double x, double y, double width, double height) {
		if (width > 0) {
			if (x + width/2.0 >= scrollWindow.x && x - width/2.0 <= scrollWindow.x + scrollWindow.width && 
					y + height >= scrollWindow.y && y <= scrollWindow.y + scrollWindow.height) {
				gc.drawImage(image, (x - width/2.0 - scrollWindow.x) * dM, (scrollWindow.height - (y - scrollWindow.y) - height) * dM, width * dM, height * dM);
			}
		} else {
			if (x - width/2.0 >= scrollWindow.x && x + width/2.0 <= scrollWindow.x + scrollWindow.width && 
					y + height >= scrollWindow.y && y <= scrollWindow.y + scrollWindow.height) {
				gc.drawImage(image, (x - width/2.0 - scrollWindow.x) * dM, (scrollWindow.height - (y - scrollWindow.y) - height) * dM, width * dM, height * dM);
			}
		}
	}
	
	public void drawBackground(Image levelImage) {
		// TODO there is something wrong with this, I'm sure of it. with the scrollWindow.y... is there??
		gc.drawImage(levelImage, scrollWindow.x, scrollWindow.y, scrollWindow.width, scrollWindow.height, 0, 0, scrollWindow.width * dM, scrollWindow.height * dM);
	}
	
	private final GraphicsContext gc;
	private final double dM;
	private final Rectangle2D.Double scrollWindow;
	
}
