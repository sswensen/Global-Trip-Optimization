import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.DOMImplementation;

public class View implements TripView {
	
	//Document builders
	public DocumentBuilderFactory factory;
	public DocumentBuilder docBuilder;
	public Document XMLdoc;
	public Element locations;
	
	//root element
	
	public void initializeTrip() throws ParserConfigurationException{
		factory = DocumentBuilderFactory.newInstance(); 
		docBuilder = factory.newDocumentBuilder();
		//XMLdoc is the XML file
		XMLdoc = docBuilder.newDocument();
		Element rootElement = XMLdoc.createElement("trip");
		XMLdoc.appendChild(rootElement);
		locations = XMLdoc.createElement("locations");
		rootElement.appendChild(locations);
	}
	
	public static void convertCoordinates(double x, double y){
		
		double startX = 30;
		double endX = 60;
		double xPixels = 1066.6073;
		double yPixels = 783.0824;
		double stride = endX - startX;
		double relativeX = (x - startX);
		double realX = relativeX * (xPixels / stride);
		
	}
	public void addLocation(String name, double lat, double lng) {
		//location grouping
		Element location = XMLdoc.createElement("location");
		location.setAttribute("name", name);
		location.setAttribute("latitude", "" + lat);
		location.setAttribute("longitude", "" + lng);
		locations.appendChild(location);
	}
	
	public void addLeg(String sequence,String start, String finish, int mileage){
		//leg grouping
		Element leg = XMLdoc.createElement("leg");
		XMLdoc.getDocumentElement().appendChild(leg);
		
		//Sequence element
		Element sequenceElement = XMLdoc.createElement("sequence");
		sequenceElement.appendChild(XMLdoc.createTextNode(sequence));
		leg.appendChild(sequenceElement);
		
		//Start element
		Element startElement = XMLdoc.createElement("start");
		startElement.appendChild(XMLdoc.createTextNode(start));
		leg.appendChild(startElement);
		
		//finish element
		Element finishElement = XMLdoc.createElement("finish");
		finishElement.appendChild(XMLdoc.createTextNode(finish));
		leg.appendChild(finishElement);
				
		//mileage element
		Element mileageElement = XMLdoc.createElement("mileage");
		mileageElement.appendChild(XMLdoc.createTextNode(Integer.toString(mileage)));
		leg.appendChild(mileageElement);
	}
	
	public void addLline(double longtitude1, double latitude1, double longitude2, double latitude2){
		
	}
	
	public void addLabel(double longitude, double latitude, String c, String d){
		
	}
	
	public void addHeader(String header){
		
	}
	
	public void addFooter(String Footer){
		
	}
	
	public void finalizeTrip() throws TransformerException{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(XMLdoc);
		StreamResult result = new StreamResult(new File("testfile.xml"));
		transformer.transform(source, result);
		System.out.println("File saved!");
	}
	
	public static void main(String argv[]) throws ParserConfigurationException {
		View map = new View();
		String name1 = "sandeep";
		String name2 = "chundru";
		String sequence = "1";
		int mileage = 10;
		
		try {
			map.initializeTrip();
			map.addLocation("Denver", 10, 10);
			map.addLocation("Somewhere in the ocean lul", 0, 0);
			map.finalizeTrip();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
