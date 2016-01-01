package gameEngines.realmEngine;

public abstract class Realm {
	
//	public abstract String realmID();
//	
//	public abstract String name();
	
	//Reads in data about levels and sets currentlevel etc.
	
	/**
	 * Generates and returns a new RealmMap object corresponding to the Realm.
	 * @return
	 */
	public abstract RealmMap newRealmMap();
	
	/**
	 * Generates and returns a new RealmBackgroundStuff object corresponding to the Realm.
	 * @return
	 */
	public abstract RealmBackgroundStuff newRealmBackgroundStuff();
	
	
}
