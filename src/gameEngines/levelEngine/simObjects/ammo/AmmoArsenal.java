package gameEngines.levelEngine.simObjects.ammo;

import java.util.ArrayList;

/**
 * Holds all of the Dork's available AmmoFactory objects.
 *
 */
public class AmmoArsenal {
	
	public AmmoArsenal() {
		factories = new ArrayList<AmmoFactory>();
		factories.add(new EmptyAmmoFactory());
		factories.add(new RabbitPelletFactory());
		
		currentFactoryIndex = 0;	// EmptyAmmoFactory
	}
	
	public AmmoFactory getCurrentFactory() {
		return factories.get(currentFactoryIndex);
	}
	
	/**
	 * Switches the current factory to the next one in the ArrayList field factories (looping).
	 */
	public void changeFactory() {
		++currentFactoryIndex;
		if (currentFactoryIndex == factories.size()) {
			currentFactoryIndex = 0;
		}
	}
	
	private ArrayList<AmmoFactory> factories;
	private int currentFactoryIndex;
	
}
