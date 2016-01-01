package gameEngines.levelEngine;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import gameEngines.DrawMachine;
import gameEngines.levelEngine.simObjects.AllForegroundExtras;
import gameEngines.levelEngine.simObjects.Dork;
import gameEngines.levelEngine.simObjects.ammo.AllAmmo;
import gameEngines.levelEngine.simObjects.enemies.AllEnemies;
import gameEngines.levelEngine.simObjects.world.World;
import gameWindow.SoundPlayer;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import levelData.LevelData;
import levelData.SoundEffects;
import threading.ThreadFactories;

public class LevelEngine {
	
	public static final double DEFAULT_TIME_PER_TICK = .08;
	
	public final Random generator = new Random();
	
	public LevelEngine(LevelData level, double dM, SoundPlayer soundPlayer, double timePerTick) {
		this.dM = dM;
		this.level = level;
		this.soundPlayer = soundPlayer;
		this.timePerTick = timePerTick;
		
		Rectangle rect = level.initialGameWindow();
		scrollWindow = new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height);
		
		world = new World(level);
		allForegroundExtras = new AllForegroundExtras();
		dork = new Dork();
		allEnemies = new AllEnemies(dork, level);
		allAmmo = new AllAmmo(allEnemies);
		
		gameEventQueue = new LevelEventQueue(this);
		
		PAUSED_SCREEN = new Image("/resources/images/PAUSED screen.png");
		
		done = new SimpleBooleanProperty(false);
	}
	
	public BooleanProperty getDone() {
		return done;
	}
	
	public void setTimePerTick(double timePerTick) {
		synchronized (synchronizer) {
			this.timePerTick = timePerTick;
		}
	}
	
	public void start(GraphicsContext gc) {
		this.gc = gc;
		
		drawMachine = new DrawMachine(gc, dM, scrollWindow);
		
		time = 0;
		enemiesKilled = 0;
		
		currentMessage = "";
		currentMessageTimer = 0;
		
		paused = false;
		
		if(level.backgroundMusicURL() != null) {
			this.soundPlayer.setBackgroundMusicFromURL(level.backgroundMusicURL());
			this.soundPlayer.playBackgroundMusic();
		}
		
		
		Ticker ticker = new Ticker();
		tickerFuture = Executors.newSingleThreadScheduledExecutor(ThreadFactories.daemonThreadFactory).scheduleAtFixedRate(ticker, 0, 17, TimeUnit.MILLISECONDS);
		
	}
	
	public void sayGoodBye() {
		soundPlayer.stopBackgroundMusic();
		System.out.println("Kills: " + enemiesKilled);
		tickerFuture.cancel(true);
		done.set(true);
	}
	
	
	private class Ticker implements Runnable {
		@Override
		public void run() {
			synchronized (synchronizer) {
				tick();
			}
		}
	}
	
	private void tick() {
		if (!paused) {
			time += timePerTick;
			
			world.tick(timePerTick, gameEventQueue, level);
			allForegroundExtras.tick(timePerTick, gameEventQueue, level);
			allEnemies.tick(timePerTick, gameEventQueue, level);
			dork.tick(timePerTick, gameEventQueue, level);
			allAmmo.tick(timePerTick, gameEventQueue, level);
						
			if (dork.getX() - scrollWindow.x < scrollWindow.width * .25) {
				scrollWindow.x -= Math.abs(dork.getX() - scrollWindow.x - scrollWindow.width * .25);
			} else if (dork.getX() - scrollWindow.x > scrollWindow.width * .375) {
				scrollWindow.x += Math.abs(dork.getX() - scrollWindow.x - scrollWindow.width * .375);
			}
			scrollWindow.x += scrollWindowXOutOfBounds();
			//TODO same for y...
			
			
			if (currentMessageTimer > 0) {
				currentMessageTimer -= timePerTick;
			} else if (currentMessageTimer < 0) {
				currentMessageTimer = 0;
				currentMessage = "";
			}
			
			if (allEnemies.isMissionToKillDorkAccomplished()) {
				done.set(true);
			}
		}
		
		gameEventQueue.executeAllEvents();
		
		
		Platform.runLater(new LevelEngineDrawer());
		
	}
	
	private void draw() {
		world.draw(drawMachine);
		allForegroundExtras.draw(drawMachine);
		allEnemies.draw(drawMachine);
		dork.draw(drawMachine);
		allAmmo.draw(drawMachine);
		
		gc.fillText("Time: " + time, 20, 20);
		gc.fillText("Enemies Killed: " + enemiesKilled, 20, 40);
		
		gc.fillText(currentMessage, 500, 20);
		
		if (allEnemies.isMissionToKillDorkAccomplished()) {
			gc.fillText("YOU LOSE", 100, 100);
		}
		
		if (paused) {		// TODO - use DrawMachine
			gc.drawImage(PAUSED_SCREEN, 0, 0, scrollWindow.width * dM, scrollWindow.height * dM);
		}
	}
	
	public LevelEventQueue gameEventQueue() {
		return gameEventQueue;
	}
	
	public LevelData getLevel() {
		return level;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Dork getDork() {
		return dork;
	}
	
	public AllEnemies getAllEnemies() {
		return allEnemies;
	}
	
	public AllAmmo getAllAmmo() {
		return allAmmo;
	}
	
	public AllForegroundExtras getAllForegroundExtras() {
		return allForegroundExtras;
	}
	
	public int getEnemiesKilled() {
		return enemiesKilled;
	}
	
	public void togglePaused() {
		paused = !paused;
		if (paused) {
			soundPlayer.pauseBackgroundMusic();
		} else {
			soundPlayer.playBackgroundMusic();
		}
	}
	
	public void writeMessage(String currentMessage, double currentMessageTimer) {
		this.currentMessage = currentMessage;
		this.currentMessageTimer = currentMessageTimer;
	}
	
	
	/**
	 * Increments field enemiesKilled by one.
	 */
	public void addEnemyKilled() {
		++enemiesKilled;
	}
	
	public void playSoundEffect(SoundEffects effect) {
		soundPlayer.playSoundEffect(effect);
	}
	
	/**
	 * Returns a double indicating the x-distance the source Rectangle must shift to be in bounds again, or 0 if it is already in bounds.
	 * @return
	 */
	public double scrollWindowXOutOfBounds() {
		// Too far to the left
		if (scrollWindow.x < 0) {
			return -scrollWindow.x;
		}
		
		// Too far to the right
		if (scrollWindow.x + scrollWindow.width > level.backgroundImage().getWidth()) {
			return level.backgroundImage().getWidth() - scrollWindow.width - scrollWindow.x;
		}
		
		// Still in bounds
		return 0;
	}
	
	
	
	private class LevelEngineDrawer implements Runnable {
		@Override
		public void run() {
			synchronized (synchronizer) {
				draw();
			}
		}
	}
	
	private GraphicsContext gc;
	private final double dM;
	private final SoundPlayer soundPlayer;
	
	private final LevelData level;
	
	private Rectangle2D.Double scrollWindow;
	
	private World world;
	private AllForegroundExtras allForegroundExtras;
	private AllEnemies allEnemies;
	private Dork dork;
	private AllAmmo allAmmo;
	
	private DrawMachine drawMachine;
	
	private LevelEventQueue gameEventQueue;
	
	private double time;
	private int enemiesKilled;
	
	private double timePerTick;
	
	private String currentMessage;
	private double currentMessageTimer;
	
	private boolean paused;
	private final Image PAUSED_SCREEN;
	
	private final BooleanProperty done;
	
	private ScheduledFuture<?> tickerFuture;
	
	private final Object synchronizer = new Object();
	
}
