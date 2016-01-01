package gameEngines.realmEngine;

import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import levelData.CustomLevel;

public class StandardRealm extends Realm {
	
	public StandardRealm(String filePath) {
		if (!retrieveAllFromFile(filePath)) {
			System.out.println("Error retrieving realm data");		// TODO Better handling
		}
		
		//todo retrieve location from progress files? (in realm map?)
	}
	
	
	@Override
	public RealmMap newRealmMap() {
		return realmMap;			// not instantiating a new RealmMap, but I think it's okay
	}
	
	@Override
	public RealmBackgroundStuff newRealmBackgroundStuff() {
		return new RealmBackgroundStuff(backgroundImage);
	}
	
	
	/**
	 * 
	 * @param filePath
	 * @return success
	 */
	private boolean retrieveAllFromFile(String filePath) {
		// Create parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder bob = null;
		try {
			bob = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		
		// Parse XML file to DOM tree
		Document document = null;
		try {
			document = bob.parse(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		}
		
		// Get root element
		Element realm = document.getDocumentElement();
		if (!realm.getTagName().equals("realm")) {
			System.out.println("Error: root node does not equal \"realm\"");
			return false;
		}
		
		try {
			
			// Load background image
			try {
				backgroundImage = new Image(realm.getElementsByTagName("backgroundImage").item(0).getTextContent());
			} catch (NullPointerException e) {
				backgroundImage = new Image("/resources/images/space background.png");
			}
			
			// Load width and height
			int blockWidth = Integer.parseInt(realm.getElementsByTagName("blockWidth").item(0).getTextContent());
			int blockHeight = Integer.parseInt(realm.getElementsByTagName("blockHeight").item(0).getTextContent());
			realmMap = new RealmMap(blockWidth, blockHeight);
			
			// Load all stations in the realm
			NodeList stationList = realm.getElementsByTagName("station");
			for (int i = 0; i < stationList.getLength(); ++i) {
				Node station = stationList.item(i);
				
				Element block = (Element) ((Element) station).getElementsByTagName("block").item(0);			// get location (in blocks)
				int blockX = Integer.parseInt(block.getElementsByTagName("x").item(0).getTextContent());
				int blockY = Integer.parseInt(block.getElementsByTagName("y").item(0).getTextContent());
				
				try {
					String levelDataFilePath = ((Element) station).getElementsByTagName("levelDataFilePath").item(0).getTextContent();	// get level (if it exists)
					levelDataFilePath = filePath.substring(0, filePath.indexOf("_REALM_DATA.xml")) + levelDataFilePath;
					realmMap.addStation(new Station(new CustomLevel(levelDataFilePath), blockX, blockY));
				} catch (NullPointerException e) {		// if there is no level at this station
					realmMap.addStation(new Station(null, blockX, blockY));
				}
			}
			
			// Load all roads in the realm
			NodeList roadList = realm.getElementsByTagName("road");
			for (int i = 0; i < roadList.getLength(); ++i) {
				Node road = roadList.item(i);
				
				Element block = (Element) ((Element) road).getElementsByTagName("block").item(0);				// get location (in blocks)
				int blockX = Integer.parseInt(block.getElementsByTagName("x").item(0).getTextContent());
				int blockY = Integer.parseInt(block.getElementsByTagName("y").item(0).getTextContent());
				
				NodeList sides = ((Element) road).getElementsByTagName("side");									// get sides
				SquareSide side1 = SquareSide.getFromName(sides.item(0).getTextContent());
				SquareSide side2 = SquareSide.getFromName(sides.item(1).getTextContent());
				realmMap.addRoad(new Road(blockX, blockY, side1, side2));
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private Image backgroundImage;
	
	private RealmMap realmMap;
	
}
