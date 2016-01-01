package gameEngines.levelEngine.simObjects.enemies;

import gameEngines.DrawMachine;
import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.simObjects.Dork;
import gameEngines.levelEngine.simObjects.world.Scaffold;

import java.awt.geom.Rectangle2D;
import java.util.Random;

import javafx.scene.image.Image;
import levelData.LevelData;

/**
 * A BunnyRabbit Enemy subclass that bounces unintelligently (at the same height) in one direction.
 *
 */
public class TallBunnyRabbit extends BunnyRabbit {
	
	public final int WIDTH = 60;
	public final int HEIGHT = 120;

	public TallBunnyRabbit(Random generator, EnemySpawner spawner, double x, double y, double vX, double jumpSpeed, boolean strict) {
		super(spawner);
		
		image = new Image("resources/images/tall bunny rabbit.png");
		
		this.x = x;
		this.y = y;
		
		if (strict) {
			JUMP_SPEED = jumpSpeed;
			this.vX = vX;
			vY = 0;
		} else {
//			JUMP_SPEED = 33 + generator.nextInt(8) + generator.nextDouble();
			JUMP_SPEED = jumpSpeed - 5 + generator.nextInt(11) + generator.nextDouble();
//			this.vX = -5 - generator.nextInt(10) + generator.nextDouble();
			this.vX = vX - 3.5 + generator.nextInt(7) + generator.nextDouble();
			vY = 0;
		}
	}

	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		super.tick(timeElapsed, gameEventQueue, level);
		
		double oldX = x, oldY = y;
		
		// Modify position
		x += vX * timeElapsed;
		y += vY * timeElapsed;
		
		// Modify velocity
		vY += level.gravity() * timeElapsed;
		
		// Check for intersection with Scaffold and modify velocity accordingly
		for (Scaffold scaf : level.scaffolds()) {			// TODO?? - should this be from the World, and not the level? They should be pointing at the same place.//for enemies too
			
			// Bottom boundary hits the Scaffold...
			if (vY < 0 && (scaf.getArea().intersects(x - WIDTH/2.2, y - 1, WIDTH/1.1, oldY - y + 1) || scaf.getArea().intersectsLine(x - WIDTH/2.2, y, x + WIDTH/2.2, y))) {
					// Not exact, but hopefully close enough: estimating the parallelogram of mvmt
				y = scaf.surface();
				vY = JUMP_SPEED;
			}
			
			if (scaf.isImpassible()) {
				// Top boundary hits the Scaffold...
				if (vY > 0 && (scaf.getArea().intersects(x - WIDTH/2.2, oldY + HEIGHT, WIDTH/1.1, y - oldY) || 
						scaf.getArea().intersectsLine(x - WIDTH/2.2, y + HEIGHT, x + WIDTH/2.2, y + HEIGHT))) {
					vY = 0;
				}
				// Dork's left or right boundary hits the Scaffold...
				if ((vX < 0 && (scaf.getArea().intersects(oldX - WIDTH/2.0, (oldY + y) / 2 + 2, x - oldX, HEIGHT - 2) || 		// left boundary
							scaf.getArea().intersectsLine(x - WIDTH/2.0, y + 2, x - WIDTH/2.0, y + HEIGHT - 2))) || 	
						(vX >  0 && (scaf.getArea().intersects(oldX + WIDTH/2.0, (oldY + y) / 2 + 2, x - oldX, HEIGHT - 2) || 	// right boundary
							scaf.getArea().intersectsLine(x + WIDTH/2.0, y + 2, x + WIDTH/2.0, y + HEIGHT - 2)) )) {
					vX = -vX;
				}
			}

		}

	}

	@Override
	public void draw(DrawMachine drawMachine) {
		drawMachine.draw(image, x, y, WIDTH, HEIGHT);
	}
	
	@Override
	public double getVY() {
		return vY;
	}
	
	@Override
	public int height() {
		return HEIGHT;
	}
	
	@Override
	public int width() {
		return WIDTH;
	}
	
	@Override
	public double x() {
		return x;
	}
	
	@Override
	public double y() {
		return y;
	}
	
	@Override
	public Rectangle2D.Double boundsRectangle() {
		return new Rectangle2D.Double(x - WIDTH/2, y, WIDTH, HEIGHT);
	}
	
	@Override
	public boolean killedDork(Dork dork) {
		return dork.getVitalBounds().intersects(x - (WIDTH/2 - 10), y + 5, WIDTH - 20, HEIGHT - 10);
	}
	
	@Override
	public boolean killedByDork(Dork dork) {
		return dork.getLethalBounds().intersects(x - (WIDTH/2 - 10), y + 5, WIDTH - 20, HEIGHT - 10);
	}
	
	private final Image image;
	
	private final double JUMP_SPEED;
	private double x;
	private double y;
	private double vX;
	private double vY;
	
}
