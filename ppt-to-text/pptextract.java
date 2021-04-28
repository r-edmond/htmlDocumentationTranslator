import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


public class pptextract  {  
	public static void main(String args[])  
	{  
		//Slide file names
		String slide1 = "slide1.xml";
		String slide1_relation = "slide1.xml.rels";
		
		//dir name:
		String dirName = ".\\2020-06-22 108 Hindu Holocaust FACTBOOK\\ppt\\";
		String slidesDirName = "slides";
		String mediaRef = "media";
		String slideNotes = "notesSlides";
		String slidesRel = "_rels";
		
		//Enter slides directory
		File dir = new File(dirName + slidesDirName);
		System.out.println("Directory: " + dir.getName());
		
		//Store slide xmls
		File[] slidesList = dir.listFiles();
		//slidesList.sort(Comparator.Comparing(file::getName));
		//Slides' textbody list
		List<TextBody> data = new ArrayList<TextBody>();
			
		if (slidesList != null) {
			//Scan through slides
			for (File child : slidesList) {
				String ext = child.getName().substring(child.getName().lastIndexOf(".") + 1, child.getName().length());
				
				System.out.println("File extension: " + ext);
				//Work on .xml filetype
				if (ext.equalsIgnoreCase("xml")) {
					//Get slide number
					String slideNum = child.getName().replaceAll("slide","");
					slideNum = slideNum.replaceAll(".xml", "");
					int s = Integer.parseInt(slideNum);  
					
					System.out.println(child.getName());
					System.out.println("slide number: " + s);
					
					//add slide text to 
					data.add(getData(child, s));
				}
			}
		} else {
			System.out.println("empty");
		}
		
		data.sort(Comparator.comparingInt(TextBody::getSlideNum));
		
		try {
			File testFile = new File("slideData.txt");
			
			FileWriter myWriter = new FileWriter("slideData.txt");
			for (TextBody i : data) {
				myWriter.write(i.toString());
			}
			myWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	
	private static TextBody getData(File slide, int s) {
		try {
			//Create DocumentBuilder object
			System.out.println("Creating DocumentBuilder object...");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.out.println("DocumentBuilder obj created");
			
			//Build document
			System.out.println("Reading XML...");
			//Document document = builder.parse(new File(slide1));
			Document document = builder.parse(slide);
			
			//Normalize XMl structure
			//document.getDocumentElement().normalize();
			
			//Root node
			//Element root = document.getDocumentElement();
			//System.out.println(root.getNodeName());
						
			//Number of textbody nodes
			NodeList textList = document.getElementsByTagName("p:txBody");
			System.out.println("Number of textbody nodes: " + textList.getLength());
			
			//Total number of text nodes
			NodeList nList = document.getElementsByTagName("a:r");
			System.out.println("Number of text nodes: " + nList.getLength());
			
			String slideText = "";
			
			//For each textbody node: 
			for (int i=0; i < textList.getLength(); i++) {
				//store embedded text
				Node n = textList.item(i);
				if (n.getNodeType()==Node.ELEMENT_NODE) {
					//System.out.println(n.getTextContent());
					slideText += n.getTextContent() + "\n";
				} else {
					System.out.println("?");
				}
				//System.out.println(document.getElementsByTagName("a:r").item(i).getTextContent());
				//slideText += document.getElementsByTagName("a:r").item(i).getTextContent();
			}
			
			System.out.println("Returned text: ");
			System.out.println(slideText);
			TextBody t1 = new TextBody(slideText, s);
			return t1;
			
		} catch (Exception e) {
			System.out.println("Found an issue. Details: ");
			System.out.println(e);
			return null;
		}
	}
}  