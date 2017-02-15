package View;

/**
 * Created by SummitDrift on 2/13/17.
 */
import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.DOMImplementation;

public class View {
	public DocumentBuilderFactory factory;
	public DocumentBuilder docBuilder;
	
	// root elements
	public Document XMLdoc;
	public Element  rootElement;
	
	public void initializeTrip(){
		factory = DocumentBuilderFactory.newInstance(); 
		docBuilder = factory.newDocumentBuilder();
		XMLdoc = docBuilder.newDocument();
		rootElement = XMLdoc.createElement("trip");
		XMLdoc.appendChild(rootElement);
	}
	
	public void addLeg(String sequence,String start, String finish, int mileage){
		//leg grouping
		Element leg = XMLdoc.createElement("leg");
		rootElement.appendChild(leg);
		
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
		mileageElement.appendChild(XMLdoc.createTextNode(mileage));
		leg.appendChild(mileageElement);
	}
	
	public void addLline(double longtitude1, double latitude1, double longitude2, double latitude2){
		
	}
	
	public void addLabel(double a, double b, String c, String d){
		
	}
	
	public void addHeader(String header){
		
	}
	
	public void addFooter(String Footer){
		
	}
	
	public void finalizeTrip(){
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(XMLdoc);
		StreamResult result = new StreamResult(new File("testfile.xml"));
		transformer.transform(source, result);
		System.out.println("File saved!");
	}
	
	public static void main(String argv[]) {
		View map = new View();
		map.initializeTrip();
		String name1 = "name1";
		String name2 = "name2";
		String sequence = "1";
		int mileage = 10;
		map.addLeg(sequence, name1, name2, mileage);
		try {
			map.finalizeTrip();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
