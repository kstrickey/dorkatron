package gameEngines.levelEngine.simObjects.ammo;

import gameEngines.DrawMachine;
import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.levelEvents.StandardEnemyHitByAmmoEvent;
import gameEngines.levelEngine.simObjects.enemies.AllEnemies;
import gameEngines.levelEngine.simObjects.enemies.Enemy;
import javafx.scene.image.Image;
import levelData.LevelData;

/**
 * Simple, but sturdy. Will go through platforms and such.
 * @author Kevin
 *
 */
public class RabbitPellet extends Ammo {
	
	public final int WIDTH = 15;
	public final int HEIGHT = 10;
	
	public RabbitPellet(double x, double y, double vX, double vY) {
		super(x, y, vX, vY);
		
		image = new Image("/resources/images/rabbit pellet.png");
		
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
	}
	
	@Override
	public String name() {
		return "Rabbit Pellet";
	}
	
	@Override
	public String description() {
		return "Your standard, run-of-the-mill rabbit pellet. Nothing special, but surprisingly sturdy.";
	}
	
	@Override
	public boolean hitGround(LevelData level) {
		return y < -HEIGHT;
	}
	
	@Override
	public boolean killedEnemy(AllEnemies allEnemies, LevelEventQueue levelEventQueue) {
		boolean killedEnemy = false;
		for (Enemy enemy : allEnemies.getEnemies()) {
			if (enemy.boundsRectangle().intersects(x - WIDTH/2, y, WIDTH, HEIGHT)) {
				levelEventQueue.addEvent(new StandardEnemyHitByAmmoEvent(enemy));
				killedEnemy = true;
			}
		}
		return killedEnemy;
	}
	
	@Override
	public void tick(double timeElapsed, LevelEventQueue gameEventQueue, LevelData level) {
		x += vX * timeElapsed;
		y += vY * timeElapsed;
		
		vY += level.gravity() * timeElapsed;
		
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		drawMachine.draw(image, x, y, WIDTH, HEIGHT);
	}
	
	private final Image image;
	
	private double x;
	private double y;
	private double vX;
	private double vY;

}
