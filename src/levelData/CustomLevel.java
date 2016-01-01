package levelData;

import gameEngines.levelEngine.LevelEngine;
import gameEngines.levelEngine.simObjects.enemies.EnemySpawner;
import gameEngines.levelEngine.simObjects.enemies.TallBunnyRabbitSpawner;
import gameEngines.levelEngine.simObjects.world.Scaffold;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.image.Image;

public class CustomLevel extends LevelData {
	
	public CustomLevel(String filePath) {
		scaffolds = new ArrayList<Scaffold>();
		enemySpawners = new ArrayList<EnemySpawner>();
		
		if (!retrieveAllFromFile(filePath)) {
			System.out.println("Error: Could not read level data properly.");		// TODO Better handling
			System.exit(1);
		}
	}
	
	@Override
	public String levelID() {
		return levelID;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public double gravity() {
		return gravity;
	}
	
	@Override
	public Image backgroundImage() {
		return backgroundImage;
	}
	
	@Override
	public Dimension levelBounds() {
		return levelBounds;
	}
	
	@Override
	public String levelObjective() {
		return levelObjective;
	}
	
	@Override
	public ArrayList<Scaffold> scaffolds() {
		return scaffolds;
	}

	@Override
	public Rectangle initialGameWindow() {
		return initialGameWindow;
	}

	@Override
	public String backgroundMusicURL() {
		return backgroundMusicURL;
	}

	@Override
	public ArrayList<EnemySpawner> enemySpawners() {
		return enemySpawners;
	}

	@Override
	public boolean isDone(LevelEngine levelEngine) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	private boolean retrieveAllFromFile(String filePath) {
		// Create parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
		
		// Parse XML file to DOM tree
		Document document = null;
		try {
			document = builder.parse(new File(filePath));
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		// Get root element
		Element level = document.getDocumentElement();
		if (!level.getTagName().equals("level")) {
			System.out.println("Error: Root tag name must be \"level\"");
			return false;
		}
		
		// Find and set values
		try {
			// Level ID
			levelID = level.getElementsByTagName("levelID").item(0).getTextContent();
			
			// Name
			name = level.getElementsByTagName("name").item(0).getTextContent();
			
			// Gravity
			gravity = Double.parseDouble(level.getElementsByTagName("gravity").item(0).getTextContent());
			
			// Background Image
			try {
				backgroundImage = new Image(level.getElementsByTagName("backgroundImage").item(0).getTextContent());
			} catch (NullPointerException e) {
				// Use default background
				backgroundImage = new Image("/resources/images/space background.png");
			}
			
			// Level bounds
			Element levelBounds = (Element) level.getElementsByTagName("levelBounds").item(0);							// Casting.. already in try/catch, right?
			this.levelBounds = new Dimension();
			this.levelBounds.width = Integer.parseInt(levelBounds.getElementsByTagName("width").item(0).getTextContent());
			this.levelBounds.height = Integer.parseInt(levelBounds.getElementsByTagName("height").item(0).getTextContent());
			
			// Level Objective
			try {
				levelObjective = level.getElementsByTagName("levelObjective").item(0).getTextContent();
			} catch (NullPointerException e) {
				levelObjective = "Enjoy the beauty of Dorkatron!";
			}
				
			// Scaffolds
			NodeList scaffoldList = level.getElementsByTagName("scaffold");
			for (int i = 0; i < scaffoldList.getLength(); ++i) {
				Element scaf = (Element) scaffoldList.item(i);
				int x = Integer.parseInt(scaf.getElementsByTagName("x").item(0).getTextContent());
				int y = Integer.parseInt(scaf.getElementsByTagName("y").item(0).getTextContent());
				int width = Integer.parseInt(scaf.getElementsByTagName("width").item(0).getTextContent());
				int height = Integer.parseInt(scaf.getElementsByTagName("height").item(0).getTextContent());
				boolean impassable = scaf.getElementsByTagName("impassable").equals("true");
				scaffolds.add(new Scaffold(x, y, width, height, impassable));
			}
			
			// Initial game window
			int x, y;
			try {
				Element window = (Element) level.getElementsByTagName("initialGameWindow").item(0);
				x = Integer.parseInt(window.getElementsByTagName("x").item(0).getTextContent());
				y = Integer.parseInt(window.getElementsByTagName("y").item(0).getTextContent());
			} catch (NullPointerException e) {
				x = 0;
				y = 0;
			}
			initialGameWindow = new Rectangle(x, y, 1000, 625);										// TODO if you want zoomable
			
			// Background music URL
			try {
				backgroundMusicURL = level.getElementsByTagName("backgroundMusicURL").item(0).getTextContent();
			} catch (NullPointerException e) {
				backgroundMusicURL = null;
			}
			
			// Enemy Spawners
			NodeList spawnerList = level.getElementsByTagName("enemySpawner");
			for (int i = 0; i < spawnerList.getLength(); ++i) {
				Element spawnerElement = (Element) spawnerList.item(i);
				x = Integer.parseInt(spawnerElement.getElementsByTagName("x").item(0).getTextContent());
				y = Integer.parseInt(spawnerElement.getElementsByTagName("y").item(0).getTextContent());
				EnemySpawner spawner;
				switch (spawnerElement.getElementsByTagName("type").item(0).getTextContent()) {
					case "TallBunnyRabbit":
						spawner = new TallBunnyRabbitSpawner(x, y, this.levelBounds);
						break;
					default:
						System.out.println("Invalid EnemySpawner type");
						continue;
				}
				Node n;
				if ((n = spawnerElement.getElementsByTagName("numberPerSpawn").item(0)) != null) {
					spawner.setNumberPerSpawn(Integer.parseInt(n.getTextContent()));
				}
				if ((n = spawnerElement.getElementsByTagName("periodLength").item(0)) != null) {
					spawner.setPeriodLength(Integer.parseInt(n.getTextContent()));
				}
				if ((n = spawnerElement.getElementsByTagName("decreasingPeriod").item(0)) != null) {
					spawner.setDecreasingPeriod(n.getTextContent().equals("true"));
				}
				if ((n = spawnerElement.getElementsByTagName("minNumberOfEnemies").item(0)) != null) {
					spawner.setMinNumberOfEnemies(Integer.parseInt(n.getTextContent()));
				}
				if ((n = spawnerElement.getElementsByTagName("onlyMyEnemies").item(0)) != null) {
					spawner.setOnlyMyEnemies(n.getTextContent().equals("true"));
				}
				if ((n = spawnerElement.getElementsByTagName("minX").item(0)) != null) {
					spawner.setMinX(Integer.parseInt(n.getTextContent()));
				}
				if ((n = spawnerElement.getElementsByTagName("maxX").item(0)) != null) {
					spawner.setMaxX(Integer.parseInt(n.getTextContent()));
				}
				if ((n = spawnerElement.getElementsByTagName("minY").item(0)) != null) {
					spawner.setMinY(Integer.parseInt(n.getTextContent()));
				}
				if ((n = spawnerElement.getElementsByTagName("maxY").item(0)) != null) {
					spawner.setMaxY(Integer.parseInt(n.getTextContent()));
				}
				enemySpawners.add(spawner);
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	private String levelID;
	private String name;
	private double gravity;
	private Image backgroundImage;
	private Dimension levelBounds;
	private String levelObjective;
	private ArrayList<Scaffold> scaffolds;
	private Rectangle initialGameWindow;
	private String backgroundMusicURL;
	private ArrayList<EnemySpawner> enemySpawners;
	
}
