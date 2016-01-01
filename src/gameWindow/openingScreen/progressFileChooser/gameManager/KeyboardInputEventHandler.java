package gameWindow.openingScreen.progressFileChooser.gameManager;

import gameEngines.levelEngine.levelEvents.DownArrowKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.FireHandWeaponKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.LeftArrowKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.LeftArrowKeyReleasedEvent;
import gameEngines.levelEngine.levelEvents.LoadHandWeaponKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.PauseKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.RightArrowKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.RightArrowKeyReleasedEvent;
import gameEngines.levelEngine.levelEvents.SwitchAmmoTypeKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.SwitchHandWeaponKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.UpArrowKeyPressedEvent;
import gameEngines.levelEngine.levelEvents.UpArrowKeyReleasedEvent;
import gameEngines.realmEngine.RealmEngine;
import gameEngines.realmEngine.SquareSide;
import gameEngines.realmEngine.realmEvents.ArrowKeyPressedEvent;
import gameEngines.realmEngine.realmEvents.EnterLevelEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyboardInputEventHandler implements EventHandler<KeyEvent> {
	
	public KeyboardInputEventHandler(RealmEngine realmEngine) {
		this.realmEngine = realmEngine;
	}

	@Override
	public void handle(KeyEvent e) {
		
		if (realmEngine.insideLevel()) {
			
			if (e.getEventType() == KeyEvent.KEY_PRESSED) {				// Deal with key pressed
				switch (e.getCode()) {
					case RIGHT:
					case D:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new RightArrowKeyPressedEvent());
						break;
					case LEFT:
					case A:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new LeftArrowKeyPressedEvent());
						break;
					case UP:
					case W:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new UpArrowKeyPressedEvent());
						break;
					case DOWN:
					case S:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new DownArrowKeyPressedEvent());
						break;
						
					case J:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new FireHandWeaponKeyPressedEvent());
						break;
					case H:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new LoadHandWeaponKeyPressedEvent());
						break;
						
					case U:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new SwitchHandWeaponKeyPressedEvent());
						break;
					case I:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new SwitchAmmoTypeKeyPressedEvent());
						break;
						
					case P:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new PauseKeyPressedEvent());
						break;
						
					default:
						break;
				}
			} else if (e.getEventType() == KeyEvent.KEY_RELEASED) {		// Deal with key released
				switch (e.getCode()) {
					case RIGHT:
					case D:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new RightArrowKeyReleasedEvent());
						break;
					case LEFT:
					case A:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new LeftArrowKeyReleasedEvent());
						break;
					case UP:
					case W:
						realmEngine.getCurrentLevelEngine().gameEventQueue().addEvent(new UpArrowKeyReleasedEvent());
						break;
						
					default:
						break;
				}
			}
		
		} else {

			if (e.getEventType() == KeyEvent.KEY_RELEASED){
				switch (e.getCode()) {
					case ENTER:
						if (realmEngine.levelAvailable()) {
							realmEngine.getRealmEventQueue().addEvent(new EnterLevelEvent());
						}
						break;
						
					case RIGHT:
					case D:
						realmEngine.getRealmEventQueue().addEvent(new ArrowKeyPressedEvent(SquareSide.RIGHT));
						break;
					case DOWN:
					case S:
						realmEngine.getRealmEventQueue().addEvent(new ArrowKeyPressedEvent(SquareSide.BOTTOM));
						break;
					case LEFT:
					case A:
						realmEngine.getRealmEventQueue().addEvent(new ArrowKeyPressedEvent(SquareSide.LEFT));
						break;
					case UP:
					case W:
						realmEngine.getRealmEventQueue().addEvent(new ArrowKeyPressedEvent(SquareSide.TOP));
						break;
						
					default:
						break;
				}
			}
			
		}
	}
	
	
	private final RealmEngine realmEngine;
	
}
