package gameEngines.levelEngine.simObjects;

import gameEngines.DrawMachine;
import gameEngines.Drawable;
import gameEngines.Tickable;
import gameEngines.levelEngine.LevelEventQueue;
import gameEngines.levelEngine.levelEvents.PlaySingleSoundEffectEvent;
import gameEngines.levelEngine.simObjects.accessories.Accessory;
import gameEngines.levelEngine.simObjects.accessories.ornaments.EnhancedDorkohat;
import gameEngines.levelEngine.simObjects.accessories.ornaments.Hat;
import gameEngines.levelEngine.simObjects.accessories.ornaments.Ornament;
import gameEngines.levelEngine.simObjects.accessories.weapons.EmptyHandWeapon;
import gameEngines.levelEngine.simObjects.accessories.weapons.HandWeaponArsenal;
import gameEngines.levelEngine.simObjects.ammo.AmmoArsenal;
import gameEngines.levelEngine.simObjects.enemies.Enemy;
import gameEngines.levelEngine.simObjects.world.Scaffold;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javafx.scene.image.Image;
import levelData.LevelData;
import levelData.SoundEffects;

public class Dork implements Tickable, Drawable {
	
	public final int WIDTH = 80;
	public final int HEIGHT = 160;
	
	public Dork() {
		IMAGE_REGULAR = new Image("/resources/images/le dork.png");
		IMAGE_ANGRY = new Image("/resources/images/angry dork.png");
		currentImage = IMAGE_REGULAR;
		
		HORIZONTAL_SPEED = 18;
		JUMP_SPEED = 63;
		STANDARD_BOUNCE_SPEED = 50;
		MAXIMUM_JUMP_SPEED = 80;
		STOMP_SPEED = -80;
		
		x = 300;
		vX = 0;
		y = 0;
		vY = 0;
		supported = false;
		facingRight = true;
		stomping = 0;
		
		ornaments = new ArrayList<Ornament>();
		hat = new EnhancedDorkohat();
		ornaments.add(hat);
		
		handWeaponArsenal = new HandWeaponArsenal();
		ammoArsenal = new AmmoArsenal();
		
		rightDepressed = false;
		leftDepressed = false;
		upDepressed = false;
	}
	
