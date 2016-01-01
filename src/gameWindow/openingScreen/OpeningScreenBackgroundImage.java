package gameWindow.openingScreen;

import java.util.Random;

import javafx.scene.image.ImageView;

public class OpeningScreenBackgroundImage {
	
	public OpeningScreenBackgroundImage(ImageView imageView, double dM) {
		this.dM = dM;
		this.imageView = imageView;
		
		imageView.setFitWidth(generator.nextDouble() * 100 + 50);
		imageView.setPreserveRatio(true);
		
		imageView.setX(generator.nextDouble() * 1000 * dM);
		imageView.setY(generator.nextDouble() * 625 * dM);
		imageView.setOpacity(generator.nextDouble() * .75 + .25);
		
		rotationalVelocity = generator.nextDouble() * 90 - 45;			// range: (-45, 45)
		linearVelocity = generator.nextDouble() * 100 * dM;
		direction = generator.nextDouble() * 360;
		opacityChange = (generator.nextBoolean() ? 1 : -1) * generator.nextDouble() / 10 + .1;
	}
	
	public void tick(double secondsElapsed) {
//		try{
		imageView.setX(imageView.getX() + linearVelocity * Math.cos(Math.toRadians(direction)) * dM * secondsElapsed);
//		}catch(Throwable x) {
//			x.printStackTrace();
//		}
		imageView.setY(imageView.getY() + linearVelocity * Math.sin(Math.toRadians(direction)) * dM * secondsElapsed);
		imageView.setRotate(imageView.getRotate() + rotationalVelocity * secondsElapsed);
		imageView.setOpacity(imageView.getOpacity() + opacityChange * secondsElapsed);
		
		// Bounce off edges
		if (imageView.getX() <= 0 || imageView.getX() >= 1000 - imageView.getFitWidth()) {
			direction = 180 - direction;
		}
		while (direction < 0) {
			direction += 360;
		}
		if (imageView.getY() <= imageView.getFitHeight() || imageView.getY() >= 625) {
			direction = 360 - direction;
		}
		
		// Fluctuate opacity
		if (imageView.getOpacity() <= 0.25 || imageView.getOpacity() >= 1.0) {
			opacityChange = -opacityChange;
		}
	}
	
	private double dM;
	
	private final ImageView imageView;
	private double rotationalVelocity;			// units: degrees/second
	private double linearVelocity;				// units: pixels/second
	private double direction;					// units: degrees
	private double opacityChange;				// units: percent/second
	
	private static final Random generator = new Random();
	
}
