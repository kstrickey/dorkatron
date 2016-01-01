package gameEngines.realmEngine;

import gameEngines.DrawMachine;
import gameEngines.levelEngine.LevelEngine;
import gameWindow.SoundPlayer;

import java.awt.geom.Rectangle2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import threading.ThreadFactories;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.GraphicsContext;

public class RealmEngine {
	
//	public static final double TOLERANCE = .00001;
	
	public RealmEngine(double dM, SoundPlayer soundPlayer, Realm realm, double timePerTick) {
		this.dM = dM;
		this.soundPlayer = soundPlayer;
		this.realm = realm;
		this.timePerTick = timePerTick;
		
		scrollWindow = new Rectangle2D.Double(0, 0, 1000, 625);		//TODO coordinates? maybe?
		
		currentLevelEngine = null;
		
	}
	
	public LevelEngine getCurrentLevelEngine() {
		return currentLevelEngine;
	}
	
	/**
	 * Sets the current LevelEngine's timePerTick field (if not null) and sets this.timePerTick for future LevelEngines.
	 * @param timePerTick : double
	 */
	public void setTimePerTick(double timePerTick) {
		this.timePerTick = timePerTick;
		if (currentLevelEngine != null) {
			currentLevelEngine.setTimePerTick(timePerTick);
		}
	}
	
	public void start(GraphicsContext gc) {
		this.gc = gc;
		
		realmEventQueue = new RealmEventQueue(this);
		
		realmBackgroundStuff = realm.newRealmBackgroundStuff();
		realmMap = realm.newRealmMap();
		
		drawMachine = new DrawMachine(gc, dM, scrollWindow);
		
		Ticker ticker = new Ticker();
		tickerFuture = Executors.newSingleThreadScheduledExecutor(ThreadFactories.daemonThreadFactory).scheduleAtFixedRate(ticker, 0, 10, TimeUnit.MILLISECONDS);
		
	}
	
	public void enterLevel() {
		currentLevelEngine = new LevelEngine(realmMap.currentLevel(), dM, soundPlayer, timePerTick);
		currentLevelEngine.getDone().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
				// Level is done
				currentLevelEngine.sayGoodBye();
				currentLevelEngine = null;
				start(gc);		// TODO different method for simply resuming
			}
		});
		currentLevelEngine.start(gc);
		
		sayGoodBye();		// TODO different method for simply pausing.
	}
	
	public boolean insideLevel() {
		return currentLevelEngine != null;
	}
	
	/**
	 * @return true if sitting on a Station with a level; false otherwise
	 */
	public boolean levelAvailable() {
		return realmMap.currentLevel() != null;
	}
	
	public RealmEventQueue getRealmEventQueue() {
		return realmEventQueue;
	}
	
	/**
	 * Indicates that an arrow key was pressed (user request to move along grid).
	 * @param direction
	 */
	public void requestMoveToward(SquareSide direction) {
		realmMap.moveMarker(direction);
	}
	
	/**
	 * Returns true if the passed block contains a Road object in its location in layout by calling realmMap.containsRoad(block).
	 * @param block
	 * @return
	 * @precondition block's coordinates are within the layout boundaries
	 */
	public boolean containsRoad(Block block) {
		return realmMap.containsRoad(block);
	}
	
	/**
	 * 
	 * @param block
	 * @return the Mappable object contained at the block's location in the realmMap
	 */
	public Mappable retrieveMappable(Block block) {
		return realmMap.retrieveMappable(block);
	}
	
	private void sayGoodBye() {
		tickerFuture.cancel(true);
	}
	
	private class Ticker implements Runnable {
		@Override
		public void run() {
try{//for testing TODO
			synchronized (synchronizer) {
				realmMap.tick(16, realmEventQueue);
				realmEventQueue.executeAllEvents();
				Platform.runLater(new RealmEngineDrawer());
			}
} catch (Throwable e) {
e.printStackTrace();
}
		}
	}

	
	private class RealmEngineDrawer implements Runnable {
		@Override
		public void run() {
			synchronized(synchronizer) {
				realmBackgroundStuff.draw(drawMachine);
				realmMap.draw(drawMachine);
			}
		}
	}
	
	
	private GraphicsContext gc;
	private final double dM;
	private final SoundPlayer soundPlayer;
	private final Realm realm;
	
	private Rectangle2D.Double scrollWindow;
	
	private RealmBackgroundStuff realmBackgroundStuff;
	private RealmMap realmMap;
	
	private LevelEngine currentLevelEngine;			// null if not in level
	private double timePerTick;						// used for LevelEngine (not RealmEngine)
	
	private DrawMachine drawMachine;
	
	private RealmEventQueue realmEventQueue;
	
	private ScheduledFuture<?> tickerFuture;

	private final Object synchronizer = new Object();
	
	
}