	@Override		//TODO BUG: The Dork's velocity for a ground-pound was not set: positive vY and kept going upwards
	public void tick(double timeElapsed, LevelEventQueue levelEventQueue, LevelData level) {		//TODO 1000, 625 --> ??? scrollWindow!!
		// Limit upward velocity... so he doesn't go shooting offscreen unreasonably
		if (vY > MAXIMUM_JUMP_SPEED) {
			vY = MAXIMUM_JUMP_SPEED;
		}
		
		double oldX = x, oldY = y;
		
		// Modify position
		x += vX * timeElapsed;
		y += vY * timeElapsed;
		
		// Modify x and y-velocities
		if (stomping == 0) {
			
			// Modify x-velocity & direction facing
			if (rightDepressed && !leftDepressed) {
				vX = HORIZONTAL_SPEED;
				facingRight = true;
			} else if (leftDepressed && !rightDepressed) {
				vX = -HORIZONTAL_SPEED;
				facingRight = false;
			} else {
				vX = 0;
			}
			
			// Modify y-velocity according to supported
			if (!supported) {
				vY += level.gravity() * timeElapsed;
			} else {
				if (upDepressed) {
					vY = JUMP_SPEED;		// jump
					supported = false;
				} else {
					checkSupport(level.scaffolds());
					vY = 0;
				}
			}
			
			// Be sure s/he is not outside of the level bounds
			if (x < WIDTH/2.0) {
				x = WIDTH/2.0;
				vX = 0;
			} else if (x > level.levelBounds().width - WIDTH/2.0) {
				x = level.levelBounds().width - WIDTH/2.0;
				vX = 0;
			}
			
		} else {
			
			// Stomping cycle
			stomping += timeElapsed;
			if (stomping > 0 && stomping < 3) {
				// Hovering
				// vY, vX set to 0 when stomping was initially started
			} else if (stomping >= 3) {
				if (vY == 0) {
					vY = STOMP_SPEED;
				}
				// Falling
				// not affected by gravity.
				if (supported) {		// Reached Scaffold
					vY = 0;
					stomping = -3;
					levelEventQueue.addEvent(new PlaySingleSoundEffectEvent(SoundEffects.STOMP_RECOVERY));
					currentImage = IMAGE_REGULAR;
				}
			} else if (stomping < 0) {
				// Recovering
				// vY set to 0 when recovery initially started
				if (stomping >= -timeElapsed) {	// if close enough to 0
					stomping = 0;
				}
			}
			
		}
		
		// Check for intersection with Scaffold and set supported accordingly
		for (Scaffold scaf : level.scaffolds()) {			// TODO?? - should this be from the World, and not the level? They should be pointing at the same place.//for enemies too
			
			// Dork's bottom boundary hits the Scaffold...
			if (vY <= 0 && (scaf.getArea().intersects(x - WIDTH/2.2, y - 1, WIDTH/1.1, oldY - y + 1) || scaf.getArea().intersectsLine(x - WIDTH/2.2, y, x + WIDTH/2.2, y))) {
					// Not exact, but hopefully close enough: estimating the parallelogram of mvmt
				supported = true;
				y = scaf.surface();
			}
			
			if (scaf.isImpassible()) {
				if (!supported) {		// in midair
					// Dork's top boundary hits the Scaffold...
					if (vY > 0 && (scaf.getArea().intersects(x - WIDTH/2.2, oldY + HEIGHT, WIDTH/1.1, y - oldY) || 
							scaf.getArea().intersectsLine(x - WIDTH/2.2, y + HEIGHT, x + WIDTH/2.2, y + HEIGHT))) {
						vY = 0;
					}
					// Dork's left or right boundary hits the Scaffold...
					if ((vX < 0 && (scaf.getArea().intersects(oldX - WIDTH/2.0, (oldY + y) / 2, x - oldX, HEIGHT) || 		// left boundary
								scaf.getArea().intersectsLine(x - WIDTH/2.0, y, x - WIDTH/2.0, y + HEIGHT))) || 	
							(vX >  0 && (scaf.getArea().intersects(oldX + WIDTH/2.0, (oldY + y) / 2, x - oldX, HEIGHT) || 	// right boundary
								scaf.getArea().intersectsLine(x + WIDTH/2.0, y, x + WIDTH/2.0, y + HEIGHT)) )) {
						vX = 0;
					}
				} else {				// walking on ground
					// Dork's left or right boundary hits the Scaffold...
					if ((vX < 0 && (scaf.getArea().intersects(oldX - WIDTH/2.0, y + 2, x - oldX, HEIGHT - 2) ||
								scaf.getArea().intersectsLine(x - WIDTH/2.0, y + 2, x - WIDTH/2.0, y + HEIGHT - 2))) || 
							(vX > 0 && (scaf.getArea().intersects(oldX + WIDTH/2.0, y + 2, x - oldX, HEIGHT - 2) || 
								scaf.getArea().intersectsLine(x + WIDTH/2.0, y + 2, x + WIDTH/2.0, y + HEIGHT - 2)))) {
						vX = 0;
					}
				}
			}
			
		}
		
		// Tick accessories
		handWeaponArsenal.currentHandWeapon().tick(levelEventQueue);
		
		for (Ornament ornament : ornaments) {
			ornament.tick(levelEventQueue);
		}
	}
	
	@Override
	public void draw(DrawMachine drawMachine) {
		if (facingRight) {
			drawMachine.draw(currentImage, x, y, WIDTH, HEIGHT);
		} else {
			drawMachine.draw(currentImage, x, y, -WIDTH, HEIGHT);
		}
		
		// Draw Accessories
		if (!(handWeaponArsenal.currentHandWeapon() instanceof EmptyHandWeapon)) {
			drawAccessory(handWeaponArsenal.currentHandWeapon(), drawMachine);
		}
		for (Ornament ornament : ornaments) {
			drawAccessory(ornament, drawMachine);
		}
	}
	
	public AmmoArsenal getAmmoArsenal() {
		return ammoArsenal;
	}
	
	public HandWeaponArsenal getHandWeaponArsenal() {
		return handWeaponArsenal;
	}
	
	public void setLeftDepressed(boolean leftDepressed) {
		this.leftDepressed = leftDepressed;
	}
	
	public void setRightDepressed(boolean rightDepressed) {
		this.rightDepressed = rightDepressed;
	}
	
