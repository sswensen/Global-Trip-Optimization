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
	public DocumentBuilder docbuilder;
	
	// root elements
	public Document XMLdoc;
	public Element  rootElement;
	
	public void initializeTrip(){
		factory = DocumentBuilderFactory.newInstance(); 
		docbuilder = factory.newDocumentBuilder();
		XMLdoc = docBuilder.newDocument();
		rootElement = doc.createElement("trip");
		doc.appendChild(rootElement);
	}
	
	public void addLeg(String sequence,String start, String finish, int mileage){
		//leg grouping
		Element leg = XMLdoc.createElement("leg");
		rootElement.appendChild(leg);
		
		//Sequence element
		Element sequence = XMLdoc.createElement("sequence");
		sequence.appendChild(doc.createTextNode(sequence));
		leg.appendChild(sequence);
		
		//Start element
		Element start = XMLdoc.createElement("start");
		start.appendChild(doc.createTextNode(start));
		leg.appendChild(start);
		
		//finish element
		Element finish = XMLdoc.createElement("finish");
		finish.appendChild(doc.createTextNode(finish));
		leg.appendChild(finish);
				
		//mileage element
		Element mileage = XMLdoc.createElement("mileage");
		mileage.appendChild(doc.createTextNode(mileage));
		leg.appendChild(mileage);
	}
	
	public void addLline(double longtitude1, latitude1, longitude2, latitude2){
		
	}
	
	public void addLabel(double a, double b, String c, String d){
		
	}
	
	public void addHeader(String header){
		
	}
	
	public void addFooter(String Footer){
		
	}
	
	public void finalizeTrip(){
		
	}
	
	public static void main(String argv[]) {
		View map = new View();
		map.initializeTrip();
	}
}
