package gameEngines.levelEngine.simObjects.ammo;

import gameEngines.levelEngine.simObjects.Dork;

import java.util.Random;

public class RabbitPelletFactory extends AmmoFactory {

	public RabbitPelletFactory() {
		
	}
	
	@Override
	public String ammoType() {
		return "Rabbit Pellet";
	}
	
	@Override
	public Ammo createOne(Dork dork, int relativeX, int relativeY) {
		Random generator = new Random();
		
		double x = dork.getX();
		if (dork.isFacingRight()) {
			x += relativeX;
		} else {
			x -= relativeX;
		}
		
		double y = dork.getY() + relativeY;
		
		double vX = 18 + 4 * generator.nextDouble();
		if (dork.isFacingRight()) {
			vX = dork.getVX() + vX;
		} else {
			vX = dork.getVX() - vX;
		}
		
		double vY = 50 + 25 * generator.nextDouble();
		vY += dork.getVY();
		
		return new RabbitPellet(x, y, vX, vY);
	}
	
	@Override
	public int stockRemaining() {
		return INFINITY;
	}

}