	public void setUpDepressed(boolean upDepressed) {
		this.upDepressed = upDepressed;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getVX() {
		return vX;
	}
	
	public double getVY() {
		return vY;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}
	
	/**
	 * Returns true only if the Dork's stomp cycle does not equal 0.
	 * @return
	 */
	public boolean isStomping() {
		return stomping != 0;
	}
	
	/**
	 * Returns an AWT Rectangle object containing the dimensions, in the Level system (origin at bottom-left), with the vital (will kill Dork if touched by Enemy) bounds of the Dork.
	 * @return
	 */
	public Rectangle2D.Double getVitalBounds() {
		if (stomping > 0) {
			return new Rectangle2D.Double(0, 0, 0, 0);
		}
		return new Rectangle2D.Double(x - WIDTH/2.0, y + HEIGHT * 3.0/16.0, WIDTH, HEIGHT * 13.0/16.0);		// above feet
	}
	
	/**
	 * Returns an AWT Rectangle object containing the dimensions, in the Level system (origin at bottom-left), with the lethal (will kill an Enemy) bounds of the Dork.
	 * @return
	 */
	public Rectangle2D.Double getLethalBounds() {
		if (stomping > 0) {
			return new Rectangle2D.Double(x - WIDTH/2.0, y, WIDTH, HEIGHT);	// whole body
		}
		return new Rectangle2D.Double(x - WIDTH/2.0, y, WIDTH, HEIGHT * 3.0/16.0);		// feet
	}
	
	public void bounceOnEnemy(Enemy enemy) {
		vY = Math.abs(vY) + Math.abs(enemy.getVY());
		if (upDepressed) {
			vY += JUMP_SPEED;
		} else if (vY > STANDARD_BOUNCE_SPEED) {
			vY = STANDARD_BOUNCE_SPEED;
		}
	}
	
	/**
	 * note: meant for use by DownArrowKeyPressedEvent
	 */
	public boolean eligibleToStomp() {
		return stomping == 0 && !supported;
	}
	
	/**
	 * note: meant for use by DownArrowKeyPressedEvent
	 */
	public void startStomping() {
		stomping = .0001;
		currentImage = IMAGE_ANGRY;
		vY = 0;
		vX = 0;
	}
	
	
	private void drawAccessory(Accessory acc, DrawMachine drawMachine) {
		if (facingRight) {
			drawMachine.draw(acc.image(), x + acc.relativeX(), y + acc.relativeY(), acc.width(), acc.height());
		} else {
			drawMachine.draw(acc.image(), x - acc.relativeX(), y + acc.relativeY(), -acc.width(), acc.height());
		}
	}
	
	/**
	 * Checks if the Dork has walked off a Scaffold and sets supported accordingly.
	 */
	private void checkSupport(ArrayList<Scaffold> scaffolds) {
		if (supported) {
			boolean onPlatform = false;
			for (Scaffold scaf : scaffolds) {
				if (scaf.getArea().intersects(x - WIDTH/2.2, y - 2, WIDTH/1.1, 2)) {
					onPlatform = true;
					break;
				}
			}
			if (!onPlatform) {
				supported = false;
			}
		}
	}
	
	
	// Variables for Image to draw onscreen
	private Image currentImage;
	private final Image IMAGE_REGULAR;
	private final Image IMAGE_ANGRY;
	
	// Kinetics constants
	private final double HORIZONTAL_SPEED;
	private final double JUMP_SPEED;
	private final double STANDARD_BOUNCE_SPEED;
	private final double MAXIMUM_JUMP_SPEED;
	private final double STOMP_SPEED;
	
	// General physics
	private double x;
	private double vX;
	private double y;
	private double vY;
	private boolean supported;		// true if standing on the ground or a Scaffold.
	private boolean facingRight;
	private double stomping;		// while stomping, the Dork cannot move left or right, or stop stomping until stomping == 0.0.
		// Stomping cycle: 0.0, not stomping. 0.0-3.0, hovering. >3.0, falling. -3.0-0.0, recovering.
		// Cannot start stomping unless in air.
	
	// Ornaments
	private ArrayList<Ornament> ornaments;
	private Hat hat;
	
	// Available Weapon and Ammo choices
	private HandWeaponArsenal handWeaponArsenal;
	private AmmoArsenal ammoArsenal;
	
	// Variables for keyboard input
	private boolean leftDepressed;
	private boolean rightDepressed;
	private boolean upDepressed;
	// no downDepressed

}
