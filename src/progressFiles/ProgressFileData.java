package progressFiles;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProgressFileData {
	
	/**
	 * Loads progress data from "progress file data.xml".
	 */
	public ProgressFileData() {
		totalProgressFiles = new TotalProgressFile[NUMBER_PROGRESS_FILES];			// each element is null
		progressFileIcons = new ProgressFileIcon[NUMBER_PROGRESS_FILES];
		
		loadProgressFileIcons();
	}
	
	public void update() {
		
	}
	
	/**
	 * Loads progressFileIcons from the file "progress file data.xml".
	 */
	private void loadProgressFileIcons() {
		// Create parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		// Parse XML file to DOM tree
		Document document = null;
		try {
			document = builder.parse(new File("/progressFiles/progress file data.xml"));
		} catch (SAXException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// Get root element
		Element progressFileData = document.getDocumentElement();
		if (!progressFileData.getTagName().equals("progressFileData")) {
			System.out.println("Error: Root tag name must be \"progressFileData\"");
			System.exit(1);
		}
		
		// Load progress files
		try {
			NodeList progressFileList = progressFileData.getElementsByTagName("progressFile");
			for (int i = 0; i < NUMBER_PROGRESS_FILES; ++i) {
				Element progressFile = (Element) progressFileList.item(i);
				String name = progressFile.getElementsByTagName("name").item(0).getTextContent();
				String imagePath = progressFile.getElementsByTagName("imagePath").item(0).getTextContent();
				progressFileIcons[i] = new ProgressFileIcon(name, imagePath);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private TotalProgressFile[] totalProgressFiles;
	private ProgressFileIcon[] progressFileIcons;
	
	private final int NUMBER_PROGRESS_FILES = 3;
	
}
